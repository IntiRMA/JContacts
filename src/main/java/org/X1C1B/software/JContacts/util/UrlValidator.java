package org.X1C1B.software.JContacts.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator
{
    private Pattern pattern;
    private Matcher matcher;

    private static final String HOMEPAGE_PATTERN;

    static
    {
        HOMEPAGE_PATTERN = "(http://|https://)?[a-z]{3}\\.[\\w|-]\\w[\\w|-]*\\.[a-z]{2,3}";
    }

    public UrlValidator()
    {
        pattern = Pattern.compile(HOMEPAGE_PATTERN);
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
