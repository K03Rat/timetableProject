import psycopg2
from psycopg2 import sql
import os
import csv
from datetime import datetime

def create_database_and_insert_data(dbname, user, password, host='localhost', port='5432'):
    """
    Creates a new PostgreSQL database and a table within it, if they do not already exist.
    Then reads CSV files from the 'timetables' folder and inserts their values into the 'timetables' table.
    """
    # Connection parameters for the default database
    default_db_params = {
        'dbname': 'postgres',
        'user': user,
        'password': password,
        'host': host,
        'port': port
    }

    # Connection parameters for the new database
    new_db_params = {
        'dbname': dbname,
        'user': user,
        'password': password,
        'host': host,
        'port': port
    }

    try:
        # Connect to the default database
        conn = psycopg2.connect(**default_db_params)
        cur = conn.cursor()

        # Check if the database already exists
        cur.execute("SELECT 1 FROM pg_catalog.pg_database WHERE datname = %s", (dbname,))
        exists = cur.fetchone()
        if exists:
            print(f"Database '{dbname}' already exists.")
        else:
            # Create the new database
            cur.execute(sql.SQL("CREATE DATABASE {}").format(sql.Identifier(dbname)))
            print(f"Database '{dbname}' created successfully.")

        # Close the connection to the default database
        cur.close()
        conn.close()

        # Connect to the new database
        conn = psycopg2.connect(**new_db_params)
        cur = conn.cursor()

        # Create the table within the new database, only if it does not already exist
        create_table_query = """
        CREATE TABLE IF NOT EXISTS timetables (
            id SERIAL PRIMARY KEY,
            course_code VARCHAR(255),
            semester VARCHAR(10),
            module_code VARCHAR(10),
            module_name VARCHAR(255),
            time TIME,
            day VARCHAR(15),
            lecturer VARCHAR(255),
            venue VARCHAR(255)
        );
        """
        cur.execute(create_table_query)
        print(f"Table 'timetables' is ready in database '{dbname}'.")

        # Read CSV files from the 'timetables' folder and insert values
        csv_folder = 'timetables'
        for filename in os.listdir(csv_folder):
            if filename.endswith('.csv'):
                print(f"Processing file: {filename}")
                with open(os.path.join(csv_folder, filename), 'r', encoding='cp1252') as csvfile:
                    reader = csv.DictReader(csvfile)
                    for row in reader:
                        try:
                            # Convert time string to PostgreSQL time format
                            time_str = row['time']
                            time_obj = datetime.strptime(time_str, '%H:%M:%S').time()
                            
                            # Insert values into the table
                            cur.execute("""
                                INSERT INTO timetables (course_code, semester, module_code, module_name, time, day, lecturer, venue)
                                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                            """, (
                                row['course_code'],
                                row['semester'],
                                row['module_code'],
                                row['module_name'],
                                time_obj,
                                row['day'],
                                row['lecturer'] or None,
                                row['venue'].strip()  # Remove leading/trailing spaces
                            ))
                            conn.commit()
                        except Exception as e:
                            print(f"Error inserting row from {filename}: {str(e)}")

        # Close the cursor and connection
        cur.close()
        conn.close()

    except Exception as e:
        print(f"Error: {e}")

# Usage
create_database_and_insert_data(
    dbname="uog_timetables",
    user="postgres",
    password="",
    host="localhost",
    port="5432"
)
