package org.tools.social.util;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This singleton class defines the interface between the application and the SQLite
 * database file. Moreover it implements methods to read, write and delete contacts
 * in database.
 *
 * @since 2018-03-23
 * @see Contact
 */

public class SqlContactDatabase implements AutoCloseable
{
    private static final String DATABASE_DRIVER;
    private static SqlContactDatabase instance;

    private Connection connection;
    private final String DATABASE_PATH;
    private final String TABLE_NAME;

    static
    {
        instance = null;
        DATABASE_DRIVER = "org.sqlite.JDBC";
    }

    private SqlContactDatabase(File file, String table) throws ClassNotFoundException, SQLException
    {
        this.DATABASE_PATH = file.getAbsolutePath();
        this.TABLE_NAME = table;

        Class.forName(SqlContactDatabase.DATABASE_DRIVER);
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.DATABASE_PATH);
    }

    public static boolean initialize(File file, String table)
            throws ClassNotFoundException, SQLException, FileNotFoundException
    {
        if(null == SqlContactDatabase.instance)
        {
            if(!file.exists() || file.isDirectory())
            {
                throw new FileNotFoundException(file.getAbsolutePath());
            }

            SqlContactDatabase.instance = new SqlContactDatabase(file, table);

            return true;
        }

        return false;
    }

    public static SqlContactDatabase getInstance()
    {
        return instance;
    }

    /**
     * This method closes the database connection. It is highly
     * recommended to call this method before quitting the application.
     *
     * @throws Exception
     */

    @Override public void close() throws Exception
    {
        this.connection.close();
    }

    /**
     * This method reads all stored contacts from database.
     *
     * @return An array of read contacts.
     * @throws FileNotFoundException
     * @throws SQLException
     */

    public Contact [] readAllContacts() throws SQLException
    {
        Contact [] contactArray = null;
        List <Contact> contactList = new ArrayList <> ();

        try(Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + this.TABLE_NAME + ";"))
        {
            while(result.next())
            {
                Contact contact = new Contact();

                contact.setPrename(result.getString("PRENAME"));
                contact.setSurname(result.getString("SURNAME"));
                contact.setEmailAddress(result.getString("EMAIL"));
                contact.setPhoneNumber(result.getString("PHONE"));
                contact.setHomepage(result.getString("HOMEPAGE"));
                contact.setLocation(result.getString("LOCATION"));

                contactList.add(contact);
            }
        }

        contactArray = new Contact [contactList.size()];
        contactArray = contactList.toArray(contactArray);

        return contactArray;
    }

    /**
     * The writeContact method stores a given contact to the database.
     *
     * @param contact The contact which should be stored.
     * @throws FileNotFoundException
     * @throws SQLException
     */

    public void writeContact(Contact contact) throws FileNotFoundException, SQLException
    {
        try(Statement statement = this.connection.createStatement())
        {
            String sql = "INSERT INTO " + this.TABLE_NAME + " VALUES ('" + contact.getPrename() +
                    "', '" + contact.getSurname() + "', '" + contact.getEmailAddress() +
                    "', '" + contact.getPhoneNumber() + "', '" + contact.getHomepage() +
                    "', '" + contact.getLocation() + "');";

            statement.executeUpdate(sql);
        }
    }

    /**
     * This method deletes a contact of database, comparing their signatures.
     *
     * @param contact The contact which should be deleted.
     * @throws FileNotFoundException
     * @throws SQLException
     */

    public void deleteContact(Contact contact) throws FileNotFoundException, SQLException
    {
        try(Statement statement = this.connection.createStatement())
        {
            String sql = "DELETE FROM " + this.TABLE_NAME + " WHERE PRENAME = '" + contact.getPrename() +
                    "' AND SURNAME = '" + contact.getSurname() + "';";

            statement.executeUpdate(sql);
        }
    }

    /**
     * This method updates a contact of database, comparing their signatures.
     *
     * @param contact The contact which should be updated.
     * @throws FileNotFoundException
     * @throws SQLException
     */

    public void updateContact(Contact contact) throws FileNotFoundException, SQLException
    {
        try(Statement statement = this.connection.createStatement())
        {
            String sql = "UPDATE " + this.TABLE_NAME + " SET EMAIL='" + contact.getEmailAddress() +
                    "', PHONE='" + contact.getPhoneNumber() + "', HOMEPAGE='" + contact.getHomepage() +
                    "', LOCATION='" + contact.getLocation() + "' WHERE PRENAME='" + contact.getPrename() +
                    "' AND SURNAME='" + contact.getSurname() + "';";

            statement.executeUpdate(sql);
        }
    }
}
