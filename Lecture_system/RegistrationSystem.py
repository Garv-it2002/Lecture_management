import time
import csv
from Teacher_Login import Teacher_Login
from LoginSystem import LoginSystem

class RegistrationSystem:
    @staticmethod
    def main():
        print("==================================================================================================")
        print("Welcome to the Hour wise lecture management System")
        print("==================================================================================================")

        while True:
            print(".........................................\nPlease choose how do you want to proceed\n.........................................")
            print("1. Admin\n-----------------")
            print("2. Teacher\n-----------------")
            print("3. Student\n-----------------")
            print("4. Exit\n-----------------")
            choice = int(input("Please choose an option: "))

            if choice == 1:
                LoginSystem.mainn()
            elif choice == 2:
                Teacher_Login.mainn()
            elif choice == 3:
                RegistrationSystem.time_table()
                input("Press enter any key to exit")
                return
            elif choice == 4:
                print("==================================================================================================\nThank you for using the Hour wise lecture management System.\n==================================================================================================")
                time.sleep(3)
                exit(0)
            else:
                print("------------------------------------\nInvalid choice. Please try again.")

    @staticmethod
    def time_table():
        fileName = "Period.csv"  # Replace with your CSV file name
        semester = input("Hello Student, Please enter your Semester: ")

        try:
            timetable = RegistrationSystem.generate_timetable(fileName, semester)
            print("==================================================")
            print(f"           Timetable for Semester {semester}              |")
            print("==================================================")

            for hour, classes in timetable.items():
                print("--------------------------------------------------")
                print(f"|            {hour}                 |")
                print("--------------------------------------------------")
                print("|  Teacher's Name  |   Subject   |  Room Number  |")
                print("--------------------------------------------------")

                for entry in classes:
                    print("      " + entry)

                print()

        except Exception as e:
            print(f"An error occurred while reading the file: {str(e)}")

    @staticmethod
    def generate_timetable(fileName, semester):
        timetable = {}

        with open(fileName, 'r') as csvfile:
            csvreader = csv.reader(csvfile)
            next(csvreader)  # Skip the header row
            for row in csvreader:
                teacher_name, current_semester, subject, class_timing, room_number = map(str.strip, row)

                if current_semester == semester:
                    if class_timing not in timetable:
                        timetable[class_timing] = []

                    timetable[class_timing].append(f"{teacher_name}\t\t{subject}\t\t{room_number}\n..................................................")

        return timetable

if __name__ == "__main__":
    RegistrationSystem.main()