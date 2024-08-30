import io.Utils;
import model.Student;

import java.util.ArrayList;
import java.util.Scanner;

import static io.Utils.listToFile;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // variable for storing location of file transaction
        String transaction = "src/data/transaction.dat";
        // variable for storing location of file data source
        String dataSource = "src/data/students-data.dat";

        Utils.getStudentView().welcome();
        ArrayList<Student> students;

        Utils.checkingCommit(input, transaction, dataSource);

        // reading data from file into list
        //students =
        Utils.readFileToList(dataSource);
        //listToFile(students, transaction);

        // read data from data source into transaction file
        boolean running = true;
        while (running) {
            Utils.getStudentController().display(transaction, dataSource);
        }
    }
}