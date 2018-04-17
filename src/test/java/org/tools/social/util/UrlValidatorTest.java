package org.tools.social.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class) public class UrlValidatorTest
{
    private String inputAddress;
    private Boolean expectedResult;
    private UrlValidator urlValidator;

    @Before public void initialize()
    {
        this.urlValidator = new UrlValidator();
    }

    public UrlValidatorTest(String inputAddress, Boolean expectedResult)
    {
        this.inputAddress = inputAddress;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters public static Collection emailAddresses()
    {
        return Arrays.asList(new Object[][] {
                { "https://www.example.de", true },
                { "http://www.example.com", true },
                { "web.example.net", true },
                { "example.com", false },
                { "example.x", false },
                { "x.example", false },
                { "www.example", false },
                { "example.example.de", false }
        });
    }

    @Test public void validate()
    {
        assertEquals(this.expectedResult, this.urlValidator.validate(this.inputAddress));
    }
}