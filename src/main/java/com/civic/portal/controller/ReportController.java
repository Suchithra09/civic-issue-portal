package com.civic.portal.controller;

import com.civic.portal.entity.Report;
import com.civic.portal.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // CREATE REPORT
    @PostMapping("/create")
    public Report createReport(@RequestBody Report report) {

        report.setTrackingId(
                "CIV-" + UUID.randomUUID().toString().substring(0, 8)
        );

        report.setStatus("RECEIVED");
        report.setCreatedAt(LocalDateTime.now());

        return reportService.saveReport(report);
    }

    // GET ALL REPORTS
    @GetMapping("/all")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    // TEST API
    @GetMapping("/test")
    public String testAPI() {
        return "Civic Issue Portal is working!";
    }

    // TRACK REPORT BY TRACKING ID
    @GetMapping("/track/{trackingId}")
    public Report trackReport(
            @PathVariable String trackingId) {

        return reportService.getByTrackingId(trackingId);
    }

    // UPDATE STATUS
    @PutMapping("/update-status/{trackingId}")
    public Report updateStatus(
            @PathVariable String trackingId,
            @RequestParam String status) {

        return reportService.updateStatus(
                trackingId,
                status
        );
    }

    // DASHBOARD STATS
    @GetMapping("/stats")
    public Map<String, Long> getStats() {

        List<Report> reports =
                reportService.getAllReports();

        long total = reports.size();

        long received = reports.stream()
                .filter(r ->
                        "RECEIVED".equals(r.getStatus()))
                .count();

        long inProgress = reports.stream()
                .filter(r ->
                        "IN_PROGRESS".equals(r.getStatus()))
                .count();

        long resolved = reports.stream()
                .filter(r ->
                        "RESOLVED".equals(r.getStatus()))
                .count();

        Map<String, Long> stats =
                new HashMap<>();

        stats.put("total", total);
        stats.put("received", received);
        stats.put("inProgress", inProgress);
        stats.put("resolved", resolved);

        return stats;
    }
}