package services;

import io.StudentIO;
import io.StudentTableModel;
import io.Utils;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StudentService {
    static Scanner sc = new Scanner(System.in);
    public static Random random = new Random();
    static List<Student> students = StudentIO.getAll();
    private static final int rowSize = 4;
    private static int currentPage = 1;

    public static void addNewData() {
        System.out.println(".........................");
        System.out.print("[+] Insert student's name: ");
        String name = sc.nextLine();
        System.out.println("[+] Student date of birth");
        System.out.print("> Year (number): ");
        int year = Integer.parseInt(sc.nextLine());
        System.out.print("> Month (number): ");
        int month = Integer.parseInt((sc.nextLine()));
        System.out.print("> Day (number): ");
        int day = Integer.parseInt(sc.nextLine());
        System.out.print("[+] Insert student's classroom: ");
        String classroom = sc.nextLine();
        System.out.print("[+] Insert student's subject: ");
        String subject = sc.nextLine();

        Student student = new Student();
        student.setId(String.valueOf(random.nextInt(100000)));
        student.setName(name);
        student.setDateOfBirth(LocalDate.of(year, month, day).toString());
        student.setClassroom(classroom);
        student.setSubject(subject);

        StudentIO.add(student);
    }

    public static void getAllData() {

        if (students == null) {
            System.out.println("Unable to get students data");
        }

        int currentPage = 1;
        int totalPages = (int)Math.ceil((double)students.size() / rowSize);
        int totalRecords = students.size();
        while (true) {
            int startIndex = (currentPage -1) * rowSize;
            int endIndex = Math.min(startIndex + rowSize, students.size());
            List<Student> pageStudents = students.subList(startIndex, endIndex);

            System.out.println();
            StudentTableModel.renderStudentsToTable(pageStudents, rowSize, currentPage, totalPages, totalRecords);
            StudentTableModel.renderPagination();

            System.out.print("Enter the option(pagination): ");
            String pageOption = new Scanner(System.in).nextLine();
            if (pageOption.equalsIgnoreCase("p")) {
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    System.out.println("You're already on the first page.");
                }
            } else if (pageOption.equalsIgnoreCase("n")){
                if (currentPage < totalPages) {
                    currentPage++;
                } else {
                    System.out.println("You're already on the last page.");
                }
            } else if (pageOption.equalsIgnoreCase("f")) {
                currentPage = 1;
            } else if (pageOption.equalsIgnoreCase("l")) {
                currentPage = totalPages;
            } else if (pageOption.equalsIgnoreCase("b")) {
                return;
            } else {
                try {
                    int pageNumber = Integer.parseInt(pageOption);
                    if (pageNumber >= 1 && pageNumber <= totalPages) {
                        currentPage = pageNumber;
                    } else {
                        System.out.println("Invalid page number. Please enter a number between 1 and " + totalPages);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                }
            }
        }
    }

    public static void commitDataToFile(String originalFile, String destinationFile) {
        Utils.exchangeData(originalFile, destinationFile);
        Utils.clearTransactionFile(originalFile, "");
    }

    public static void updateDataById() {
        System.out.println("---------------------------------------");
        System.out.println("[+] Updating student's information:");
        System.out.println("---------------------------------------");
        System.out.print("Insert student's Id: ");
        String id = sc.nextLine();

        Student student = StudentIO.get(id);

        if (student == null) {
            System.out.println("Student " + id + " was not found.");
        } else {
            Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 20, 20);
            }
            // header table
            table.addCell("Id");
            table.addCell("Student Name");
            table.addCell("Date Of Birth");
            table.addCell("Classroom");
            table.addCell("Subject");

            for (Student s : students) {
                if (s.getId().equals(id)) {
                    table.addCell(student.getId());
                    table.addCell(student.getName());
                    table.addCell(student.getDateOfBirth());
                    table.addCell(student.getClassroom());
                    table.addCell(student.getSubject());

                    System.out.println(table.render());

                    System.out.println("---------------------------------------");
                    System.out.println("[*] Update student's information");
                    System.out.println("---------------------------------------");
                    System.out.println("1. Update student's name");
                    System.out.println("2. Update student's date of birth");
                    System.out.println("3. Update student's classroom");
                    System.out.println("4. Update student's subject");
                    System.out.println(">>Input b to Back to menu");
                    System.out.println("---------------------------------------");
                    System.out.print("Enter the option(edit): ");
                    String option = sc.nextLine();
                    try {
                        switch (option) {
                            case "1" -> {
                                System.out.print("[+] Insert new student's name: ");
                                String name = sc.nextLine();
                                s.setName(name);
                                StudentIO.saveAll();
                            }
                            case "2" -> {
                                System.out.print("[+] Insert new student's dob(yyyy-mm-dd): ");
                                String dob = sc.nextLine();
                                s.setDateOfBirth(dob);
                                StudentIO.saveAll();
                            }
                            case "3" -> {
                                System.out.print("[+] Insert new student's classroom: ");
                                String classroom = sc.nextLine();
                                s.setClassroom(classroom);
                                StudentIO.saveAll();
                            }
                            case "4" -> {
                                System.out.print("[+] Insert new student's subject: ");
                                String subject = sc.nextLine();
                                s.setSubject(subject);
                                StudentIO.saveAll();
                            }
                            case "b" -> {
                                return;
                            }
                            default -> System.out.println("Invalid input!");
                        }

                    } catch (Exception e) {
                        System.out.println("Error! found!");
                    }

                }
            }
        }
    }

    public static void deleteData() {
        System.out.println(".........................");
        System.out.println("[*] Delete student's by Id: ");
        System.out.print("Insert student's Id: ");
        String id = sc.nextLine();

//        Student student = StudentIO.get(id);
//        String choice = "";
//        if (student == null) {
//            System.out.println("Error!, Unable to get student.");
//        } else {
//            System.out.print("Are you sure want to delete?(Y/N): ");
//            choice = new Scanner(System.in).nextLine();
//            if (choice.equalsIgnoreCase("y")) {
//                StudentIO.delete(student);
//                System.out.println("Student's name " + student.getName() + " was deleted.");
//            } else {
//                System.out.println("Delete was cancel.");
//            }
//        }
        for (Student s : students) {
            if (s.getId().equals(id)) {
                StudentIO.delete(s);
                System.out.println(s.getName() + " was deleted.");
                return;
            }
        }
        System.out.println("Student " + id + " was not found!");
    }

    public static void generateDataIntoFile() {
        System.out.print("Enter number of student: ");
        int studentCount = new Scanner(System.in).nextInt();
        for (int i = 0; i < studentCount; i++) {
            Student student = new Student();
            student.setId(String.valueOf(random.nextInt(10000)));
            student.setName("Student" + (i+1));
            student.setDateOfBirth(LocalDate.of(1990 + random.nextInt(30),
                    1 + random.nextInt(11), 1 + random.nextInt(28)).toString());
            student.setClassroom("A" + (1 + random.nextInt(10)));
            student.setSubject("Java" + (1 + random.nextInt(5)));
            students.add(student);
        }
        StudentIO.saveAll();

    }


    public static void deleteAllDataFromFile(String dataSource) {
        try (PrintWriter writer = new PrintWriter(dataSource)) {
            // Write an empty string to the file
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
        }
        StudentIO.saveAll();
    }

    // Search for student
    public static void searchForData() {
        System.out.println(".........................");
        System.out.println("[+] Searching Student:");
        System.out.println(".........................");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Id");
        System.out.println("(Back/B) To Back");
        System.out.println(".........................");
        while (true) {
            System.out.print("> Insert option(search): ");
            String option = sc.nextLine();
            if (option.equals("1")) {
                searchByName();
            } else if (option.equals("2")) {
                searchById();
            } else if (option.equalsIgnoreCase("b") || option.equalsIgnoreCase("back")) {
                return;
            } else {
                System.out.println("Invalid input!");
            }
        }
    }

    public static void searchByName () {
        boolean found = false;
        System.out.print(">>> Enter Name: ");
        String name = sc.nextLine();

        List<Student> searchRecords = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                if (!found) {
                    found = true;
                }
                searchRecords.add(student);
            }
        }
        if (!found) {
            System.out.println("Student name: " + name + " was not found!");
        } else {
            int totalPages = (int)Math.ceil((double)searchRecords.size() / rowSize);
            int totalRecords = searchRecords.size();
            while (true) {
                int startIndex = (currentPage - 1) * rowSize;
                int endIndex = Math.min(startIndex + rowSize, searchRecords.size());
                List<Student> pageStudents = searchRecords.subList(startIndex, endIndex);

                System.out.println();
                StudentTableModel.renderStudentsToTable(pageStudents, rowSize, currentPage, totalPages, totalRecords);
                StudentTableModel.renderPagination();

                System.out.print("Enter the option(pagination): ");
                String pageOption = new Scanner(System.in).nextLine();
                if (pageOption.equalsIgnoreCase("p")) {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You're already on the first page.");
                    }
                } else if (pageOption.equalsIgnoreCase("n")) {
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("You're already on the last page.");
                    }
                } else if (pageOption.equalsIgnoreCase("f")) {
                    currentPage = 1;
                } else if (pageOption.equalsIgnoreCase("l")) {
                    currentPage = totalPages;
                } else if (pageOption.equalsIgnoreCase("b")) {
                    return;
                } else {
                    try {
                        int pageNumber = Integer.parseInt(pageOption);
                        if (pageNumber >= 1 && pageNumber <= totalPages) {
                            currentPage = pageNumber;
                        } else {
                            System.out.println("Invalid page number. Please enter a number between 1 and " + totalPages);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                    }
                }
            }
        }
    }

    public static void searchById () {
        System.out.print("Insert student's Id: ");
        String id = sc.nextLine();

        boolean found = false;
        List<Student> searchRecords = new ArrayList<>();

        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                if (!found) {
                    found = true;
                }
                searchRecords.add(student);
            }
        }
        if (!found) {
            System.out.println("Student Id: " + id + " was not found!");
        } else {
            int totalPages = (int)Math.ceil((double)searchRecords.size() / rowSize);
            int totalRecords = searchRecords.size();
            while (true) {
                int startIndex = (currentPage - 1) * rowSize;
                int endIndex = Math.min(startIndex + rowSize, searchRecords.size());
                List<Student> pageStudents = searchRecords.subList(startIndex, endIndex);

                System.out.println();
                StudentTableModel.renderStudentsToTable(pageStudents, rowSize, currentPage, totalPages, totalRecords);
                StudentTableModel.renderPagination();

                System.out.print("Enter the option(pagination): ");
                String pageOption = new Scanner(System.in).nextLine();
                if (pageOption.equalsIgnoreCase("p")) {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You're already on the first page.");
                    }
                } else if (pageOption.equalsIgnoreCase("n")){
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("You're already on the last page.");
                    }
                } else if (pageOption.equalsIgnoreCase("f")) {
                    currentPage = 1;
                } else if (pageOption.equalsIgnoreCase("l")) {
                    currentPage = totalPages;
                } else if (pageOption.equalsIgnoreCase("b")) {
                    return;
                } else {
                    try {
                        int pageNumber = Integer.parseInt(pageOption);
                        if (pageNumber >= 1 && pageNumber <= totalPages) {
                            currentPage = pageNumber;
                        } else {
                            System.out.println("Invalid page number. Please enter a number between 1 and " + totalPages);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                    }
                }
            }
        }
    }

}
