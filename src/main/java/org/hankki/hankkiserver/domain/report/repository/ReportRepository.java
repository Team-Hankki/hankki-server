package org.hankki.hankkiserver.domain.report.repository;

import org.hankki.hankkiserver.domain.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
