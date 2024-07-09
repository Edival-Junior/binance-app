package com.example.binanceapp.service;

import com.example.binanceapp.model.Candle;
import com.example.binanceapp.util.BinanceApiClient;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.json.JSONArray;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class CandleServiceTest {

    @Mock
    private BinanceApiClient binanceApiClient;

    @InjectMocks
    private CandleService candleService;

    public CandleServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCandles() throws IOException, JSONException {
        String symbol = "BTCUSDT";
        String interval = "1m";
        when(binanceApiClient.fetchCandles(symbol, interval)).thenReturn(new JSONArray("[[12345.67, 12346.78, 12344.56, 12345.89, 12345.67, 1.234, 12346.78]]"));

        List<Candle> candles = candleService.getCandles(symbol, interval);
        assertNotNull(candles);
        verify(binanceApiClient, times(1)).fetchCandles(symbol, interval);
    }

    @Test
    void testUpdatePrice() {
        String symbol = "BTCUSDT";
        double price = 12345.67;

        candleService.updatePrice(symbol, price);
        Double cachedPrice = candleService.getPrice(symbol);

        assertNotNull(cachedPrice);
        assert(cachedPrice == price);
    }
}