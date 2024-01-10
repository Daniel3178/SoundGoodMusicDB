package se.kth.iv1351.daniel.model.exception;

public class NotExistInDatabaseException extends Exception {

    public NotExistInDatabaseException(String failureMsg) {
        super(failureMsg);
    }
}
