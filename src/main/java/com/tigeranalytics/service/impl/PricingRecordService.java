package com.tigeranalytics.service.impl;

import com.tigeranalytics.bean.PricingRecordBean;
import com.tigeranalytics.dto.PricingRecord;
import com.tigeranalytics.exception.ApplicationException;
import com.tigeranalytics.repo.PricingRecordRepository;
import com.tigeranalytics.service.IPricingRecordService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PricingRecordService implements IPricingRecordService {

    @Autowired
    private PricingRecordRepository pricingRecordRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void savePricingRecord(PricingRecord pricingRecord) {
        log.info("savePricingRecord(): process started");
        try {
            PricingRecordBean pricingRecordBean = modelMapper.map(pricingRecord, PricingRecordBean.class);
            pricingRecordRepository.save(pricingRecordBean);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }


    }

    @Override
    public PricingRecord getPricingRecordById(Long id) {
        log.info("getPricingRecordById(): process started");
        try {
            PricingRecordBean pricingRecordBean = pricingRecordRepository.findById(id).get();
            return modelMapper.map(pricingRecordBean, PricingRecord.class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public List<PricingRecord> searchPricingRecords(Integer storeId, String sku, String productName, LocalDate date) {
        log.info("searchPricingRecords(): process started");
        try {
            return pricingRecordRepository.findByCriteria(storeId, sku, productName, date)
                    .stream().map(e -> mapToObject(e)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public Page<PricingRecordBean> findAll(String keyword, Pageable pageable) {
        log.info("findAll(): process started");
        try {
            Page<PricingRecordBean> pricingRecords = pricingRecordRepository.findAll(pageable);
            return pricingRecords;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public List<PricingRecord> getAllPricingFeeds() {
        log.info("getAllPricingFeeds(): process started");
        try {
            return pricingRecordRepository.findAll().stream().map(e -> mapToObject(e)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private PricingRecord mapToObject(PricingRecordBean pricingFeedBean) {
        return modelMapper.map(pricingFeedBean, PricingRecord.class);
    }

}
