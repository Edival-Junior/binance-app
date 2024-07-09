package com.example.binanceapp.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class WebSocketServiceTest {

    @Mock
    private CandleService candleService;

    @InjectMocks
    private WebSocketService webSocketService;

    public WebSocketServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessMessage() {
        String message = "{\"stream\":\"btcusdt@trade\",\"data\":{\"s\":\"BTCUSDT\",\"p\":12345.67,\"q\":1.234}}";

        webSocketService.processMessage(message);

        verify(candleService, times(1)).updatePrice("BTCUSDT", 12345.67);
    }
}