package se.kth.iv1351.daniel.startup;

import se.kth.iv1351.daniel.controller.Controller;
import se.kth.iv1351.daniel.integration.exception.DatabaseException;
import se.kth.iv1351.daniel.integration.exception.WrongCredentialException;
import se.kth.iv1351.daniel.view.BlockingInterpreter;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            try
            {
                System.out.print("Enter your username to database: ");
                String username = getUsername(scanner);
                System.out.print("Enter your password to database: ");
                String password = getPassword(scanner);
              //  new BlockingInterpreter(new Controller(username, password)).handleCmds();
                BlockingInterpreter.getInstance(Controller.getInstance(username,password)).handleCmds();
                break;
            }
            catch (DatabaseException e)
            {
                System.out.println("Could not connect to the database!");
            }
            catch (WrongCredentialException e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("\nPress Enter to try again...");
            scanner.nextLine();
        }
        scanner.close();
    }

    private static String getUsername(Scanner scanner)
    {
        return scanner.nextLine();
    }

    private static String getPassword(Scanner scanner)
    {
        return scanner.nextLine();
    }
}
