package com.atmostadam.cats.spring.boot.jpa;

import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.atmostadam.cats.test.data.CatEntityTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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
        repository.insertSingleRow(CatEntityTestData.dynamicCatEntity());
    }

    @Test
    void updateSingleRow() {
        repository.updateSingleRow(CatEntityTestData.dynamicCatEntity());
    }

    @Test
    void deleteSingleRow() {
        repository.deleteSingleRow(CatEntityTestData.dynamicCatEntity());
    }
}
