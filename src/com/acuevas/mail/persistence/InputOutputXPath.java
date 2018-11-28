package com.acuevas.mail.persistence;

import com.acuevas.mail.model.Message;
import com.acuevas.mail.model.Person;
import static com.acuevas.mail.persistence.InOutXMLUsingJAXP.getMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alex
 */
public abstract class InputOutputXPath {

    private static final String MESSAGETAG = "mensaje";
    private static final String TRANSMITTERTAG = "emisor";
    private static final String RECIEVERTAG = "receptor";
    private static final String TEXTTAG = "texto";
    private static final String DATETIMETAG = "fechahora";

    /**
     * Gets all the messages from a specific Person
     *
     * @param reciever
     * @return Collection
     */
    public static Collection<Message> getMessagesFrom(Person reciever) {
        Collection<Message> messages = new HashSet<>();
        try {
            String consulta = "//mensaje[./receptor = '";
            consulta += reciever.getName() + "']";

            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xpath.compile(consulta);

            NodeList result = (NodeList) exp.evaluate(DocumentGenerator.getDocument(), XPathConstants.NODESET);

            for (int i = 0; i < result.getLength(); i++) {
                Node messageNode = result.item(i);
                Message message = InOutXMLUsingJAXP.getMessage(messageNode);
                if (message.getTransmitter() != null) {
                    messages.add(message);
                }
            }
        } catch (XPathExpressionException ex) {
            System.out.println("An error ocurred " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error reading the message");
        }
        return messages;
    }

    /**
     * Erases a specific message
     *
     * @param message
     */
    public static void eraseMessage(Message message) {

        try {
            String consulta = "//*[@";
            consulta += DATETIMETAG;
            consulta += "='";
            consulta += message.getDateTimeString();
            consulta += "']";

            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xpath.compile(consulta);
            Document document = DocumentGenerator.getDocument();
            NodeList result = (NodeList) exp.evaluate(document, XPathConstants.NODESET);

            Node root = document.getFirstChild();
            root.removeChild(result.item(0));
            InOutXMLUsingJAXP.write(document);
            System.out.println("Sucessfully erased");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(InputOutputXPath.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
