package com.tigeranalytics.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tigeranalytics.bean.PricingFeedBean;
import com.tigeranalytics.dto.PricingFeed;
import com.tigeranalytics.exception.ApplicationException;
import com.tigeranalytics.repo.PricingFeedRepository;
import com.tigeranalytics.service.IPricingFeedService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PricingFeedService implements IPricingFeedService {
    @Autowired
    private PricingFeedRepository pricingFeedRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void savePricingFeeds(MultipartFile file) {
        log.info("savePricingFeeds(): process started");
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<PricingFeedBean> csvToBean = new CsvToBeanBuilder<PricingFeedBean>(reader)
                    .withType(PricingFeedBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<PricingFeedBean> pricingFeeds = csvToBean.parse();
            pricingFeedRepository.saveAll(pricingFeeds);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<PricingFeed> getAllPricingFeeds() {
        log.info("getAllPricingFeeds(): process started");
        try {
            return pricingFeedRepository.findAll().stream().map(e -> mapToObject(e)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private PricingFeed mapToObject(PricingFeedBean pricingFeedBean) {
        return modelMapper.map(pricingFeedBean, PricingFeed.class);
    }


}