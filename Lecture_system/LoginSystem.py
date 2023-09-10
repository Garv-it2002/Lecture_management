import time
import csv

class LoginSystem:
    USERS_FILE = "users.csv"
    Teachers_FILE = "teachers.csv"

    @staticmethod
    def mainn():
        while True:
            print("==================================================================================================")
            print("Admin login")
            print("==================================================================================================")
            username = input("Enter your username: ")
            password = input("Enter your password: ")

            if LoginSystem.is_valid_credentials(username, password):
                print("==================================================================================================")
                print("Login successful. Welcome, " + username + "!")
                print("==================================================================================================")
                LoginSystem.success()
                break
            else:
                print("==================================================================================================")
                print("Invalid username or password. Please try again.")
                print("==================================================================================================")

        print("==================================================================================================\nThank you for using the Hour wise lecture management System. You have been successfully logged out\n==================================================================================================")
        time.sleep(1)

    @staticmethod
    def success():
        while True:
            print(".........................................\nPlease choose how do you want to proceed\n.........................................")
            print("1. Register a teacher\n-----------------")
            print("2. List of teachers\n-----------------")
            print("3. Exit\n-----------------")
            choice = int(input("Please choose an option: "))

            if choice == 1:
                LoginSystem.register_user()
            elif choice == 2:
                LoginSystem.read_and_print_names()
            elif choice == 3:
                print("==================================================================================================\nThank you for using the Hour wise lecture management System. You have been successfully logged out\n==================================================================================================")
                time.sleep(1)
                exit(0)
            else:
                print("------------------------------------\nInvalid choice. Please try again.")

    @staticmethod
    def is_valid_credentials(username, password):
        try:
            with open(LoginSystem.USERS_FILE, 'r') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Skip the header row
                for row in csvreader:
                    file_username, file_password = map(str.strip, row)
                    if file_username == username and file_password == password:
                        return True
        except IOError:
            print("An error occurred while reading the user file.")
        return False

    @staticmethod
    def register_user():
        print("==== Teacher Registration ====")
        username = input("Enter name of teacher: ")

        if LoginSystem.user_exists(username):
            print("Username already exists. Please choose a different username.")
            return

        password = input("Enter a password: ")

        try:
            with open(LoginSystem.Teachers_FILE, 'a', newline='') as csvfile:
                csvwriter = csv.writer(csvfile)
                csvwriter.writerow([username, password])
                print("Registration successful.")
        except IOError:
            print("An error occurred while registering the user.")

    @staticmethod
    def user_exists(username):
        try:
            with open(LoginSystem.Teachers_FILE, 'r') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Skip the header row
                for row in csvreader:
                    file_username, _ = map(str.strip, row)
                    if file_username == username:
                        return True
        except IOError:
            print("An error occurred while reading the user file.")
        return False

    @staticmethod
    def read_and_print_names():
        g = 0
        try:
            with open(LoginSystem.Teachers_FILE, 'r') as csvfile:
                csvreader = csv.reader(csvfile)
                next(csvreader)  # Skip the header row
                print("=================================\nThe teachers registered are\n=================================")
                for g, row in enumerate(csvreader, start=1):
                    name, _ = map(str.strip, row)
                    print(f"{g}. {name}\n--------------------")
        except IOError:
            print("An error occurred while reading the user file.")


if __name__ == "__main__":
    LoginSystem.mainn()
