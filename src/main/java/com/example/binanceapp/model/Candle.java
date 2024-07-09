package com.example.binanceapp.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Candle {

    private long openTime;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private long closeTime;
}
