package se.kth.iv1351.daniel.integration.exception;

public class WrongCredentialException extends Exception
{
    public WrongCredentialException()
    {
        super("Could not connect to database! Check your username and password");
    }
}
