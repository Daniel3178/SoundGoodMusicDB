package se.kth.iv1351.daniel.startup;

import se.kth.iv1351.daniel.controller.Controller;
import se.kth.iv1351.daniel.view.BlockingInterpreter;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your password to database: ");
            String password = scanner.nextLine();
            new BlockingInterpreter(new Controller(password)).handleCmds();
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong!");
        }
    }
}