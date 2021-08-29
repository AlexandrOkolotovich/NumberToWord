package by.bsuir.task.dao.impl;

import by.bsuir.task.dao.FileDegreeReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileDegreeReaderImpl implements FileDegreeReader {
    private String[][] array = new String[10][10];

    @Override
    public String[][] readFile(String filePath) {
        Scanner scanner = null;
        ArrayList<ArrayList> matrix = new ArrayList<ArrayList>();

        try{
            scanner = new Scanner(new File(filePath));
        }catch(FileNotFoundException e){
            System.err.println("Файл не найден");
        }

        while(scanner.hasNextLine()){
            Scanner scanLine = new Scanner(scanner.nextLine());
            ArrayList<String> line = new ArrayList<String>();
            while(scanLine.hasNext()){
                line.add(scanLine.next());
            }
            matrix.add(line);
        }

        int arrWidth = matrix.size();
        int arrLength = matrix.get(0).size();
        array = new String[arrWidth][arrLength];
        for(int y = 0; y < matrix.size(); y++){
            for(int x = 0; x < matrix.get(y).size(); x++){
                array[y][x] = (String) matrix.get(y).get(x);
            }
        }
        return array;
    }

    @Override
    public String[][] getDegrees(){
        return array;
    }


}
