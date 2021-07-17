package com.company;

import java.util.Scanner;

public class Direction {

    private int idnumber;
    private Place fromd;
    private Place tod;
    private String direction;
    private DirType dirT;   // utilize the enum class
    private boolean ifWhalesPresent = false;

    private enum DirType          // enum which gives player the option to type their action in multiple ways
    {
        NONE("None", "None"), N("North", "N"), S("South", "S"), E("East", "E"), W("West", "W"), U("Up", "U"),
        D("Down", "D"), NE("Northeast", "NE"), NW("Northwest", "NW"), SE("Southeast", "SE"), SW("Southwest", "SW"),
        NNE("North-Northeast", "NNE"), NNW("North-Northwest", "NNW"), ENE("East-Northeast", "ENE"), WNW("West-Northwest", "WNW"),
        ESE("East-Southeast", "ESE"), WSW("West-Southwest", "WSW"), SSE("South-Southeast", "SSE"), SSW("South-Southwest", "SSW");

        private String text;
        private String abbreviation;

        DirType(String t, String a)   // 2 fields in the enums for increase in choice
        {
            text = t;
            abbreviation = a;
        }

        public boolean match(String s)     // matches string with enum
        {
            if(this == NONE) return false;
            s = s.trim(); // remove leading spaces
            if(text.equalsIgnoreCase(s) || abbreviation.equalsIgnoreCase(s))    // gives the user more options
            {                                                                   // with case insensitive words
                return true;
            }
            else
            {
                return false;
            }
        }

        public String toString()   // returns the text
        {
            return this.text;
        }

    }

    public Direction(int ID, Place from, Place to, String dir){

        this.idnumber = ID;
        this.fromd = from;
        this.tod = to;
        this.direction = dir;

    }

    // Constructors of the Direction class

    public Direction(Scanner infile)
    {
        String line = CleanLineScanner.getCleanLine(infile);
        Scanner lineScanner = new Scanner(line);

        idnumber = lineScanner.nextInt();

        int sourceID = lineScanner.nextInt();
        fromd = Place.getPlaceByID(sourceID);

        String dir = lineScanner.next();
        dirT = DirType.NONE;
        for(DirType t : DirType.values())
        {
            if(t.match(dir))
            {
                dirT = t;
                break;
            }
        }

        ifWhalesPresent = false;
        int destID = lineScanner.nextInt();
        if(destID <= 0)
        {
            ifWhalesPresent = true;
            destID = -destID;
        }
        tod = Place.getPlaceByID(destID);

        // And finally add this Direction to its from Place
        fromd.addDirection(this);

        return;
    }

    public boolean match(String s) {

        if(dirT.match(s))
        {
            return true;
        }
        else return false;
    }

    public void spacewhales() { // space whales make it difficult to travel to a planets when present

        if (isWhalespresent() == true)    // if whale is there then warn player they can't proceed
        {
            System.out.println("It is difficult to travel to location while Space whale is present");
            System.out.println("Maybe I can do something about it...");
            return;
        }
        else
        {
            ifWhalesPresent = true;       // whale is there player can't proceed
        }
    }

    public void unlock() {

        if (isWhalespresent())
        {
            ifWhalesPresent = false;
        }
        else
        {
            System.out.println("The Space Whales are already gone...");
            return;
        }

    }

    public boolean isWhalespresent() {
        if (ifWhalesPresent == true)  // if the locked variable is true then the path is locked
        {
            return true;
        }
        else return false;
    }

    public Place follow() {
        if(isWhalespresent())
        {
            System.out.println("Can't traverse, space whales present");
            return this.fromd;
        }
        else {
            return this.tod;
        }
    }

    public void printDirection (){
        System.out.println(this.dirT);
    }
    public void print() {
        System.out.println("Directions class information: ");
        System.out.println("ID number: " + this.idnumber);
        System.out.println("From: " + this.fromd);
        System.out.println("To: " + this.tod);
        System.out.println(this.direction);
    }
}
