package se.kth.iv1351.daniel.integration.exception;

public class DatabaseException extends Exception
{
    public DatabaseException(String failureMsg)
    {
        super(failureMsg);
    }
}
