package se.kth.iv1351.daniel.view;

import se.kth.iv1351.daniel.controller.Controller;
import se.kth.iv1351.daniel.integration.DatabaseException;
import se.kth.iv1351.daniel.model.*;
import se.kth.iv1351.daniel.model.DTO.InstrumentDTO;
import se.kth.iv1351.daniel.model.DTO.RentingDTO;
import se.kth.iv1351.daniel.model.exception.NotExistInDatabaseException;
import se.kth.iv1351.daniel.model.exception.RentalLimitException;

import java.util.List;
import java.util.Scanner;

public class BlockingInterpreter
{
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private final Controller ctrl;

    public BlockingInterpreter(Controller ctrl)
    {
        this.ctrl = ctrl;
    }

    public void handleCmds()
    {
        boolean keepReceivingCmds = true;
        while (keepReceivingCmds)
        {
            try
            {
                CmdLine cmdLine = new CmdLine(readNextLine());
                switch (cmdLine.getCmd())
                {
                    case LIST_A_I:
                    {
                        List<? extends InstrumentDTO> instruments;
                        try
                        {
                            if (cmdLine.getParameter(0).isEmpty())
                            {
                                instruments = ctrl.getAllAvailableInstruments();
                            }
                            else
                            {
                                instruments =
                                        ctrl.getAvailableInstrumentsByType(capitaliseFirstLetter(cmdLine.getParameter(0)));
                            }
                            for (InstrumentDTO instrument : instruments)
                            {
                                System.out.printf("Instrument id: %-5d | Type: %-15s | Brand: %-15s | Price: $%-10s%n",
                                                  instrument.getInstrumentId(),
                                                  instrument.getModel(),
                                                  instrument.getBrand(),
                                                  instrument.getPrice()
                                );
                            }
                        }
                        catch (DatabaseException e)
                        {
                            System.out.println("Ops! There has been an issue with connecting to database, pls try " +
                                               "again");
                        }
                        break;
                    }
                    case RENT:
                    {
                        try
                        {
                            RentingDTO rentingInfo = ctrl.rent(Integer.parseInt(cmdLine.getParameter(0)),
                                      Integer.parseInt(cmdLine.getParameter(1)));
                            System.out.printf("Renting successful! A new rental record for the student with ID: " +
                                              "%-3d, " +
                                              "has been officially submitted as of %-12s. " +
                                              "The student has now rented a total of %d instruments.\n",
                                              rentingInfo.getStudentId(),
                                              rentingInfo.getStartDate(),
                                              rentingInfo.getStudentCurrNoOfBorrowedIns()
                            );
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Pls enter correct format!");
                        }
                        catch (DatabaseException dbException)
                        {
                            System.out.println("Ops! There has been an issue with connecting to database, pls try " +
                                               "again");
                        }
                        catch (RentalLimitException | NotExistInDatabaseException rentalException)
                        {
                            System.out.println(rentalException.getMessage());
                        }
                        break;
                    }
                    case LIST_R_I:
                    {
                        List<? extends RentingRecord> rentedInstruments;
                        try
                        {

                            if (cmdLine.getParameter(0).isEmpty())
                            {
                                rentedInstruments = ctrl.getAllRentedInstrument();
                            }
                            else
                            {
                                rentedInstruments =
                                        ctrl.getRentedInstrumentByStudentId(Integer.parseInt(cmdLine.getParameter(0)));
                            }
                            for (RentingRecord rentedInstrument : rentedInstruments)
                            {
                                System.out.println(rentedInstrument.toString());
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Pls enter correct format!");
                        }
                        catch (DatabaseException dbExc)
                        {
                            System.out.println("Ops! There has been an issue with connecting to database, pls try " +
                                               "again");
                        }
                        catch (NotExistInDatabaseException notExistInDatabaseException)
                        {
                            System.out.println(notExistInDatabaseException.getMessage());
                        }
                        break;
                    }
                    case TERMINATE_RENT:
                    {
                        try
                        {
                            RentingDTO rentingInfo = ctrl.terminateRent(Integer.parseInt(cmdLine.getParameter(0)));
                            System.out.printf("The rental record with ID: %-3d" +
                                              "has been officially terminated as of %-12s.\n",
                                              rentingInfo.getRentId(),
                                              rentingInfo.getEndDate()
                            );
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Pls enter correct format!");
                        }
                        catch (DatabaseException dbExc)
                        {
                            System.out.println(
                                    "Ops! There has been an issue with connecting to database, pls try " + "again");
                        }
                        catch (NotExistInDatabaseException notExistInDatabaseException)
                        {
                            System.out.println(notExistInDatabaseException.getMessage());
                        }
                        break;
                    }
                    case QUIT:
                        keepReceivingCmds = false;
                    case HELP:
                    {
                        for (Command command : Command.values())
                        {
                            if (command == Command.ILLEGAL_COMMAND)
                            {
                                continue;
                            }
                            System.out.println(command.toString().toLowerCase());
                        }
                        break;
                    }
                    default:
                        System.out.println("Illegal command");
                }
            }
            catch (Exception e)
            {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    private String capitaliseFirstLetter(String name)
    {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String readNextLine()
    {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
