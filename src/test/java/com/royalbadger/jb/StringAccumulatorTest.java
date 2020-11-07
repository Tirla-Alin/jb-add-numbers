package com.royalbadger.jb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringAccumulatorTest {
    private final StringAccumulator service = new StringAccumulator();

    @Test
    public void testAddEmpty() {
        assertEquals(service.add(""), 0);
    }

    @Test
    public void testAddNull() {
        assertEquals(service.add(null), 0);
    }

    @Test
    public void testAddOne() {
        assertEquals(service.add("1"), 1);
    }

    @Test
    public void testAddTwo() {
        assertEquals(service.add("1,2"), 3);
    }

    @Test
    public void testAddFirst100Numbers() {
        StringBuilder input = new StringBuilder("1");
        int numbers = 100;
        for (int i = 2; i <= numbers; i++) {
            input.append(",").append(i);
        }
        assertEquals(service.add(input.toString()), numbers * (numbers + 1) / 2);
    }

    @Test
    public void testAddNewLine() {
        assertEquals(service.add("1\n2"), 3);
    }

    @Test
    public void testAddCustomDelimiter() {
        assertEquals(service.add("//;\n1;2"), 3);
    }

    @Test
    public void testAddOneNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> service.add("1,2,3,-1,2"));
        assertEquals(exception.getMessage(), "negatives not allowed [-1]");
    }

    @Test
    public void testAddMultipleNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> service.add("1,2,-3,-1,-2"));
        assertEquals(exception.getMessage(), "negatives not allowed [-3, -1, -2]");
    }

    @Test
    public void testAddIgnoreLargeNumber() {
        assertEquals(service.add("2,1001"), 2);
    }

    @Test
    public void testAddDelimAnyLength() {
        assertEquals(service.add("//***\n1***2***3"), 6);
    }

    @Test
    public void testAddMultipleDelimLengthOne() {
        assertEquals(service.add("//*|_|/\n1*2/3_4"), 10);
    }

    @Test
    public void testAddMultipleDelimAnyLength() {
        assertEquals(service.add("//***|_|//\n1***2//3_4"), 10);
    }
}
