package com.company;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Place {

    private int idnumber;
    private String nameofplace;
    private String descriptionofplace;
    private Vector<Direction> dirVector = new Vector<>();  // create direction vector
                                                                    // to store directions
    private static boolean firstPlace = true;
    private static HashMap<Integer, Place> places = new HashMap<>();

    // Constructor (Data Fields)
    public Place(int id, String name, String description) {
        this.idnumber = id;
        this.nameofplace = name;
        this.descriptionofplace = description;
        dirVector = new Vector<>();
        places.put(id, this);
        return;
    }

    public Place(Scanner infile) {
        // First initialize the collections
        dirVector = new Vector<>();

        // Then to read data from the file
        String line = CleanLineScanner.getCleanLine(infile);        // cleans lines
        Scanner lineScanner = new Scanner(line);
        idnumber = lineScanner.nextInt();                         // stores id from file
        lineScanner.skip("[ \t]*");                         // skips tabs and spaces
        nameofplace = lineScanner.nextLine();                      // stores name from file

        line = CleanLineScanner.getCleanLine(infile);        // cleans lines
        lineScanner = new Scanner(line);
        int nLines = lineScanner.nextInt();                       // gets the number of lines from description
        descriptionofplace = "";                                  // start outwith empty description string
        for (int i = 0; i < nLines; i++)
        {
            descriptionofplace = descriptionofplace + CleanLineScanner.getCleanLine(infile) + "\n";     // add description from file
        }
        // finally to add this Place to the static collection
        places.put(idnumber, this); // Check for duplicates

        // If this is the first Place created, create two pseudo places
        if(firstPlace)
        {
            firstPlace = false;
            new Place(1, "Exit", "Pseudo place representing the exit");
            new Place(0, "Nowhere", "This place doesn't really exist.");
        }

        places.put(idnumber,this);

    }

    public static Place getPlaceByID(int id)       // this was meant to use for parsing but
    {                                       // could not get to that part.
        return places.get(id);
    }

    public String getName() {
        return this.nameofplace;
    }

    public String getDescription() {
        return this.descriptionofplace;
    }

    public void addDirection(Direction d) {
        this.dirVector.add(d);
    }

    public Place followDirection(String s) {
        for (Direction d : dirVector)    // for each direction in the Direction vector
        {                                  // if the direction and input string match
            if(d.match(s))               // then follow through to the destination
            {
                return d.follow();
            }
        }
        System.out.println("No path exists.");  // if input string does not match
        return this;                            // return current place because no
    }

    public void print() {
        System.out.println(this.idnumber);
        System.out.println(this.nameofplace);
        System.out.println(this.descriptionofplace);
    }

    public void display()                 // displays possible directions and
    {                                     // current place
        System.out.println(getName());
        System.out.println(getDescription());
        System.out.println("*Possible Directions: ");
        for (Direction d : dirVector)
        {
            d.printDirection();
        }
    }
}
