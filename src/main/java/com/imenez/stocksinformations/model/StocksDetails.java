package com.imenez.stocksinformations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "StocksDetails")
@Getter
@Setter
public class StocksDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DATA2")
    @SequenceGenerator(sequenceName = "my_seq2", allocationSize = 1, name = "SEQ_DATA2")
    @Column(nullable = false)
    private Integer id;

    private String stocksCode;
    private String currentStockPrice;
    private String fkValue;
    private String pdDDValue;
    private Double circulationRate;
    private Integer circulationStocksNumber;
    private String lastPeriod;
    private String lastPeriodProfit;
    private String tradeMarket;



}
