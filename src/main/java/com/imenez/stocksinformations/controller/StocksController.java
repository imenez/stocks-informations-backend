package com.imenez.stocksinformations.controller;


import com.imenez.stocksinformations.model.Stocks;
import com.imenez.stocksinformations.model.StocksDetails;
import com.imenez.stocksinformations.service.StocksService;
import com.imenez.stocksinformations.util.StocksDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {


    @Autowired
    private StocksService stocksService;

    @Autowired
    private StocksDetailsUtil stocksDetailsUtil;


    @GetMapping("/fetchstocks")
    public ResponseEntity<List<Stocks>> fetchStocks(){

        return ResponseEntity.ok(stocksService.fetchStocks());
    }


    @GetMapping("/fetchstocksdetails")
    public void fetchStocksDetails(){
        stocksDetailsUtil.fetchStocksDetails();

    }

    @GetMapping("/getstocksdetails")
    public List<StocksDetails> getStocksDetails(){

        return stocksService.getStocksDetails();
    }






}
