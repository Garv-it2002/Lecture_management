package main;
import admin.LoginSystem;
import teacher.Teacher_Login;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class RegistrationSystem {
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginSystem l=new LoginSystem();
        Teacher_Login t=new Teacher_Login();
        System.out.println("==================================================================================================");
        System.out.println("Welcome to the Hour wise lecture management System");
        System.out.println("==================================================================================================");

        while (true) {
        	System.out.println(".........................................\nPlease choose how do you want to proceed\n.........................................");
            System.out.println("1. Admin\n-----------------");
            System.out.println("2. Teacher\n-----------------");
            System.out.println("3. Student\n-----------------");
            System.out.println("4. Exit\n-----------------");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    l.mainn();
                    break;
                case 2:
                    t.mainn();
                    break;
                case 3:
                	Student.time_table();
                	System.out.println("Press enter any key to exit");
                	String garbage=scanner.next();
                	return;
                case 4:
                    System.out.println("==================================================================================================\nThank you for using the Hour wise lecture management System.\n==================================================================================================");
                    try {
    					TimeUnit.SECONDS.sleep(3);
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
}


class Student {
    public static void time_table() {
    	Scanner sem=new Scanner(System.in);
        String fileName = "Period.txt";
        System.out.println("Hello Student, Please enter your Semester: ");
        String semester = sem.next();

        try {
            Map<String, List<String>> timetable = generateTimetable(fileName, semester);
            System.out.println("==================================================");
            System.out.println("           Timetable for " + "Semester "+semester + "              |");
            System.out.println("==================================================");
            for (String hour : timetable.keySet()) {
            	System.out.println("--------------------------------------------------");
                System.out.println("|            "+hour + "                 |");
                System.out.println("--------------------------------------------------");
                System.out.println("|  Teacher's Name  |   Subject   |  Room Number  |");
                System.out.println("--------------------------------------------------");
                List<String> classes = timetable.get(hour);
                for (String entry : classes) {
                    System.out.println("      " + entry);
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    private static Map<String, List<String>> generateTimetable(String fileName, String semester) throws IOException {
        Map<String, List<String>> timetable = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                String currentSemester = details[1].trim();
                String teacherName = details[0].trim();
                String subject = details[2].trim();
                String classTiming = details[3].trim();
                String roomNumber = details[4].trim();

                if (currentSemester.equals(semester)) {
                    if (!timetable.containsKey(classTiming)) {
                        timetable.put(classTiming, new ArrayList<>());
                    }
                    timetable.get(classTiming).add(teacherName + "\t\t" + subject + "\t\t" + roomNumber + "\n..................................................");
                }
            }
        }

        return timetable;
    }
}
