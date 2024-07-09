package com.example.binanceapp.service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import okio.ByteString;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WebSocketService extends TextWebSocketHandler {

    private static final String BINANCE_WEBSOCKET_URL = "wss://stream.binance.com:9443/ws";
    private WebSocket webSocket;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final CandleService candleService;

    @Autowired
    public WebSocketService(CandleService candleService) {
        this.candleService = candleService;

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(BINANCE_WEBSOCKET_URL)
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                WebSocketService.this.webSocket = webSocket;
                System.out.println("Conectado ao Binance WebSocket");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("Mensagem: " + text);
                processMessage(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                System.out.println("Binary message: " + bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("Fechando conexão WebSocket");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messageText = message.getPayload();
        subscribeToStream(messageText);
    }

    protected void processMessage(String message) {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.has("stream") && jsonObject.getString("stream").endsWith("@trade")) {
            JSONObject data = jsonObject.getJSONObject("data");
            String symbol = data.getString("s");
            double price = data.getDouble("p");
            double quantity = data.getDouble("q");
            System.out.println("Trade event received: " + symbol + " - Price: " + price + " - Quantity: " + quantity);

            candleService.updatePrice(symbol, price);

            TextMessage textMessage = new TextMessage(message);
            sessions.values().forEach(s -> {
                try {
                    s.sendMessage(textMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            System.out.println("Unknown message: " + message);
        }
    }

    public void subscribeToStream(String stream) {
        if (webSocket != null) {
            webSocket.send("{\"method\":\"SUBSCRIBE\",\"params\":[\"" + stream + "\"],\"id\":1}");
        }
    }

    public void unsubscribeFromStream(String stream) {
        if (webSocket != null) {
            webSocket.send("{\"method\":\"UNSUBSCRIBE\",\"params\":[\"" + stream + "\"],\"id\":1}");
        }
    }
}