package com.tigeranalytics.service.impl;

import com.tigeranalytics.bean.PricingRecordBean;
import com.tigeranalytics.dto.PricingRecord;
import com.tigeranalytics.repo.PricingRecordRepository;
import com.tigeranalytics.service.IPricingRecordService;
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
public class PricingRecordService implements IPricingRecordService {

        @Autowired
        private PricingRecordRepository pricingRecordRepository;
        @Autowired
        private ModelMapper modelMapper;

        @Override
        public void savePricingRecord(PricingRecord pricingRecord) throws Exception {
                PricingRecordBean pricingRecordBean = modelMapper.map(pricingRecord, PricingRecordBean.class);
                pricingRecordRepository.save(pricingRecordBean);

        }

        @Override
        public PricingRecord getPricingRecordById(Long id) {
                PricingRecordBean pricingRecordBean = pricingRecordRepository.findById(id).get();
                return modelMapper.map(pricingRecordBean, PricingRecord.class);
        }

        @Override
        public List<PricingRecord> searchPricingRecords(Integer storeId, String sku, String productName, LocalDate date) {
                return pricingRecordRepository.findByCriteria(storeId, sku, productName, date)
                        .stream().map(e -> mapToObject(e)).collect(Collectors.toList());
        }

        @Override
        public Page<PricingRecordBean> findAll(String keyword, Pageable pageable) {
                Page<PricingRecordBean> pricingRecords = pricingRecordRepository.findAll(pageable);
                return pricingRecords;

        }

        @Override
        public List<PricingRecord> getAllPricingFeeds() {
                return pricingRecordRepository.findAll().stream().map(e -> mapToObject(e)).collect(Collectors.toList());
        }

        private PricingRecord mapToObject(PricingRecordBean pricingFeedBean) {
                return modelMapper.map(pricingFeedBean, PricingRecord.class);
        }

}
