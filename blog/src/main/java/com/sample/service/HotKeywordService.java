package com.sample.service;

import com.sample.dto.HotKeyword;
import com.sample.model.HotKeywordEntity;
import com.sample.repository.HotKeywordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class HotKeywordService {

    private final HotKeywordRepository hotKeywordRepository;

    public void increaseCountByKeyword(String keyword) {
        hotKeywordRepository.findByKeyword(keyword).ifPresentOrElse(
                entity -> hotKeywordRepository.increaseCountByKeyword(keyword),
                () -> {
                    HotKeywordEntity entity = new HotKeywordEntity();
                    entity.setKeyword(keyword);
                    entity.setCount(1);
                    try {
                        hotKeywordRepository.save(entity);
                    } catch (DataIntegrityViolationException e) {
                        log.debug(e.getMessage());
                        hotKeywordRepository.increaseCountByKeyword(keyword);
                    }
                }
        );
    }

    public List<HotKeyword> findHotKeywords() {
        return hotKeywordRepository.findFirst10ByOrderByCountDesc()
                .stream()
                .map(e -> new HotKeyword(e.getKeyword(), e.getCount()))
                .toList();
    }
}