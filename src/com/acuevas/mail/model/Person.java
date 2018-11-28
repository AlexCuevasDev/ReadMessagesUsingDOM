package com.acuevas.mail.model;

import com.acuevas.mail.manager.InputAsker;
import com.acuevas.mail.manager.Manager;
import com.acuevas.mail.persistence.InOutXMLUsingJAXP;
import com.acuevas.mail.persistence.InputOutputXPath;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Alex
 */
public class Person {

    Account account = new Account(); //right now a person only has one account but with this class-model we can update the program so each person has multiple ones.
    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        return Objects.equals(this.name, other.name);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return (" Name: " + getName());
    }

    /**
     * Sends a message.
     */
    public void sendMessage() {
        String reciever1 = InputAsker.pedirCadena("Please write down who do you want to send the message: ");
        Person reciever;
        if (Manager.getPersons().values().contains(new Person(reciever1))) {
            reciever = Manager.getPersons().get(reciever1);
        } else {
            reciever = new Person(reciever1);
        }
        String text = InputAsker.pedirCadena("Please write down your message: ");
        Message message = new Message(LocalDateTime.now(), this, reciever, text);
        InOutXMLUsingJAXP.writeMessage(message);
    }

    /**
     * reads all the messages this person has.
     */
    public void readMessages() {
        if (this.getAccount().getRecieved().isEmpty()) {
            System.out.println("You don't have any messages");
        } else {
            System.out.println("Here are your messages: ");
            InputOutputXPath.getMessagesFrom(this).forEach(System.out::println);
        }
    }

    /**
     * Deletes a specific message.
     */
    public void deleteMessage() {
        int i = 1;
        Map<Integer, Message> messages = new HashMap<>();
        for (Message message : getAccount().getRecieved()) {
            System.out.println(i + "...... " + message);
            messages.put(i, message);
            i++;
        }
        int option = InputAsker.pedirEntero("Which one?: ");
        while (option > getAccount().getRecieved().size() || option <= 0) {
            option = InputAsker.pedirEntero("incorrect number");
        }
        InputOutputXPath.eraseMessage(messages.get(option));
        getAccount().getRecieved().remove(messages.get(option));
    }
}
