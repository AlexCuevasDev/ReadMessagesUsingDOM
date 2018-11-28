/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acuevas.mail.model;

import java.util.Objects;

/**
 * Class not used at this moment, ment to be used in the future to add more
 * accounts and to not depend on a date for the messages
 *
 * @author Alex
 */
public abstract class Codeable {

    private String code;
    private static long index;

    public Codeable() {
        code = this.getClass().getSimpleName().substring(0, 3) + index++;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.code);
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
        final Codeable other = (Codeable) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Code: " + code;
    }

    public String getCode() {
        return code;
    }

}
