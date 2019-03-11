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
    public String sampleQuery(){
        String out = "Empty :)";
        try {
            // New statement
            Statement statement = connection.createStatement();
            // Execute query
            ResultSet rs = statement.executeQuery("SELECT LocalName FROM country");

            int Count = 0; //Count to make sure it doesn't go anywhere near Travis max log size

            // Print results
            while (rs.next()) {
                if(Count > 50) return out; //Only going to 50
                out = rs.getString("LocalName");
                System.out.println(out);
                Count++;
            }
        }
        // Catch any exception from this code
        catch (Exception e){
            System.out.println("Except! :: " + e.getMessage());
        }
        return out;
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
