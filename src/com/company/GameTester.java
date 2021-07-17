package com.company;

import java.io.*;
import java.util.*;

public class GameTester {

    public GameTester()
    {

    }

    public static void main(String[] args) {
        String filename = "GalaxyTraverser.gdf";
        if(args.length > 0)
        {
            filename = args[0];
        }

        Scanner infile = null;

        try
        {
            infile = new Scanner(new File(filename));
        }
        catch(FileNotFoundException e)
        {
            System.err.println("File Not Found: " + filename);
            System.exit(-3);
        }

        Game g = new Game(infile);

        // Print the game. Will print() all Places and Directions.

        g.print();

        // And Let's Play!

        System.out.println("\n Hello Galaxy Traverser!! \n\n");

        g.play();
    }
}
