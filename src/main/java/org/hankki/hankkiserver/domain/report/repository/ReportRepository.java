package org.hankki.hankkiserver.domain.report.repository;

import java.util.List;
import org.hankki.hankkiserver.domain.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

  @Query("select r from Report r join fetch r.store where r.user.id = :userId and r.store.isDeleted = false order by r.createdAt desc")
  List<Report> findAllByUserIdWithStoreAndStoreImage(@Param("userId") Long userId);
}