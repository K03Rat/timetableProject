import os
import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin
import csv
from datetime import datetime

# Base URL of the website
base_url = 'https://schoolmaster.universityofgalway.ie'

# Function to process timetable for a specific cohort
def process_cohort(cohort_id):
    try:
        timetable_url = urljoin(base_url, f'/schoolmaster/cohorts/{cohort_id}')
        response = requests.get(timetable_url)
        soup = BeautifulSoup(response.text, 'html.parser')

        # Find the course name from an <h1> tag
        full_course_name = soup.find('h1').get_text(strip=True)

        # Extract text up to ' - ' if it exists
        delimiter = ' - '
        course_name = full_course_name.split(delimiter)[0] if delimiter in full_course_name else full_course_name

        # Find both tables on the page
        tables = soup.find_all('table', class_='times-long')

        # Prepare a mapping from day names to numeric values for sorting
        day_to_num = {}
        tables_data = []
        semesters = ['1', '2']

        for semester_index, table in enumerate(tables):
            # Remove the first row (header row with days of the week)
            header_row = table.find('tr')
            if header_row:
                days_of_week = [th.get_text(strip=True) for th in header_row.find_all('th', class_='times-long')]
                day_to_num = {day: index for index, day in enumerate(days_of_week)}  # Update mapping for current semester
                header_row.decompose()

            # Extract the remaining rows
            rows = table.find_all('tr')

            # Prepare dataset for current semester
            dataset = []

            # Function to extract module details from a cell
            def extract_module_details(cell):
                details = []
                inner_tables = cell.find_all('table', class_='times-inner')

                for inner_table in inner_tables:
                    inner_rows = inner_table.find_all('tr')

                    for i in range(0, len(inner_rows), 2):  # Iterate in steps of 2
                        module_row = inner_rows[i].find('td', class_='times-m')
                        if module_row:
                            module_name_tag = module_row.find('a')
                            module_name = module_name_tag['title'] if module_name_tag else ''
                            module_code = module_name_tag.get_text(strip=True) if module_name_tag else ''
                            module_link = module_name_tag['href'] if module_name_tag else ''

                            # Extract venue from span title
                            venue_info = module_row.find('span', title=True)
                            venue = venue_info.get_text(strip=True) if venue_info else ''

                            # Extract lecturer
                            lecturer_row = inner_rows[i + 1].find('td', class_='times-v')
                            lecturer = lecturer_row.get_text(strip=True) if lecturer_row else ''

                            details.append({
                                'course_code': course_name,
                                'module_name': module_name,
                                'module_code': module_code,
                                'module_link': urljoin(base_url, module_link) if module_link else '',
                                'lecturer': lecturer,
                                'venue': venue
                            })

                return details

            # Process each row
            for row in rows:
                time_slot = row.find('th', class_='times').get_text(strip=True) if row.find('th', class_='times') else ''
                cells = row.find_all('td', class_='times-long')

                for index, cell in enumerate(cells):
                    if cell.get_text(strip=True):  # Check if the cell has content
                        modules = extract_module_details(cell)
                        for module in modules:
                            dataset.append({
                                'time': time_slot,
                                'day': days_of_week[index] if index < len(days_of_week) else '',
                                'semester': semesters[semester_index],
                                **module
                            })

            tables_data.extend(dataset)

        # Function to convert time into a sortable format
        def time_to_sortable(time_str):
            try:
                return datetime.strptime(time_str, '%H:%M').time()
            except ValueError:
                return datetime.strptime('00:00', '%H:%M').time()

        # Add numeric day and sortable time for sorting
        for entry in tables_data:
            entry['day_num'] = day_to_num.get(entry['day'], -1)
            entry['time'] = time_to_sortable(entry['time'])

        # Add numeric semester for sorting
        semester_to_num = {sem: index for index, sem in enumerate(semesters)}
        for entry in tables_data:
            entry['semester_num'] = semester_to_num.get(entry['semester'], -1)

        # Sort by semester number, then by day number, and then by time
        sorted_dataset = sorted(tables_data, key=lambda x: (x['semester_num'], x['day_num'], x['time']))

        save_dir = r'C:\Users\krist\NUIG_timetable\timetables'
        os.makedirs(save_dir, exist_ok=True)

        # Construct the full file path for the CSV file
        csv_file = os.path.join(save_dir, f'{course_name}.csv')

        # Write to CSV file
        with open(csv_file, 'w', newline='', encoding='cp1252') as csvfile:
            fieldnames = ['course_code', 'semester', 'module_code', 'module_name', 'time', 'day', 'lecturer', 'venue']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            writer.writeheader()
            for data in sorted_dataset:
                writer.writerow({key: data[key] for key in fieldnames})

        print(f"Data for cohort {cohort_id} sorted and saved to {csv_file}")
    except Exception as e:
        print(f"Skipping cohort {cohort_id} due to error: {e}")

# Process cohorts from 1 to 64 and cohort 99 specifically
for cohort_id in list(range(1, 1000)) + [99]:
    process_cohort(cohort_id)
