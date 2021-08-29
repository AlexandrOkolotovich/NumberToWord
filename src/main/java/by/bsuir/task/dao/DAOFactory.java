package by.bsuir.task.dao;

import by.bsuir.task.dao.impl.FileDegreeReaderImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final FileDegreeReader fileDegreeReader = new FileDegreeReaderImpl();

    private DAOFactory(){}

    public FileDegreeReader getFileDegreeReader() {
        return fileDegreeReader;
    }

    public static DAOFactory getInstance(){
        return instance;
    }
}
