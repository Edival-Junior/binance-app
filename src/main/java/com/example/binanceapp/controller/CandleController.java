package com.example.binanceapp.controller;
import com.example.binanceapp.model.Candle;
import com.example.binanceapp.service.CandleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/candles")
@Tag(name = "Candle API", description = "Endpoints para obter dados de candles")
public class CandleController {

    private final CandleService candleService;

    public CandleController(CandleService candleService) {
        this.candleService = candleService;
    }

    @GetMapping("/{symbol}/{interval}")
    @Operation(summary = "Obter candles", description = "Obtém dados de candles por símbolo e intervalo")
    public List<Candle> getCandles (
            @Parameter(description = "Símbolo do ativo") @PathVariable String symbol,
            @Parameter(description = "Intervalo dos candles (ex: M1, M5, H1)") @PathVariable String interval) throws IOException {
        return candleService.getCandles(symbol, interval);
    }
}
