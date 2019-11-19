package ru.deft.homework.messagesystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Message {
    public static final Message VOID_MESSAGE = new Message();

    private final UUID id = UUID.randomUUID();
    private String from;
    private String to;
    private Optional<UUID> sourceMessageId;
    private String type;
    private String content;
    private int payloadLength;
    private byte[] payload;

    public Message() {
        this.from = null;
        this.to = null;
        this.sourceMessageId = Optional.empty();
        this.type = "voidTechnicalMessage";
        this.payload = new byte[1];
        this.payloadLength = payload.length;
        this.content = null;
    }

    public <T> Message(String from, String to, String type, T data) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.content = data.toString();
    }

    public Message(String from, String to, Optional<UUID> sourceMessageId, String type, String payload) {
        this.from = from;
        this.to = to;
        this.sourceMessageId = sourceMessageId;
        this.type = type;
        this.content = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", sourceMessageId=" + sourceMessageId +
                ", type='" + type + '\'' +
                ", payloadLength=" + payloadLength +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public byte[] getPayload() {
        return payload;
    }

    public int getPayloadLength() {
        return payloadLength;
    }


    public Optional<UUID> getSourceMessageId() {
        return sourceMessageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
