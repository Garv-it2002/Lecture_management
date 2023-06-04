package admin;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class LoginSystem {
	private static final String USERS_FILE = "users.txt";
    private static final String Teachers_FILE = "teachers.txt";

    public static void mainn() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
        	System.out.println("==================================================================================================");
            System.out.println("Admin login");
            System.out.println("==================================================================================================");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (isValidCredentials(username, password)) {
            	System.out.println("==================================================================================================");
                System.out.println("Login successful. Welcome, " + username + "!");
                System.out.println("==================================================================================================");
                success();
                break;
            } else {
            	System.out.println("==================================================================================================");
                System.out.println("Invalid username or password. Please try again.");
                System.out.println("==================================================================================================");
            }
        }

        System.out.println("==================================================================================================\nThank you for using the Hour wise lecture management System. You have been successfully logged out\n==================================================================================================");
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public static void success() {
    	Scanner scanner = new Scanner(System.in);
    	while (true) {
    		System.out.println(".........................................\nPlease choose how do you want to proceed\n.........................................");
            System.out.println("1. Register a teacher\n-----------------");
            System.out.println("2. List of teachers\n-----------------");
            System.out.println("3. Exit\n-----------------");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                	readAndPrintNames();
                    break;
                case 3:
                	System.out.println("==================================================================================================\nThank you for using the Hour wise lecture management System. You have been successfully logged out\n==================================================================================================");
                	try {
    					TimeUnit.SECONDS.sleep(1);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
                	System.exit(0);
                    break;
                default:
                	System.out.println("------------------------------------\nInvalid choice. Please try again.");
                    break;
            }
    }
    }
    
    private static boolean isValidCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user file.");
        }
        return false;
    }
    
    private static void registerUser(Scanner scanner) {
        System.out.println("==== Teacher Registration ====");
        System.out.print("Enter name of teacher: ");
        String username = scanner.nextLine();

        if (userExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Teachers_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            System.out.println("Registration successful.");
        } catch (IOException e) {
            System.out.println("An error occurred while registering the user.");
        }
    }
    private static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Teachers_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user file.");
        }
        return false;
    }
    
    public static void readAndPrintNames(){
    	int g=0;
    	try (BufferedReader reader = new BufferedReader(new FileReader(Teachers_FILE))){
        String line;
        System.out.println("=================================\nThe teachers registered are\n=================================");
        while ((line = reader.readLine()) != null) {
            String[] entry = line.split(",");
            if (entry.length >= 1) {
                String name = entry[0];
                g++;
                System.out.println(g+". "+name+"\n--------------------");
            }
        }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the user file.");
    }
    }
}
