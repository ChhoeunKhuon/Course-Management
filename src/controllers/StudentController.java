package controllers;

import model.Student;
import services.StudentService;
import view.View;
import java.util.Scanner;

public class StudentController {

    public static void display(String transaction, String dataSource) {
        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("=================================================");
        System.out.println("\t\t\tSTUDENT MANAGEMENT SYSTEM");

        String option = "";
        while (!option.equalsIgnoreCase("0")) {
            View.menu();
            System.out.print("> Insert option: ");
            option = sc.nextLine();

            switch (option) {
                case "1":
                    StudentService.addNewData();
                    break;
                case "2":
                    StudentService.getAllData();
                    break;
                case "3":
                    StudentService.commitDataToFile(transaction, dataSource);
                    break;
                case "4":
                    StudentService.searchForData();
                    break;
                case "5":
                    StudentService.updateDataById();
                    break;
                case "6":
                    StudentService.deleteData();
                    break;
                case "7":
                    StudentService.generateDataIntoFile();
                    break;
                case "8":
                    StudentService.deleteAllDataFromFile(dataSource);
                    break;
                case "9":
                    System.out.println("9. Exit");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Bye!");
            }
        }
    }

}
