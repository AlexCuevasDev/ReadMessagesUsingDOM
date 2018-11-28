/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acuevas.mail.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Class which controls the messages
 *
 * @author Alex
 */
public class Message implements Comparable {

    private LocalDateTime dateTime;
    private Person transmitter;
    private Person receiver;
    private String text;

    public Message(LocalDateTime dateTime, Person transmitter, Person receiver, String text) {
        this.dateTime = dateTime;
        this.transmitter = transmitter;
        this.receiver = receiver;
        this.text = text;
    }

    @Override
    public int compareTo(Object t) {
        if (t.getClass() == this.getClass()) {
            Message msg = (Message) t;
            return this.dateTime.compareTo(msg.dateTime);
        } else {
            return 0;
        }
    }

    /**
     * Sets the time of the message based on a String
     *
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.parse(dateTime, formatter);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * note: if you want the string use getDateTimeString instead
     *
     * @return LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * returns the LocalDateTime atribute with the pattern "yyyy-MM-dd HH:mm:ss"
     *
     * @return String
     */
    public String getDateTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public Person getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Person transmitter) {
        this.transmitter = transmitter;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" + "\n" + "dateTime=" + dateTime + "\n" + ". transmitter=" + transmitter + "\n" + ". receiver=" + receiver + "\n" + ". text= " + text + '}' + "\n";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.dateTime);
        return hash;
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
        final Message other = (Message) obj;
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        return true;
    }

}
