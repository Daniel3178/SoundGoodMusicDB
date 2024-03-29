package se.kth.iv1351.daniel.controller;

import java.util.List;

import se.kth.iv1351.daniel.integration.SoundGoodMusicDAO;
import se.kth.iv1351.daniel.integration.exception.DatabaseException;
import se.kth.iv1351.daniel.integration.exception.WrongCredentialException;
import se.kth.iv1351.daniel.model.RentalInstrument;
import se.kth.iv1351.daniel.model.RentingManager;
import se.kth.iv1351.daniel.model.RentingRecord;
import se.kth.iv1351.daniel.model.Student;
import se.kth.iv1351.daniel.model.dto.InstrumentDTO;
import se.kth.iv1351.daniel.model.dto.RentingDTO;
import se.kth.iv1351.daniel.model.exception.NotExistInDatabaseException;
import se.kth.iv1351.daniel.model.exception.RentalLimitException;

public class Controller
{
    private static Controller ctrl = null;
    private final SoundGoodMusicDAO soundGoodMusicDb;

    private Controller(String username, String password) throws DatabaseException, WrongCredentialException
    {
        soundGoodMusicDb = new SoundGoodMusicDAO(username, password);
    }

    public static Controller getInstance(String username, String password) throws DatabaseException, WrongCredentialException
    {
        if (ctrl == null)
        {
            ctrl = new Controller(username, password);
        }
        return ctrl;
    }
    public RentingDTO terminateRent(int rentId) throws DatabaseException, NotExistInDatabaseException
    {
        String failureMsg = "Unable to terminate rent";
        try
        {
            RentalInstrument rentalInstrument = soundGoodMusicDb.findRentedInstrumentByRentId(rentId, true);
            RentingManager rentingManager = new RentingManager(rentalInstrument);
            rentingManager.terminateRent();
            soundGoodMusicDb.terminateRent(rentingManager);
            return rentingManager;
        }
        catch (DatabaseException e)
        {
            commitOnGoingTransaction(failureMsg);
            throw new DatabaseException("Unable to terminate");
        }
    }

    public RentingDTO rent(int instrumentId, int studentId) throws DatabaseException, NotExistInDatabaseException,
            RentalLimitException
    {
        String failureMsg = "Unable to rent";
        try
        {
            Student student = soundGoodMusicDb.findStudentBorrowedInstrumentCount(studentId, true);
            RentalInstrument rentalInstrument = soundGoodMusicDb.findInstrumentIfAvailableById(instrumentId, true);
            RentingManager rentingManager = new RentingManager(rentalInstrument, student);
            rentingManager.rent();
            soundGoodMusicDb.submitRent(rentingManager);
            return rentingManager;
        }
        catch (DatabaseException e)
        {
            commitOnGoingTransaction(failureMsg);
            throw new DatabaseException("Unable to rent");
        }
    }

    public List<? extends InstrumentDTO> getAllAvailableInstruments() throws DatabaseException
    {
        try
        {
            return soundGoodMusicDb.findAllAvailableInstrument();
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Unable to list instruments");
        }
    }

    public List<? extends InstrumentDTO> getAvailableInstrumentsByType(String type) throws DatabaseException
    {
        try
        {
            return soundGoodMusicDb.findAvailableInstrumentsByType(type);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Unable to list instruments");
        }
    }

    public List<? extends RentingRecord> getAllRentedInstrument() throws DatabaseException, NotExistInDatabaseException
    {
        try
        {
            return soundGoodMusicDb.findAllRentedInstrument();
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Unable to List instruments");
        }
        catch (NotExistInDatabaseException e)
        {
            throw new NotExistInDatabaseException("No record found");
        }
    }

    public List<? extends RentingRecord> getRentedInstrumentByStudentId(int studentId) throws DatabaseException,
            NotExistInDatabaseException
    {
        try
        {
            return soundGoodMusicDb.findRentedInstrumentsByStudentId(studentId);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Unable to List instruments");
        }
    }

    private void commitOnGoingTransaction(String failureMsg) throws DatabaseException
    {
        try
        {
            soundGoodMusicDb.commit();
        }
        catch (Exception e)
        {
            throw new DatabaseException(failureMsg);
        }
    }
}
