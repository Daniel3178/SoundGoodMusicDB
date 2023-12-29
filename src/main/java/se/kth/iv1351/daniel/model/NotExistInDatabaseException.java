package se.kth.iv1351.daniel.model;

public class NotExistInDatabaseException extends Exception
{
    public NotExistInDatabaseException(String failureMsg){
        super(failureMsg);
    }
}
