package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SemUnitTests {

    static App app;

    @BeforeAll
    static void BeforeTasks(){
        app = new App();
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
