package com.example.binanceapp.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BinanceApiClient {

    private final        OkHttpClient client   = new OkHttpClient();
    private static final String       BASE_URL = "https://api.binance.com/api/v3/klines";

    public JSONArray fetchCandles(String symbol, String interval) throws IOException {
        String url = String.format("%s?symbol=%s&interval=%s", BASE_URL, symbol, interval);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return new JSONArray(response.body().string());
        }
    }
}