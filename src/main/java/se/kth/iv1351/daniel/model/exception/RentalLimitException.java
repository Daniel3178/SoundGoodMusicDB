package se.kth.iv1351.daniel.model.exception;

import se.kth.iv1351.daniel.model.dto1.StudentDTO;

public class RentalLimitException extends Exception
{

    private final StudentDTO studentWhoCanNotRent;

    public RentalLimitException(StudentDTO student)
    {
        super("Unable to rent! The student with ID " + student.getStudentId()
                + " has already reached the maximum limit for allowed rentals.");
        this.studentWhoCanNotRent = student;
    }

    public StudentDTO getStudentWhoCanNotRent()
    {
        return studentWhoCanNotRent;
    }
}
