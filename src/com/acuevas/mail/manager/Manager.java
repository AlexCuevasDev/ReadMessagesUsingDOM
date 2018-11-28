package com.acuevas.mail.manager;

import com.acuevas.mail.model.Message;
import com.acuevas.mail.model.Person;
import com.acuevas.mail.persistence.InOutXMLUsingJAXP;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * class which manages the app. Singleton
 *
 * @author Alex
 */
public class Manager {

    private static final File FILE = new File("Mensajes.xml");
    private static final File OLDFILE = new File("Mensajes.old.xml");
    private static Map<String, Person> persons = new HashMap<>();
    private static Manager manager;

    private Manager() {
    }

    public Manager getInstance() { //singleton
        if (manager != null) {
            manager = new Manager();
        }
        return manager;
    }

    //TODO PARA BORRAR UN MENSAJE HAY QUE SITUARSE EN EL NODO PADRE Y .REMOVECHILD. (VER FOTO EN GALERÍA PARA MÁS INFO)
    public static void main(String[] args) {
        refreshMessages();
        menu1();
        int option = InputAsker.pedirEntero("Please, choose an option: ");
        while (option != 3) {
            switch (option) {
                case 1:
                    String user = InputAsker.pedirCadena("Please, identify yourself. ");
                    Person person;
                    if (persons.values().contains(new Person(user))) {
                        person = persons.get(user);
                    } else {
                        person = new Person(user);
                    }
                    loggedInOptions(person);
                    System.out.println("-------------------------------------------------------------------");
                    menu1();
                    option = InputAsker.pedirEntero("Please, choose an option: ");
                    break;
                case 2:
                    seeRegistered();
                    System.out.println("-------------------------------------------------------------------");
                    menu1();
                    option = InputAsker.pedirEntero("Please, choose an option: ");
                    break;
            }
        }
    }

    public static void refreshMessages() {
        Collection<Message> newMessages = new HashSet<>();
        newMessages = InOutXMLUsingJAXP.getMessages();

        for (Message message : newMessages) {
            persons.get(message.getReceiver().getName()).getAccount().addRecieved(message);
        }
    }

    public static Map<String, Person> getPersons() {
        return persons;
    }

    public static void setPersons(Map<String, Person> persons) {
        Manager.persons = persons;
    }

    public static void seeRegistered() {
        persons.values().forEach(System.out::println);
    }

    public static void loggedInOptions(Person person) {
        menu2();
        int option = InputAsker.pedirEntero("Please, choose an option: ");
        while (option != 4) {
            switch (option) {
                case 1:
                    person.readMessages();
                    menu2();
                    option = InputAsker.pedirEntero("Please, choose an option: ");
                    break;
                case 2:
                    person.sendMessage();
                    refreshMessages();
                    menu2();
                    option = InputAsker.pedirEntero("Please, choose an option: ");
                    break;
                case 3:
                    person.deleteMessage();
                    refreshMessages();
                    menu2();
                    option = InputAsker.pedirEntero("Please, choose an option: ");
                    break;
            }
        }
    }

    private static void menu1() {
        System.out.println("1. Log in");
        System.out.println("2. See registered users");
        System.out.println("3. Exit");
    }

    private static void menu2() {
        System.out.println("1. Read my messages");
        System.out.println("2. Send a message");
        System.out.println("3. Erase a message");
        System.out.println("4. Log out");
    }

    public static File getFILE() {
        return FILE;
    }

}
