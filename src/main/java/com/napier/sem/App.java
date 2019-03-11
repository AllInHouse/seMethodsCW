package com.napier.sem;

import com.napier.sem.DatabaseObjects.City;

import java.sql.*;

public class App {

    public static void main(String[] args)
    {
        // App instance to connect to db with
        App app = new App();

        // Connect to the db
        app.connect("localhost:33060");

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

    public void connect(){
        connect("db:3306");
    }

    /**
     * Connect to the MySQL database.
     * Code adapted from the lab tutorials :)
     */
    public void connect(String dbLocation)
    {
        try
        {
            // Load / test database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database :)   Used to be db:3306
                connection = DriverManager.getConnection("jdbc:mysql://" + dbLocation + "/world?useSSL=false", "root", "example");
                // Print a message in console when db is connected
                System.out.println("Connected to database.");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     * Code adapted from the lab tutorials :)
     */
    public void disconnect()
    {
        if (connection != null)
        {
            try
            {
                // Close connection
                connection.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Sample query that prints the contents of LocalName in the Country table
     */
    public void sampleQuery(){
        try {
            // New statement
            Statement statement = connection.createStatement();
            // Execute query
            ResultSet rs = statement.executeQuery("SELECT LocalName FROM country");

            int Count = 0; //Count to make sure it doesn't go anywhere near Travis max log size

            // Print results
            while (rs.next()) {
                if(Count > 50) return; //Only going to 50
                System.out.println(rs.getString("LocalName"));
                Count++;
            }
        }
        // Catch any exception from this code
        catch (Exception e){
            System.out.println("Except! :: " + e.getMessage());
        }
    }

    public ArrayList<Country> getCountry(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();

            while(rset.next())
            {
                Country cntry = new Country();
                cntry.Code = rset.getInt("Code");
                cntry.Name = rset.getString("Name");
                cntry.Continent = rset.getString("Continent");
                cntry.Region = rset.getString("Region");
                cntry.Population = rset.getInt("Population");
                cntry.Capital = rset.getString("Capital");
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getContinent(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Continent, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Continent, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<Country> continents = new ArrayList<Country>();

            while(rset.next())
            {
                Country cont = new Country();
                cont.Code = rset.getInt("Code");
                cont.Name = rset.getString("Name");
                cont.Continent = rset.getString("Continent");
                cont.Region = rset.getString("Region");
                cont.Population = rset.getInt("Population");
                cont.Capital = rset.getString("Capital");
            }
            return continents;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
    
    public ArrayList<Country> getRegion(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Region, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Region, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<Country> regions = new ArrayList<Country>();

            while(rset.next())
            {
                Country reg = new Country();
                reg.Code = rset.getInt("Code");
                reg.Name = rset.getString("Name");
                reg.Continent = rset.getString("Continent");
                reg.Region = rset.getString("Region");
                reg.Population = rset.getInt("Population");
                reg.Capital = rset.getString("Capital");
            }
            return regions;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
    
    public ArrayList<City> getCity(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<City> cities = new ArrayList<City>();

            while(rset.next())
            {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.country.Name = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
    
    public ArrayList<City> getContCity(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY Continent, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY Continent, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<City> ctCont = new ArrayList<City>();

            while(rset.next())
            {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.country.Name = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return ctCont;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
    
    public ArrayList<City> getContCity(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY Region, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY Region, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<City> ctReg = new ArrayList<City>();

            while(rset.next())
            {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.country.Name = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return ctReg;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<City> getContCity(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY country.Name, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY country.Name, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<City> ctCountry = new ArrayList<City>();

            while(rset.next())
            {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.country.Name = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return ctCountry;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<City> getDistrict(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY District, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Name, country.Name, District, Population "
                            + "FROM city "
                            + "JOIN country ON CountryCode = country.Code "
                            + "ORDER BY District, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<City> districts = new ArrayList<City>();

            while(rset.next())
            {
                City ct = new City();
                ct.Name = rset.getString("Name");
                ct.country.Name = rset.getString("country.Name");
                ct.District = rset.getString("District");
                ct.Population = rset.getInt("Population");
            }
            return districts;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getCountry(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Capital, Name, Population "
                            + "FROM country "
                            + "ORDER BY Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Capital, Name, Population "
                            + "FROM country "
                            + "ORDER BY Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<Country> capitals = new ArrayList<Country>();

            while(rset.next())
            {
                Country cntry = new Country();
                cntry.Capital = rset.getString("Capital");
                cntry.Name = rset.getString("Name");
                cntry.Population = rset.getInt("Population");
            }
            return capitals;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getCountry(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Capital, Name, Population "
                            + "FROM country "
                            + "ORDER BY Continent, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Capital, Name, Population "
                            + "FROM country "
                            + "ORDER BY Continent, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<Country> captCont = new ArrayList<Country>();

            while(rset.next())
            {
                Country cntry = new Country();
                cntry.Capital = rset.getString("Capital");
                cntry.Name = rset.getString("Name");
                cntry.Population = rset.getInt("Population");
            }
            return captCont;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public ArrayList<Country> getCountry(boolean limit, int limNum)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            
            if(limit == false)
            {
                // Create string for SQL statement
                String strSelect =
                    "SELECT Capital, Name, Population "
                            + "FROM country "
                            + "ORDER BY Region, Population DESC";
            
            } else {

                String strSelect =
                    "SELECT Capital, Name, Population "
                            + "FROM country "
                            + "ORDER BY Region, Population DESC"
                            + "LIMIT " + limNum;
            }
            
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            
            // Extract country information
            ArrayList<Country> captReg = new ArrayList<Country>();

            while(rset.next())
            {
                Country cntry = new Country();
                cntry.Capital = rset.getString("Capital");
                cntry.Name = rset.getString("Name");
                cntry.Population = rset.getInt("Population");
            }
            return captReg;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    

    /**
     * Not sure this will stay, prints out all data for a City.
     * May throw IllegalArgumentException if ct is null
     * @param ct the city to print
     */
    public void PrintCity(City ct){
        if(ct == null) throw new IllegalArgumentException();
        String.format("%-10s %-15s %-20s %-8s", "ID", "Country Code", "District", "Name", "Population");
        String.format("%-10s %-15s %-20s %-8s", ct.ID, ct.CountryCode, ct.District, ct.Name, ct.Population);
    }
}