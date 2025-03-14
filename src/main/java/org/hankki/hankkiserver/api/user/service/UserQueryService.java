package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.favorite.service.FavoriteFinder;
import org.hankki.hankkiserver.api.store.service.HeartFinder;
import org.hankki.hankkiserver.api.user.service.response.UserFavoritesGetResponse;
import org.hankki.hankkiserver.api.user.service.response.UserHeartedStoreListResponse;
import org.hankki.hankkiserver.api.user.service.response.UserNicknameResponse;
import org.hankki.hankkiserver.api.report.service.ReportFinder;
import org.hankki.hankkiserver.api.user.service.response.UserStoresReportedGetResponse;
import org.hankki.hankkiserver.api.user.service.response.UserUniversityFindResponse;
import org.hankki.hankkiserver.common.code.UserUniversityErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.report.model.Report;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserUniversityFinder userUniversityFinder;
    private final FavoriteFinder favoriteFinder;
    private final UserInfoFinder userInfoFinder;
    private final HeartFinder heartFinder;
    private final ReportFinder reportFinder;

    @Transactional(readOnly = true)
    public UserUniversityFindResponse findUserUniversity(Long userId) {
        return UserUniversityFindResponse.of(userUniversityFinder.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(UserUniversityErrorCode.USER_UNIVERSITY_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public UserFavoritesGetResponse findUserFavorites(final Long userId) {
        return UserFavoritesGetResponse.of(favoriteFinder.findAllByUserId(userId));
    }

    @Transactional(readOnly = true)
    public UserNicknameResponse getUserNickname(final Long userId) {
        return UserNicknameResponse.of(userInfoFinder.getUserInfo(userId));
    }

    @Transactional(readOnly = true)
    public UserHeartedStoreListResponse findUserHeartedStoresView(final Long userId) {
        return UserHeartedStoreListResponse.of(heartFinder.findHeartedStoresByUserId(userId));
    }

    @Transactional(readOnly = true)
    public UserStoresReportedGetResponse findUserStoreReported(final Long userId) {
        return UserStoresReportedGetResponse.of(reportFinder.findAllByUserId(userId).stream().map(Report::getStore).distinct().toList());
    }
}
