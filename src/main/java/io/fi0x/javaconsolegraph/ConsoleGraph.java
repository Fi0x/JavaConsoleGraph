package io.fi0x.javaconsolegraph;

import java.util.Arrays;

class ConsoleGraph
{
    String xAxisName = "x";
    String yAxisName = "y";
    double xMin;
    double yMin;
    double scaleX = 1;
    double scaleY = 1;
    int xSize;
    int ySize;

    void updateOutline(String[][] currentGraph)
    {
        currentGraph[0][0] = yAxisName;
        for(int rowCount = 2; rowCount < currentGraph[0].length; rowCount++)
            currentGraph[0][currentGraph[0].length - rowCount] = "" + (yMin + scaleY * (rowCount - 2));

        for(int columnCount = 1; columnCount < currentGraph.length - 1; columnCount++)
            currentGraph[columnCount][currentGraph[0].length - 1] = "" + (xMin + scaleX * columnCount - 1);
        currentGraph[currentGraph.length - 1][currentGraph[0].length - 1] = xAxisName;
    }
    String[][] getBlankGraph()
    {
        String[][] layout = new String[xSize + 2][ySize + 2];
        for(String[] c : layout)
            Arrays.fill(c, "");
        return layout;
    }
    void fillInValues(String[][] graph, double[] values)
    {
        for(int i = 0; i < values.length; i++)
            graph[i + 1][graph[0].length - getBestY(values[i]) - 2] = "" + values[i];
    }

    private int getBestY(double value)
    {
        value /= scaleY;
        int idx = (int) (value + 0.5);

        if(idx < 0)
            idx = 0;
        if(idx >= ySize)
            idx = ySize - 1;

        return idx;
    }
}
