package by.bsuir.task;

import by.bsuir.task.dao.DAOFactory;
import by.bsuir.task.dao.FileDegreeReader;
import by.bsuir.task.service.ActionHandler;
import by.bsuir.task.service.impl.NumberInWordActionHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class NumberInWordActionHandlerTest {
    ActionHandler numberInWord = new NumberInWordActionHandler();

    @Before
    public void readFile(){
        DAOFactory factory = DAOFactory.getInstance();
        FileDegreeReader fileReader = factory.getFileDegreeReader();
        String filePath = "src/main/resources/numbers_db.txt";
        fileReader.readFile(filePath);
    }

    @Test
    public void testGetName() {

        long expected = 2222222222L;
        String actual = "два миллиарда двести двадцать два миллиона двести двадцать две тысячи двести двадцать два";

        System.out.println("Test Program: test 1 ");
        System.out.println(expected + " = " + numberInWord.getResult(expected));
        assertEquals(actual, numberInWord.getResult(expected));
    }

    @Test
    public void testGetNameZero() {

        int expected = 0;
        String actual = "ноль";

        System.out.println("Test Program: test 2 - zero");
        System.out.println(expected + " = " + numberInWord.getResult(expected));
        assertEquals(actual, numberInWord.getResult(expected));
    }

    @Test
    public void testGetNameNull() {
        String actual = "Number not received";

        System.out.println("Test Program: test 3 - null");
        assertEquals(actual, numberInWord.getResult());
    }

    @Test
    public void testGetNameUnit() {

        String[] actual = new String[]{"один", "два", "три", "четыре",
                "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать",
                "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        System.out.println("Test Program: test 4 - Numbers 1-19");

        for (int i = 1; i < 20; i++) {
            System.out.println(i + " = " + numberInWord.getResult(i));
            assertEquals("Ошибка в числах от одного до девятнадцати", actual[i - 1], numberInWord.getResult(i));
        }
    }

    @Test
    public void testGetNameTens() {
        int[] expected = new int[]{21, 32, 43, 54, 65, 76, 87, 98, 99};

        String[] actual = new String[]{"двадцать один", "тридцать два", "сорок три", "пятьдесят четыре",
                "шестьдесят пять", "семьдесят шесть", "восемьдесят семь", "девяносто восемь", "девяносто девять"};
        System.out.println("Test Program: test 5 - Numbers >20");

        for (int i = 1; i < 8; i++) {
            System.out.println(expected[i - 1] + " = " + numberInWord.getResult(expected[i - 1]));
            assertEquals(actual[i - 1], numberInWord.getResult(expected[i - 1]));
        }
    }

    @Test
    public void testGetNameAllTable() throws Exception {

        System.out.println("Test Program: test 6 - Different numbers");
        InputStream in = new FileInputStream("src/main/resources/data.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);

        long number = 0;
        String string = null;

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case NUMERIC:
                        System.out.print((number=(long)cell.getNumericCellValue()) + " = ");
                        break;

                    case STRING:
                        System.out.print((string=cell.getStringCellValue()));
                        break;

                    default:
                        break;
                }
            }
            System.out.println();
            assertEquals("Ошибка в числе: " + number, string, numberInWord.getResult(number));
        }
    }

}
