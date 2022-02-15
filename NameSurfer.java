/*
    CSC 220-04
    Fall 2021
    Project 4: Name Surfer
    John DeGood
    Amaan Gadatia, Casey Conti
    December 9, 2021

    This program is the driver class for the NameRecord class. All of the data from the SSA text
    files from 1880-2020 are opened, read, and stored in two ArrayLists: one for female names and
    one for male names. A menu is offered to the user for getting particular information for a 
    certain name. If 1, 2, 3, or 4 is entered, the user is prompted to enter a name. That name
    is then searched for in either of the two ArrayLists (ignoring case) and if the name is found,
    the desired information is printed and the menu is displayed again. If the name is not found,
    an error message is printed and the menu is offered again. To exit the program, the user enters
    5.
*/

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class NameSurfer {

    // offers the user a menu of selections
    private void userMenu() {
        System.out.println("1 - Find best year for a female name");
        System.out.println("2 - Find best rank for a female name");
        System.out.println("3 - Find best year for a male name");
        System.out.println("4 - Find best rank for a male name");
        System.out.println("5 - Quit");
        System.out.println("Enter your selection");
    }

    // prompts the user to enter a name
    private String userName(Scanner s) {
        String userChoice;

        System.out.print("Enter a name: ");
        userChoice = s.next();

        return userChoice;
    }

    // searches for the inputted name in the specified NameRecord ArrayList
    // if name is found, return the NameRecord object, otherwise return null
    private NameRecord nameSearch(ArrayList<NameRecord> n, String name) {
        for (int i = 0; i < n.size(); ++i) {
            if (name.equalsIgnoreCase(n.get(i).getName())) {
                return n.get(i);
            }
        }
        return null;
    }

    // creates new NameRecord object for name that does not exist for either ArrayList
    // or sets the rank of the name if it exists in either ArrayList (depending on which is passed in)
    private static void processNameRecord(ArrayList<NameRecord> records, String currName, int rank, int year) {
        NameRecord record;              // NameRecord object
        boolean nameFound = false;      // checks if name was found in specified ArrayList

        // if name already exists in specified ArrayList, set the rank for the name
        // for the particular year
        if (records.size() > 0) {
            for (int j = 0; j < records.size(); ++j) {
                if (records.get(j).getName().equalsIgnoreCase(currName)) {
                    nameFound = true;
                    record = records.get(j);
                    record.setRank(year, rank);
                    break;
                }
            }
        }
        // if name not found in specified ArrayList, add new NameRecord to ArrayList
        // and sets its rank for the particular year
        if (nameFound == false) {
            record = new NameRecord(currName);
            records.add(record);
            record.setRank(year, rank);
        }
    }

    public static void main(String[] args) throws IOException {
        NameSurfer s = new NameSurfer();    // driver class object     
        FileInputStream fBS = null;         // File input stream
        Scanner inFS = null;                // Scanner object for file input stream
        Scanner scnr = new Scanner(System.in);  // Scanner object for taking in number selection choice or name
        ArrayList<NameRecord> males = new ArrayList<NameRecord>();      // ArrayList of male names
        ArrayList<NameRecord> females = new ArrayList<NameRecord>();    // ArrayList of female names
        int choice = 0;                 // user enters either 1 to 5
        String nameChoice = "";         // user inputs name they want info about
        String nameInfo = "";           // captures the name, sex, and # of occurrences per line in each text file
        NameRecord male;                // NameRecord object for male name
        NameRecord female;              // NameRecord object for female name
        NameRecord record;              // NameRecord object 
        String currName;                // current name that is read from a file
        String currSex;                 // current sex that is read from a file
        int femaleRank;                 // rank for a female name
        int maleRank;                   // rank for a male name

        // opening and reading text files
        for (int i = NameRecord.START; i <= NameRecord.END; ++i) {
            String str = "names/yob" + i + ".txt";
            fBS = new FileInputStream(str);
            inFS = new Scanner(fBS);
            femaleRank = 0;
            maleRank = 0;

            // checks if current text file has a line to be read
            while(inFS.hasNextLine()) {
                nameInfo = inFS.nextLine();         // reads the current line in text file, then shifts a line down

                // separates the name, sex, and number of occurrences where
                // the commas occur into a String array
                String [] data = nameInfo.split(",");
                currName = data[0];
                currSex = data[1];
                
                
                // if currSex equals "F", call processNameRecord for females ArrayList
                // otherwise call it for males ArrayList
                if (currSex.equals("F")) {
                    processNameRecord(females, currName, ++femaleRank, i - NameRecord.START);
                }

                else {
                    processNameRecord(males, currName, ++maleRank, i - NameRecord.START);
                }     
            }
            // close the current year's file
            fBS.close();
        }
        

        // offers user a menu, initial selection
        // 5 selection choices
        while(choice != 5) {
            s.userMenu();

            choice = scnr.nextInt();

            switch(choice) {
                // if users enters 1
                case 1:
                    nameChoice = s.userName(scnr);
                    record = s.nameSearch(females, nameChoice);
                    if (record == null) {
                        System.out.println("ERROR: name not found\n");
                    }
                    else {
                        System.out.println("The best year for the female name " + record.getName() + " was " + record.bestYear() + "\n");
                    }
                    break;
                // if user enters 2
                case 2:
                    nameChoice = s.userName(scnr);
                    record = s.nameSearch(females, nameChoice);
                    if (record == null) {
                        System.out.println("ERROR: Name not found\n");
                    } 
                    else {
                        System.out.println("The best rank for the female name " + record.getName() + " was " + record.bestRank() + "\n");
                    }
                    break;
                // if user enters 3
                case 3:
                    nameChoice = s.userName(scnr);
                    record = s.nameSearch(males, nameChoice);
                    if (record == null) {
                        System.out.println("ERROR: name not found\n");
                    }
                    else {
                        System.out.println("The best year for the male name " + record.getName() + " was " + record.bestYear() + "\n");
                    }
                    break;
                // if user enters 4
                case 4:
                    nameChoice = s.userName(scnr);
                    record = s.nameSearch(males, nameChoice);
                    if (record == null) {
                        System.out.println("ERROR: Name not found\n");
                    } 
                    else {
                        System.out.println("The best rank for the male name " + record.getName() + " was " + record.bestRank() + "\n");
                    }
                    break;
                // if user enters 5 or any other number besides 1, 2, 3, or 4
                default:
                    System.exit(0);
                    break;
            }
        }    
    }
}