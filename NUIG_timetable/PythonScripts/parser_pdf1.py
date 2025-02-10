import requests
from bs4 import BeautifulSoup
import os
import pdfplumber
import re
import pandas as pd

webpage_url = 'https://www.universityofgalway.ie/science-engineering/school-of-computer-science/currentstudents/timetables/'

# Directory to save PDFs
save_dir = r'C:\Users\krist\NUIG_timetable\timetables'
os.makedirs(save_dir, exist_ok=True)

# List to keep track of the files downloaded in the current session
downloaded_files = []

# Function to delete the files downloaded during the current session
def delete_downloaded_files(files_list):
    for file_path in files_list:
        try:
            if os.path.isfile(file_path):
                os.remove(file_path)
                print(f"Deleted: {file_path}")
        except Exception as e:
            print(f"Failed to delete {file_path}: {e}")

# Function to handle cells with multiple modules
def handle_special_cell(cell, day, time, course_code, semester, data):
    parts = re.split(r'(?=\[[A-Z]\])', cell)
    for part in parts:
        if part.strip():
            module_code_match = re.search(r'\[([A-Z])\]', part)
            module_code = module_code_match.group(1) if module_code_match else ''
            
            # Remove the module code from the part to process the rest
            part = re.sub(r'\[[A-Z]\]\s*', '', part).strip()
           
            # Extract the full module name and venue
            details = part.split(':')
            full_module_name = details[0].strip() if len(details) > 0 else ''
            venue = details[-1].replace('\n', ' ') if len(details) > 1 else ''
            
            # Extract module ID and module name from the full module name
            module_code_name = re.search(r'([A-Z]+\d+)\s+(.+)', full_module_name)
            module_id = module_code_name.group(1) if module_code_name else ''
            module_name = module_code_name.group(2) if module_code_name else ''
            
            # Append the extracted data to the list
            data.append({
                'course_code': course_code,
                'semester': semester,
                'module_code': module_id,
                'module_name': module_name,
                'time': time,
                'day': day,
                'venue': venue
            })

# Send request to the webpage
response = requests.get(webpage_url)

if response.status_code == 200:
    try:
        # Parse the HTML content using BeautifulSoup
        soup = BeautifulSoup(response.content, 'html.parser')

        # Find all tables in the HTML
        timetables = soup.find_all('table')

        # Assuming you want the first table (index 0)
        if timetables:
            first_table = timetables[0]

            # Extract all the links within this first table
            first_semester_links = first_table.find_all('a', href=True)

            # Base URL for constructing absolute URLs
            base_url = "https://www.universityofgalway.ie"

            # Process each link
            for link in first_semester_links:
                href = link['href']
                # Check if the link is a PDF
                if href.endswith('.pdf'):
                    # Construct full URL
                    full_url = href if href.startswith('http') else base_url + href
                    # Download the PDF
                    pdf_response = requests.get(full_url)
                    if pdf_response.status_code == 200:
                        filename = os.path.basename(href)
                        pdf_path = os.path.join(save_dir, filename)
                        with open(pdf_path, 'wb') as file:
                            file.write(pdf_response.content)
                        downloaded_files.append(pdf_path)  # Track the downloaded file
                        print(f"Downloaded: {pdf_path}")
                    else:
                        raise Exception(f"Failed to download: {full_url}")

            # If all files are downloaded successfully, continue with processing
            for filename in os.listdir(save_dir):
                pdf_path = os.path.join(save_dir, filename)
                if filename.lower().endswith('.pdf') and pdf_path in downloaded_files:
                    # Open the PDF file and process it
                    with pdfplumber.open(pdf_path) as pdf:
                        # Iterate through all pages
                        for i, page in enumerate(pdf.pages):
                            # Extract tables from the page
                            tables = page.extract_tables()
                            
                            # Check if there is a table in the page
                            if tables:
                                # Assuming there's only one table in the file
                                table = tables[0]  # Get the first (and only) table
                                break  # Exit after finding the first table

                    # Extract semester information
                    semester_info = table[0][0].split()
                    course_code = semester_info[0]
                    semester = semester_info[6] if len(semester_info) > 2 else ''

                    # Initialize an empty list to store the processed data
                    data = []
                    day_to_num = {}
                    # Extract headers
                    headers = table[2][1:]  # ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']
                    # Process each time slot
                    for row in table[3:]:
                        time = row[0]
                        for i, cell in enumerate(row[1:]):
                            if cell:
                                day = headers[i]
                                if '[' in cell:
                                    handle_special_cell(cell, day, time, course_code, semester, data)
                                else:
                                    parts = cell.split(':')
                                    module_info = parts[0]
                                    module_code = module_info.split()[0]
                                    module_name = " ".join(module_info.split()[1:])
                                    venue_and_lecturer_info = parts[1].split('\n')
                                    if venue_and_lecturer_info and venue_and_lecturer_info[0] == '':
                                        venue_and_lecturer_info = venue_and_lecturer_info[1:] 
                                    print(venue_and_lecturer_info)
                                    venue = " ".join(venue_and_lecturer_info[0:-1]) if len(venue_and_lecturer_info) > 2 else venue_and_lecturer_info[0]
                                    lecturer = venue_and_lecturer_info[-1] if len(venue_and_lecturer_info) > 1 else ''
                                    data.append({
                                        'course_code': course_code,
                                        'semester': semester,
                                        'module_code': module_code,
                                        'module_name': module_name,
                                        'time': time,
                                        'day': day,
                                        'lecturer': lecturer,
                                        'venue': venue
                                    })
                    # Convert the list of dictionaries to a pandas DataFrame
                    df = pd.DataFrame(data)

                    # Define a custom sorting order for days of the week
                    day_order = {'Monday': 1, 'Tuesday': 2, 'Wednesday': 3, 'Thursday': 4, 'Friday': 5}

                    # Add numeric day and sortable time for sorting
                    df['DayOrder'] = df['day'].map(day_order)
                    df['time'] = pd.to_datetime(df['time'], format='%I:%M %p').dt.time

                    # Sort the DataFrame by day and time
                    df = df.sort_values(by=['DayOrder', 'time']).drop(columns=['DayOrder'])

                    # Generate the CSV file name using the course code
                    csv_file_name = f"{course_code}.csv"
                    csv_file_path = os.path.join(save_dir, csv_file_name)

                    # Save the sorted DataFrame to a CSV file
                    df.to_csv(csv_file_path, index=False, encoding = 'cp1252')
                    os.remove(pdf_path)
                    print(f"Deleted processed PDF file: {pdf_path}")

    except Exception as e:
        print(f"An error occurred: {e}")
        # If an error occurs, delete only the files downloaded in this session
        delete_downloaded_files(downloaded_files)

else:
    print(f"Failed to retrieve webpage: {webpage_url}")
