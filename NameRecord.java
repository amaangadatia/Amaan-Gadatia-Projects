/*
    CSC 220-04
    Fall 2021
    Project 4: Name Surfer
    John DeGood
    Amaan Gadatia, Casey Conti
    December 9, 2021

    This program encapsulates the data for one name (female or male) from the birth years 1880 to
    2020 where the Social Security Adminstration created text files containing the baby names for
    each year, their corresponding sex, and how many times the name occurred per year.
*/

public class NameRecord {
    public static final int START = 1880;         // starting year
    public static final int END = 2020;           // ending year
    private String name;                          // name in a file
    private int[] rank = new int[END - START + 1];      // stores the rank of a certain name for each year from 1880 to 2020

    // takes in a String name, initializes the NameRecord object
    NameRecord(String name) {
        this.name = name;
    }

    // returns the name of a NameRecord object
    public String getName() {
        return this.name;
    }

    // sets the popularity rank of a name in the given year
    // for parameter year, use convention that year = 0 corresponds to 1880, year = 1 is 1881, and so on
    // parameter year input is either 0 to 140
    public void setRank(int year, int r) {
        rank[year] = r;
    }

    // returns the popularity rank of a name in the given year
    // for parameter year, use convention that year = 0 corresponds to 1880, year = 1 is 1881, and so on
    // parameter year input is either 0 to 140
    public int getRank(int year) {
        return rank[year];
    }

    // returns the 4 digit year where the name was most popular
    // return the earliest year in the event of a tie
    // FIXME: algorithm fails if the name didn't exist in 1880, i.e. rank[0] == 0
    public int bestYear() {
        int bY = 0;             // first year (1880)
        int bR = rank[0];       // first rank of the name (from the year 1880)

        for (int i = 0; i < rank.length; ++i) {
            if (rank[i] != 0 && rank[i] < bR) {
                bR = rank[i];
                bY = i;
            }
        }
        return bY + START;
    }

    // returns the best rank for a name throughout the years 1880 to 2020
    public int bestRank() {
        int bR = rank[0];

        for (int i = 0; i < rank.length; ++i) {
            if (rank[i] != 0 && rank[i] < bR) {
                bR = rank[i];
            }
        }
        return bR;
    }
}
