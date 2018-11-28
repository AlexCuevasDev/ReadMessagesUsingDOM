package com.acuevas.mail.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Account class with the messages related to this account
 *
 * @author Alex
 */
public class Account extends Codeable {

    private String password;
    private Person person; // I create double dependency to check out whether an account has been asigned or not
    private Set<Message> recieved = new HashSet<>();
    private String username;

    /**
     * Adds a Message to the Set of messages recieved on this account.
     *
     * @param message Message
     */
    public void addRecieved(Message message) {
        recieved.add(message);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the messages recieved
     */
    public Set<Message> getRecieved() {
        return recieved;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns a boolean depending on if the user correctly logs in
     * or not
     *
     * @param username String
     * @param password String
     * @return Boolean
     */
    @Deprecated
    public boolean identificate(String username, String password) {
        if (this.username.equals(username)) {
            if (this.password.equals(password)) {
                System.out.println("Succesfully logged in");
            }
            return true;
        }
        System.out.println("username/password doesn't match, be careful with the caps");
        return false;
    }
}
