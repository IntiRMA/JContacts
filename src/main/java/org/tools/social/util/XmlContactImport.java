package org.tools.social.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlContactImport
{
    private static DocumentBuilderFactory factory;

    static
    {
        factory = DocumentBuilderFactory.newInstance();
    }

    public static Contact importContact(String path)
            throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilder builder = XmlContactImport.factory.newDocumentBuilder();
        Document document = builder.parse(path);

        NodeList nodeList = document.getElementsByTagName("contact");

        if(0 >= nodeList.getLength())
        {
            throw new IOException("No stored contact found!");
        }

        Contact contact = new Contact();
        Node node = nodeList.item(0);

        if(Node.ELEMENT_NODE == node.getNodeType())
        {
            Element element = (Element) node;

            contact.setPrename(element.getElementsByTagName("prename").item(0).getTextContent());
            contact.setSurname(element.getElementsByTagName("surname").item(0).getTextContent());
            contact.setEmailAddress(element.getElementsByTagName("email").item(0).getTextContent());
            contact.setPhoneNumber(element.getElementsByTagName("phone").item(0).getTextContent());
            contact.setHomepage(element.getElementsByTagName("homepage").item(0).getTextContent());
            contact.setLocation(element.getElementsByTagName("location").item(0).getTextContent());
        }

        return contact;
    }
}
