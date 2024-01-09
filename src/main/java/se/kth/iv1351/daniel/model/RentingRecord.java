package se.kth.iv1351.daniel.model;

import se.kth.iv1351.daniel.model.dto.RentedInstrumentDTO;
import se.kth.iv1351.daniel.model.dto.StudentDTO;
import se.kth.iv1351.daniel.model.exception.NotExistInDatabaseException;

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
        return String.format("SID: %-4d | Name: %-15s, %-15s | RID: %-5d | Rented since: %-13s " +
                             "| IID: %-5d | TY.: %-15s | BR.: %-15s | PR.: $%-10s | Current number of " +
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
        return String.format("SID: %-4d | Name: %-12s, %-12s | RID: %-5d | Rented since: %-13s " +
                             "| IID: %-5d | TY.: %-10s | BR.: %-10s | PR.: $%-10s",
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
