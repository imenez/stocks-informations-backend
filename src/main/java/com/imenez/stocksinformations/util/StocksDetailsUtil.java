package com.imenez.stocksinformations.util;


import com.imenez.stocksinformations.model.StocksDetails;
import com.imenez.stocksinformations.model.Stocks;
import com.imenez.stocksinformations.repository.StocksDetailsRepository;
import com.imenez.stocksinformations.repository.StocksRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class StocksDetailsUtil {

    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksDetailsRepository stocksDetailsRepository;

    private static final String STOCKS_DETAILS_URL = "https://www.borsagundem.com/piyasa-ekrani/hisse-detay/";
    private static final String USER_AGENT_HEADER =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:56.0) Gecko/20100101 Firefox/56.0";
    private static final int TIMEOUT = 120000000;

    private List<StocksDetails> stocksDetailsList = new ArrayList<>();


    @Transactional
    public void fetchStocksDetails() {
        List<Stocks> stocksList = getStocksFromDB();
        stocksDetailsRepository.truncateDataStocksDetailsTable();

        for (Stocks stocks : stocksList) {
            String stockCodeParameter = stocks.getStocksCode();
            StocksDetails stocksDetails = new StocksDetails();

            //jsoup

            try {

                final Document document = Jsoup.connect(STOCKS_DETAILS_URL + stockCodeParameter).userAgent(USER_AGENT_HEADER).timeout(TIMEOUT).get();


                String lastPeriod = "NaN";
                Elements div = document.getElementsByClass("hisdtl");
                //System.out.println(table);
                Elements ul = div.select("ul");
                //System.out.println(row);
                Element firstRow = ul.get(0);
                Element thirdRow = ul.get(2);
                Element fourthRow = ul.get(3);

                String currentStockPrice = firstRow.getElementsByClass("c1").select("span").text();
                String fkValue = firstRow.getElementsByClass("c4").select("span").text();
                String pdDDValue = firstRow.getElementsByClass("c6").select("span").text();
                String circulationRate = thirdRow.getElementsByClass("s4").select("span").text();
                String circulationStocksNumber = fourthRow.getElementsByClass("s1").select("span").text();
                if (!fourthRow.getElementsByClass("s2").select("span").text().isEmpty()) {
                    lastPeriod = fourthRow.getElementsByClass("s2").select("span").text();
                }
                String lastPeriodProfit = fourthRow.getElementsByClass("s3").select("span").text();
                String tradeMarket = fourthRow.getElementsByClass("s4").select("span").text();


                circulationRate = circulationRate.trim().replaceAll(",", ".");
                String[] circulationStocksNumberParsed = circulationStocksNumber.split(",");
                String circulationStocksNumberParsedString = circulationStocksNumberParsed[0].replaceAll("\\.", "");
                //fixle

                stocksDetails.setStocksCode(stockCodeParameter);
                stocksDetails.setCurrentStockPrice(currentStockPrice);
                stocksDetails.setFkValue(fkValue);
                stocksDetails.setPdDDValue(pdDDValue);
                stocksDetails.setCirculationRate(Double.parseDouble(circulationRate));
                stocksDetails.setCirculationStocksNumber(Integer.parseInt(circulationStocksNumberParsedString));
                stocksDetails.setLastPeriodProfit(lastPeriod);
                stocksDetails.setLastPeriodProfit(lastPeriodProfit);
                stocksDetails.setTradeMarket(tradeMarket);

                stocksDetailsList.add(stocksDetails);
                //System.out.println(fkValue);


            } catch (Exception ex) {

                System.out.println(ex.getMessage());

            }


        }

        stocksDetailsRepository.saveAll(stocksDetailsList);

    }


    public List<Stocks> getStocksFromDB() {

        return stocksRepository.findAll();
    }

}
