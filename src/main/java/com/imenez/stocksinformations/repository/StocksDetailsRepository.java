package com.imenez.stocksinformations.repository;



import com.imenez.stocksinformations.model.StocksDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StocksDetailsRepository extends JpaRepository<StocksDetails, Integer> {


    @Modifying
    @Query(value = "TRUNCATE TABLE stocks_details", nativeQuery = true)
    void truncateDataStocksDetailsTable();

    List<StocksDetails> findAllByOrderByCirculationStocksNumberAsc();
}
