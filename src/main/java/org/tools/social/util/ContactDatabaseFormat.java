package org.tools.social.util;

public enum ContactDatabaseFormat
{
    PRENAME_ID("PRENAME"), SURNAME_ID("SURNAME"), EMAIL_ID("EMAIL"),
        PHONE_ID("PHONE"), HOMEPAGE_ID("HOMEPAGE"), LOCATION_ID("LOCATION");

    private String token;

    ContactDatabaseFormat(String token)
    {
        this.token = token;
    }

    @Override public String toString()
    {
        return this.token;
    }
}
