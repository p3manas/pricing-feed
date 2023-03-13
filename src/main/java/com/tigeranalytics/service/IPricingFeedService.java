package com.tigeranalytics.service;

import com.tigeranalytics.dto.PricingFeed;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPricingFeedService {

     void savePricingFeeds(MultipartFile file) throws Exception;

     List<PricingFeed> getAllPricingFeeds();
}