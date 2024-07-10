package org.hankki.hankkiserver.api.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportQueryService {

    private static final long REPORT_SEQUENCE = 1;

    private final ReportFinder reportFinder;

    @Transactional(readOnly = true)
    public long getMyReportSequence() {
        return reportFinder.getReportCount() + REPORT_SEQUENCE;
    }
}
