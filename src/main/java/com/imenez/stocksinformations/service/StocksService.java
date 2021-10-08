package com.imenez.stocksinformations.service;

import com.imenez.stocksinformations.model.Stocks;
import com.imenez.stocksinformations.model.StocksDetails;

import java.util.List;

public interface StocksService {

    List<Stocks> fetchStocks();

    List<StocksDetails> getStocksDetails();
}
