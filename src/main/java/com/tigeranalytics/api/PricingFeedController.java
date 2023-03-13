package com.tigeranalytics.api;

import com.tigeranalytics.dto.PricingFeed;
import com.tigeranalytics.exception.ApplicationException;
import com.tigeranalytics.service.IPricingFeedService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Log4j2
public class PricingFeedController {

    @Autowired
    private IPricingFeedService pricingFeedService;

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("pricingFeed", new PricingFeed());
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute("pricingFeed") PricingFeed pricingFeed, @RequestParam("file") MultipartFile file, Model model) {
        log.info("upload process started");
        long inTime = System.currentTimeMillis();
        try {
            pricingFeedService.savePricingFeeds(file);
            log.info("Upload process completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
            return viewPricingFeeds(model);
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
    }

    @GetMapping("/view")
    public String viewPricingFeeds(Model model) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        try {
            List<PricingFeed> pricingFeeds = pricingFeedService.getAllPricingFeeds();
            model.addAttribute("pricingFeeds", pricingFeeds);
            log.info("View process completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
            return "view";
        } catch (Exception e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
    }
}
