package com.acuevas.mail.persistence;

import com.acuevas.mail.manager.Manager;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Alex
 */
public abstract class DocumentGenerator {

    /**
     * This method reads a file and returns a Document to work with in XML
     *
     * @param file input file to read
     * @return Document
     */
    public static Document getDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(Manager.getFILE());
            return document;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            throw new NullPointerException("An error occured reading the file");
        }
    }
}
