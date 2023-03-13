package com.tigeranalytics.api;

import com.tigeranalytics.dto.PricingFeed;
import com.tigeranalytics.service.IPricingFeedService;
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
        try {
            pricingFeedService.savePricingFeeds(file);
            return viewPricingFeeds(model);
        } catch (Exception e) {
            return "upload";
        }
    }

    @GetMapping("/view")
    public String viewPricingFeeds(Model model) {
        List<PricingFeed> pricingFeeds = pricingFeedService.getAllPricingFeeds();
        model.addAttribute("pricingFeeds", pricingFeeds);
        return "view";
    }
}
