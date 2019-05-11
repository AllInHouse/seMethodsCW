package com.napier.sem;

import com.napier.sem.DatabaseObjects.CapitalCity;
import com.napier.sem.DatabaseObjects.City;
import com.napier.sem.DatabaseObjects.Country;
import com.napier.sem.DatabaseObjects.CountryLanguage;
import com.napier.sem.DatabaseObjects.ExtendedObjects.CountryCityPopulation;
import com.napier.sem.DatabaseObjects.ExtendedObjects.CountrySumPop;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SemUnitTests {

    private static App app;

    @BeforeAll
    static void BeforeTasks(){
        app = new App();
    }

    @Test
    void TestCountryParseWithNull(){
        Country testCountry = new Country();
        assertFalse(testCountry.ParseRSET(null));
    }

    @Test
    void TestCityParseWithNull(){
        City testCity = new City();
        assertFalse(testCity.ParseRSET(null));
    }

    void TestCapitalCityParseWithNull(){
        CapitalCity testCity = new CapitalCity();
        assertFalse(testCity.ParseRSET(null));
    }

    @Test
    void TestCountryLanguageParseWithNull(){
        CountryLanguage testCountryLanguage = new CountryLanguage();
        assertFalse(testCountryLanguage.ParseRSET(null));
    }

    @Test
    void TestExtendedDataObjects(){
        CountrySumPop CountrySum = new CountrySumPop();
        CountryCityPopulation CountryCityPopulation = new CountryCityPopulation();

        assertFalse(CountrySum.ParseRSET(null));
        assertFalse(CountryCityPopulation.ParseRSET(null));
    }

    @Test
    void TestIntParsing(){
        int x = app.TryParseInput("7");
        assertEquals(x, 7);

        int y = app.TryParseInput("356789");
        assertEquals(y, 356789);

        int z = app.TryParseInput("cherry");
        assertEquals(z, -1);

        int a = app.TryParseInput(null);
        assertEquals(a, -1);
    }

    @Test
    void TestPrints(){
        //Assertions.assertThrows(IllegalArgumentException, app.PrintCity(null));
        assertThrows(IllegalArgumentException.class, this::PrintCityNull);
        assertThrows(IllegalArgumentException.class, this::PrintCountryNull);
        assertThrows(IllegalArgumentException.class, this::PrintCountryLang);
    }

    void PrintCityNull(){
        app.PrintCity(null);
    }

    void PrintCountryNull(){
        app.PrintCountry(null);
    }

    void PrintCountryLang(){
        app.PrintLanguage(null);
    }

}
