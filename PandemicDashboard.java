/*
 * TCSS 143
 * Instructor: Raghavi Sakpal
 * Programming Assignment 4
 */
import java.io.*;
import java.util.*;
/**
 * Pandemic Dashboard reads countries from a csv file along side the 
 * number of cases and deaths from COVID 19 associated with that country.
 * Pandemic Dashboard also displays options for the user to preform the action
 * of sorting and listing countries displaying COVID 19 statistic in a
 * specific order.
 *
 * @author Anthony Cabrera-Lara
 * @version 10 December 2020
 */
 
public class PandemicDashboard {
   /**
    * Main method of the Pandemic Dashboard 
    */
   public static void main(String[] args) throws FileNotFoundException {
      // list where the CovidCases of a country will be stored
      // uses fillCovidCaseList() method to fill the ArrayList
      List<CovidCase> caseList = new ArrayList<CovidCase>(fillCovidCaseList("full_data.csv"));
      // boolean expression is true untill user chooses to exit the dashboard
      boolean gameOn = true;
      
      while(gameOn) {
         DisplayDashboard();
         Scanner consoleIp = new Scanner(System.in);
         int input = consoleIp.nextInt();
         
         // Reads the option the user has chosen and sorts and list accordingly
         // in order to show the information the user has chosen
         if(input == 1) {  // reports covid cases by country
            Collections.sort(caseList, new countryNameComparator());
            DisplayCovidCases(caseList);
         } else if(input == 2) { // reports covid cases by total deaths
            Collections.sort(caseList, new covidDeathComparator());
            DisplayCovidCases(caseList);
         } else if(input == 3) { // reports covid cases by total cases
            Collections.sort(caseList, new covidCaseComparator());
            DisplayCovidCases(caseList);
         } else if(input == 4) { // reports countries with minimum and maximum deaths
            Collections.sort(caseList, new covidDeathComparator());
            minMaxDeathsDisplay(caseList);
         } else if(input == 5) { // reports countries with minimum and maximum cases
            Collections.sort(caseList, new covidCaseComparator());
            minMaxCasesDisplay(caseList);
         } else if(input == 6) { // searches for country and displays information on case
            System.out.println("Enter a country name:");
            String countryInput = consoleIp.next();
            Collections.sort(caseList, new countryNameComparator());
            // searches for the index of the country the user wants to search
            int index = Collections.binarySearch(caseList, new CovidCase(countryInput, 0, 0), 
                                                 new countryNameComparator());
            // tries to search for the country information the user wants
            try {
               System.out.println("Covid Case Data:\n" +
                                  "Country: " + caseList.get(index).getCountry() + "\n" +
                                  "Total Cases: " + caseList.get(index).getCases() + "\n" +
                                  "Total Deaths: " + caseList.get(index).getDeaths() + "\n" +
                                  "\nMortality Rate: " + caseList.get(index).getMortalityRate() + "%");
            } catch(Exception e) {
               // if the program does not contains the country prints a statement
               // saying the the program was unable to find their desired country     
               System.out.println("Couldn't find your country. Sorry. :(");
            }
         } else if(input == 7) { // reports top 10 countries by their mortality rate
            Collections.sort(caseList, new mortalityRateComparator());
            System.out.printf("%-40S%-30S%-20S%S%n","COUNTRY", 
                              "TOTAL CASES", "TOTAL DEATHS", "MORTALITY");
            System.out.println("----------------------------------------------" + 
                               "-----------------------------------------------------");
            for(int i = 0; i < 10; i++) {
            System.out.printf("%-40S%-30S%-20S%S", caseList.get(i).getCountry(),
                           caseList.get(i).getCases(), caseList.get(i).getDeaths(),
                           caseList.get(i).getMortalityRate());
            System.out.print("%\n");
         }     
         } else if(input == 8) { // ends the program
            System.out.println("Good Bye. :)");
            gameOn = false;
         }
          
      }
     
     
     
         
   }
   
   public static List<CovidCase> fillCovidCaseList(String fileName) throws FileNotFoundException {
      List<CovidCase> caseList = new ArrayList<CovidCase>();
      Scanner sc = new Scanner(new File(fileName));
      sc.useDelimiter(",");
      // The desired date of when it covid cases have occured.
      String date = "2020-11-19";
      
      while(sc.hasNextLine()) {
         String currentDate = sc.next();
         long totalCases, totalDeaths;
         if(currentDate.equals(date)) {
            String country = sc.next();
            sc.next();
            sc.next();
            if(sc.hasNextLong()) {
               totalCases = sc.nextLong();
            } else {
               totalCases = 0;
            }
            
            if(sc.hasNextLong()) {
               totalDeaths = sc.nextLong();
            } else {
               totalDeaths = 0;
            }
            
            caseList.add(new CovidCase(country, totalCases, totalDeaths));
         }
         sc.nextLine();
      }
      return caseList;
      
   }
   
   /**
    * Displays all the CovidCases along with their country name
    * total amount of cases and deaths
    */
   public static void DisplayCovidCases(List<CovidCase> caseList) {
      System.out.printf("%-40S%-30S%-20S%n","COUNTRY",
                        "TOTAL CASES", "TOTAL DEATHS");
      System.out.println("-------------------------------------" + 
                         "-----------------------------------------------");
                         
      for(int i = 0; i < caseList.size(); i++) {
         System.out.printf("%-40S%-30S%-20S%n", caseList.get(i).getCountry(),
                           caseList.get(i).getCases(), caseList.get(i).getDeaths());
      }
   }
   
   /**
    * Displays the countries with the minimum and maximum number of deaths
    */
   public static void minMaxDeathsDisplay(List<CovidCase> caseList) {
      System.out.println("Countries with Minimum Deaths:");
      // reads the countries with the minimum deaths
      for(int i = caseList.size() - 1; i >= 0; i--) {
         if(i == caseList.size() - 1) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths());
         } else if(caseList.get(i).getDeaths() == caseList.get(i + 1).getDeaths()) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths());   
         } else {
            break;
         }
      }
      
      System.out.println("\nCountries with Maximum Deaths:");
      // reads the countries with the maximum deaths
      for(int i = 0; i < caseList.size(); i++) {
         if(i == 0) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths());
         } else if(caseList.get(i).getDeaths() == caseList.get(i - 1).getDeaths()) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths() + "\n");   
         } else {
            break;
         }
      }      
   }
   
   /**
    * Displays the countries with the minimum and maximum number of cases
    */
   public static void minMaxCasesDisplay(List<CovidCase> caseList) {
      System.out.println("Countries with Minimum Cases:");
      // reads the counties with the minimum cases
      for(int i = caseList.size() - 1; i >= 0; i--) {
         if(i == caseList.size() - 1) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths());
         } else if(caseList.get(i).getCases() == caseList.get(i + 1).getCases()) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths());   
         } else {
            break;
         }
      }
      
      System.out.println("\nCountries with Maximum Cases:");
      // reads the countries with the maximum deaths
      for(int i = 0; i < caseList.size(); i++) {
         if(i == 0) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths());
         } else if(caseList.get(i).getCases() == caseList.get(i - 1).getCases()) {
            System.out.println("\nCountry: " + caseList.get(i).getCountry());
            System.out.println("Total Cases: " + caseList.get(i).getCases());
            System.out.println("Total Deaths: " + caseList.get(i).getDeaths() + "\n");   
         } else {
            break;
         }
      }      
   }
   
   /**
    * Displays the posible option the user can choose from for the
    * CovidCases to be sorted and listed
    */
   public static void DisplayDashboard() {
      System.out.println("Dashboard for reporting Covid Cases\n" +
                         "-----------------------------------\n" +
                         "Select one of the following options:\n" +
                         "1.Report (Display) Covid Cases by Countries.\n" +
                         "2.Report (Display) Covid Cases by Total Deaths (decreasing order).\n" +
                         "3.Report (Display) Covid Cases by Total Cases (decreasing order).\n" +
                         "4.Report Countries with minimum and maximum number of Total Deaths.\n" +
                         "5.Report Countries with minimum and maximum number of Total Cases.\n" +
                         "6.Search for a Country and report their Mortality Rate (death-to-case %).\n" +
                         "7.Report (Display) top 10 countries by their Mortality Rate (decreasing order).\n" +
                         "8.Exit from the program!");
   }
}