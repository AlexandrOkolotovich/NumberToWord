package by.bsuir.task.main;

import by.bsuir.task.dao.DAOFactory;
import by.bsuir.task.dao.FileDegreeReader;
import by.bsuir.task.service.ActionHandler;
import by.bsuir.task.service.impl.NumberInWordActionHandler;

import java.util.Scanner;

public class Application {
    private static final String filePath = "src/main/resources/numbers_db.txt";

    public static void main(String[] args) {
        long number;

        Scanner sc = new Scanner(System.in);

        DAOFactory factory = DAOFactory.getInstance();
        FileDegreeReader fileReader = factory.getFileDegreeReader();
        fileReader.readFile(filePath);

        while(true) {

            System.out.println("Input number: ");
            System.out.print(" >> ");
            while (!sc.hasNextLong()) {
                sc.next();
                System.out.print(" >> ");
            }
            number = sc.nextLong();

            ActionHandler numberInWord = new NumberInWordActionHandler();
            System.out.println(numberInWord.getResult(number));
        }

    }
}
