package org.hankki.hankkiserver.api.universitystore.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.api.university.service.UniversityFinder;
import org.hankki.hankkiserver.api.universitystore.service.command.UniversityStorePostCommand;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniversitystoreCommandService {

    private final StoreFinder storeFinder;
    private final UniversityFinder universityFinder;
    private final UniversityStoreUpdater universityStoreUpdater;

    @Transactional
    public void createUniversityStore(final UniversityStorePostCommand universityStorePostCommand) {
        Store store = storeFinder.findByIdWhereDeletedIsFalse(universityStorePostCommand.storeId());
        University university = universityFinder.findById(universityStorePostCommand.universityId());
        universityStoreUpdater.save(UniversityStore.create(store,university));
    }
}
