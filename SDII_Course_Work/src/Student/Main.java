package Student;


import java.io.*;
import java.util.*;

public class Main{
    static final int MAXIMUM_SEATS= 100;//maximum number of students
    static Student[] new_students = new Student[MAXIMUM_SEATS];//Array to store students
    static int studentCount = 0;//count number of students
    static final String FILENAME = "students.txt"; // store students details for text file

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);//Scanner for user input
        int choice;

        loadStudentDetails();//load student data from file
        do {
            displayMenu();// display menuoptions
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                displayMenu();
            }
            choice = scanner.nextInt();//get user choice
            scanner.nextLine();//consume newline character

            //execute choosen option
            switch (choice) {
                case 1 -> checkAvailableSeats();//check available seats
                case 2 -> registerStudent(scanner);//register new student
                case 3 -> deleteStudent(scanner);//delete a student
                case 4 -> findStudent(scanner);//find student
                case 5 -> storeStudentDetails();//store student details  to file
                case 6 -> loadStudentDetails();//load student details
                case 7 -> viewStudents();//view list of registered students
                case 8 -> additionalOptions(scanner);//additional options
                case 0 -> System.out.println("Exit");//exit the program
                default -> System.out.println("Invalid choice. Try again.");// invalid manu choice
            }
        } while (choice != 0);//this loop continue until user chooses to exit
        storeStudentDetails(); //  stored data before exiting
        scanner.close();
    }

    private static void displayMenu() {
        //display menu
        System.out.println("Menu:");
        System.out.println("1. Check Available Seats");
        System.out.println("2. Register Student");
        System.out.println("3. Delete Student");
        System.out.println("4. Find Student");
        System.out.println("5. Store Student Details");
        System.out.println("6. Load Student Details");
        System.out.println("7. View Student List");
        System.out.println("8. additionalOptions");
        System.out.println("0. Exit");
        System.out.print("Select your choice: ");
    }


    static void checkAvailableSeats() {
        //check  available seat for students
        System.out.println("Available seats: " + (MAXIMUM_SEATS - studentCount));
    }

    static void registerStudent(Scanner scanner) {
        // Register a new student
        if (studentCount >= MAXIMUM_SEATS) {
            System.out.println("No available seats.");
            return;
        }
        //prompt user for student ID
        String id;
        while (true) {
            System.out.print("Enter Student ID (8 characters, must be in the format w1234567): ");
            id = scanner.nextLine();
            if (!isValidStudentId(id) || studentExists(id)) {
                System.out.println("Invalid ID. Please try again.");
            } else {
                break;
            }
        }
        //prompt user for student name
        String name;
        while (true) {
            System.out.print("Enter a student name: ");
            name = scanner.nextLine();
            if (!isValidStudentName(name)) {
                System.out.println("Invalid name. Please enter only English letters.");
            } else {
                break;
            }
        }
        //create a new student object and add it to the students array
        Student student = new Student(id, name);
        new_students[studentCount++] = student; // Add student to the array and increment count
        System.out.println("Student registered successfully.");
    }

    static boolean isValidStudentId(String id) {
        //valid student Id format(w1234567)
        return id.matches("w\\d{7}");
    }

    static boolean isValidStudentName(String name) {
        //valid student name format(english letters only)
        return name.matches("[a-zA-Z]+");
    }

    static boolean studentExists(String id) {
        //  check if a student with the givenID already exists
        for (int i = 0; i < studentCount; i++) {
            if (new_students[i].getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    static void deleteStudent(Scanner scanner) {
        //Delete a student using the ID number specified.
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (new_students[i].getId().equals(id)) {
                new_students[i] = new_students[studentCount - 1];//replace with last student
                new_students[--studentCount] = null;//Remove the last students
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void findStudent(Scanner scanner) {
        // find the student information by the student id
        System.out.print("Enter Student ID to find: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (new_students[i].getId().equals(id)) {
                System.out.println("Student found: " + new_students[i]);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void storeStudentDetails() {
        //Save students to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (int i = 0; i < studentCount; i++) {
                Student student = new_students[i];
                writer.println(student.getId() + "," + student.getName() + "," +
                        student.getModule1Mark() + "," + student.getModule2Mark() + "," + student.getModule3Mark()+","+student.getAverage()+","+student.getGrade() );
            }
            System.out.println("Student details stored in " + FILENAME);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    static void loadStudentDetails() {
        //load student details from file
        File file = new File(FILENAME);
        if (!file.exists()) {
            //if the file does not exit print a message and return
            System.out.println("No student data file found.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String id = parts[0];
                    //Get student ID,name,add marks from the [arts
                    String name = parts[1];

                    int module1Mark = Integer.parseInt(parts[2]);
                    int module2Mark = Integer.parseInt(parts[3]);
                    int module3Mark = Integer.parseInt(parts[4]);
                    double average = Double.parseDouble(parts[5]);
                    String grade = parts[6];
                    // create a new student with the ID and name
                    Student student = new Student(id, name);
                    student.setModuleMarks(module1Mark, module2Mark, module3Mark);//set the student's marks
                    new_students[studentCount++] = student;//Add the student to the list and increase the count
                }
            }

            System.out.println("Student details loaded successfully.");
        } catch (IOException e) {
            // print n error message if something gone wrong
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }

    static void viewStudents() {
        //view and display the list of registered students
        ByName(new_students, studentCount);//sort students by name
        for (int i = 0; i < studentCount; i++) {
            System.out.println(new_students[i]);
        }
    }

    static void additionalOptions(Scanner scanner) {
        //additional option menu
        System.out.println("a. Add student name");
        System.out.println("b. Add module marks");
        System.out.println("c. Generate summary report");
        System.out.println("d. Generate complete report");
        System.out.print("Select your choice: ");
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
        switch (choice) {
            case 'a' -> addStudentName(scanner);//update student name
            case 'b' -> addModuleMarks(scanner);//add student module marks
            case 'c' -> generateSummaryReport();//show how many students pass each module
            case 'd' -> generateCompleteReport();//show full information with marks
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
// if user want to update student name
    static void addStudentName(Scanner scanner) {

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (new_students[i].getId().equals(id)) {
                System.out.print("Enter Student Name: ");
                new_students[i].setName(scanner.nextLine());
                System.out.println("Student name updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }
// add student module marks
    static void addModuleMarks(Scanner scanner) {
        //update  module marks
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (new_students[i].getId().equals(id)) {
                System.out.print("Enter marks for Module 1: ");
                int mark1 = scanner.nextInt();
                System.out.print("Enter marks for Module 2: ");
                int mark2 = scanner.nextInt();
                System.out.print("Enter marks for Module 3: ");
                int mark3 = scanner.nextInt();
                if (isValidMark(mark1) && isValidMark(mark2) && isValidMark(mark3)) {
                    new_students[i].setModuleMarks(mark1, mark2, mark3);
                    System.out.println("Module marks updated successfully.");
                } else {
                    System.out.println("Invalid marks. Please enter marks between 0 and 100.");
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static boolean isValidMark(int mark) {
        //check if marks is valid(0-100)
        return mark >= 0 && mark <= 100;
    }
// give summary for marks
    static void generateSummaryReport() {
        //generate summary report
        int passCount1 = 0, passCount2 = 0, passCount3 = 0;
        for (int i = 0; i < studentCount; i++) {
            if (new_students[i].getModule1Mark() > 40) passCount1++;//if module marks<40: 0
            if (new_students[i].getModule2Mark() > 40) passCount2++;
            if (new_students[i].getModule3Mark() > 40) passCount3++;
        }
        System.out.println("Total student registrations: " + studentCount);
        System.out.println("Students scoring more than 40 marks in Module 1: " + passCount1);
        System.out.println("Students scoring more than 40 marks in Module 2: " + passCount2);
        System.out.println("Students scoring more than 40 marks in Module 3: " + passCount3);
    }

    static void generateCompleteReport() {
        //generate complete detailed report
        ByName(new_students, studentCount);
        for (int i = 0; i < studentCount; i++) {
            System.out.println(new_students[i].generateReport());
        }
    }

    static void ByName(Student[] students, int n) {
        //sort by name
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (students[j].getName().compareTo(students[j+1].getName()) > 0) {

                    Student temp = students[j];
                    students[j] = students[j+1];
                    students[j+1] = temp;
                }
            }
        }
    }
}
