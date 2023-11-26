package com.sample.service;

import com.sample.dto.HotKeyword;
import com.sample.model.HotKeywordEntity;
import com.sample.repository.HotKeywordRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class HotKeywordServiceTest {
    @Autowired
    private HotKeywordService hotKeywordService;

    @Autowired
    private HotKeywordRepository hotKeywordRepository;

    @Test
    public void increase_concurrency(){
        int size = 30;
        int concurrency = 4;
        List<Integer> aList = IntStream.rangeClosed(1, size).boxed().toList();
        List<Integer> bList = IntStream.rangeClosed(1, concurrency).boxed().toList();

        ForkJoinPool customThreadPool = new ForkJoinPool(4);

        aList.forEach(n1 ->{
            try {
                customThreadPool.submit(() -> {
                    bList.parallelStream().forEach(n2 ->
                            hotKeywordService.increaseCountByKeyword("sample" + n1)
                    );
                }).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        List<HotKeywordEntity> list = hotKeywordRepository.findAll();
        assertEquals(size, list.size());

        int countSum = list.stream().mapToInt(HotKeywordEntity::getCount).sum();
        assertEquals(size * concurrency, countSum);
    }

    @Test
    public void get_hotKeyword(){
        hotKeywordService.increaseCountByKeyword("test");
        hotKeywordService.increaseCountByKeyword("test2");
        hotKeywordService.increaseCountByKeyword("test2");
        hotKeywordService.increaseCountByKeyword("test3");
        hotKeywordService.increaseCountByKeyword("test3");
        hotKeywordService.increaseCountByKeyword("test3");

        List<HotKeyword> hotKeywords = hotKeywordService.findHotKeywords();
        assertEquals(3, hotKeywords.get(0).getCount());
        assertEquals(2, hotKeywords.get(1).getCount());
        assertEquals(1, hotKeywords.get(2).getCount());
    }
}