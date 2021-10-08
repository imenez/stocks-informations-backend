package com.imenez.stocksinformations.service.impl;


import com.google.gson.Gson;
import com.imenez.stocksinformations.model.Stocks;
import com.imenez.stocksinformations.model.StocksDetails;
import com.imenez.stocksinformations.repository.StocksDetailsRepository;
import com.imenez.stocksinformations.repository.StocksRepository;
import com.imenez.stocksinformations.service.StocksService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StocksServiceImpl implements StocksService {



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private StocksDetailsRepository stocksDetailsRepository;



    private static final String TRADINGVIEW_STOCKS_URL = "https://scanner.tradingview.com/turkey/scan";


    @Override
    @Transactional
    public List<Stocks> fetchStocks() {
        stocksRepository.truncateDataStocksTable();
        Object stocksObject = new Object();


        stocksObject = restTemplate.getForObject(TRADINGVIEW_STOCKS_URL, Object.class);
        //System.out.println(stocksObject);
        String jsonInString = new Gson().toJson(stocksObject);
        JSONObject stocksJSONObject = new JSONObject(jsonInString);
        //System.out.println(stocksJSONObject);
        List<Stocks> stocksList = new ArrayList<>();


        String count = String.valueOf(stocksJSONObject.get("totalCount"));


        stocksJSONObject.keySet().forEach(key -> {
            if (key.equals("data")) {
                JSONArray entry = stocksJSONObject.getJSONArray(key);
                for (int i = 0; i < entry.length(); i++) {

                    Stocks stocks = new Stocks();

                    String value = String.valueOf(entry.getJSONObject(i).get("s"));
                    String stocksCode = value.substring(value.lastIndexOf(":") + 1);

                    stocks.setStocksCode(stocksCode);
                    stocksList.add(stocks);

                }
            }

        });


        stocksRepository.saveAll(stocksList);


        return stocksList;
    }


    public List<StocksDetails> getStocksDetails(){
        return stocksDetailsRepository.findAllByOrderByCirculationStocksNumberAsc();
    }


}
