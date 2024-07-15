package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserInfoFinder;
import org.hankki.hankkiserver.api.favorite.service.FavoriteFinder;
import org.hankki.hankkiserver.api.store.service.HeartFinder;
import org.hankki.hankkiserver.api.user.service.response.UserFavoritesGetResponse;
import org.hankki.hankkiserver.api.user.service.response.UserProfileAndNicknameResponse;
import org.hankki.hankkiserver.api.user.service.response.UserHeartedStoreListResponse;
import org.hankki.hankkiserver.api.user.service.response.UserUniversityFindResponse;
import org.hankki.hankkiserver.common.code.UserUniversityErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserUniversityFinder userUniversityFinder;
    private final FavoriteFinder favoriteFinder;
    private final UserInfoFinder userInfoFinder;
    private final HeartFinder heartFinder;

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
    public UserProfileAndNicknameResponse getUserProfileAndNickname(final Long userId) {
        return UserProfileAndNicknameResponse.of(userInfoFinder.getUserInfo(userId));
    }

    @Transactional(readOnly = true)
    public UserHeartedStoreListResponse findUserHeartedStoresView(final Long userId) {
        List<Heart> hearts = heartFinder.findHeartedStoresByUserId(userId);
        return UserHeartedStoreListResponse.of(hearts);
    }
}
