package se.kth.iv1351.daniel.model;

import se.kth.iv1351.daniel.model.dto.RentedInstrumentDTO;
import se.kth.iv1351.daniel.model.dto.StudentDTO;
import se.kth.iv1351.daniel.model.exception.NotExistInDatabaseException;

public record RentingRecord(RentedInstrumentDTO instrument, StudentDTO student)
{
    public String getStudentSpecificRecord()
    {
        return String.format("SID: %-4d | Name: %-15s, %-15s | RID: %-5d | Rented since: %-13s " +
                                     "| IID: %-5d | TY.: %-15s | BR.: %-15s | PR.: $%-10s | Current number of " +
                                     "borrowed Ins : %-2d",
                             student.getStudentId(),
                             student.getFirstName(),
                             student.getLastName(),
                             instrument.getRentId(),
                             instrument.getStartRentingDate(),
                             instrument.getInstrumentId(),
                             instrument.getModel(),
                             instrument.getBrand(),
                             instrument.getPrice(),
                             student.getNumberOfBorrowedInstrument()
        );
    }

    @Override
    public String toString()
    {
        return String.format("SID: %-4d | Name: %-12s, %-12s | RID: %-5d | Rented since: %-13s " +
                                     "| IID: %-5d | TY.: %-10s | BR.: %-10s | PR.: $%-10s",
                             student.getStudentId(),
                             student.getFirstName(),
                             student.getLastName(),
                             instrument.getRentId(),
                             instrument.getStartRentingDate(),
                             instrument.getInstrumentId(),
                             instrument.getModel(),
                             instrument.getBrand(),
                             instrument.getPrice()
        );

    }

}
