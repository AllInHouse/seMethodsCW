package com.napier.sem;

import com.napier.sem.DatabaseObjects.City;
import com.napier.sem.DatabaseObjects.Country;
import com.napier.sem.DatabaseObjects.CountryLanguage;
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

    @Test
    void TestCountryLanguageParseWithNull(){
        CountryLanguage testCountryLanguage = new CountryLanguage();
        assertFalse(testCountryLanguage.ParseRSET(null));
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
