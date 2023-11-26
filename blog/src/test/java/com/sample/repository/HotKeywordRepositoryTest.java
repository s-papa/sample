package com.sample.repository;

import com.sample.model.HotKeywordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class HotKeywordRepositoryTest {
    @Autowired
    private HotKeywordRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void findByKeyword() {
        String keyword = "hello";
        HotKeywordEntity entity = makeMockEntity(keyword);

        repository.save(entity);
        assertEquals("hello", repository.findByKeyword(keyword).get().getKeyword());
    }

    @Test
    public void save_duplicated() {
        String keyword = "hello";
        HotKeywordEntity entity1 = makeMockEntity(keyword);
        repository.save(entity1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            HotKeywordEntity entity2 = makeMockEntity(keyword);
            repository.save(entity2);
        });
    }

    @Test
    public void findFirst10ByOrderByCountDesc() {
        IntStream.rangeClosed(1, 20).boxed().forEach(i -> {
            repository.save(makeMockEntity("keyword" + i, i));
        });
        List<HotKeywordEntity> hotKeywords = repository.findFirst10ByOrderByCountDesc();

        assertEquals(10, hotKeywords.size());
        assertEquals("keyword20", hotKeywords.get(0).getKeyword());
        assertEquals("keyword11", hotKeywords.get(9).getKeyword());
    }

    @Test
    public void increaseCountByKeyword() throws ExecutionException, InterruptedException {
        String keyword = "hello";
        HotKeywordEntity entity = makeMockEntity(keyword);
        repository.save(entity);

        List<Integer> aList = IntStream.rangeClosed(1, 100).boxed()
                .toList();

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        customThreadPool.submit(() -> {
                    aList.parallelStream().forEach(number ->
                            repository.increaseCountByKeyword(keyword)
                    );
                }
        ).get();

        HotKeywordEntity saved = repository.findByKeyword(keyword).get();
        assertEquals(100, saved.getCount());
    }

    private HotKeywordEntity makeMockEntity(String keyword) {
        HotKeywordEntity entity = new HotKeywordEntity();
        entity.setKeyword(keyword);
        return entity;
    }

    private HotKeywordEntity makeMockEntity(String keyword, int count) {
        HotKeywordEntity entity = new HotKeywordEntity();
        entity.setKeyword(keyword);
        entity.setCount(count);
        return entity;
    }
}