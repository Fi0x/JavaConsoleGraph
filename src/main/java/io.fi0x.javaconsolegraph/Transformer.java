package io.fi0x.javaconsolegraph;

import java.util.ArrayList;

public class Transformer
{
    private final ConsoleTable tableInstance;
    private final ConsoleGraph graphInstance;

    private static Transformer instance;

    private Transformer()
    {
        tableInstance = new ConsoleTable();
        graphInstance = new ConsoleGraph();
    }
    /**
     * Get the {@link Transformer}-singleton and create it if it does not exist yet.
     * @return The instance of the {@link Transformer}-singleton.
     */
    public static Transformer getInstance()
    {
        if(instance == null)
            instance = new Transformer();

        return instance;
    }

    /**
     * Get a correctly formatted {@link String} that will print out a table.
     * @param columnHeaders The headers of each column that the table should have.
     * @param columns The individual entries of each column.
     * @return A correctly formatted string that will create a table with the provided headers and entries.
     */
    public String getTable(String[] columnHeaders, String[][] columns)
    {
        tableInstance.setColumnSizes(columnHeaders, columns);
        ArrayList<String> lines = new ArrayList<>(tableInstance.getLineString(columnHeaders, true));

        for(String[] line : ConsoleTable.transformColumnsToRows(columns))
            lines.addAll(tableInstance.getLineString(line, false));

        StringBuilder result = new StringBuilder(lines.get(0));
        for(int i = 1; i < lines.size(); i++)
            result.append("\n").append(lines.get(i));

        return result.toString();
    }
    /**
     * Get a correctly formatted {@link String} that will print out a table.
     * @param columns The individual columns of the table, including their headers.
     * @return A correctly formatted string that will create a table with the provided headers and entries.
     */
    public String getTable(String[][] columns)
    {
        String[] headers = new String[columns.length];
        String[][] content = new String[columns.length][columns[0].length - 1];
        for(int column = 0; column < columns.length; column++)
        {
            headers[column] = columns[column][0];
            for(int row = 1; row < columns[0].length; row++)
                content[column][row - 1] = columns[column][row];
        }
        return getTable(headers, content);
    }
    /**
     * Change the behaviour of the table formatting.
     * @param centeredEntries Weather or not entries should be centered in their column.
     * @param useSeparators Weather or not separators should be added between columns and rows.
     * @param entryPadding Weather or not entries should get extra spaces before and after the normal padding.
     */
    public void setTableSettings(boolean centeredEntries, boolean useSeparators, int entryPadding)
    {
        tableInstance.centered = centeredEntries;
        tableInstance.separators = useSeparators;
        tableInstance.padding = entryPadding;
    }

    public String getGraphString()
    {
        return null;
    }
}
