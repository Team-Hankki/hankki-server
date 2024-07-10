package org.hankki.hankkiserver.domain.user.repository;

import org.hankki.hankkiserver.domain.user.model.UserUniversity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserUniversityRepository extends CrudRepository<UserUniversity, Long> {
    Optional<UserUniversity> findByUserId(Long userId);
    void deleteById(Long id);
    void deleteByUserId(Long userId);
}
