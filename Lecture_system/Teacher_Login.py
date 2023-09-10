import time
import csv

class Teacher_Login:
    Teachers_FILE = "teachers.csv"

    @staticmethod
    def mainn():
        while True:
            print("==================================================================================================")
            print("Teacher login")
            print("==================================================================================================")
            username = input("Enter your username: ")
            password = input("Enter your password: ")

            if Teacher_Login.is_valid_credentials(username, password):
                print("==================================================================================================")
                print("Login successful. Welcome, " + username + "!")
                print("==================================================================================================")
                Teacher_Login.success(username)
                break
            else:
                print("==================================================================================================")
                print("Invalid username or password. Please try again.")
                print("==================================================================================================")

        print("==================================================================================================\nThank you for using the Hour wise lecture management System. You have been successfully logged out\n==================================================================================================")
        time.sleep(1)

    @staticmethod
    def success(username):
        while True:
            print(".........................................\nPlease choose how do you want to proceed\n.........................................")
            print("1. Schedule a class\n-----------------")
            print("2. Exit\n-----------------")
            choice = int(input("Please choose an option: "))

            if choice == 1:
                writer.enter_period(username)
            elif choice == 2:
                print("==================================================================================================\nThank you for using the Hour wise lecture management System. You have been successfully logged out\n==================================================================================================")
                time.sleep(1)
                exit(0)
            else:
                print("------------------------------------\nInvalid choice. Please try again.")

    @staticmethod
    def is_valid_credentials(username, password):
        try:
            with open(Teacher_Login.Teachers_FILE, 'r') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Skip the header row
                for row in csvreader:
                    file_username, file_password = map(str.strip, row)
                    if file_username == username and file_password == password:
                        return True
        except IOError:
            print("An error occurred while reading the user file.")
        return False

class writer:
    @staticmethod
    def enter_period(username):
        fileName = "Period.csv"  # Replace with your file name
        name = username  # Replace with the name you want to search for

        try:
            unavailable_hours = writer.get_unavailable_hours(fileName, name)
            available_hours = writer.get_available_hours(unavailable_hours)
            occupied_rooms = writer.get_occupied_rooms(fileName)

            print("Unavailable hours for " + name + ":")
            for hour in unavailable_hours:
                print(hour)

            if not available_hours:
                print("Sorry, there are no available slots for you at the moment.")
                return

            print("Available hours for " + name + ":")
            for i, hour in enumerate(available_hours, start=1):
                print(f"{i}. {hour}")

            chosen_hour_index = -1
            while chosen_hour_index < 0 or chosen_hour_index >= len(available_hours):
                chosen_hour_index = int(input("Choose an available hour by entering the list index: ")) - 1

            chosen_hour = available_hours[chosen_hour_index]

            available_rooms = writer.get_available_rooms(occupied_rooms, chosen_hour)

            if not available_rooms:
                print("Sorry, there are no available rooms for you at the chosen hour.")
                return

            print("Available rooms for " + name + " at " + chosen_hour + ":")
            for i, room in enumerate(available_rooms, start=1):
                print(f"{i}. {room}")

            chosen_room_index = -1
            while chosen_room_index < 0 or chosen_room_index >= len(available_rooms):
                chosen_room_index = int(input("Choose a room number from the available rooms by entering the list index: ")) - 1

            chosen_room = available_rooms[chosen_room_index]

            subject = input("Enter the subject: ")
            semester = input("Enter the semester: ")

            new_entry = f"{name},{semester},{subject},{chosen_hour},{chosen_room}\n"
            writer.save_entry_to_file(fileName, new_entry)
            print("Entry saved successfully!")

        except IOError as e:
            print(f"An error occurred while reading/writing the file: {str(e)}")

    @staticmethod
    def get_unavailable_hours(file_name, name):
        unavailable_hours = []

        try:
            with open(file_name, 'r') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Skip the header row
                for row in csvreader:
                    if len(row) >= 5:
                        current_name, _, _, class_timing, _ = map(str.strip, row)
                        if current_name == name:
                            unavailable_hours.append(class_timing)
        except IOError:
            print("An error occurred while reading the file.")

        return unavailable_hours



    @staticmethod
    def get_available_hours(unavailable_hours):
        all_hours = writer.generate_all_hours()
        available_hours = [hour for hour in all_hours if hour not in unavailable_hours]
        return available_hours

    @staticmethod
    def generate_all_hours():
        start_hour = 9
        end_hour = 13
        all_hours = [f"{hour}:00 AM - {hour + 1}:00 AM" for hour in range(start_hour, end_hour)]
        return all_hours

    @staticmethod
    def get_occupied_rooms(file_name):
        occupied_rooms = set()

        try:
            with open(file_name, 'r') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Skip the header row
                for row in csvreader:
                    if len(row) >= 5:
                        _, _, _, class_timing, room_number = map(str.strip, row)
                        occupied_rooms.add(f"{class_timing}:{room_number}")
        except IOError:
            print("An error occurred while reading the file.")
        return occupied_rooms


    @staticmethod
    def get_available_rooms(occupied_rooms, chosen_hour):
        start_room = 501
        end_room = 505
        available_rooms = [str(room) for room in range(start_room, end_room + 1) if f"{chosen_hour}:{room}" not in occupied_rooms]
        return available_rooms

    @staticmethod
    def save_entry_to_file(file_name, new_entry):
        try:
            with open(file_name, 'a', newline='') as csvfile:
                csvfile.write(new_entry)
        except IOError:
            print("An error occurred while writing to the file.")
