
package com.napier.sem;

import com.napier.sem.DatabaseObjects.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    public static final Logger log = LogManager.getLogger("seMethodsCW");


    public static void main(String[] args) {

        String dbloc;
        //Check for arguments passed to the app. If args[0] exists we will use it as the host address
        if (args.length > 0) dbloc = args[0];
        else dbloc = "localhost:33060"; //localhost? Is this for Travis?
        log.debug("Using DB Location :: " + dbloc);

        connect(dbloc);

        //Setup SPRING :) Winter is so last season..
        SpringApplication.run(App.class, args);
    }


    /**
     * Connection to MySQL database.
     */
    private static Connection connection = null;

    /**
     * Connect to the MySQL database.
     * Code adapted from the lab tutorials :)
     */
    public static void connect(String dbLocation) {
        try {
            // Load / test database driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.fatal("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            log.info("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database :)   Used to be db:3306
                connection = DriverManager.getConnection("jdbc:mysql://" + dbLocation + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                // Print a message in console when db is connected
                log.info("Connected to database.");
                break;
            } catch (SQLException sqle) {
                log.error("Failed to connect to database attempt " + i);
                log.error(sqle.getMessage());
            } catch (InterruptedException ie) {
                log.fatal("Thread interrupted? Should not happen.");
            }
        }
    }

    /*
     * Disconnect from the MySQL database.
     * Code adapted from the lab tutorials :)
     * Not used anymore now that Spring is integrated
     */
    /*
    public static void disconnect() {
        log.info("Disconnecting from database.");
        if (connection != null) {
            try {
                // Close connection
                connection.close();
            } catch (Exception e) {
                log.error("Error closing connection to database");
            }
        }
    }*/

    /**
     * Run a query that returns an ArrayList of DataObjects
     * @param returnType The class of type T
     * @param Query The query to run
     * @param <T> the type of DataObject to return
     * @return An ArrayList of type <T> containing results or null
     */
    public <T extends DataObject> ArrayList<T> RunListQuery(Class<T> returnType, String Query){
        try{
            //Create the new Statement
            Statement statement = connection.createStatement();
            //Execute query
            ResultSet rs = statement.executeQuery(Query);
            // Create return object
            ArrayList<T> dataSet = new ArrayList<>();

            //Loop through entries and get the data
            while(rs.next()){
                T type = returnType.newInstance();
                log.trace("Parsing to object :: " + returnType.getSimpleName());
                if (type.ParseRSET(rs))
                    log.trace("Parsed RSET Successfully");
                else
                    log.warn("Failed RSET Parsing -> " + returnType.getSimpleName());

                dataSet.add(type);
            }
            return dataSet;
        } catch (SQLException | IllegalAccessException | InstantiationException e){
            log.error("Exception caught at RunListQuery :: " + e.getMessage());
        }
        return null;
    }

    /**
     * Attempt to parse a string input.
     * @param input String to parse
     * @return Number representation of input or -1 if NumberFormatException is thrown.
     */
    public int TryParseInput(String input){
        int result;
        try{
            result = Integer.parseInt(input);
        }catch (NumberFormatException nfe){
            log.warn("TryParseInput for input : '" + input + "' has failed. returning -1.");
            return -1;
        }
        return result;
    }

    /**
     * Sample query that prints the contents of LocalName in the Country table
     */
    @RequestMapping("App_Sample_Query")
    public ArrayList<Country> sampleQuery() {
        String qr = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC";

        ArrayList<Country> al = RunListQuery(Country.class, qr);

        if(al != null) {
            for (Country country : al) {
                PrintCountry(country);
            }
        }else{
            log.error("Testing query returned null..");
            return null;
        }

        return al;
    }

    /**
     * Don't think this actually needed limNum if its requirement 1
     * @param limNum the limit
     * @return ArrayList of Country or Null
     */
    @RequestMapping("country")
    public ArrayList<Country> getCountry(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC";
        if (!(limit <= 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }



    /**
     * Requirement 1 - /countries_largest_to_smallest
     * All the countries in the world organised by largest population to smallest.
     * @return ArrayList of Country or Null
     */
    @RequestMapping("countries_largest_to_smallest")
    public ArrayList<Country> getCountriesLargestToSmallest(){
        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC";
        return RunListQuery(Country.class, strSelect);
    }

    /**
     * Requirement 2 - /countries_largest_to_smallest_group_continent
     * All the countries in a continent organised by largest population to smallest.
     * @return ArrayList of Country or Null
     */
    @RequestMapping("countries_largest_to_smallest_group_continent")
    public ArrayList<Country> getCountriesLargestToSmallestGroupByContinent(){
        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Continent, Population DESC";
        return RunListQuery(Country.class, strSelect);
    }

    /**
     * Requirement 3 - /countries_largest_to_smallest_group_region
     * All the countries in a region organised by largest population to smallest.
     * @return ArrayList of Country or Null
     */
    @RequestMapping("countries_largest_to_smallest_group_region")
    public ArrayList<Country> getCountriesLargestToSmallestGroupByRegion(){
        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Region, Population DESC";
        return RunListQuery(Country.class, strSelect);
    }

    /**
     * Requirement 4 - /get_countries_largest_to_smallest_limited
     * The top N populated countries in the world where N is provided by the user.
     * @param limNum N in the above requirement
     * @return ArrayList of Country or Null
     */
    @RequestMapping("get_countries_largest_to_smallest_limited")
    public ArrayList<Country> getCountriesLargestToSmallestLimited(@RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;

        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC "
                + " LIMIT " + limit;
        return RunListQuery(Country.class, strSelect);
    }

    /**
     * Requirement 5 - /top_populated_countries_continent
     * The top N populated countries in a continent where N is provided by the user.
     * @param continent The continent to get from
     * @param limNum The limit for entries
     * @return ArrayList of Country or Null
     */
    @RequestMapping("top_populated_countries_continent")
    public ArrayList<Country> getTopPopulatedCountriesContinent(@RequestParam(value = "continent") String continent, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
               + "FROM country "
               + "WHERE Continent = '" + continent + "' "
               + "ORDER BY Population DESC "
               + "LIMIT " + limit;
        return RunListQuery(Country.class, strSelect);
    }

    /**
     * Requirement 6 - /top_populated_countries_region
     * The top N populated countries in a region where N is provided by the user.
     * @param region The region to get from
     * @param limNum The limit for entries
     * @return ArrayList of Country or Null
     */
    @RequestMapping("top_populated_countries_region")
    public ArrayList<Country> getTopPopulatedCountriesRegion(@RequestParam(value = "region") String region, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
               + "FROM country "
               + "WHERE Region = '" + region + "' "
               + "ORDER BY Population DESC "
               + "LIMIT " + limit;
        return RunListQuery(Country.class, strSelect);
    }

    /**
     * Requirement 7 - /cities_largest_to_smallest
     * All the cities in the world organised by largest population to smallest.
     * @return ArrayList of City or Null
     */
    @RequestMapping("cities_largest_to_smallest")
    public ArrayList<City> getCitiesLargestToSmallest(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Population DESC";
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 8 - /cities_largest_to_smallest_group_continent
     * All the cities in a continent organised by largest population to smallest.
     * @return ArrayList of City or Null
     */
    @RequestMapping("cities_largest_to_smallest_group_continent")
    public ArrayList<City> getCitiesLargestToSmallestGroupByContinent(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Continent, Population DESC";
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 9 - /cities_largest_to_smallest_group_region
     * All the cities in a region organised by largest population to smallest.
     * @return ArrayList of City or Null
     */
    @RequestMapping("cities_largest_to_smallest_group_region")
    public ArrayList<City> getCitiesLargestToSmallestGroupByRegion(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, Region, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Region, Population DESC";
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 10 - /cities_largest_to_smallest_group_country
     * All the cities in a country organised by largest population to smallest.
     * @return ArrayList of City or Null
     */
    @RequestMapping("cities_largest_to_smallest_group_country")
    public ArrayList<City> getCitiesLargestToSmallestGroupByCountry(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY country.Name, Population DESC";
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 11 - /cities_largest_to_smallest_group_district
     * All the cities in a district organised by largest population to smallest.
     * @return ArrayList of City or Null
     */
    @RequestMapping("cities_largest_to_smallest_group_district")
    public ArrayList<City> getCitiesLargestToSmallestGroupByDistrict(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY District, Population DESC";
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 12 - /cities_largest_to_smallest_limited
     * The top N populated cities in the world where N is provided by the user.
     * @param limNum N in the above requirement
     * @return ArrayList of City or Null
     */
    @RequestMapping("cities_largest_to_smallest_limited")
    public ArrayList<City> getCitiesLargestToSmallestLimited(@RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;

        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Population DESC "
                + " LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 13 - /top_populated_cities_continent
     * The top N populated cities in a continent where N is provided by the user.
     * @param continent The continent to get from
     * @param limNum The limit for entries
     * @return ArrayList of City or Null
     */
    @RequestMapping("top_populated_cities_continent")
    public ArrayList<City> getTopPopulatedCitiesContinent(@RequestParam(value = "continent") String continent, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "WHERE country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC "
                + "LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 14 - /top_populated_cities_region
     * The top N populated cities in a region where N is provided by the user.
     * @param region The region to get from
     * @param limNum The limit for entries
     * @return ArrayList of City or Null
     */
    @RequestMapping("top_populated_cities_region")
    public ArrayList<City> getTopPopulatedCitiesRegion(@RequestParam(value = "region") String region, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "WHERE country.region = '" + region + "' "
                + "ORDER BY Population DESC "
                + "LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 15 - /top_populated_cities_country
     * The top N populated cities in a country where N is provided by the user.
     * @param country The country to get from
     * @param limNum The limit for entries
     * @return ArrayList of City or Null
     */
    @RequestMapping("top_populated_cities_country")
    public ArrayList<City> getTopPopulatedCitiesCountry(@RequestParam(value = "country") String country, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "WHERE country.Name = '" + country + "' "
                + "ORDER BY Population DESC "
                + "LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 16 - /top_populated_cities_district
     * The top N populated cities in a district where N is provided by the user.
     * @param district The district to get from
     * @param limNum The limit for entries
     * @return ArrayList of City or Null
     */
    @RequestMapping("top_populated_cities_district")
    public ArrayList<City> getTopPopulatedCitiesDistrict(@RequestParam(value = "district") String district, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT city.Name as Name, country.Name as Country, District, city.Population as Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "WHERE city.District = '" + district + "' "
                + "ORDER BY Population DESC "
                + "LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 17 - /capital_cities_largest_to_smallest
     * All the capital cities in the world organised by largest population to smallest.
     * @return ArrayList of CapitalCity or Null
     */
    @RequestMapping("capital_cities_largest_to_smallest")
    public ArrayList<CapitalCity> getCapitalCitiesLargestToSmallest(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, city.Population as Population, country.Continent as Continent "
                + "FROM country "
                + "JOIN city on country.Capital = city.ID "
                + "ORDER BY Population DESC";
        return RunListQuery(CapitalCity.class, strSelect);
    }

    /**
     * Requirement 18 - /capital_cities_lagest_to_smallest_group_continent
     * All the capital cities in a continent organised by largest population to smallest.
     * @return ArrayList of CapitalCity or Null
     */
    @RequestMapping("capital_cities_largest_to_smallest_group_continent")
    public ArrayList<CapitalCity> getCapitalCitiesLargestToSmallestGroupByContinent(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, city.Population as Population, country.Continent as Continent "
                + "FROM country "
                + "JOIN city on country.Capital = city.ID "
                + "ORDER BY country.Continent, Population DESC";
        return RunListQuery(CapitalCity.class, strSelect);
    }

    /**
     * Requirement 19 - /capital_cities_largest_to_smallest_group_region
     * All the capital cities in a region organised by largest to smallest.
     * @return ArrayList of CapitalCity or Null
     */
    @RequestMapping("capital_cities_largest_to_smallest_group_region")
    public ArrayList<CapitalCity> getCapitalCitiesLargestToSmallestGroupByRegion(){
        String strSelect = "SELECT city.Name as Name, country.Name as Country, city.Population as Population, country.Continent as Continent "
                + "FROM country "
                + "JOIN city on country.Capital = city.ID "
                + "ORDER BY country.Region, Population DESC";
        return RunListQuery(CapitalCity.class, strSelect);
    }

    /**
     * Requirement 20 - /capital_cities_largest_to_smallest_limited
     * The top N populated capital cities in the world where N is provided by the user.
     * @param limNum N in the above requirement
     * @return ArrayList of CapitalCity or Null
     */
    @RequestMapping("capital_cities_largest_to_smallest_limited")
    public ArrayList<CapitalCity> getCapitalCitiesLargestToSmallestLimited(@RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;

        String strSelect = "SELECT city.Name as Name, country.Name as Country, city.Population as Population, country.Continent as Continent "
                + "FROM country "
                + "JOIN city on country.Capital = city.ID "
                + "ORDER BY Population DESC "
                + " LIMIT " + limit;
        return RunListQuery(CapitalCity.class, strSelect);
    }

    /**
     * Requirement 21 - /top_populated_capital_cities_continent
     * The top N populated capital cities in a continent where N is provided by the user.
     * @param continent The continent to get from
     * @param limNum The limit for entries
     * @return ArrayList of City or Null
     */
    @RequestMapping("top_populated_capital_cities_continent")
    public ArrayList<City> getTopPopulatedCapitalCitiesContinent(@RequestParam(value = "continent") String continent, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT city.Name as Name, country.Name as Country, city.Population as Population "
                + "FROM country "
                + "JOIN city ON country.Capital = city.ID "
                + "WHERE country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC "
                + "LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

    /**
     * Requirement 22 - /top_populated_capital_cities_continent
     * The top N populated capital cities in a region where N is provided by the user.
     * @param region The region to get from
     * @param limNum The limit for entries
     * @return ArrayList of City or Null
     */
    @RequestMapping("top_populated_capital_cities_region")
    public ArrayList<City> getTopPopulatedCapitalCitiesRegion(@RequestParam(value = "region") String region, @RequestParam(value = "limNum") String limNum){
        int limit = TryParseInput(limNum);
        if(limit < 0) return null;
        String strSelect = "SELECT city.Name as Name, country.Name as Country, city.Population as Population "
                + "FROM country "
                + "JOIN city ON country.Capital = city.ID "
                + "WHERE country.Region = '" + region + "' "
                + "ORDER BY Population DESC "
                + "LIMIT " + limit;
        return RunListQuery(City.class, strSelect);
    }

	
    /*
     * Requirement 23 - /population_per_continent
     * The population of people, people living in cities, and people not living in cities in each continent.
     * @return ArrayList of Population or Null
    
    @RequestMapping("population_per_continent")
    public ArrayList<Population> getPopulationPerContinent(){
        String strSelect = "";
        return RunListQuery(Population.class, strSelect);
    }
	
	public ArrayList<Country> getPopCont() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Continent AS Name, SUM(country.Population) AS TotalPopulation, SUM(city.Population) AS TotalPopInCities, (SUM(country.Population)-SUM(city.Population)) AS TotalPopNotInCities "
                            + "FROM country "
                            + "JOIN city ON Code = city.CountryCode "
                            + "GROUP BY Continent";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> popCont = new ArrayList<Country>();

            while (rset.next()) {
                //CountrySumPop pop = new CountrySumPop();
                //pop.Continent = rset.getString("Continent");
                //pop.Population = rset.getInt("Population");
                //pop.city.Population = rset.getString("city.Population");
                //pop.SumPop = rset.getInt("SUM(Population - city.Population)");
            }
            return popCont;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    //The population of people, people living in cities, and people not living in cities in each region.
    public ArrayList<Country> getPopReg() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Region AS Name, SUM(country.Population) AS TotalPopulation, SUM(city.Population) AS TotalPopInCities, (SUM(country.Population)-SUM(city.Population)) AS TotalPopNotInCities "
                            + "FROM country "
                            + "JOIN city ON Code = city.CountryCode "
                            + "GROUP BY Region";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> popReg = new ArrayList<Country>();

            while (rset.next()) {
                Country pop = new Country();
                pop.Region = rset.getString("Region");
                //pop.Population = rset.getInt("Population");
                //pop.city.Population = rset.getString("city.Population");
                //pop.SUM(Population - city.Population) = rset.getInt("SUM(Population - city.Population)");
            }
            return popReg;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    //The population of people, people living in cities, and people not living in cities in each country.
    public ArrayList<Country> getPop() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, SUM(country.Population) AS TotalPopulation, SUM(city.Population) AS TotalPopInCities, (SUM(country.Population)-SUM(city.Population)) AS TotalPopNotInCities "
                            + "FROM country "
                            + "JOIN city ON Code = city.CountryCode "
                            + "GROUP BY Name";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> pop = new ArrayList<Country>();

            while (rset.next()) {
                Country popC = new Country();
                popC.Name = rset.getString("Name");
                //popC.Population = rset.getInt("Population");
                //pop.city.Population = rset.getString("city.Population");
                //pop.SUM(Population - city.Population) = rset.getInt("SUM(Population - city.Population)");
            }
            return pop;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
	*/
	
    /**
     * Requirement 26? - /world_population
     * The population of the world.
     * @return ArrayList containing a single AditionalQueries object
     */
    @RequestMapping("world_population")
    public ArrayList<AditionalQueries> getWorldPopulation(){
        String strSelect = "SELECT 'World Population' AS Name, SUM(Population) AS Population "
            + "FROM country";
        return RunListQuery(AditionalQueries.class, strSelect);
    }

    /**
     * Requirement 27? - /continent_population
     * The population of a continent.
     * @return ArrayList of AditionalQueries or Null
     */
    @RequestMapping("continent_population")
    public ArrayList<AditionalQueries> getContinentPopulation(@RequestParam(value = "continent") String continent){
        String strSelect = "SELECT Continent AS Name, SUM(Population) AS Population "
                + "FROM country "
                + "WHERE Continent = '" + continent + "' "
                + "GROUP BY Continent";
        return RunListQuery(AditionalQueries.class, strSelect);
    }

    /**
     * Requirement 28? - /region_population
     * The population of a region.
     * @return ArrayList of AditionalQueries or Null
     */
    @RequestMapping("region_population")
    public ArrayList<AditionalQueries> getRegionPopulation(@RequestParam(value = "region") String region){
        String strSelect = "SELECT Region AS Name, SUM(Population) AS Population "
                + "FROM country "
                + "WHERE Region = '" + region + "' "
                + "GROUP BY Region";
        return RunListQuery(AditionalQueries.class, strSelect);
    }

    /**
     * Requirement 29? - /country_population
     * The population of a country.
     * @return ArrayList of AditionalQueries or Null
     */
    @RequestMapping("country_population")
    public ArrayList<AditionalQueries> getCountryPopulation(@RequestParam(value = "country") String country){
        String strSelect = "SELECT Name, SUM(Population) AS Population "
                + "FROM country "
                + "WHERE Name = '" + country + "'";
        return RunListQuery(AditionalQueries.class, strSelect);
    }

    /**
     * Requirement 30? - /district_population
     * The population of a district.
     * @return ArrayList of AditionalQueries or Null
     */
    @RequestMapping("district_population")
    public ArrayList<AditionalQueries> getDistrictPopulation(@RequestParam(value = "district") String district){
        String strSelect = "SELECT District AS Name, SUM(Population) AS Population "
                + "FROM city "
                + "WHERE District = '" + district + "' "
                + "GROUP BY District";
        return RunListQuery(AditionalQueries.class, strSelect);
    }

    /**
     * Requirement 31? - /city_population
     * The population of a city.
     * @return ArrayList of AditionalQueries or Null
     */
    @RequestMapping("city_population")
    public ArrayList<AditionalQueries> getCityPopulation(@RequestParam(value = "city") String city){
        String strSelect = "SELECT Name, SUM(Population) AS Population "
                + "FROM city "
                + "WHERE Name = '" + city + "'";
        return RunListQuery(AditionalQueries.class, strSelect);
    }


    /*
    //Requirement 32
    public ArrayList<LanguageQuery> getLanguagesReport(){
        String strSelect = "";

        return RunListQuery(LanguageQuery.class, strSelect);
    }

    public ArrayList<Country> getlanChinese() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) "
                            + "FROM country "
                            + "JOIN countrylanguage ON Code = countrylanguage.CountryCode "
                            + "WHERE countrylanguage.Language = 'Chinese' "
                            + "ORDER BY lanulation DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> lanChinese = new ArrayList<Country>();

            while (rset.next()) {
                Country lan = new Country();
                lan.Name = rset.getString("Name");
                //lan.Population = rset.getInt("Population");
                //lan.SUM(countrylanguage.Percentage) = rset.getInt("SUM(countrylanguage.Percentage)");
            }
            return lanChinese;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getlanEnglish() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) "
                            + "FROM country "
                            + "JOIN countrylanguage ON Code = countrylanguage.CountryCode "
                            + "WHERE countrylanguage.Language = 'English' "
                            + "ORDER BY lanulation DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> lanEnglish = new ArrayList<Country>();

            while (rset.next()) {
                Country lan = new Country();
                lan.Name = rset.getString("Name");
                //lan.Population = rset.getInt("Population");
                //lan.SUM(countrylanguage.Percentage) = rset.getInt("SUM(countrylanguage.Percentage)");
            }
            return lanEnglish;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getlanHindi() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) "
                            + "FROM country "
                            + "JOIN countrylanguage ON Code = countrylanguage.CountryCode "
                            + "WHERE countrylanguage.Language = 'Hindi' "
                            + "ORDER BY lanulation DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> lanHindi = new ArrayList<Country>();

            while (rset.next()) {
                Country lan = new Country();
                lan.Name = rset.getString("Name");
                //lan.Population = rset.getInt("Population");
                //lan.SUM(countrylanguage.Percentage) = rset.getInt("SUM(countrylanguage.Percentage)");
            }
            return lanHindi;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getlanSpanish() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) "
                            + "FROM country "
                            + "JOIN countrylanguage ON Code = countrylanguage.CountryCode "
                            + "WHERE countrylanguage.Language = 'Spanish' "
                            + "ORDER BY lanulation DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> lanSpanish = new ArrayList<Country>();

            while (rset.next()) {
                Country lan = new Country();
                lan.Name = rset.getString("Name");
                //lan.Population = rset.getInt("Population");
                //lan.SUM(countrylanguage.Percentage) = rset.getInt("SUM(countrylanguage.Percentage)");
            }
            return lanSpanish;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getlanArabic() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) "
                            + "FROM country "
                            + "JOIN countrylanguage ON Code = countrylanguage.CountryCode "
                            + "WHERE countrylanguage.Language = 'Arabic' "
                            + "ORDER BY lanulation DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> lanArabic = new ArrayList<Country>();

            while (rset.next()) {
                Country lan = new Country();
                lan.Name = rset.getString("Name");
                //lan.Population = rset.getInt("Population");
                //lan.SUM(countrylanguage.Percentage) = rset.getInt("SUM(countrylanguage.Percentage)");
            }
            return lanArabic;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }*/

    /**
     * Not sure this will stay, prints out all data for a City.
     * May throw IllegalArgumentException if ct is null
     *
     * @param cntry the city to print
     */
    public void PrintCountry(Country cntry) {
        if (cntry == null) throw new IllegalArgumentException();
        log.debug("Code | Name | Continent | Region | Population | Capital");
        log.debug(cntry.Code + " | " + cntry.Name + " | " + cntry.Continent + " | " + cntry.Region + " | " + cntry.Population + " | " + cntry.Capital);
    }

    public void PrintCity(City ct) {
        if (ct == null) throw new IllegalArgumentException();
        log.debug("Name | Country | District | Population");
        log.debug(ct.Name + " | " + ct.CountryCode + " | " + ct.District + " | " + ct.Population);
    }

    public void PrintLanguage(Country lan) {
        if (lan == null) throw new IllegalArgumentException();
        log.debug("Name | Population");
        log.debug(lan.Name + " | " + lan.Population);
    }
}