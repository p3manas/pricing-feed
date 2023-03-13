package com.tigeranalytics.repo;

import com.tigeranalytics.bean.PricingFeedBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingFeedRepository extends JpaRepository<PricingFeedBean, Long> {
}
