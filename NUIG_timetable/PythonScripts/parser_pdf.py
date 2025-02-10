import pdfplumber
import re
# Path to your PDF file
pdf_path = 'table.pdf'

# Open the PDF file
with pdfplumber.open(pdf_path) as pdf:
    # Iterate through all pages
    for i, page in enumerate(pdf.pages):
        # Extract tables from the page
        tables = page.extract_tables()
        
        # Check if there is a table in the page
        if tables:
            # Assuming there's only one table in the file, print it
            #print(f"Table from page {i + 1}")
            table = tables[0]  # Get the first (and only) table
            #print(table)
            break  # Exit after finding the first table
# Extract semester information
semester_info = table[0][0]
print(semester_info)
course_code = semester_info.split()[0]
print(course_code)
semester = (semester_info.split()[6])
print(semester)
# Initialize an empty list to store the processed data
data = []

# Extract headers
headers = table[2][1:]  # ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']
print(headers)
# Process each time slot
for row in table[3:]:
    time = row[0]
    for i, cell in enumerate(row[1:]):
        if cell:
            day = headers[i]
            # Split the cell content to get course details
            parts = cell.split(':')
            print(parts)
            course_name = parts[0]
            # venue = parts[1] if len(parts) > 1 else ''
            # lecturer = parts[-1] if len(parts) > 2 and '(' in parts[-1] else ''
            module_code = course_name.split()[1]  if'[' in course_name else course_name.split()[0] 
            module_name = course_name.split()[0]+" "+" ".join(course_name.split()[2:]) if'[' in course_name else  " ".join(course_name.split()[1:])
            
            # # Append the extracted data to the list
            # data.append({
            #     'Course Code': course_code,
            #     'Semester': semester,
            #     'Module code': module_code,
            #     'Time': time,
            #     'Day': day,
            #     'Course Name': course_name,
            #     'Lecturer': lecturer,
            #     'Venue': venue
            # })

# Convert the list of dictionaries to a pandas DataFrame

def splitter(cell):
    newline_count = cell.count('/n')
    return newline_count
# Display the DataFrame
# print(data)
