package com.tigeranalytics.service;

import com.tigeranalytics.bean.PricingRecordBean;
import com.tigeranalytics.dto.PricingFeed;
import com.tigeranalytics.dto.PricingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IPricingRecordService {

     void savePricingRecord(PricingRecord pricingRecord) throws Exception;

     PricingRecord getPricingRecordById(Long id) ;

     List<PricingRecord> searchPricingRecords(Integer storeId, String sku, String productName, LocalDate date) ;

     Page<PricingRecordBean> findAll(String keyword, Pageable pageable);

     List<PricingRecord> getAllPricingFeeds();
}