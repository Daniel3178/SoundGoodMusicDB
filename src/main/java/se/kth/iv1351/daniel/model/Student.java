package se.kth.iv1351.daniel.model;

import se.kth.iv1351.daniel.model.DTO.StudentDTO;

public class Student implements StudentDTO
{
    private final String firstName;
    private final String lastName;
    private final int studentId;
    private int numberOfBorrowedInstrument;


    public Student(String firstName, String lastName, int studentId, int numberOfBorrowedInstrument)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.numberOfBorrowedInstrument = numberOfBorrowedInstrument;
    }

    public Student(int studentId, int numberOfBorrowedInstrument)
    {
        this.firstName = null;
        this.lastName = null;
        this.studentId = studentId;
        this.numberOfBorrowedInstrument = numberOfBorrowedInstrument;
    }

    public void increaseBorrowedInsByOne()
    {
        this.numberOfBorrowedInstrument ++;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public int getStudentId()
    {
        return studentId;
    }

    @Override
    public int getNumberOfBorrowedInstrument()
    {
        return numberOfBorrowedInstrument;
    }

}
