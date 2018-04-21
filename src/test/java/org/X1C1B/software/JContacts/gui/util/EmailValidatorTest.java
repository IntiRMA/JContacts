package org.X1C1B.software.JContacts.gui.util;

import java.util.Arrays;
import java.util.Collection;

import org.X1C1B.software.JContacts.util.EmailValidator;
import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) public class EmailValidatorTest
{
    private String inputAddress;
    private Boolean expectedResult;
    private EmailValidator emailValidator;

    @Before public void initialize()
    {
        this.emailValidator = new EmailValidator();
    }

    public EmailValidatorTest(String inputAddress, Boolean expectedResult)
    {
        this.inputAddress = inputAddress;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters public static Collection emailAddresses()
    {
        return Arrays.asList(new Object[][] {
                { "example@gxm.de", true },
                { "example.xy@gmail.de", true },
                { "example-xy@gmail.de", true },
                { "example.com", false },
                { "example@xy", false },
                { "example@gmail.a", false },
                { "example@.com", false },
                { "example@.com.com", false }
        });
    }

    @Test public void validationChecker()
    {
        assertEquals(this.expectedResult, this.emailValidator.validate(this.inputAddress));
    }
}