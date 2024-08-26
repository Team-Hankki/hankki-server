package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.menu.service.MenuUpdater;
import org.hankki.hankkiserver.api.report.service.ReportUpdater;
import org.hankki.hankkiserver.api.store.service.command.StorePostCommand;
import org.hankki.hankkiserver.api.store.service.response.StorePostResponse;
import org.hankki.hankkiserver.api.university.service.UniversityFinder;
import org.hankki.hankkiserver.api.universitystore.service.UniversityStoreUpdater;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.code.StoreImageErrorCode;
import org.hankki.hankkiserver.common.exception.BadGatewayException;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.report.model.Report;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.model.StoreImage;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.external.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreCommandService {

    private static final String STORE_IMAGE_DIRECTORY = "store/";

    private final StoreUpdater storeUpdater;
    private final MenuUpdater menuUpdater;
    private final StoreImageUpdater storeImageUpdater;
    private final S3Service s3Service;
    private final UniversityStoreUpdater universityStoreUpdater;
    private final UniversityFinder universityFinder;
    private final ReportUpdater reportUpdater;
    private final UserFinder userFinder;
    private final StoreFinder storeFinder;

    @Transactional(rollbackFor = Exception.class)
    public StorePostResponse createStore(final StorePostCommand command) {
        if (storeExists(command.latitude(), command.longitude(), command.name(), false)) {
            throw new BadRequestException(StoreErrorCode.BAD_STORE_INFO);
        }

        Store store = storeUpdater.save(command.toEntity());
        saveImages(command, store);
        menuUpdater.saveAll(getMenus(command, store));

        University university = universityFinder.findById(command.universityId());
        universityStoreUpdater.save(UniversityStore.create(store, university));
        reportUpdater.save(Report.create(userFinder.getUser(command.userId()), store, university));

        return StorePostResponse.of(store);
    }

    private boolean storeExists(final double latitude, final double longitude, final String name, final boolean isDeleted) {
        return storeFinder.existsByLatitudeAndLongitudeAndNameAndIsDeleted(latitude, longitude, name, isDeleted);
    }

    private void saveImages(final StorePostCommand command, final Store store) {
        if (!isNullOrEmptyImage(command)) {
            try {
                String imageUrl = s3Service.uploadImage(STORE_IMAGE_DIRECTORY, command.image());
                storeImageUpdater.saveImage(StoreImage.createImage(store, imageUrl));
            } catch (IOException e) {
                throw new BadGatewayException(StoreImageErrorCode.STORE_IMAGE_UPLOAD_FAILED);
            }
        }
    }

    private boolean isNullOrEmptyImage(final StorePostCommand command) {
        return command.image() == null || command.image().isEmpty();
    }

    private List<Menu> getMenus(final StorePostCommand command, final Store store) {
        return command.menus().stream()//메뉴를 엔티티로 저장한다.
                .map(menuPostRequest -> Menu.create(store, menuPostRequest.name(), menuPostRequest.price()))
                .toList();
    }

    @Transactional
    public void deleteStore(final Long id) {
      storeFinder.findByIdWhereDeletedIsFalse(id).softDelete();
    }
}
