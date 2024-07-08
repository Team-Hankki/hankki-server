package org.hankki.hankkiserver.api.report.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.report.service.ReportService;
import org.hankki.hankkiserver.api.report.service.response.ReportServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports/count")
    public ResponseEntity<ReportServiceResponse> getReportCounts() {
        long reportCount = reportService.getMyReportSequence();
        ReportServiceResponse response = new ReportServiceResponse(reportCount);
        return ResponseEntity.ok(response);
    }
}
