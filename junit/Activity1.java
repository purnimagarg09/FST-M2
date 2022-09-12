package activities;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Activity1 {

    // Test fixtures
    static ArrayList<String> list;

    @BeforeAll
    public static void setUp(){
        // Initialize a new ArrayList
        list = new ArrayList<String>();

        // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    @Order(1)
    public void insertTest(){
        // Assert size of list
        assertEquals(2, list.size(), "Wrong List size");

        //Adding third element to list
        list.add(2,"gama");

        // Assert updated size of list
        assertEquals(3, list.size(), "Wrong updated List size");

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong list element");
        assertEquals("beta", list.get(1), "Wrong list element");
        assertEquals("gama", list.get(2), "Wrong list element");
    }

    @Test
    @Order(2)
    public void replaceTest(){

        // Replace element in ArrayList
        list.set(1,"cargo");

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong list element");
        assertEquals("cargo", list.get(1), "Wrong list element");
        assertEquals("gama", list.get(2), "Wrong list element");
    }
}
