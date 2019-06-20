package com.learning.basics;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator calc = Calculator.getInstance();

    @Test
    public void testAdd() throws Exception {
        Assert.assertEquals(5, calc.add(2,3));
    }

}
