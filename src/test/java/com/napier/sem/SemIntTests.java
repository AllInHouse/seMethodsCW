package com.napier.sem;

import com.napier.sem.DatabaseObjects.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SemIntTests {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("db:33060");
    }

    @Test
    void TestSampleQuery()
    {
        String str = app.sampleQuery();
        assertEquals(str, "Costa Rica");
    }

    @Test
    void TestRunningAQuery(){
        String qr = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC";

        ArrayList<Country> al = app.RunListQuery(Country.class, qr);
        assertNotNull(al);
            for (Country country : al) {
                assertNotNull(country);
                app.PrintCountry(country);
            }
    }
}
