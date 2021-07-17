package com.company;

import java.util.*;

public class Game {

    private String name;        // The Game's name
    private static Vector<Place> dirPlace = new Vector<>(); // Vector of planets
    private Scanner keys;       // for player input
    private static Place currentP;


    public Game(Scanner infile) {
        dirPlace = new Vector<>();

        // Now to start parsing the file.

        // Parse first line
        String line = CleanLineScanner.getCleanLine(infile);
        Scanner lineScanner = new Scanner(line);
        String word = lineScanner.next();
        if(!word.equalsIgnoreCase("GDF"))
        {
            System.err.println("Error parsing input file. \"GDF\" not found.\n");
            System.exit(-1);
        }

        double version = lineScanner.nextDouble();
        if(version != 1.0)
        {
            System.err.println("Error parsing input file. Wrong version number" + version);
            System.exit(-2);
        }

        lineScanner.skip("[\t]*");

        name = lineScanner.nextLine();

        // Now look for the PLACES keyword, and call new Place(Scanner) as needed.

        int nPlaces = keywordCount(infile,"PLACES"); // Error here

        for(int i = 0; i < nPlaces; i++)
        {
            dirPlace.add(new Place(infile));
        }

        // Now look for the DIRECTIONS keyword, and call new Direction(Scanner) as needed.

        int nDirections = keywordCount(infile, "DIRECTIONS");

        for(int i = 0; i < nDirections; i++)
        {
            new Direction(infile);
        }
    }

    private int keywordCount(Scanner infile, String keyword)
    {
        String line = CleanLineScanner.getCleanLine(infile);
        Scanner lineScanner = new Scanner(line);
        String word = lineScanner.next();

        if(!word.equalsIgnoreCase(keyword))
        {
            System.err.println("Error parsing input file. \"" + keyword + "\" not found.\n");
            System.exit(-4);
        }
        int count = lineScanner.nextInt();

        if(count <= 0)
        {
            System.err.println("Error - Invalid counter found \"" + line + "\"");
            System.exit(-5);
        }

        lineScanner.close();
        return count;
    }

    public void print() {
        System.out.println(name);
    }

    public void play() {
        System.out.println("Now playing the game" + name + ".");

        if (dirPlace.size() < 1)       // error checking
        {
            return;
        }

        currentP = dirPlace.get(0);        // gets entrance hall as current place

        Scanner keys = new Scanner(System.in);     // keyboard input
        String line;

        while(true)
        {
            System.out.print("You are currently here: ");
            currentP.display();

            // Get input from user
            System.out.println("> ");
            line = keys.nextLine();
            line = line.trim(); // Remove any leading and trailing spaces

            // Now process the user input
            // First check for commands that take no arguments

            if(line.equalsIgnoreCase("QUIT") || line.equalsIgnoreCase("EXIT")) // exit case
            {
                break;
            }
            else if(line.length() > 3 && line.substring(0,3).equalsIgnoreCase("GO "))             // go to the destination using enums
            {
                line = line.substring(3).trim();
                currentP = currentP.followDirection(line);
                continue;
            }

            System.out.println("What action will you take? ");
        }
    }
}
