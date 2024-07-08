package org.hankki.hankkiserver.api.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportFinder reportFinder;

    public long getMyReportSequence() {
        return reportFinder.getReportCount() + 1;
    }
}
