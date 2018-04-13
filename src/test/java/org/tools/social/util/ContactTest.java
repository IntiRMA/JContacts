package org.tools.social.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest
{
    private String prename;
    private String surname;
    private String email;
    private String phone;
    private String homepage;
    private String location;
    private Contact contact;

    @Before public void initalize()
    {
        this.prename = "prename";
        this.surname = "surname";
        this.email = "prename@surname.net";
        this.phone = "0000/0000";
        this.homepage = "prename-surname.net";
        this.location = "US";

        this.contact = new Contact(this.prename, this.surname, this.email,
                this.phone, this.homepage, this.location);
    }

    @Test public void getPrename()
    {
        assertEquals(this.contact.getPrename(), this.prename);
    }

    @Test public void getSurname()
    {
        assertEquals(this.contact.getPrename(), this.prename);
    }

    @Test public void getEmailAddress()
    {
        assertEquals(this.contact.getEmailAddress(), this.email);
    }

    @Test public void getPhoneNumber()
    {
        assertEquals(this.contact.getPhoneNumber(), this.phone);
    }

    @Test public void getHomepage()
    {
        assertEquals(this.contact.getHomepage(), this.homepage);
    }

    @Test public void getLocation()
    {
        assertEquals(this.contact.getLocation(), this.location);
    }

    @Test public void equals()
    {
        final Contact OTHER = new Contact(this.prename, this.surname, this.email,
                this.phone, this.homepage, this.location);

        assertEquals(this.contact.getPrename(), OTHER.getPrename());
        assertEquals(this.contact.getSurname(), OTHER.getSurname());
        assertEquals(this.contact.getEmailAddress(), OTHER.getEmailAddress());
        assertEquals(this.contact.getPhoneNumber(), OTHER.getPhoneNumber());
        assertEquals(this.contact.getHomepage(), OTHER.getHomepage());
        assertEquals(this.contact.getLocation(), OTHER.getLocation());
    }

    @Test public void ContactCopyConstructor()
    {
        final Contact OTHER = new Contact(this.contact);

        assertEquals(this.contact.equals(OTHER), true);
    }

    @Test public void setPrename()
    {
        Contact OTHER = new Contact(this.contact);

        OTHER.setPrename("test");
        assertEquals(OTHER.getPrename(), "test");
    }

    @Test public void setSurname()
    {
        Contact OTHER = new Contact(this.contact);

        OTHER.setSurname("test");
        assertEquals(OTHER.getSurname(), "test");
    }

    @Test public void setEmailAddress()
    {
        Contact OTHER = new Contact(this.contact);

        OTHER.setEmailAddress("test");
        assertEquals(OTHER.getEmailAddress(), "test");
    }

    @Test public void setPhoneNumber()
    {
        Contact OTHER = new Contact(this.contact);

        OTHER.setPhoneNumber("test");
        assertEquals(OTHER.getPhoneNumber(), "test");
    }

    @Test public void setHomepage()
    {
        Contact OTHER = new Contact(this.contact);

        OTHER.setHomepage("test");
        assertEquals(OTHER.getHomepage(), "test");
    }

    @Test public void setLocation()
    {
        Contact OTHER = new Contact(this.contact);

        OTHER.setLocation("test");
        assertEquals(OTHER.getLocation(), "test");
    }
}