package org.hankki.hankkiserver.api.report.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportFinder {

    private final ReportRepository reportRepository;

    public long getReportCount() {
        return reportRepository.count();
    }
}
