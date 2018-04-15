package org.tools.social.util;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class XmlContactImportTest
{
    private Contact tempContact;

    @Before public void initalize()
    {
        this.tempContact = new Contact("Max", "Mustermann", "mustermann@provider.com",
                "01234/56789", "www.mustermann.com", "Germany");
    }

    @Test public void importContact() throws IOException, SAXException, ParserConfigurationException, ContactXmlFormatException
    {
        Contact importedContact = XmlContactImport.importContact(getClass().getClassLoader()
                .getResource("Mustermann_Max.xml").toString());

        assertEquals(tempContact.equals(importedContact), true);
    }

    @Test(expected = ContactXmlFormatException.class) public void importDamagedContact()
            throws IOException, SAXException, ParserConfigurationException, ContactXmlFormatException
    {
        Contact importedContact = XmlContactImport.importContact(getClass().getClassLoader()
                .getResource("Mustermann_Max_damaged.xml").toString());
    }
}