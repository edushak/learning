package com.learning.basics;

//import org.junit.Assert;
//import org.junit.Test;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CalculatorTest {

    Calculator calc = Calculator.getInstance();

    @Test
    public void testAdd() {
        Assert.assertEquals(5, calc.add(2,3));
    }

}
