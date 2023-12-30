package io.fi0x.javaconsolegraph;

import java.util.ArrayList;

/**
 * This class contains methods, to transform arrays and lists into a single String,
 * that is formatted to display a nice looking graph or table.
 */
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
     *
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
     *
     * @param columnHeaders The headers of each column that the table should have.
     * @param columns       The individual entries of each column.
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
     *
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
            if(columns[0].length - 1 >= 0)
                System.arraycopy(columns[column], 1, content[column], 0, columns[0].length - 1);
        }
        return getTable(headers, content);
    }
    /**
     * Get a correctly formatted {@link String} that will print out a table.
     *
     * @param rows The individual rows of the table, including their headers.
     * @return A correctly formatted string that will create a table with the provided headers and entries.
     */
    public String getTable(ArrayList<String[]> rows)
    {
        String[] headers = rows.get(0);
        String[][] content = new String[rows.size() - 1][rows.get(0).length];
        for(int column = 0; column < rows.get(0).length; column++)
        {
            for(int row = 1; row < rows.size(); row++)
                content[row][column] = rows.get(row)[column];
        }
        return getTable(headers, content);
    }
    /**
     * Change the behaviour of the table formatting.
     *
     * @param centeredEntries Weather or not entries should be centered in their column.
     * @param useSeparators   Weather or not separators should be added between columns and rows.
     * @param entryPadding    Weather or not entries should get extra spaces before and after the normal padding.
     */
    public void setTableSettings(boolean centeredEntries, boolean useSeparators, int entryPadding)
    {
        tableInstance.centered = centeredEntries;
        tableInstance.separators = useSeparators;
        tableInstance.padding = entryPadding;
    }

    /**
     * Get a correctly formatted {@link String} that will print out a graph.
     *
     * @param values The individual values that get mapped on the xAxis.
     * @return A correctly formatted {@link String}-Array that represents a graph. Can be used to create a table.
     */
    public String[][] getGraphString(double[] values)
    {
        String[][] layout = graphInstance.getBlankGraph();
        graphInstance.updateOutline(layout);
        graphInstance.fillInValues(layout, values);

        return layout;
    }
    /**
     * Change the behaviour of the graph formatting.
     *
     * @param xAxis   How the xAxis should be named.
     * @param yAxis   How the yAxis should be named.
     * @param xMin    What the lowest value on the xAxis is.
     * @param yMin    What the lowestValue on the yAxis is.
     * @param scaleX  How large the steps between individual xValues should be.
     * @param scaleY  How large the steps between individual yValues should be.
     * @param xValues How many individual values should be displayed in x-direction.
     * @param yValues How many individual values should be displayed in y-direction.
     */
    public void setGraphSettings(String xAxis, String yAxis, double xMin, double yMin, double scaleX, double scaleY, int xValues, int yValues)
    {
        graphInstance.xAxisName = xAxis;
        graphInstance.yAxisName = yAxis;
        graphInstance.xMin = xMin;
        graphInstance.yMin = yMin;
        graphInstance.scaleX = scaleX;
        graphInstance.scaleY = scaleY;
        graphInstance.xSize = xValues;
        graphInstance.ySize = yValues;
    }
}
