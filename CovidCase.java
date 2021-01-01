/*
 * TCSS 143
 * Instructor: Raghavi Sakpal
 * Programming Assignment 4
 */
import java.util.*;
/**
 * CovidCase is an object that recieves countries along side
 * its COVID 19 facts such as total cases and total deaths from covid
 *
 * @author Anthony Cabrera-Lara
 * @version 10 December 2020
 */
public class CovidCase {
   private String country;
   private long totalCases;
   private long totalDeaths;
   private double deathToCaseRatio;
   
   /**
    * Constructor method instantiates a COVID case that
    * gets its country name along with its covid statistics
    * such as total cases and total deaths
    * 
    * @param country to set the assigned country for the covid case
    * @param totalCases to set the amount of total cases to the country
    * @param totalDeaths to set the amount od total deaths to the country
    */
   public CovidCase(String country, long totalCases, long totalDeaths) {
      this.country = country;
      this.totalCases = totalCases;
      this.totalDeaths = totalDeaths;
      // checks if either the amount of cases or deaths are
      // equal to zero and if it is, it assigns the deathToCaseRatio
      // to 0.0 since you cannot divid by zero in either case
      if (totalDeaths == 0 || totalCases == 0) {
         this.deathToCaseRatio = 0.0;
      } else {
         double mRatio = (double)totalDeaths / (double)totalCases * 100;
         double mR = Math.round(mRatio * 100.0) / 100.0;
         this.deathToCaseRatio = mR;
      }
   }
   /**
    * Gets the country name
    *  
    * @return country name
    */
   public String getCountry() {
      return this.country;
   }
   /**
    * Gets the total amount of cases in a country from COVID 19
    *  
    * @return total amount of cases
    */
   public long getCases() {
      return this.totalCases;
   }
   /**
    * Gets the total amount of deaths in a country from COVID 19
    *  
    * @return total amount of deaths
    */
   public long getDeaths() {
      return this.totalDeaths;
   }
   /**
    * Gets the mortality rate in a country from COVID 19
    *  
    * @return mortality rate
    */
   public double getMortalityRate() {
      return this.deathToCaseRatio;
   }
}

// Comparator methods for CovidCase object used to sort COVID
// cases within a list


// class that implements Comparator used to sort CovidCase within a
// list by name in decsending order (A to Z)
class countryNameComparator implements Comparator<CovidCase> {
   /**
    * Compares CovidCases country names to sort in descending order
    *  
    * @return an integer comparing two CovidCases based on its
    * alpabetical order
    */
   public int compare(CovidCase cc1, CovidCase cc2) {
      return cc1.getCountry().compareTo(cc2.getCountry());
   }
}

// class that implements Comparator used to sort CovidCase within a
// list by the total number of cases in decsending order
class covidCaseComparator implements Comparator<CovidCase> {
   /**
    * Compares CovidCases case number to sort in descending order
    *  
    * @return an integer comparing two CovidCases based on the
    * total amount of covid cases
    */
   public int compare(CovidCase cc1, CovidCase cc2) {
      return Long.compare(cc2.getCases(), cc1.getCases());
   }
}

// class that implements Comparator used to sort CovidCase within a
// list by the total number of deaths in decsending order
class covidDeathComparator implements Comparator<CovidCase> {
   /**
    * Compares CovidCases deaths to sort in descending order
    *  
    * @return an integer comparing two CovidCases based on the
    * amount of deaths from covid within a country
    */
   public int compare(CovidCase cc1, CovidCase cc2) {
      return Long.compare(cc2.getDeaths(), cc1.getDeaths());
   }
}

// class that implements Comparator used to sort CovidCase within a
// list by the mortality rate from covid in decsending order
class mortalityRateComparator implements Comparator<CovidCase> {
   /**
    * Compares CovidCases mortaliy rate to sort in descending order
    *  
    * @return an integer comparing two CovidCases based on the
    * mortality rate from covid within a country
    */
   public int compare(CovidCase cc1, CovidCase cc2) {
      return Double.compare(cc2.getMortalityRate(), cc1.getMortalityRate());   }
}

