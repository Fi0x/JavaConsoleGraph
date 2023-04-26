package io.fi0x.javaconsolegraph;

import java.util.ArrayList;

class ConsoleTable
{
    boolean centered = true;
    boolean separators = true;
    int padding = 1;
    private int[] widestStrings;

    void setColumnSizes(String[] headers, String[]... columns)
    {
        widestStrings = new int[headers.length];
        for(int i = 0; i < columns.length; i++)
            widestStrings[i] = getWidestColumnString(headers[i], columns[i]);
    }
    ArrayList<String> getLineString(String[] entries, boolean firstLine)
    {
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < entries.length; i++)
            line.append(getFormattedEntry(entries[i], widestStrings[i], i + 1 == entries.length));

        ArrayList<String> lines = new ArrayList<>();
        if(separators && !firstLine)
        {
            StringBuilder separatorLine = new StringBuilder();
            while(separatorLine.length() < getCombinedLength())
                separatorLine.append("-");
            lines.add(separatorLine.toString());
        }
        lines.add(line.toString());

        return lines;
    }
    static String[][] transformColumnsToRows(String[][] columns)
    {
        String[][] rows = new String[columns[0].length][columns.length];

        for(int c = 0; c < columns.length; c++)
        {
            for(int r = 0; r < columns[0].length; r++)
                rows[r][c] = columns[c][r];
        }

        return rows;
    }

    private static int getWidestColumnString(String header, String[] column)
    {
        int widestString = header.length();
        for(String s : column)
        {
            if(s.length() > widestString)
                widestString = s.length();
        }

        return widestString;
    }
    private String getFormattedEntry(String entry, int maxSize, boolean isLast)
    {
        StringBuilder result = new StringBuilder();

        if(centered)
        {
            int padding = maxSize - entry.length();
            padding /= 2;
            for(; padding > 0; padding--)
                result.append(" ");
        }
        for(int i = 0; i < padding; i++)
            result.append(" ");

        result.append(entry);

        while(result.length() < maxSize + 2 * padding)
            result.append(" ");

        if(separators && !isLast)
            result.append("|");

        return result.toString();
    }
    private int getCombinedLength()
    {
        int length = 0;
        for(int l : widestStrings)
            length += l;
        if(separators)
            length += widestStrings.length - 1;
        length += padding * 2 * widestStrings.length;
        return length;
    }
}
