package ru.deft.homework.messagesystem.messagesystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String from;
    private String message;

    public Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message [from=" + from + ", message=" + message + "]";
    }

}
