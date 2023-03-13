package com.tigeranalytics.api;

import com.tigeranalytics.bean.PricingRecordBean;
import com.tigeranalytics.dto.PricingRecord;
import com.tigeranalytics.exception.ApplicationException;
import com.tigeranalytics.service.IPricingRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/pricing")
@Log4j2
public class PricingRecordController {
    @Autowired
    private IPricingRecordService pricingRecordService;

    @GetMapping("/pricingFeeds")
    public String getPricingFeeds(Model model, @RequestParam(required = false) String keyword,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        try {
            Pageable paging = PageRequest.of(page - 1, size);
            Page<PricingRecordBean> pricingFeedBeans = pricingRecordService.findAll(keyword, paging);
            model.addAttribute("pricingRecords", pricingFeedBeans.getContent());
            model.addAttribute("currentPage", pricingFeedBeans.getNumber() + 1);
            model.addAttribute("totalItems", pricingFeedBeans.getTotalElements());
            model.addAttribute("totalPages", pricingFeedBeans.getTotalPages());
            model.addAttribute("pageSize", size);
            log.info("Operation completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }

        return "pricingFeeds";
    }

    @GetMapping("/search")
    public String searchPricingRecords(@RequestParam(value = "storeId", required = false) Integer storeId,
                                       @RequestParam(value = "sku", required = false) String sku,
                                       @RequestParam(value = "productName", required = false) String productName,
                                       @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                       Model model) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        try {
            List<PricingRecord> pricingRecords = pricingRecordService.searchPricingRecords(storeId, sku, productName, date);
            model.addAttribute("pricingRecords", pricingRecords);
            log.info("Operation completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
            return "search";
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
    }

    @GetMapping("/edit/{id}")
    public String editPricingRecord(@PathVariable Long id, Model model) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        try {
            PricingRecord pricingRecord = pricingRecordService.getPricingRecordById(id);
            model.addAttribute("pricingRecord", pricingRecord);
            log.info("Operation completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
            return "pricing-record-edit";
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
    }

    @GetMapping("/create")
    public String showUploadForm(Model model) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        try {
            model.addAttribute("pricingRecord", new PricingRecord());
            log.info("Operation completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
            return "pricing-record-add";
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
    }

    @GetMapping("/fetchAll")
    public String fetchAll(Model model) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        try {
            model.addAttribute("pricingRecord", new PricingRecord());
            log.info("Operation completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
            return "pricing-record-add";
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
    }

    @PostMapping("/save")
    public String savePricingRecord(@ModelAttribute @Valid PricingRecord pricingRecord,
                                    BindingResult bindingResult, Model model) {
        log.info("Operation started");
        long inTime = System.currentTimeMillis();
        if (bindingResult.hasErrors()) {
            return "pricing-record-add";
        }
        try {
            pricingRecordService.savePricingRecord(pricingRecord);
        } catch (ApplicationException e) {
            log.error("Operation failed {}", e.getMessage());
            model.addAttribute("message", "Operation failed due to internal error");
            return "failed";
        }
        log.info("Operation completed. Total time taken {} in ms", System.currentTimeMillis() - inTime);
        return "pricing-record-add";
    }

}
