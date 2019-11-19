package ru.deft.homework.model;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import ru.deft.homework.messagesystem.model.Message;

/*
 * Created by sgolitsyn on 11/18/19
 */
public class MessageEncoder implements Encoder.Text<Message>{
    private static Gson gson = new Gson();

    @Override
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
