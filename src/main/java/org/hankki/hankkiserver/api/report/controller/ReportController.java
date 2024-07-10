package org.hankki.hankkiserver.api.report.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.report.service.ReportQueryService;
import org.hankki.hankkiserver.api.report.service.response.ReportServiceResponse;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportController {

    private final ReportQueryService reportQueryService;

    @GetMapping("/reports/count")
    public HankkiResponse<ReportServiceResponse> getReportCounts() {
        return HankkiResponse.success(CommonSuccessCode.OK, ReportServiceResponse.of(reportQueryService.getMyReportSequence()));

    }
}
