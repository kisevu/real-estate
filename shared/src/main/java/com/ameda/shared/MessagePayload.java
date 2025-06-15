package com.ameda.shared;

import java.io.Serializable;
import java.util.Objects;

/**
 * Author: kev.Ameda
 */

public class MessagePayload implements Serializable {
    private String message;
    private String sender;

    public MessagePayload(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public MessagePayload() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "MessagePayload{" +
                "message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MessagePayload that = (MessagePayload) o;
        return Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getSender(), that.getSender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getSender());
    }
}
