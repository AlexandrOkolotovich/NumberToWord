package by.bsuir.task.service.impl;

import by.bsuir.task.dao.DAOFactory;
import by.bsuir.task.dao.FileDegreeReader;
import by.bsuir.task.service.ActionHandler;

public class NumberInWordActionHandler implements ActionHandler {

    private static final String[][] dig1 = {{"одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
            {"один", "два"}};
    private static final String[] dig10 = {"десять","одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private static final String[] dig20 = {"двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private static final String[] dig100 = {"сто","двести", "триста", "четыреста", "пятьсот",
            "шестьсот", "семьсот", "восемьсот", "девятьсот"};

    private static final String SEPARATOR = " ";


    @Override
    public String getResult() {
        return "Number not received";
    }

    @Override
    public String getResult(long number){
        return getResult(number, 0);
    }

    @Override
    public String getResult(long number, int level) {
        StringBuilder result = new StringBuilder(50);

        if(number == 0){
            result.append("ноль");
        }

        DAOFactory factory = DAOFactory.getInstance();
        FileDegreeReader fileReader = factory.getFileDegreeReader();
        String[][] degree = fileReader.getDegrees();

        int currentThreeDigitSegment = (int)Math.abs(number%1000);
        int hundreds = currentThreeDigitSegment/100;

        if(hundreds > 0){
            result.append(dig100[hundreds-1]).append(SEPARATOR);
        }

        int unitsDigit = currentThreeDigitSegment%100;
        int tensDigit = unitsDigit/10;
        unitsDigit = unitsDigit%10;

        switch (tensDigit){
            case 0:break;
            case 1: {
                result.append(dig10[unitsDigit]).append(SEPARATOR);
                break;
            }
            default:
                result.append(dig20[tensDigit-2]).append(SEPARATOR);
        }

        int kindOfWord = checkSyntax(level);

        if (tensDigit==1) unitsDigit=0;

        switch(unitsDigit) {
            case 0: break;
            case 1:
            case 2: result.append(dig1[kindOfWord][unitsDigit-1]).append(SEPARATOR);
                break;
            default: result.append(dig1[0][unitsDigit-1]).append(SEPARATOR);
        }

        if(level!=0){
            switch(unitsDigit) {
                case 1: result.append(degree[level-1][0]);
                    break;
                case 2:
                case 3:
                case 4: {
                    result.append(degree[level - 1][1]);
                    break;
                }
                default: {
                    if(hundreds!=0 || tensDigit!=0 || unitsDigit!=0) {
                        result.append(degree[level - 1][2]);
                    }
                }
            }
        }

        long nextNumber = number/1000;

        if(nextNumber>0){
            return (getResult(nextNumber, level+1) + " " + result.toString()).trim();
        }
        if(number<0){
            return ("минус " + getResult(-number, level)).trim();
        }
        else {
            return result.toString().trim();
        }
    }

    static private int checkSyntax(int level){
        int kindOfWord = 0;

        if(level!=1){
            kindOfWord = 1;
        }

        return kindOfWord;
    }

}
