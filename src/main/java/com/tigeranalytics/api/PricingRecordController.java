package com.tigeranalytics.api;

import com.tigeranalytics.bean.PricingFeedBean;
import com.tigeranalytics.bean.PricingRecordBean;
import com.tigeranalytics.dto.PricingRecord;
import com.tigeranalytics.service.IPricingRecordService;
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
public class PricingRecordController {
    @Autowired
    private IPricingRecordService pricingRecordService;

    @GetMapping("/pricingFeeds")
    public String getPricingFeeds(Model model, @RequestParam(required = false) String keyword,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<PricingRecordBean> pricingFeedBeans = pricingRecordService.findAll(keyword, paging);
        model.addAttribute("pricingRecords", pricingFeedBeans.getContent());
        model.addAttribute("currentPage", pricingFeedBeans.getNumber() + 1);
        model.addAttribute("totalItems", pricingFeedBeans.getTotalElements());
        model.addAttribute("totalPages", pricingFeedBeans.getTotalPages());
        model.addAttribute("pageSize", size);
        return "pricingFeeds";
    }

    @GetMapping("/search")
    public String searchPricingRecords(@RequestParam(value = "storeId", required = false) Integer storeId,
                                       @RequestParam(value = "sku", required = false) String sku,
                                       @RequestParam(value = "productName", required = false) String productName,
                                       @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                       Model model) {
        List<PricingRecord> pricingRecords = pricingRecordService.searchPricingRecords(storeId, sku, productName, date);
        model.addAttribute("pricingRecords", pricingRecords);
        return "search";
    }

    @GetMapping("/edit/{id}")
    public String editPricingRecord(@PathVariable Long id, Model model) {
        PricingRecord pricingRecord = pricingRecordService.getPricingRecordById(id);
        model.addAttribute("pricingRecord", pricingRecord);
        return "pricing-record-edit";
    }

    @GetMapping("/create")
    public String showUploadForm(Model model) {
        model.addAttribute("pricingRecord", new PricingRecord());
        return "pricing-record-add";
    }

    @GetMapping("/fetchAll")
    public String fetchAll(Model model) {
        model.addAttribute("pricingRecord", new PricingRecord());
        return "pricing-record-add";
    }


    @PostMapping("/save")
    public String savePricingRecord(@ModelAttribute @Valid PricingRecord pricingRecord, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pricing-record-add";
        }
        try {
            pricingRecordService.savePricingRecord(pricingRecord);
        } catch (Exception e) {

        }

        return "pricing-record-add";
    }
}
