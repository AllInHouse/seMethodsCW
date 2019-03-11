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
    void TestPrint(){
        //Assertions.assertThrows(IllegalArgumentException, app.PrintCity(null));
        assertThrows(IllegalArgumentException.class, this::PrintCityNull);
    }

    void PrintCityNull(){
        app.PrintCity(null);
    }

}
