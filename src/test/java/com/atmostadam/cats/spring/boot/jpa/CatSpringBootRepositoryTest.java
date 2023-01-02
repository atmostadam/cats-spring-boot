package com.atmostadam.cats.spring.boot.jpa;

import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.atmostadam.cats.api.util.CatDefaultValues.catEntityTestData;
import static com.atmostadam.cats.spring.boot.data.CatTestValues.randomCatEntityTestData;

@SpringJUnitConfig(CatSpringBootTestConfiguration.class)
class CatSpringBootRepositoryTest {
    @Autowired
    CatSpringBootRepository repository;

    @Test
    void queryByMicrochipNumber() {
        repository.querySingleRowByMicrochipNumber(123456789000001L);
    }

    @Test
    void insertSingleRow() {
        repository.insertSingleRow(randomCatEntityTestData());
    }

    @Test
    void updateSingleRow() {
        repository.updateSingleRow(catEntityTestData());
    }

    @Test
    void deleteSingleRow() {
        repository.deleteSingleRow(catEntityTestData());
    }
}
