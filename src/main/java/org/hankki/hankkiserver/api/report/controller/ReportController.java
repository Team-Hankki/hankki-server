package org.hankki.hankkiserver.api.report.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.report.service.ReportService;
import org.hankki.hankkiserver.api.report.service.response.ReportServiceResponse;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
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
    public HankkiResponse<ReportServiceResponse> getReportCounts() {
        ReportServiceResponse response = new ReportServiceResponse(reportService.getMyReportSequence());
        return HankkiResponse.success(CommonSuccessCode.OK, response);

    }
}
