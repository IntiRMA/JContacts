package org.X1C1B.software.JContacts.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlContactExport
{
    private static DocumentBuilderFactory factory;

    static
    {
        factory = DocumentBuilderFactory.newInstance();
    }

    public static void exportContact(String path, Contact contact)
            throws ParserConfigurationException, TransformerException
    {
        DocumentBuilder builder = XmlContactExport.factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element rootElement = document.createElement("root");

        document.appendChild(rootElement);
        rootElement.appendChild(getContactNode(document, contact));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult stream = new StreamResult(path);
        transformer.transform(source, stream);
    }

    private static Node getContactNode(Document document, Contact contact)
    {
        Element node = document.createElement("contact");

        node.appendChild(getContactElement(document, "prename", contact.getPrename()));
        node.appendChild(getContactElement(document, "surname", contact.getSurname()));
        node.appendChild(getContactElement(document, "email", contact.getEmailAddress()));
        node.appendChild(getContactElement(document, "phone", contact.getPhoneNumber()));
        node.appendChild(getContactElement(document, "homepage", contact.getHomepage()));
        node.appendChild(getContactElement(document, "location", contact.getLocation()));

        return node;
    }

    private static Node getContactElement(Document document, String name, String value)
    {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));

        return node;
    }
}
