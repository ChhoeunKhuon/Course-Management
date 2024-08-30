package io;

import controllers.StudentController;
import model.Student;
import view.View;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static services.StudentService.random;

public class Utils {
    private static final int NUM_THREADS = 100;
    private static StudentController studentController;
    private static View studentView;
    private static final int BUFFER_SIZE = 64 * 1024; // 64KB

    // write data in transaction file into data source file and clear the list
    public static void exchangeData(String originalFile, String destinationFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush(); // Flush the writer to ensure all data is written
        } catch (IOException e) {
            System.err.println("Error committing data: " + e.getMessage());
        }
    }
    // checking first is it user already commit before close the program
    // if no commit before close program ask them
    // y commit the data from transaction just modify into data source file
    // no just clear the data in file transaction
    public static void checkingCommit(Scanner input, String transactionFile, String dataSource) {
        File file = new File(transactionFile);
        if (file.length() > 0) {
            // We ask if they want to commit data from the current file or transaction file to data source
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Do you want to commit? (Y/N): ");
                String answer = input.nextLine();
                switch (answer.toLowerCase()) { // Convert to lowercase for case-insensitive comparison
                    // If yes then we call the method commit to perform the commit action
                    case "y" -> {
                        exchangeData(transactionFile, dataSource);
                        validInput = true; // Valid input provided, exit the loop
                    }
                    // If no just print a line and clear all the data in transaction file
                    // This means that all the modifying has not changed
                    case "n" -> {
                        System.out.println("You are canceling...");
                        clearTransactionFile(transactionFile, "");
                        validInput = true; // Valid input provided, exit the loop
                    }
                    default -> System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }
    }
    public static void clearTransactionFile(String transactionFile, String msg) {
        try (PrintWriter writer = new PrintWriter(transactionFile)) {
            // Clear transaction file by writing an empty string to it
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error clearing transaction file: " + e.getMessage());
        }
    }

    public static StudentController getStudentController() {
        if (studentController == null) {
            studentController = new StudentController();
        }
        return studentController;
    }

    // this method is make sure the list data file transaction has the same data
    // it synchronizes
    public static void listToFile(List<Student> students, String transaction) {

        /**long start = System.currentTimeMillis();

        // Use BufferedWriter with a specified buffer size for more efficient buffering
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transaction), BUFFER_SIZE)) {
            for (Student student : students) {
                writer.write(studentToString(student));
                writer.newLine(); // Ensures each student is on a new line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Writing duration: " + duration + " ms");
        System.out.println();
         **/

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        int partitionSize = (students.size() + NUM_THREADS - 1) / NUM_THREADS; // Round up division

        for (int i = 0; i < students.size(); i += partitionSize) {
            int startIdx = i;
            int endIdx = Math.min(i + partitionSize, students.size());
            List<Student> partition = students.subList(startIdx, endIdx);

            executor.submit(() -> {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(transaction, true), BUFFER_SIZE)) {
                    for (Student student : partition) {
                        synchronized (writer) { // Ensure thread-safe writes
                            writer.write(studentToString(student));
                            writer.newLine();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Writing duration: " + duration + " ms");
        System.out.println();

    }


    public static View getStudentView() {
        if (studentView == null) {
            studentView = new View();
        }
        return studentView;
    }

    // convert each student separate by (,) comma easy to dealing with it
    public static String studentToString(Student student) {
        return student.getId() + "," + student.getName() + "," + student.getDateOfBirth() + "," + student.getClassroom() + "," + student.getSubject();
    }

    public static ArrayList<Student> readFileToList(String dataSource) {
        ArrayList<Student> students = new ArrayList<>();
        long start = System.currentTimeMillis();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataSource))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Ensure that the line is correctly formatted
                    Student student = new Student();
                    student.setId(parts[0]);
                    student.setName(parts[1]);
                    student.setDateOfBirth(parts[2]);
                    student.setClassroom(parts[3]);
                    student.setSubject(parts[4]);
                    StudentIO.students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Reading duration: " + duration + " ms");
        return students;
    }

    // Generate student
//    public static List<Student> generateMockStudents(int count) {
//        List<Student> studentsGenerate = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            Student student = new Student();
//            student.setId(String.valueOf(random.nextInt(100000)));
//            student.setName("Student" + (i + 1));
//            student.setDateOfBirth(LocalDate.of(2000 + random.nextInt(20), 1 + random.nextInt(12), 1 + random.nextInt(28)).toString());
//            student.setClassroom("E" + (1 + random.nextInt(10)));
//            student.setSubject("Java" + (1 + random.nextInt(5)));
//            studentsGenerate.add(student);
//        }
//        return studentsGenerate;
//    }
}
