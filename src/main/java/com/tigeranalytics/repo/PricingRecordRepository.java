package com.tigeranalytics.repo;

import com.tigeranalytics.bean.PricingRecordBean;
import com.tigeranalytics.dto.PricingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PricingRecordRepository extends JpaRepository<PricingRecordBean, Long> {

    @Query("SELECT p FROM PricingRecord p WHERE (:storeId IS NULL OR p.storeId = :storeId) "
            + "AND (:sku IS NULL OR p.sku = :sku) "
            + "AND (:productName IS NULL OR p.productName = :productName) "
            + "AND (:date IS NULL OR p.date = :date)")
    List<PricingRecordBean>  findByCriteria(Integer storeId, String sku, String productName, LocalDate date);
}
