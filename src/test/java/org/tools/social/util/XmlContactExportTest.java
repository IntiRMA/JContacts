package org.tools.social.util;

import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class XmlContactExportTest
{
    private Contact tempContact;

    @Before public void initalize()
    {
        this.tempContact = new Contact("Max", "Mustermann", "mustermann@provider.com",
                "01234/56789", "www.mustermann.com", "Germany");
    }

    @Test public void exportContact() throws IOException, TransformerException, ParserConfigurationException
    {
        String buffer = null;
        StringBuilder originBuilder = new StringBuilder();
        StringBuilder tempBuilder = new StringBuilder();

        File file = new File(getClass().getClassLoader()
            .getResource("Mustermann_Max.xml").getFile());

        try(BufferedReader stream =  new BufferedReader(new FileReader(file)))
        {
            while((buffer = stream.readLine()) != null)
            {
                originBuilder.append(buffer);
            }
        }

        XmlContactExport.exportContact(file.getParent() + "/Mustermann_Max_Temp.xml", this.tempContact);

        try(BufferedReader stream =  new BufferedReader(new FileReader(
                file.getParent() + "/Mustermann_Max_Temp.xml")))
        {
            while((buffer = stream.readLine()) != null)
            {
                tempBuilder.append(buffer);
            }
        }

        assertEquals(originBuilder.toString().equals(tempBuilder.toString()), true);
    }
}