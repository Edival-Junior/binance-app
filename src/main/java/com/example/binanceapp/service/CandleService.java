package com.example.binanceapp.service;

import com.example.binanceapp.model.Candle;
import com.example.binanceapp.util.BinanceApiClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.binanceapp.mapper.CandleMapper.mapJsonArrayToCandles;

@Service
public class CandleService {

    private final BinanceApiClient    binanceApiClient;
    private final Map<String, Double> priceCache = new HashMap<>();

    public CandleService(BinanceApiClient binanceApiClient) {
        this.binanceApiClient = binanceApiClient;
    }

    @Cacheable("candles")
    public List<Candle> getCandles(String symbol, String interval) throws IOException {
        return mapJsonArrayToCandles(binanceApiClient.fetchCandles(symbol, interval));
    }

    @CachePut(value = "candles", key = "#symbol")
    public void updatePrice(String symbol, double price) {
        priceCache.put(symbol, price);
    }

    public Double getPrice(String symbol) {
        return priceCache.get(symbol);
    }
}