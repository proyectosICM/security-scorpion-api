package com.icm.security_scorpion_api.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        //System.out.println("Cliente conectado (s): " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //System.out.println("Mensaje recibido (s): " + message.getPayload());

        // Reenvía el mensaje a todos los clientes conectados
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("Mensaje de " + session.getId() + ": " + message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        //System.out.println("Cliente desconectado (s): " + session.getId());
    }

    public void sendMessageToAll(String message) {
        System.out.println("1 - Enviando mensaje a todos");

        synchronized (sessions) {
            System.out.println("Sesiones activas: " + sessions.size());

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    System.out.println("2 - Enviando a sesión: " + session.getId());
                    try {
                        session.sendMessage(new TextMessage(message));
                        System.out.println("3 - Mensaje enviado correctamente a " + session.getId());
                    } catch (IOException e) {
                        System.out.println("Error enviando mensaje a " + session.getId());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Sesión cerrada: " + session.getId());
                }
            }
        }
    }
}
