package se.kth.iv1351.daniel.view;

import java.util.List;
import java.util.Scanner;

import se.kth.iv1351.daniel.controller.Controller;
import se.kth.iv1351.daniel.integration.exception.DatabaseException;
import se.kth.iv1351.daniel.model.RentingRecord;
import se.kth.iv1351.daniel.model.dto.InstrumentDTO;
import se.kth.iv1351.daniel.model.dto.RentingDTO;
import se.kth.iv1351.daniel.model.exception.NotExistInDatabaseException;
import se.kth.iv1351.daniel.model.exception.RentalLimitException;

public class BlockingInterpreter
{
    private static final String PROMPT = "> ";
    private final Scanner console;
    private final Controller ctrl;
    private static BlockingInterpreter blockingInterpreter = null;

    private BlockingInterpreter(Controller ctrl)
    {
        this.console = new Scanner(System.in);
        this.ctrl = ctrl;
    }

    public static BlockingInterpreter getInstance(Controller ctrl)
    {
        if (blockingInterpreter == null)
        {
            blockingInterpreter = new BlockingInterpreter(ctrl);
        }
        return blockingInterpreter;
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
                    case LIST_A_I -> handleListAvaInst(cmdLine);
                    case RENT -> handleRent(cmdLine);
                    case LIST_R_I -> handleListRentInst(cmdLine);
                    case TERMINATE_RENT -> handleTerminateRental(cmdLine);
                    case QUIT -> keepReceivingCmds = false;
                    case HELP -> handleHelp();
                    default -> System.out.println("Illegal command");
                }
            }

            catch (NumberFormatException e)
            {
                System.out.println("Pls enter correct format!");
            }
            catch (DatabaseException dbException)
            {
                System.out.println("Ops! There has been an issue with connecting to database, pls try again");
            }
            catch (RentalLimitException | NotExistInDatabaseException rentalException)
            {
                System.out.println(rentalException.getMessage());
            }
            catch (Exception e)
            {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void handleHelp()
    {
        for (Command command : Command.values())
        {
            if (command == Command.ILLEGAL_COMMAND)
            {
                continue;
            }
            System.out.println(command.toString().toLowerCase());
        }
    }

    private void handleTerminateRental(CmdLine cmdLine)
            throws NumberFormatException, DatabaseException, NotExistInDatabaseException
    {
        RentingDTO rentingInfo = ctrl.terminateRent(Integer.parseInt(cmdLine.getParameter(0)));
        System.out.printf("The rental record with ID: %-3d" +
                                  "has been officially terminated as of %-12s.\n",
                          rentingInfo.getRentId(),
                          rentingInfo.getEndDate()
        );
    }

    private void handleListRentInst(CmdLine cmdLine)
            throws NumberFormatException, DatabaseException, NotExistInDatabaseException
    {
        List<? extends RentingRecord> rentedInstruments;
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

    private void handleListAvaInst(CmdLine cmdLine) throws DatabaseException
    {
        List<? extends InstrumentDTO> instruments;

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

    private void handleRent(CmdLine cmdLine)
            throws NumberFormatException, DatabaseException, RentalLimitException, NotExistInDatabaseException
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
