package se.kth.iv1351.daniel.view;

public class CmdLine
{
    private static final String PARAM_DELIMETER = " ";
    private String[] params;
    private Command cmd;
    private final String enteredLine;

    CmdLine(String enteredLine)
    {
        this.enteredLine = enteredLine;
        parseCmd(enteredLine);
        extractParams(enteredLine);
    }

    Command getCmd()
    {
        return cmd;
    }

    String getUserInput()
    {
        return enteredLine;
    }

    String getParameter(int index)
    {
        if (params == null)
        {
            return null;
        }
        if (index >= params.length)
        {
            return null;
        }
        return params[index];
    }

    private String removeExtraSpaces(String source)
    {
        if (source == null)
        {
            return source;
        }
        String oneOrMoreOccurrences = "+";
        return source.trim().replaceAll(PARAM_DELIMETER + oneOrMoreOccurrences, PARAM_DELIMETER);
    }

    private void parseCmd(String enteredLine)
    {
        int cmdNameIndex =0;
        try
        {
            String trimmed = removeExtraSpaces(enteredLine);
            if (trimmed == null)
            {
                cmd = Command.ILLEGAL_COMMAND;
                return;
            }
            String[] enteredTokens = trimmed.split(PARAM_DELIMETER);
            cmd = Command.valueOf(enteredTokens[cmdNameIndex].toUpperCase());
        }
        catch (Exception failedToReadCmd)
        {
            cmd = Command.ILLEGAL_COMMAND;
        }
    }

    private void extractParams(String enteredLine)
    {
        if (enteredLine == null)
        {
            params = null;
            return;
        }
        String paramPartOfCmd = removeExtraSpaces(removeCmd(enteredLine));
        if (paramPartOfCmd == null)
        {
            params = null;
            return;
        }
        params = paramPartOfCmd.split(PARAM_DELIMETER);
    }

    private String removeCmd(String enteredLine)
    {
        if (cmd == Command.ILLEGAL_COMMAND)
        {
            return enteredLine;
        }
        int indexAfterCmd = enteredLine.toUpperCase().indexOf(cmd.name()) + cmd.name().length();
        String withoutCmd = enteredLine.substring(indexAfterCmd);
        return withoutCmd.trim();
    }

}
