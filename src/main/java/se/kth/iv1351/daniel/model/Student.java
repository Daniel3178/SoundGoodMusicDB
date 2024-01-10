package se.kth.iv1351.daniel.model;

import se.kth.iv1351.daniel.model.dto.StudentDTO;

public class Student implements StudentDTO
{

    private final String firstName;
    private final String lastName;
    private final int studentId;
    private int numberOfBorrowedInstrument;

    public static class Builder
    {

        private String firstName;
        private String lastName;
        private int studentId;
        private int numberOfBorrowedInstrument;

        public Builder()
        {
            this.firstName = null;
            this.lastName = null;
            studentId = -1;
            numberOfBorrowedInstrument = -1;
        }

        public Builder setFirstName(String firstName)
        {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName)
        {
            this.lastName = lastName;
            return this;
        }

        public Builder setStudentId(int studentId)
        {
            this.studentId = studentId;
            return this;
        }

        public Builder setNumberOfBorrowedInstrument(int numberOfBorrowedInstrument)
        {
            this.numberOfBorrowedInstrument = numberOfBorrowedInstrument;
            return this;
        }

        public Student build()
        {
            return new Student(this);
        }

    }

    public Student(Builder builder)
    {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.studentId = builder.studentId;
        this.numberOfBorrowedInstrument = builder.numberOfBorrowedInstrument;
    }

    public void increaseBorrowedInsByOne()
    {
        this.numberOfBorrowedInstrument++;
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
