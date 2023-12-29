package se.kth.iv1351.daniel.model;

public class RentingRecord
{
    private final RentedInstrumentDTO currentRentedInstrument;
    private final StudentDTO currentStudent;

    public RentingRecord(RentedInstrumentDTO currentRentedInstrument, StudentDTO currentStudent) throws NotExistInDatabaseException
    {
        String failureMsg = "";
        if (currentRentedInstrument == null && currentStudent == null)
        {
            failureMsg = "Both instrument and student do not exist in database";
        }
        else if (currentRentedInstrument == null)
        {
            failureMsg = "Instrument does not exist in database";
        }
        else if (currentStudent == null)
        {
            failureMsg = "Student does not exist in database";
        }
        if (currentRentedInstrument == null || currentStudent == null)
        {
            throw new NotExistInDatabaseException(failureMsg);
        }
        this.currentRentedInstrument = currentRentedInstrument;
        this.currentStudent = currentStudent;
    }

    public String getStudentSpecificRecord(){
        return String.format("Student id: %-4d | Name: %-15s, %-15s | Rent id: %-5d | Rented since: %-13s " +
                             "| Instrument id: %-5d | Type: %-15s | Brand: %-15s | Price: $%-10s | Current number of " +
                             "borrowed Ins : %-2d",
                             currentStudent.getStudentId(),
                             currentStudent.getFirstName(),
                             currentStudent.getLastName(),
                             currentRentedInstrument.getRentId(),
                             currentRentedInstrument.getStartRentingDate(),
                             currentRentedInstrument.getInstrumentId(),
                             currentRentedInstrument.getModel(),
                             currentRentedInstrument.getBrand(),
                             currentRentedInstrument.getPrice(),
                             currentStudent.getNumberOfBorrowedInstrument()
        );
    }
    @Override
    public String toString()
    {
        return String.format("Student id: %-4d | Name: %-12s, %-12s | Rent id: %-5d | Rented since: %-13s " +
                             "| Instrument id: %-5d | Type: %-10s | Brand: %-10s | Price: $%-10s",
                             currentStudent.getStudentId(),
                             currentStudent.getFirstName(),
                             currentStudent.getLastName(),
                             currentRentedInstrument.getRentId(),
                             currentRentedInstrument.getStartRentingDate(),
                             currentRentedInstrument.getInstrumentId(),
                             currentRentedInstrument.getModel(),
                             currentRentedInstrument.getBrand(),
                             currentRentedInstrument.getPrice()
        );

    }

}
