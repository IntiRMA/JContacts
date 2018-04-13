package org.tools.social.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements a method to validate the signature of an email
 * address with help of regular expressions.
 *
 * @since 2018-03-22
 */

public class EmailValidator
{
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN;

    static
    {
        EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    }

    public EmailValidator()
    {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * The validate method uses a regular expression pattern to check the
     * specific email address.
     *
     * @param str The email address for validating.
     * @return True if the given email address matches the pattern, else false.
     * @see java.util.regex.Matcher
     * @see java.util.regex.Pattern
     */

    public boolean validate(final String str)
    {
        matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
