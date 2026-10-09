package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    // object build pii mha function ko Hlnn call loh ya ml, also static nk
    // function a loke ma loke khin mhr initialization pone sn nk, object ty array ty br ty a yin build
    static Main sth; // Main si ka build tr ma loh
    @BeforeAll
    static void init(){
        sth = new Main();
    }
    @Test
    public void testMethod1() {
        assertEquals(5,sth.method("Hello"));
    }
// this doesnt need object creation cuz it does nt use method from anywhere
//    @Test
//    void unitTest()
//    {
//        assertEquals(5, 5);
//    }

    // Define method(String) here, or call the method you're testing.
}
