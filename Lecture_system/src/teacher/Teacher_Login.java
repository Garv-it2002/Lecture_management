package teacher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Teacher_Login {
    private static final String Teachers_FILE = "teachers.txt";
    public static void mainn() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
        	System.out.println("==================================================================================================");
            System.out.println("Teacher login");
            System.out.println("==================================================================================================");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (isValidCredentials(username, password)) {
            	System.out.println("==================================================================================================");
                System.out.println("Login successful. Welcome, " + username + "!");
                System.out.println("==================================================================================================");
                success(username);
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

    public static void success(String username) {
    	Scanner scanner = new Scanner(System.in);
    	while (true) {
    		System.out.println(".........................................\nPlease choose how do you want to proceed\n.........................................");
            System.out.println("1. Schedule a class\n-----------------");
            System.out.println("2. Exit\n-----------------");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    writer.enter_period(username);
                    break;
                case 2:
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
        try (BufferedReader reader = new BufferedReader(new FileReader(Teachers_FILE))) {
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
}

class writer {
    public static void enter_period(String Username) {
        String fileName = "Period.txt"; // Replace with your file name
        String name = Username; // Replace with the name you want to search for

        try {
            List<String> unavailableHours = getUnavailableHours(fileName, name);
            List<String> availableHours = getAvailableHours(unavailableHours);
            Set<String> occupiedRooms = getOccupiedRooms(fileName);

            System.out.println("Unavailable hours for " + name + ":");
            for (String hour : unavailableHours) {
                System.out.println(hour);
            }

            if (availableHours.isEmpty()) {
                System.out.println("Sorry, there are no available slots for you at the moment.");
                return;
            }

            System.out.println("Available hours for " + name + ":");
            for (int i = 0; i < availableHours.size(); i++) {
                System.out.println((i + 1) + ". " + availableHours.get(i));
            }

            Scanner scanner = new Scanner(System.in);
            int chosenHourIndex = -1;
            while (chosenHourIndex < 0 || chosenHourIndex >= availableHours.size()) {
                System.out.print("Choose an available hour by entering the list index: ");
                chosenHourIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // Consume the newline character
            }
            String chosenHour = availableHours.get(chosenHourIndex);

            List<String> availableRooms = getAvailableRooms(occupiedRooms, chosenHour);

            if (availableRooms.isEmpty()) {
                System.out.println("Sorry, there are no available rooms for you at the chosen hour.");
                return;
            }

            System.out.println("Available rooms for " + name + " at " + chosenHour + ":");
            for (int i = 0; i < availableRooms.size(); i++) {
                System.out.println((i + 1) + ". " + availableRooms.get(i));
            }

            int chosenRoomIndex = -1;
            while (chosenRoomIndex < 0 || chosenRoomIndex >= availableRooms.size()) {
                System.out.print("Choose a room number from the available rooms by entering the list index: ");
                chosenRoomIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // Consume the newline character
            }
            String chosenRoom = availableRooms.get(chosenRoomIndex);

            System.out.print("Enter the subject: ");
            String subject = scanner.nextLine();

            System.out.print("Enter the semester: ");
            String semester = scanner.nextLine();

            String newEntry = name + "," + semester + "," + subject + "," + chosenHour + "," + chosenRoom + "\n";
            saveEntryToFile(fileName, newEntry);
            System.out.println("Entry saved successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while reading/writing the file: " + e.getMessage());
        }
    }

    private static List<String> getUnavailableHours(String fileName, String name) throws IOException {
        List<String> unavailableHours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                String currentName = details[0].trim();
                String classTiming = details[3].trim();

                if (currentName.equals(name)) {
                    unavailableHours.add(classTiming);
                }
            }
        }

        return unavailableHours;
    }

    private static List<String> getAvailableHours(List<String> unavailableHours) {
        List<String> availableHours = new ArrayList<>();
        Set<String> allHours = generateAllHours();

        for (String hour : allHours) {
            if (!unavailableHours.contains(hour)) {
                availableHours.add(hour);
            }
        }

        return availableHours;
    }

    private static Set<String> generateAllHours() {
        Set<String> allHours = new HashSet<>();
        int startHour = 9;
        int endHour = 13;

        for (int hour = startHour; hour <= endHour; hour++) {
            allHours.add(hour + ":00 AM - " + (hour + 1) + ":00 AM");
        }

        return allHours;
    }

    private static Set<String> getOccupiedRooms(String fileName) throws IOException {
        Set<String> occupiedRooms = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                String classTiming = details[3].trim();
                String roomNumber = details[4].trim();

                occupiedRooms.add(classTiming + ":" + roomNumber);
            }
        }

        return occupiedRooms;
    }

    private static List<String> getAvailableRooms(Set<String> occupiedRooms, String chosenHour) {
        List<String> availableRooms = new ArrayList<>();
        int startRoom = 501;
        int endRoom = 505;

        for (int room = startRoom; room <= endRoom; room++) {
            String timingAndRoom = chosenHour + ":" + room;
            if (!occupiedRooms.contains(timingAndRoom)) {
                availableRooms.add(Integer.toString(room));
            }
        }

        return availableRooms;
    }

    private static void saveEntryToFile(String fileName, String newEntry) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(newEntry);
        }
    }
}