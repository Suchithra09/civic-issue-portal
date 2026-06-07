package com.civic.portal.service;

import com.civic.portal.entity.Report;
import com.civic.portal.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getByTrackingId(String trackingId) {
        return reportRepository.findByTrackingId(trackingId);
    }

    public Report updateStatus(String trackingId, String status) {

        Report report = reportRepository.findByTrackingId(trackingId);

        if (report != null) {
            report.setStatus(status);
            return reportRepository.save(report);
        }

        return null;
    }
}