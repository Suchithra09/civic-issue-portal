package com.civic.portal.repository;

import com.civic.portal.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findByTrackingId(String trackingId);

}