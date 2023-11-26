package com.sample.repository;

import com.sample.model.HotKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotKeywordRepository extends JpaRepository<HotKeywordEntity, Long> {
    Optional<HotKeywordEntity> findByKeyword(String keyword);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update #{#entityName} u set u.count = u.count + 1 where u.keyword = :keyword")
    void increaseCountByKeyword(@Param("keyword") String keyword);

    List<HotKeywordEntity> findFirst10ByOrderByCountDesc();
}