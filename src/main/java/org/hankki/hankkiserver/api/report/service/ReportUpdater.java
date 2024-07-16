package org.hankki.hankkiserver.api.report.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.report.model.Report;
import org.hankki.hankkiserver.domain.report.repository.ReportRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportUpdater {

    private final ReportRepository repository;

    public void save(final Report report) {
        repository.save(report);
    }
}
