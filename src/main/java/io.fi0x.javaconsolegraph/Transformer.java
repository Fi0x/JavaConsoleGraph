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
     * @return A correctly formatted {@link String} that will create a table with the provided headers and entries.
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

    /**
     * Get a correctly formatted {@link String} that will print out a graph.
     * @param values The individual values that get mapped on the xAxis.
     * @return A correctly formatted {@link String}-Array that represents a graph. Can be used to create a table.
     */
    public String[][] getGraphString(double[] values)
    {
        String[][] layout = new String[values.length + 1][values.length + 1];

        layout[0][0] = graphInstance.yAxisName;
        for(int i = 1; i < layout[0].length; i++)
            layout[0][layout[0].length - i] = "" + (graphInstance.yMin + graphInstance.scaleY * (i - 1));

        for(int columnCount = 1; columnCount < layout.length; columnCount++)
        {
            for(int rowCount = 0; rowCount < layout[0].length - 1; rowCount++)
                layout[columnCount][rowCount] = "";//TODO: Fill with correct values

            layout[columnCount][layout[0].length - 1] = "" + (graphInstance.xMin + graphInstance.scaleX * columnCount);
        }
        layout[layout.length - 1][layout[0].length - 1] = graphInstance.xAxisName;

        return layout;
    }
    /**
     * Change the behaviour of the graph formatting.
     * @param xAxis How the xAxis should be named.
     * @param yAxis How the yAxis should be named.
     * @param xMin What the lowest value on the xAxis is.
     * @param yMin What the lowestValue on the yAxis is.
     * @param scaleX How large the steps between individual xValues should be.
     * @param scaleY How large the steps between individual yValues should be.
     */
    public void setGraphSettings(String xAxis, String yAxis, double xMin, double yMin, double scaleX, double scaleY)
    {
        graphInstance.xAxisName = xAxis;
        graphInstance.yAxisName = yAxis;
        graphInstance.xMin = xMin;
        graphInstance.yMin = yMin;
        graphInstance.scaleX = scaleX;
        graphInstance.scaleY = scaleY;
    }
}
