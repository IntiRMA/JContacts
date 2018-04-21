package org.X1C1B.software.JContacts.util;

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

    private SqlContactDatabase(File dbPath, String tableName)
            throws ClassNotFoundException, SQLException, FileNotFoundException
    {
        if(!dbPath.exists() || dbPath.isDirectory())
        {
            throw new FileNotFoundException(dbPath.getAbsolutePath());
        }
        else
        {
            this.DATABASE_PATH = dbPath.getAbsolutePath();
        }

        Class.forName(SqlContactDatabase.DATABASE_DRIVER);
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.DATABASE_PATH);

        if(false == this.tableExist(tableName))
        {
            throw new SQLException("Table not found in database");
        }
        else
        {
            this.TABLE_NAME = tableName;
        }
    }

    public static boolean initialize(File dbPath, String tableName)
            throws ClassNotFoundException, SQLException, FileNotFoundException
    {
        if(null == SqlContactDatabase.instance)
        {
            SqlContactDatabase.instance = new SqlContactDatabase(dbPath, tableName);

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
     * This method checks if the database connection is open. It is highly
     * recommended to call this method before using any methods.
     *
     * @throws SQLException
     */

    public boolean isOpen() throws SQLException
    {
        return !this.connection.isClosed();
    }

    /**
     * This method checks if a given table exists in database.
     *
     * @throws SQLException
     */

    public boolean tableExist(String tableName) throws SQLException
    {
        boolean exists = false;

        try(ResultSet resultSet = this.connection.getMetaData().getTables(null,
                null, tableName, null))
        {
            while(resultSet.next())
            {
                String name = resultSet.getString("TABLE_NAME");

                if(null != name && name.equals(tableName))
                {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
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

                contact.setPrename(result.getString(ContactDatabaseFormat.PRENAME_ID.toString()));
                contact.setSurname(result.getString(ContactDatabaseFormat.SURNAME_ID.toString()));
                contact.setEmailAddress(result.getString(ContactDatabaseFormat.EMAIL_ID.toString()));
                contact.setPhoneNumber(result.getString(ContactDatabaseFormat.PHONE_ID.toString()));
                contact.setHomepage(result.getString(ContactDatabaseFormat.HOMEPAGE_ID.toString()));
                contact.setLocation(result.getString(ContactDatabaseFormat.LOCATION_ID.toString()));

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

    public void writeContact(Contact contact) throws SQLException
    {
        try(Statement statement = this.connection.createStatement())
        {
            String sql = "INSERT INTO " + this.TABLE_NAME + " VALUES (" + contact.toStringList() + ");";
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

    public void deleteContact(Contact contact) throws SQLException
    {
        try(Statement statement = this.connection.createStatement())
        {
            String sql = "DELETE FROM " + this.TABLE_NAME + " WHERE " +
                    ContactDatabaseFormat.PRENAME_ID.toString() + " = '" + contact.getPrename() +
                    "' AND " + ContactDatabaseFormat.SURNAME_ID.toString() + " = '" + contact.getSurname() + "';";

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

    public void updateContact(Contact contact) throws SQLException
    {
        try(Statement statement = this.connection.createStatement())
        {
            String sql = "UPDATE " + this.TABLE_NAME + " SET " +
                    ContactDatabaseFormat.EMAIL_ID.toString() + "='" + contact.getEmailAddress() + "', " +
                    ContactDatabaseFormat.PHONE_ID.toString() + "='" + contact.getPhoneNumber() + "', " +
                    ContactDatabaseFormat.HOMEPAGE_ID.toString() + "='" + contact.getHomepage() + "', " +
                    ContactDatabaseFormat.LOCATION_ID.toString() + "='" + contact.getLocation() +
                    "' WHERE " + ContactDatabaseFormat.PRENAME_ID.toString() + "='" + contact.getPrename() +
                    "' AND " + ContactDatabaseFormat.SURNAME_ID.toString() + "='" + contact.getSurname() + "';";

            statement.executeUpdate(sql);
        }
    }
}
