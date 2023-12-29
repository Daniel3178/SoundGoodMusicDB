package se.kth.iv1351.daniel.integration;

import se.kth.iv1351.daniel.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoundGoodMusicDAO
{
    private static final String RENT_SYS_TBL = "renting_system";
    private static final String INV_TBL = "inventory";
    private static final String INST_TBL = "instrument";
    private static final String STU_TBL = "student";
    private static final String PER_TBL = "person";
    private static final String RENT_SYS_PK_COL = "rent_id";
    private static final String INST_PK_COL = "instrument_id";
    private static final String STU_PK_COL = "student_id";
    private static final String INV_PK_COL = "inventory_id";
    private static final String PER_PK_COL = "person_id";
    private static final String RENT_SYS_START_RENT_DATE_COL = "start_renting_date";
    private static final String RENT_SYS_END_RENT_DATE_COL = "end_renting_date";
    private static final String INV_MDL_TBL = "model";
    private static final String INV_BRD_TBL = "brand";
    private static final String INV_MO_PR = "monthly_price";
    private static final String INV_NO_OF_INST = "number_of_instrument";
    private static final String DER_NO_OF_BD_INST_COL= "number_of_borrowed_ins";
    private static final String PER_FST_N_COL = "first_name";
    private static final String PER_LST_N_COL = "last_name";
    private static final String RENT_SYS_FK_INST_COL = INST_PK_COL;
    private static final String INST_FK_INV_COL = INV_PK_COL;
    private static final String STU_FK_PER_COL = PER_PK_COL;
    private static final String RENT_SYS_FK_STU_COL = STU_PK_COL;

    private Connection connection;
    private PreparedStatement findAllAvailableInstrumentStmt;
    private PreparedStatement findAllRentedInstrumentStmt;
    private PreparedStatement findRentedInstrumentsByStudentIdStmt;
    private PreparedStatement findStudentBorrowedInstrumentCountStmt;
    private PreparedStatement findAvailableInstrumentsByTypeStmt;
    private PreparedStatement findInstrumentIfAvailableByIdStmt;
    private PreparedStatement updateInventoryByInstrumentIdStmt;
    private PreparedStatement recordNewRentalStmt;
    private PreparedStatement findRentedInstrumentByRentIdStmt;
    private PreparedStatement findStudentBorrowedInstrumentCountLockingForUpdateStmt;
    private PreparedStatement findInstrumentIfAvailableByIdLockingForUpdateStmt;
    private PreparedStatement findRentedInstrumentByRentIdLockingForUpdateStmt;
    private PreparedStatement terminateRentalStmt;

    public SoundGoodMusicDAO(String username,String password) throws DatabaseException, WrongCredentialException
    {
        try
        {
            connectToSoundGoodMusicDB(username,password);
            prepareStatements();

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not connect to database");
        }
    }

    public List<RentalInstrument> findAllAvailableInstrument() throws DatabaseException
    {
        String failureMsg = "Could not list instruments";
        List<RentalInstrument> instruments = new ArrayList<>();
        try (ResultSet result = findAllAvailableInstrumentStmt.executeQuery())
        {
            while (result.next())
            {
                instruments.add(new RentalInstrument(result.getInt(INST_PK_COL),
                                                     result.getString(INV_MDL_TBL),
                                                     result.getString(INV_BRD_TBL),
                                                     result.getFloat(INV_MO_PR)));
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        return instruments;
    }

    public List<RentalInstrument> findAvailableInstrumentsByType(String type) throws DatabaseException
    {
        String failureMsg = "Could not list instruments";
        List<RentalInstrument> instruments = new ArrayList<>();
        ResultSet result = null;
        try
        {
            findAvailableInstrumentsByTypeStmt.setString(1, "%" + type + "%");
            result = findAvailableInstrumentsByTypeStmt.executeQuery();
            while (result.next())
            {
                instruments.add(new RentalInstrument(result.getInt(INST_PK_COL),
                                                     result.getString(INV_MDL_TBL),
                                                     result.getString(INV_BRD_TBL),
                                                     result.getFloat(INV_MO_PR)));
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        finally
        {
            closeResultSet(failureMsg, result);
        }
        return instruments;
    }


    public List<RentingRecord> findAllRentedInstrument() throws DatabaseException, NotExistInDatabaseException
    {
        String failureMsg = "Could not list instruments";
        List<RentingRecord> instruments = new ArrayList<>();

        try (ResultSet result = findAllRentedInstrumentStmt.executeQuery())
        {
            while (result.next())
            {
                instruments.add(new RentingRecord(new RentalInstrument(result.getInt(RENT_SYS_PK_COL),
                                                                       result.getInt(INST_PK_COL),
                                                                       result.getString(INV_MDL_TBL),
                                                                       result.getString(INV_BRD_TBL),
                                                                       result.getString(RENT_SYS_START_RENT_DATE_COL),
                                                                       result.getFloat(INV_MO_PR)),
                                                  new Student(result.getString(PER_FST_N_COL),
                                                              result.getString(PER_LST_N_COL),
                                                              result.getInt(STU_PK_COL),
                                                              result.getInt(DER_NO_OF_BD_INST_COL))));
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        return instruments;
    }

    public List<RentingRecord> findRentedInstrumentsByStudentId(int studentId) throws DatabaseException,
            NotExistInDatabaseException
    {
        String failureMsg = "Could not list instruments";
        List<RentingRecord> instruments = new ArrayList<>();
        ResultSet result = null;
        try
        {
            findRentedInstrumentsByStudentIdStmt.setInt(1, studentId);
            result = findRentedInstrumentsByStudentIdStmt.executeQuery();
            while (result.next())
            {
                instruments.add(new RentingRecord(new RentalInstrument(result.getInt(RENT_SYS_PK_COL),
                                                                       result.getInt(INST_PK_COL),
                                                                       result.getString(INV_MDL_TBL),
                                                                       result.getString(INV_BRD_TBL),
                                                                       result.getString(RENT_SYS_START_RENT_DATE_COL),
                                                                       result.getFloat(INV_MO_PR)),
                                                  new Student(result.getString(PER_FST_N_COL),
                                                              result.getString(PER_LST_N_COL),
                                                              result.getInt(STU_PK_COL),
                                                              result.getInt(DER_NO_OF_BD_INST_COL))));
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        finally
        {
            closeResultSet(failureMsg, result);
        }
        if (instruments.isEmpty())
        {
            throw new NotExistInDatabaseException("No record found");
        }
        return instruments;
    }

    public RentalInstrument findRentedInstrumentByRentId(int rentId, boolean lockingExclusive) throws DatabaseException
    {
        String failureMsg = "Could not list instruments";
        RentalInstrument rentedInstrument = null;
        PreparedStatement stmtToExecute = lockingExclusive ? findRentedInstrumentByRentIdLockingForUpdateStmt
                                                           : findRentedInstrumentByRentIdStmt;
        ResultSet result = null;
        try
        {
            stmtToExecute.setInt(1, rentId);
            result = stmtToExecute.executeQuery();
            if (result.next())
            {
                rentedInstrument
                        = new RentalInstrument(result.getInt(RENT_SYS_PK_COL),
                                               result.getInt(INST_PK_COL),
                                               result.getInt(INV_NO_OF_INST));
            }
            if (!lockingExclusive)
            {
                connection.commit();
            }
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        finally
        {
            closeResultSet(failureMsg, result);
        }
        return rentedInstrument;
    }


    public void submitRent(RentingDTO renting) throws DatabaseException
    {
        String failureMsg = "Rental submission could not be fulfilled";

        try
        {
            updateInventoryByInstrumentIdStmt.setInt(1, renting.getQuantity());
            updateInventoryByInstrumentIdStmt.setInt(2, renting.getInstrumentId());
            int updatedRows = updateInventoryByInstrumentIdStmt.executeUpdate();
            if (updatedRows != 1)
            {
                handleException(failureMsg);
            }

            recordNewRentalStmt.setString(1, renting.getStartDate());
            recordNewRentalStmt.setInt(2, renting.getStudentId());
            recordNewRentalStmt.setInt(3, renting.getInstrumentId());
            updatedRows = recordNewRentalStmt.executeUpdate();
            if (updatedRows != 1)
            {
                handleException(failureMsg);
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
    }

    public void terminateRent(RentingDTO renting) throws DatabaseException
    {
        String failureMsg = "Rental termination could not be fulfilled";

        try
        {
            updateInventoryByInstrumentIdStmt.setInt(1, renting.getQuantity());
            updateInventoryByInstrumentIdStmt.setInt(2, renting.getInstrumentId());
            int updatedRows = updateInventoryByInstrumentIdStmt.executeUpdate();
            if (updatedRows != 1)
            {
                handleException(failureMsg);
            }

            terminateRentalStmt.setString(1, renting.getEndDate());
            terminateRentalStmt.setInt(2, renting.getRentId());
            updatedRows = terminateRentalStmt.executeUpdate();
            if (updatedRows != 1)
            {
                handleException(failureMsg);
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
    }

    public RentalInstrument findInstrumentIfAvailableById(int instrumentId, boolean lockingExclusive) throws DatabaseException
    {
        String failureMsg = "Could not list instruments";
        PreparedStatement stmtToExecute = lockingExclusive ? findInstrumentIfAvailableByIdLockingForUpdateStmt
                                                           : findInstrumentIfAvailableByIdStmt;
        ResultSet result = null;
        RentalInstrument rentalInstrument = null;
        try
        {
            stmtToExecute.setInt(1, instrumentId);
            result = stmtToExecute.executeQuery();
            if (result.next())
            {
                rentalInstrument
                        = new RentalInstrument(
                        result.getInt(INST_PK_COL),
                        result.getInt(INV_NO_OF_INST));
            }
            if (!lockingExclusive)
            {
                connection.commit();
            }

        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        finally
        {
            closeResultSet(failureMsg, result);
        }
        return rentalInstrument;
    }


    public Student findStudentBorrowedInstrumentCount(int studentId, boolean lockExclusive) throws DatabaseException
    {
        String failureMsg = "Could not list instruments";

        PreparedStatement stmtToExecute = lockExclusive ? findStudentBorrowedInstrumentCountLockingForUpdateStmt
                                                        : findStudentBorrowedInstrumentCountStmt;
        ResultSet result = null;
        Student student = null;
        try
        {
            stmtToExecute.setInt(1, studentId);
            result = stmtToExecute.executeQuery();
            if (result.next())
            {
                student
                        = new Student(
                        result.getInt(STU_PK_COL),
                        result.getInt(DER_NO_OF_BD_INST_COL));
            }
            if (!lockExclusive)
            {
                connection.commit();
            }

        }
        catch (SQLException e)
        {
            handleException(failureMsg);
        }
        finally
        {
            closeResultSet(failureMsg, result);
        }
        return student;
    }



    public void commit() throws DatabaseException
    {
        try
        {
            connection.commit();
        }
        catch (SQLException e)
        {
            handleException("Failed to commit");
        }
    }


    private void handleException(String failureMsg) throws DatabaseException
    {
        String completeFailureMsg = failureMsg;
        try
        {
            connection.rollback();
        }
        catch (SQLException e)
        {
            completeFailureMsg = completeFailureMsg + ". Also failed to rollback transaction because of: " +
                                 e.getMessage();
        }

        throw new DatabaseException(completeFailureMsg);
    }

    private void closeResultSet(String failureMsg, ResultSet result) throws DatabaseException
    {
        try
        {
            result.close();
        }
        catch (Exception e)
        {
            throw new DatabaseException(failureMsg + " Could not close result set");
        }
    }

    private void connectToSoundGoodMusicDB(String username,String password) throws WrongCredentialException
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SoundGoodMusic",
                    username, password);
            connection.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            throw new WrongCredentialException();
        }
    }
    private void prepareStatements() throws SQLException
    {
        findAllAvailableInstrumentStmt = connection.prepareStatement(
                "SELECT DISTINCT I." + INST_PK_COL + ", Inv." + INV_MDL_TBL + ", Inv." + INV_BRD_TBL + ", " +
                "Inv." + INV_MO_PR + " FROM " + INV_TBL + " AS Inv LEFT JOIN " + INST_TBL + " AS I ON I." +
                INST_FK_INV_COL + " = Inv." + INV_PK_COL + " LEFT JOIN " + RENT_SYS_TBL + " AS RS ON RS." +
                RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL + " WHERE NOT EXISTS ( SELECT * " +
                "FROM " + RENT_SYS_TBL + " AS RS_NULL WHERE RS_NULL." + RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL +
                " AND RS_NULL." + RENT_SYS_END_RENT_DATE_COL + " IS NULL ) ORDER BY I." + INST_PK_COL
        );
        findAvailableInstrumentsByTypeStmt = connection.prepareStatement(
                "SELECT DISTINCT I." + INST_PK_COL + ", Inv." + INV_MDL_TBL + ", Inv." + INV_BRD_TBL + ", " +
                "Inv." + INV_MO_PR + " FROM " + INV_TBL + " AS Inv LEFT JOIN " +INST_TBL + " AS I ON I." + INST_FK_INV_COL
                + " = Inv." + INV_PK_COL + " LEFT JOIN " + RENT_SYS_TBL + " AS RS ON RS." + RENT_SYS_FK_INST_COL + " = I." +
                INST_PK_COL + " WHERE NOT EXISTS ( SELECT * FROM " + RENT_SYS_TBL + " AS RS_NULL " + "WHERE RS_NULL." +
                RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL + " AND RS_NULL." + RENT_SYS_END_RENT_DATE_COL + " IS NULL " +
                ") AND Inv." + INV_MDL_TBL + " LIKE ? ORDER BY I." + INST_PK_COL
        );

        findAllRentedInstrumentStmt = connection.prepareStatement(

                "SELECT RS." + RENT_SYS_PK_COL + ", I." + INST_PK_COL + ", S." + STU_PK_COL + ", Inv." + INV_MDL_TBL + ", " +
                "Inv." + INV_BRD_TBL + ", Per." + PER_FST_N_COL + ", Per." + PER_LST_N_COL + ", RS." + RENT_SYS_START_RENT_DATE_COL + ", " +
                "Inv." + INV_MO_PR + ", (SELECT COUNT(*) FROM " + RENT_SYS_TBL + " AS SubRS " + "WHERE SubRS." + RENT_SYS_FK_STU_COL +
                " = S." + STU_PK_COL + " AND SubRS." + RENT_SYS_END_RENT_DATE_COL + " IS NULL) AS number_of_borrowed_ins FROM " +
                INST_TBL + " AS I " + "JOIN " + RENT_SYS_TBL + " AS RS ON RS." + RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL +
                " JOIN " + STU_TBL + " AS S ON S." + STU_PK_COL + " = RS." + RENT_SYS_FK_STU_COL + " JOIN " + PER_TBL +
                " AS Per ON Per." + PER_PK_COL + " = S." + STU_FK_PER_COL + " JOIN " + INV_TBL + " AS Inv ON I." + INST_FK_INV_COL +
                " = Inv." + INV_PK_COL + " WHERE RS." + RENT_SYS_END_RENT_DATE_COL + " IS NULL"
        );

        findRentedInstrumentsByStudentIdStmt = connection.prepareStatement(
                "SELECT RS." + RENT_SYS_PK_COL + ", I." + INST_PK_COL + ", S." + STU_PK_COL + ", Inv." + INV_MDL_TBL + ", " +
                "Inv." + INV_BRD_TBL + ", Per." + PER_FST_N_COL + ", Per." + PER_LST_N_COL + ", RS." + RENT_SYS_START_RENT_DATE_COL +
                ", Inv." + INV_MO_PR + ", (SELECT COUNT(*) FROM " + RENT_SYS_TBL + " AS SubRS WHERE SubRS." + RENT_SYS_FK_STU_COL +
                " = S." + STU_PK_COL + " AND SubRS." + RENT_SYS_END_RENT_DATE_COL + " IS NULL) AS number_of_borrowed_ins FROM "
                + INST_TBL + " AS I " + "JOIN " + RENT_SYS_TBL + " AS RS ON RS." + RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL +
                " JOIN " + STU_TBL + " AS S ON S." + STU_PK_COL + " = RS." + RENT_SYS_FK_STU_COL + " JOIN " + PER_TBL + " AS Per ON Per."
                + PER_PK_COL + " = S." + STU_FK_PER_COL + " " + "JOIN " + INV_TBL + " AS Inv ON I." + INST_FK_INV_COL + " = Inv." +
                INV_PK_COL + " WHERE RS." + RENT_SYS_END_RENT_DATE_COL + " IS NULL AND S." + STU_PK_COL + " = ?"
        );

        findStudentBorrowedInstrumentCountStmt = connection.prepareStatement(
                "SELECT S." + STU_PK_COL + ", COALESCE( (SELECT COUNT(*) FROM " + RENT_SYS_TBL + " AS SubRS " +
                "WHERE SubRS." + RENT_SYS_FK_STU_COL + " = S." + STU_PK_COL + " AND SubRS." + RENT_SYS_END_RENT_DATE_COL +
                " IS NULL), 0) AS number_of_borrowed_ins FROM "+ STU_TBL + " AS S WHERE S." + STU_PK_COL + " = ? "
        );

        findStudentBorrowedInstrumentCountLockingForUpdateStmt = connection.prepareStatement(
                "SELECT S." + STU_PK_COL + ", COALESCE( (SELECT COUNT(*) FROM " + RENT_SYS_TBL + " AS SubRS " +
                "WHERE SubRS." + RENT_SYS_FK_STU_COL + " = S." + STU_PK_COL + " AND SubRS." + RENT_SYS_END_RENT_DATE_COL +
                " IS NULL), 0) AS number_of_borrowed_ins FROM "+ STU_TBL + " AS S WHERE S." + STU_PK_COL + " = ? FOR NO KEY UPDATE"
        );

        findInstrumentIfAvailableByIdStmt = connection.prepareStatement(
                "SELECT I." + INST_PK_COL + ", Inv." + INV_NO_OF_INST + " FROM " +INST_TBL + " AS I JOIN " + INV_TBL +
                " AS Inv ON I." + INST_FK_INV_COL + " = Inv." + INV_PK_COL + " JOIN " + RENT_SYS_TBL + " AS RS ON I." +
                INST_PK_COL + " = RS." + RENT_SYS_FK_INST_COL + " WHERE I." + INST_PK_COL + " = ? AND NOT EXISTS ( " +
                "SELECT 1 FROM " + RENT_SYS_TBL + " AS SubRS WHERE SubRS." + RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL +
                " AND SubRS." + RENT_SYS_START_RENT_DATE_COL + " IS NOT NULL AND SubRS." + RENT_SYS_END_RENT_DATE_COL + " IS NULL )"
        );

        findInstrumentIfAvailableByIdLockingForUpdateStmt = connection.prepareStatement(

                "SELECT I." + INST_PK_COL + ", Inv." + INV_NO_OF_INST + " FROM " +INST_TBL + " AS I JOIN " + INV_TBL +
                " AS Inv ON I." + INST_FK_INV_COL + " = Inv." + INV_PK_COL + " JOIN " +RENT_SYS_TBL + " AS RS ON I." +
                INST_PK_COL + " = RS." + RENT_SYS_FK_INST_COL + " WHERE I." + INST_PK_COL + " = ? " + "AND NOT EXISTS ( " +
                "SELECT 1 FROM " + RENT_SYS_TBL + " AS SubRS WHERE SubRS." + RENT_SYS_FK_INST_COL + " = I." + INST_PK_COL +
                " AND SubRS." + RENT_SYS_START_RENT_DATE_COL + " IS NOT NULL AND SubRS." + RENT_SYS_END_RENT_DATE_COL + " IS NULL " +
                ") FOR NO KEY UPDATE"
        );

        updateInventoryByInstrumentIdStmt = connection.prepareStatement(
                "UPDATE " + INV_TBL + " SET " + INV_NO_OF_INST + " = ? WHERE " + INV_PK_COL + " IN ( " +
                "SELECT I." + INST_FK_INV_COL + " FROM " + INST_TBL + " AS I WHERE I." + INST_PK_COL + " = ? )"
        );

        recordNewRentalStmt = connection.prepareStatement(
                "INSERT INTO " + RENT_SYS_TBL + " (" + RENT_SYS_PK_COL + ", " + RENT_SYS_START_RENT_DATE_COL + ", " +
                RENT_SYS_END_RENT_DATE_COL + ", " + RENT_SYS_FK_STU_COL + ", " + RENT_SYS_FK_INST_COL + ")" +
                "VALUES ( (SELECT COALESCE(MAX(" + RENT_SYS_PK_COL + "), 0) + 1 FROM " + RENT_SYS_TBL + "), " +
                "?, NULL, ?, ? )"
        );

        findRentedInstrumentByRentIdStmt = connection.prepareStatement(
                "SELECT RS." + RENT_SYS_PK_COL + ", I." + INST_PK_COL + ", Inv." + INV_NO_OF_INST + " FROM "
                + INST_TBL + " AS I JOIN " + RENT_SYS_TBL + " AS RS ON RS." + RENT_SYS_FK_INST_COL + " = I." +
                INST_PK_COL + " JOIN " + INV_TBL + " AS Inv ON I." + INST_FK_INV_COL + " = Inv." + INV_PK_COL +
                " WHERE RS." + RENT_SYS_START_RENT_DATE_COL + " IS NOT NULL AND RS." + RENT_SYS_END_RENT_DATE_COL +
                " IS NULL AND RS." + RENT_SYS_PK_COL + " = ? "
        );

        findRentedInstrumentByRentIdLockingForUpdateStmt = connection.prepareStatement(
                "SELECT RS." + RENT_SYS_PK_COL + ", I." + INST_PK_COL + ", Inv." + INV_NO_OF_INST + " FROM "
                + INST_TBL + " AS I JOIN " + RENT_SYS_TBL + " AS RS ON RS." + RENT_SYS_FK_INST_COL + " = I." +
                INST_PK_COL + " JOIN " + INV_TBL + " AS Inv ON I." + INST_FK_INV_COL + " = Inv." + INV_PK_COL +
                " WHERE RS." + RENT_SYS_START_RENT_DATE_COL + " IS NOT NULL AND RS." + RENT_SYS_END_RENT_DATE_COL +
                " IS NULL AND RS." + RENT_SYS_PK_COL + " = ? FOR NO KEY UPDATE"
        );

        terminateRentalStmt = connection.prepareStatement(
                "UPDATE " + RENT_SYS_TBL + " SET " + RENT_SYS_END_RENT_DATE_COL + " = ? WHERE " + RENT_SYS_PK_COL +
                " = ?"
        );

    }
}
