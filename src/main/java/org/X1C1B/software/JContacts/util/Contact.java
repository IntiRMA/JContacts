package org.X1C1B.software.JContacts.util;

import java.util.Comparator;
import java.util.Objects;

/**
 * The Contact class represents a single contact with all depended
 * information like name, phone number and other details.
 *
 * @since 2018-03-22
 */

public class Contact implements Comparable <Contact>
{
    private String prename;
    private String surname;
    private String emailAddress;
    private String phoneNumber;
    private String homepage;
    private String location;

    /**
     * This constructor should only use for internal optimization and
     * with explicit reasons. This constructor don't initialize the fields
     * with legal values and the behavior of the usage of such an object is <b>undefined</b>.
     */

    protected Contact()
    {

    }

    /**
     * The constructor initializes all fields of class with the given arguments.
     * This is the common constructor and should be used to create a new contact
     * object.
     *
     * @param prename The prename of the contact.
     * @param surname The surname of the contact.
     * @param emailAddress The email address of the contact, which should be a legal email address.
     * @param phoneNumber The phone number of the contact.
     * @param homepage The homepage of the contact, which should be a legal URL.
     * @param location The common location of the contact.
     */

    public Contact(String prename, String surname, String emailAddress,
                   String phoneNumber, String homepage, String location)
    {
        this.prename = prename;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.homepage = homepage;
        this.location = location;
    }

    /**
     * The constructor initializes all fields of class with the help of an existing
     * contact object.
     *
     * @param contact The existing contact, which shouldn't be null.
     */

    public Contact(Contact contact)
    {
        this(contact.prename, contact.surname, contact.emailAddress, contact.phoneNumber,
                contact.homepage, contact.location);
    }

    public String getPrename()
    {
        return this.prename;
    }

    public void setPrename(String prename)
    {
        this.prename = prename;
    }

    public String getSurname()
    {
        return this.surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getHomepage()
    {
        return this.homepage;
    }

    public void setHomepage(String homepage)
    {
        this.homepage = homepage;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override public int compareTo(Contact contact)
    {
        return Comparator.comparing(Contact::getPrename)
                .thenComparing(Contact::getSurname)
                .thenComparing(Contact::getEmailAddress)
                .thenComparing(Contact::getPhoneNumber)
                .thenComparing(Contact::getHomepage)
                .thenComparing(Contact::getLocation)
                .compare(this, contact);
    }

    @Override public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }

        if(object == null || getClass() != object.getClass())
        {
            return false;
        }

        Contact contact = (Contact) object;

        return Objects.equals(this.prename, contact.prename) &&
                Objects.equals(this.surname, contact.surname) &&
                Objects.equals(this.emailAddress, contact.emailAddress) &&
                Objects.equals(this.phoneNumber, contact.phoneNumber) &&
                Objects.equals(this.homepage, contact.homepage) &&
                Objects.equals(this.location, contact.location);
    }

    @Override public int hashCode()
    {
        return Objects.hash(this.prename, this.surname, this.emailAddress,
                this.phoneNumber, this.homepage, this.location);
    }

    @Override public String toString()
    {
        return "Contact {" + this.prename + ", " + this.surname + ", " + this.emailAddress +
                ", " + this.phoneNumber + ", " + this.homepage + ", " + this.location + "}";
    }

    public String toStringList()
    {
        return "'" + this.prename + "', '" + this.surname + "', '" + this.emailAddress +
                "', '" + this.phoneNumber + "', '" + this.homepage + "', '" + this.location + "'";
    }

    public static Comparator <Contact> COMPARE_BY_PRENAME = new Comparator<Contact>()
    {
        @Override public int compare(Contact first, Contact second)
        {
            return first.prename.compareTo(second.prename);
        }
    };

    public static Comparator <Contact> COMPARE_BY_SURNAME = new Comparator<Contact>()
    {
        @Override public int compare(Contact first, Contact second)
        {
            return first.surname.compareTo(second.surname);
        }
    };

    public static Comparator <Contact> COMPARE_BY_NAME = new Comparator<Contact>()
    {
        @Override public int compare(Contact first, Contact second)
        {
            return Comparator.comparing(Contact::getPrename)
                    .thenComparing(Contact::getSurname)
                    .compare(first, second);
        }
    };
}
