package com.company;

import java.util.Scanner;

public class CleanLineScanner {
    public CleanLineScanner()
    {
    }

    /*
     *  Takes a scanner as input and returns a clean line with no comments
     */
    public static String getCleanLine(Scanner s)
    {
        String line;

        while (s.hasNextLine()) {
            // Check to see if there is a next line in the file
            // Grab line from file set equal to "line"
            line = s.nextLine();

            // Find index of comment
            int commentStart = line.indexOf("//");

            //If commentStart ==0, there is no comment on the line
            if (commentStart == 0) {
                continue;
            }
            //If commentStart > 0, comment, exist. Extract characters before comment begins
            if (commentStart > 0) {
                line = line.substring(0, commentStart);
            }

            // Eliminate any unnecessary spaces
            line = line.trim();

            // Check to see if line contains only comments. If not, return the line
            if (line.length() > 0) {
                return line;
            }
        }

        return null;
    }

    /*
     *  Searches for the number following the keyword passed in
     */
    public static int keyWordCount(Scanner sc, String keyword)
    {
        // Retrieve clean line from the file
        String line = getCleanLine(sc);

        // Error with grabbing line. Trigger assertion error
        assert line != null;

        // Pass the line with a scanner to make it easier to parse
        Scanner inputParser = new Scanner(line);

        // Get first word of the line
        String word = inputParser.next();

        // If the "keyword" is not found, trigger error and exit
        if (!word.equalsIgnoreCase(keyword))
        {
            System.err.println("Error parsing file. Keyword |" + keyword + "| not found");
            System.exit(-4);
        }

        // Get the integer following the keyword
        int count = inputParser.nextInt();

        // If count is less than 0, trigger error and exit
        if (count <= 0)
        {
            System.err.println("Invalid counter found. Counter = " + count);
            System.exit(-5);
        }

        // Close scanner
        inputParser.close();

        return count;
    }
}
