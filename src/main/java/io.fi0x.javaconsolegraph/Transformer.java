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
}
