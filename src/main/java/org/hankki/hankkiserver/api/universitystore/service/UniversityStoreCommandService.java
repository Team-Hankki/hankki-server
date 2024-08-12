package org.hankki.hankkiserver.api.universitystore.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.report.service.ReportUpdater;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.api.university.service.UniversityFinder;
import org.hankki.hankkiserver.api.universitystore.service.command.UniversityStorePostCommand;
import org.hankki.hankkiserver.domain.report.model.Report;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniversityStoreCommandService {

    private final StoreFinder storeFinder;
    private final UniversityFinder universityFinder;
    private final UniversityStoreUpdater universityStoreUpdater;
    private final UserFinder userFinder;
    private final ReportUpdater reportUpdater;

    @Transactional
    public void createUniversityStore(final UniversityStorePostCommand command) {
        Store store = storeFinder.findByIdWhereDeletedIsFalse(command.storeId());
        University university = universityFinder.findById(command.universityId());
        universityStoreUpdater.save(UniversityStore.create(store,university));
        reportUpdater.save(Report.create(userFinder.getUser(command.userId()), store, university));
    }
}
