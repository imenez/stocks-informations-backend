package com.imenez.stocksinformations.repository;


import com.imenez.stocksinformations.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StocksRepository extends JpaRepository<Stocks, Integer> {

    @Modifying
    @Query(value = "TRUNCATE TABLE Stocks", nativeQuery = true)
    void truncateDataStocksTable();
}
