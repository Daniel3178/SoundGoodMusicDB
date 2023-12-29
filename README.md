Project Title: SoundGoodMusic Rental System

Description:

This console-based program, developed as the final project for the IV1351 course, allows users to interact with the 
SoundGoodMusic database. The program facilitates the rental, termination, and retrieval of information on available and 
rented musical instruments.

Acknowledgments:

This project uses some utility code from the [jdbc-bank] (https://github/KTH-IV1351/jdbc-bank.git) such as reading
user's input and database connection tools which was provided by the course.

Commands and Usages:

    1. LIST_A_I:
        - 'LIST_A_I gui..': 
                list all available Guitar instruments. You can either 
                provide the full name or a substring of that as shown above.
        - No arguments: 
                List all available instruments.

    2.LIST_R_I:
        - 'LIST_R_I 311':
                Lists all rental records for the student with ID 311.
        - No arguments:
                List all rental records.

    3.RENT:
        - 'RENT 1 311':
                Rent the instrument with ID 1 to the student with ID 311.
    
    4.TERMINATE_RENT:
        - 'TERMINATE_RENT 55':
                Terminate the rental record with ID 55.

    5.HELP:
        - Displays information about available commands.

    6.QUIT:
        - Exist the program.

    7.ILLEGAL_COMMAND:
        - Inform the user about an illegal or unrecognized command.

    How to Run:
        First make sure to create the databse using the CreationQueries.sql 
        found in resources. Then populate the database with the generated 
        data found in data-MASTER.sql. Then provide the information needed 
        to connect to your local database in 
        integration/SoundGoodMusicDAO.java. Once you have done the above 
        procedure, compile and run the program as you would normally do with
        a java program.

    Note: 
        This program has been built by MAVEN and includes a dependency 
        "postgresql", make sure it is configured properly.
        
Developer:
    Daniel Ibrahimi

Course:
    IV1351 - Data Storage, KTH - Stockholm

Date:
    29-12/23