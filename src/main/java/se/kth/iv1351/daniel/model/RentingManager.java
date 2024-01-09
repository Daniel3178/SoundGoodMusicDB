package se.kth.iv1351.daniel.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import se.kth.iv1351.daniel.model.DTO.RentingDTO;
import se.kth.iv1351.daniel.model.exception.NotExistInDatabaseException;
import se.kth.iv1351.daniel.model.exception.RentalLimitException;

public class RentingManager implements RentingDTO
{
    private final RentalInstrument currentInstrument;
    private final Student currentStudent;

    public RentingManager(RentalInstrument currentInstrument, Student currentStudent) throws NotExistInDatabaseException
    {
        String failureMsg = "";
        if (currentInstrument == null && currentStudent == null)
        {
            failureMsg = "Both instrument and student do not exist in database";
        }
        else if (currentInstrument == null)
        {
            failureMsg = "Instrument does not exist in the database or it is already rented";
        }
        else if (currentStudent == null)
        {
            failureMsg = "Student does not exist in database";
        }
        if (currentInstrument == null || currentStudent == null)
        {
            throw new NotExistInDatabaseException(failureMsg);
        }

        this.currentInstrument = currentInstrument;
        this.currentStudent = currentStudent;
    }

    public RentingManager(RentalInstrument currentInstrument) throws NotExistInDatabaseException
    {
        if (currentInstrument == null) {
            throw new NotExistInDatabaseException("No such on going rental record found in database! ");
        }
        this.currentInstrument = currentInstrument;
        this.currentStudent = null;
    }

    public void rent() throws RentalLimitException, NullPointerException
    {
        if (currentStudent == null) {
            throw new NullPointerException("No record of a student");
        }
        if (currentStudent.getNumberOfBorrowedInstrument() >= 2)
        {
            throw new RentalLimitException(currentStudent);
        }
        currentInstrument.setStartRentingDate(getCurrentDate());
        currentInstrument.decreaseQuantityByOne();
        currentStudent.increaseBorrowedInsByOne();
    }

    public void terminateRent() throws NullPointerException
    {
        currentInstrument.setEndRentingDate(getCurrentDate());
        currentInstrument.increaseQuantityByOne();
    }

    private static String getCurrentDate()
    {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedDate = currentDate.format(formatter);
        formattedDate = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
        formattedDate = formattedDate.replace(".", "");
        return formattedDate;
    }

    @Override
    public String getStartDate()
    {
        return currentInstrument.getStartRentingDate();
    }

    @Override
    public String getEndDate()
    {
        return currentInstrument.getEndRentingDate();
    }

    @Override
    public int getStudentId() throws NullPointerException
    {
        if (currentStudent == null)
        {
            throw new NullPointerException("No record of a student found");
        }
        return currentStudent.getStudentId();
    }

    @Override
    public int getInstrumentId()
    {
        return currentInstrument.getInstrumentId();
    }

    @Override
    public int getQuantity()
    {
        return currentInstrument.getQuantity();
    }

    @Override
    public int getRentId()
    {
        return currentInstrument.getRentId();
    }
    @Override
    public int getStudentCurrNoOfBorrowedIns() throws NullPointerException
    {
        if (currentStudent == null) {
            throw new NullPointerException("No record of a student");
        }
        return currentStudent.getNumberOfBorrowedInstrument();
    }
}
