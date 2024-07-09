package com.example.binanceapp.mapper;

import com.example.binanceapp.model.Candle;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CandleMapper {

    public static List<Candle> mapJsonArrayToCandles(JSONArray jsonArray) {
        List<Candle> candles = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray jsonCandle = jsonArray.getJSONArray(i);
            Candle candle = mapJsonCandleToObject(jsonCandle);
            candles.add(candle);
        }

        return candles;
    }

    private static Candle mapJsonCandleToObject(JSONArray jsonCandle) {
        Candle candle = new Candle();

        // Extract data from the JSONArray
        candle.setOpenTime(jsonCandle.getLong(0));
        candle.setOpen(Double.parseDouble(jsonCandle.getString(1)));
        candle.setHigh(Double.parseDouble(jsonCandle.getString(2)));
        candle.setLow(Double.parseDouble(jsonCandle.getString(3)));
        candle.setClose(Double.parseDouble(jsonCandle.getString(4)));
        candle.setVolume(Double.parseDouble(jsonCandle.getString(5).replace(".", ".")));
        candle.setCloseTime(jsonCandle.getLong(6));

        return candle;
    }

}