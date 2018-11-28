package com.acuevas.mail.persistence;

import com.acuevas.mail.manager.Manager;
import com.acuevas.mail.model.Message;
import com.acuevas.mail.model.Person;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alex
 */
public abstract class InOutXMLUsingJAXP extends DocumentGenerator {

    private static final String MESSAGETAG = "mensaje";
    private static final String TRANSMITTERTAG = "emisor";
    private static final String RECIEVERTAG = "receptor";
    private static final String TEXTTAG = "texto";
    private static final String DATETIMETAG = "fechahora";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Reads a XML file and returns a Message Object
     *
     * @param message Node
     * @return Message
     */
    public static Collection<Message> getMessages() {
        Set<Message> messages = new HashSet<>();
        Node root = DocumentGenerator.getDocument().getFirstChild();
        NodeList nodesOfMessages = root.getChildNodes();
        for (int i = 0; i < nodesOfMessages.getLength(); i++) {
            Node messageNode = nodesOfMessages.item(i); // nodo libro
            try {
                Message message = getMessage(messageNode);
                if (message.getTransmitter() != null) {
                    messages.add(message);
                }
            } catch (IOException ex) {
                System.out.println("Error reading the message");
            }
        }
        return messages;
    }

    /**
     * Turns a messageNode into a message object
     *
     * @param messageNode
     * @return Message
     * @throws IOException
     */
    public static Message getMessage(Node messageNode) throws IOException {
        Message message;
        LocalDateTime localDateTime = null;
        Person transmitter = null;
        Person reciever = null;
        String text = "";

        if (messageNode.getNodeType() == Node.ELEMENT_NODE) {
            localDateTime = LocalDateTime.parse(messageNode.getAttributes().item(0).getTextContent(), formatter);
            NodeList messageContent = messageNode.getChildNodes();
            for (int j = 0; j < messageContent.getLength(); j++) {
                Node actual = messageContent.item(j);
                if (actual.getNodeType() == Node.ELEMENT_NODE) {
                    switch (actual.getNodeName().toLowerCase()) {
                        case TRANSMITTERTAG:
                            if (Manager.getPersons().values().contains(actual.getTextContent())) {
                                transmitter = Manager.getPersons().get(actual.getTextContent());
                            } else {
                                transmitter = new Person(actual.getTextContent());
                                Manager.getPersons().put(transmitter.getName(), transmitter);
                            }
                            break;
                        case RECIEVERTAG:
                            if (Manager.getPersons().values().contains(actual.getTextContent())) {
                                reciever = Manager.getPersons().get(actual.getTextContent());
                            } else {
                                reciever = new Person(actual.getTextContent());
                                Manager.getPersons().put(reciever.getName(), reciever);
                            }
                            break;
                        case TEXTTAG:
                            text = actual.getTextContent();
                            break;
                        default:
                            throw new IOException(actual.getNodeName());
                    }

                }
            }
        }
        return new Message(localDateTime, transmitter, reciever, text);
    }

    /**
     * Writes down a message into the XML file.
     *
     * @param message
     */
    public static void writeMessage(Message message) {
        Document document = DocumentGenerator.getDocument();
        Node transmitterNode = document.createElement(TRANSMITTERTAG);
        transmitterNode.setTextContent(message.getTransmitter().getName());
        Node recieverNode = document.createElement(RECIEVERTAG);
        recieverNode.setTextContent(message.getReceiver().getName());
        Node textNode = document.createElement(TEXTTAG);
        textNode.setTextContent(message.getText());
        Node messageNode = document.createElement(MESSAGETAG);
        messageNode.appendChild(transmitterNode);
        messageNode.appendChild(recieverNode);
        messageNode.appendChild(textNode);
        Element messageElement = (Element) messageNode;
        messageElement.setAttribute(DATETIMETAG, message.getDateTimeString());
        Node root = document.getFirstChild();
        root.appendChild(messageElement);

        write(document);
    }

    /**
     * Writes the whole document into the XML file.
     *
     * @param document
     */
    public static void write(Document document) {
        try {
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(Manager.getFILE()), format);
            serializer.serialize(document);
        } catch (FileNotFoundException ex) {
            System.out.println("Error, file not found");
        } catch (IOException ex) {
            System.out.println("Error, file not found");
        }
    }
}
