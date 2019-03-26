package com.napier.sem;

import com.napier.sem.DatabaseObjects.City;
import com.napier.sem.DatabaseObjects.Country;

import java.sql.*;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        // App instance to connect to db with
        App app = new App();

        String dbloc = "";

        //Check for arguments passed to the app. If args[0] exists we will use it as the host address
        if (args.length > 0) dbloc = args[0];
        else dbloc = "localhost:33060";

        //app.connect("localhost:33060");
        app.connect(dbloc);

        // Execute the sample query
        app.sampleQuery();

        // Disconnect from the db
        app.disconnect();

        System.out.println("We're doing it all in house");

        //User input to add requested limit
        int limNum = Integer.parseInt(System.console().readLine());
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
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database :)   Used to be db:3306
                connection = DriverManager.getConnection("jdbc:mysql://" + dbLocation + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                // Print a message in console when db is connected
                System.out.println("Connected to database.");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
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
                System.out.println("Error closing connection to database");
            }
        }
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

    public ArrayList<Country> getCountry(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Population DESC";

            } else {

                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();

            while (rset.next()) {
                Country cntry = new Country();
                cntry.Code = rset.getString("Code");
                cntry.Name = rset.getString("Name");
                cntry.Continent = rset.getString("Continent");
                cntry.Region = rset.getString("Region");
                cntry.Population = rset.getInt("Population");
                cntry.Capital = rset.getInt("Capital");
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getContinent(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Continent, Population DESC";

            } else {

                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Continent, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> continents = new ArrayList<Country>();

            while (rset.next()) {
                Country cont = new Country();
                cont.Code = rset.getString("Code");
                cont.Name = rset.getString("Name");
                cont.Continent = rset.getString("Continent");
                cont.Region = rset.getString("Region");
                cont.Population = rset.getInt("Population");
                cont.Capital = rset.getInt("Capital");
            }
            return continents;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getRegion(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Region, Population DESC";

            } else {

                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Region, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> regions = new ArrayList<Country>();

            while (rset.next()) {
                Country reg = new Country();
                reg.Code = rset.getString("Code");
                reg.Name = rset.getString("Name");
                reg.Continent = rset.getString("Continent");
                reg.Region = rset.getString("Region");
                reg.Population = rset.getInt("Population");
                reg.Capital = rset.getInt("Capital");
            }
            return regions;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<City> getCity(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY Population DESC";

            } else {

                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> cities = new ArrayList<City>();

            while (rset.next()) {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("country.Name"); //TODO shouldn't this be CountryCode?
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getContCity(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY Continent, Population DESC";

            } else {

                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY Continent, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> ctCont = new ArrayList<City>();

            while (rset.next()) {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return ctCont;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getRegCity(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY Region, Population DESC";

            } else {

                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY Region, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> ctReg = new ArrayList<City>();

            while (rset.next()) {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return ctReg;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getCountryCity(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY country.Name, Population DESC";

            } else {

                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY country.Name, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> ctCountry = new ArrayList<City>();

            while (rset.next()) {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return ctCountry;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getDistrict(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY District, Population DESC";

            } else {

                strSelect =
                        "SELECT Name, country.Name, District, Population "
                                + "FROM city "
                                + "JOIN country ON CountryCode = country.Code "
                                + "ORDER BY District, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> districts = new ArrayList<City>();

            while (rset.next()) {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.CountryCode = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return districts;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<Country> getCap(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Capital, Name, Population "
                                + "FROM country "
                                + "ORDER BY Population DESC";

            } else {

                strSelect =
                        "SELECT Capital, Name, Population "
                                + "FROM country "
                                + "ORDER BY Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> capitals = new ArrayList<Country>();

            while (rset.next()) {
                Country cntry = new Country();
                cntry.Capital = rset.getInt("Capital");
                cntry.Name = rset.getString("Name");
                cntry.Population = rset.getInt("Population");
            }
            return capitals;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getCapCont(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Capital, Name, Population "
                                + "FROM country "
                                + "ORDER BY Continent, Population DESC";

            } else {

                strSelect =
                        "SELECT Capital, Name, Population "
                                + "FROM country "
                                + "ORDER BY Continent, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> captCont = new ArrayList<Country>();

            while (rset.next()) {
                Country cntry = new Country();
                cntry.Capital = rset.getInt("Capital");
                cntry.Name = rset.getString("Name");
                cntry.Population = rset.getInt("Population");
            }
            return captCont;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getCapReg(boolean limit, int limNum) {
        try {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            String strSelect = "";

            if (limit == false) {
                // Create string for SQL statement
                strSelect =
                        "SELECT Capital, Name, Population "
                                + "FROM country "
                                + "ORDER BY Region, Population DESC";

            } else {

                strSelect =
                        "SELECT Capital, Name, Population "
                                + "FROM country "
                                + "ORDER BY Region, Population DESC"
                                + "LIMIT " + limNum;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> captReg = new ArrayList<Country>();

            while (rset.next()) {
                Country cntry = new Country();
                cntry.Capital = rset.getInt("Capital");
                cntry.Name = rset.getString("Name");
                cntry.Population = rset.getInt("Population");
            }
            return captReg;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
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
                //pop.SUM(Population) = rset.getString("SUM(Population)");
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
     * @param cntryt the city to print
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