
package com.napier.sem;

import com.napier.sem.DatabaseObjects.City;
import com.napier.sem.DatabaseObjects.Country;
import com.napier.sem.DatabaseObjects.DataObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.plaf.nimbus.State;
import java.net.SocketOption;
import java.sql.*;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class App {

    public static Logger log = LogManager.getLogger("seMethodsCW");

    public static void main(String[] args) {
        // App instance to connect to db with
        App app = new App();

        String dbloc;

        //Check for arguments passed to the app. If args[0] exists we will use it as the host address
        if (args.length > 0) dbloc = args[0];
        else dbloc = "db:3306"; //localhost? Is this for Travis?

        System.out.println("DB Loc :: " + dbloc);

        //app.connect("localhost:33060");
        app.connect(dbloc);

        // Execute the sample query
        //app.sampleQuery();

        //ArrayList<Country> al = app.getCountry(-1);

        String qr = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC";

        ArrayList<Country> al = app.RunListQuery(Country.class, qr);

        if(al != null) {
            for (Country country : al) {
                app.PrintCountry(country);
            }
        }else
            log.error("Testing query returned null..");
        // Disconnect from the db
        app.disconnect();

        System.out.println("We're doing it all in house");
        log.debug("We're doing it all in house");
    }


    /**
     * Connection to MySQL database.
     */
    public Connection connection = null;

    /**
     * Connect to the MySQL database.
     * Code adapted from the lab tutorials :)
     */
    public void connect(String dbLocation) {
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

    /**
     * Disconnect from the MySQL database.
     * Code adapted from the lab tutorials :)
     */
    public void disconnect() {
        if (connection != null) {
            try {
                // Close connection
                connection.close();
            } catch (Exception e) {
                log.error("Error closing connection to database");
            }
        }
    }

    /**
     * Run a query that returns an ArrayList of objects
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
                log.debug("Parsing to object :: " + returnType.getSimpleName());
                if (type.ParseRSET(rs))
                    log.debug("Parsed RSET Successfully");
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
     * Sample query that prints the contents of LocalName in the Country table
     */
    public String sampleQuery() {
        String out = "Empty :)";
        try {
            // New statement
            Statement statement = connection.createStatement();
            // Execute query
            ResultSet rs = statement.executeQuery("SELECT LocalName FROM country");

            int Count = 0; //Count to make sure it doesn't go anywhere near Travis max log size

            // Print results
            while (rs.next()) {
                if (Count > 50) return out; //Only going to 50
                out = rs.getString("LocalName");
                System.out.println(out);
                Count++;
            }
        }
        // Catch any exception from this code
        catch (Exception e) {
            System.out.println("Except! :: " + e.getMessage());
        }
        return out;
    }

    @RequestMapping("Country")
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
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }

    @RequestMapping("Continent")
    public ArrayList<Country> getContinent(@RequestParam(value = "limNum") String limNum) {
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
                + "ORDER BY Continent, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }

    @RequestMapping("Region")
    public ArrayList<Country> getRegion(@RequestParam(value = "limNum") String limNum) {
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
            + "ORDER BY Region, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }

    @RequestMapping("City")
    public ArrayList<City> getCity(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Name, country.Name, District, Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(City.class, strSelect);
    }

    @RequestMapping("City_Cont")
    public ArrayList<City> getContCity(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Name, country.Name, District, Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Continent, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(City.class, strSelect);
    }

    @RequestMapping("City_Reg")
    public ArrayList<City> getRegCity(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Name, country.Name, District, Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY Region, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(City.class, strSelect);
    }

    @RequestMapping("City_Country")
    public ArrayList<City> getCountryCity(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Name, country.Name, District, Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY country.Name, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(City.class, strSelect);
    }

    @RequestMapping("City_Country")
    public ArrayList<City> getDistrict(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Name, country.Name, District, Population "
                + "FROM city "
                + "JOIN country ON CountryCode = country.Code "
                + "ORDER BY District, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(City.class, strSelect);
    }

    @RequestMapping("Country_Cap")
    public ArrayList<Country> getCap(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Capital, Name, Population "
                + "FROM country "
                + "ORDER BY Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }

    @RequestMapping("Continent_Cap")
    public ArrayList<Country> getCapCont(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Capital, Name, Population "
                + "FROM country "
                + "ORDER BY Continent, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }

    @RequestMapping("Region_Cap")
    public ArrayList<Country> getCapReg(@RequestParam(value = "limNum") String limNum) {
        int limit;
        try {
            limit = Integer.parseInt(limNum);
        } catch (NumberFormatException nfe) {
            log.error("Caught number format exception, returning bad response.");
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return null;
        }
        String strSelect = "SELECT Capital, Name, Population "
                + "FROM country "
                + "ORDER BY Region, Population DESC";
        if (!(limit < 0)) {
            strSelect = strSelect + " LIMIT " + limit;
        }
        // Execute SQL statement
        return RunListQuery(Country.class, strSelect);
    }

    public ArrayList<Country> getPopCont() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Continent, Population, city.Population, SUM(Population - city.Population) "
                            + "FROM country "
                            + "JOIN city ON Code = city.CountryCode "
                            + "GROUP BY Continent";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> popCont = new ArrayList<Country>();

            while (rset.next()) {
                CountrySumPop pop = new CountrySumPop();
                pop.Continent = rset.getString("Continent");
                pop.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
                //pop.city.Population = rset.getString("city.Population");
                pop.SumPop = rset.getInt("SUM(Population - city.Population)");
            }
            return popCont;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getPopReg() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Region, Population, city.Population, SUM(Population - city.Population) "
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
                pop.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
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

    public ArrayList<Country> getPop() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Population, city.Population, SUM(Population - city.Population) "
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
                popC.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
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

    public ArrayList<Country> getPopulation() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(Population) "
                            + "FROM country";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> population = new ArrayList<Country>();

            while (rset.next()) {
                Country pop = new Country();
                //TODO need to get these values differently, It wont compile as is
                //pop.SUM(Population) = rset.getString("SUM(Population)");
            }
            return population;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getPopulationCont() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Continent, SUM(Population) "
                            + "FROM country "
                            + "GROUP BY Continent "
                            + "ORDER BY SUM(Population) DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> populationCont = new ArrayList<Country>();

            while (rset.next()) {
                Country pop = new Country();
                pop.Continent = rset.getString("Continent");
                //TODO need to get these values differently, It wont compile as is
                pop.SumPop = rset.getString("SUM(Population)");
            }
            return populationCont;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getPopulationReg() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Region, SUM(Population) "
                            + "FROM country "
                            + "GROUP BY Region "
                            + "ORDER BY SUM(Population) DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> populationReg = new ArrayList<Country>();

            while (rset.next()) {
                Country pop = new Country();
                pop.Region = rset.getString("Region");
                //TODO need to get these values differently, It wont compile as is
                //pop.SUM(Population) = rset.getString("SUM(Population)");
            }
            return populationReg;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getPopulationCountry() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Population "
                            + "FROM country "
                            + "ORDER BY SUM(Population) DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> populationCountry = new ArrayList<Country>();

            while (rset.next()) {
                Country pop = new Country();
                pop.Name = rset.getString("Name");
                pop.Population = rset.getInt("Population");
            }
            return populationCountry;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<City> getPopulationDist() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT District, SUM(Population) "
                            + "FROM city "
                            + "GROUP BY District "
                            + "ORDER BY SUM(Population) DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> populationDist = new ArrayList<City>();

            while (rset.next()) {
                City pop = new City();
                pop.District = rset.getString("District");
                //TODO need to get these values differently, It wont compile as is
                //pop.SUM(Population) = rset.getString("SUM(Population)");
            }
            return populationDist;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getPopulationCity() {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Population "
                            + "FROM city "
                            + "ORDER BY Population DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> populationCity = new ArrayList<City>();

            while (rset.next()) {
                City pop = new City();
                pop.Name = rset.getString("Name");
                //TODO need to get these values differently, It wont compile as is
                //pop.Population = rset.getString("Population");
            }
            return populationCity;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
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
                lan.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
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
                lan.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
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
                lan.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
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
                lan.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
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
                lan.Population = rset.getInt("Population");
                //TODO need to get these values differently, It wont compile as is
                //lan.SUM(countrylanguage.Percentage) = rset.getInt("SUM(countrylanguage.Percentage)");
            }
            return lanArabic;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Not sure this will stay, prints out all data for a City.
     * May throw IllegalArgumentException if ct is null
     *
     * @param cntry the city to print
     */
    public void PrintCountry(Country cntry) {
        if (cntry == null) throw new IllegalArgumentException();
        System.out.println("Code | Name | Continent | Region | Population | Capital");
        System.out.println(cntry.Code + " | " + cntry.Name + " | " + cntry.Continent + " | " + cntry.Region + " | " + cntry.Population + " | " + cntry.Capital);
    }

    public void PrintCity(City ct) {
        if (ct == null) throw new IllegalArgumentException();
        System.out.println("Name | Country | District | Population");
        System.out.println(ct.Name + " | " + ct.CountryCode + " | " + ct.District + " | " + ct.Population);
    }

    public void PrintLanguage(Country lan) {
        if (lan == null) throw new IllegalArgumentException();
        System.out.println("Name | Population");
        System.out.println(lan.Name + " | " + lan.Population);
    }

    public class CountrySumPop extends Country {

        public int SumPop;
    }
}