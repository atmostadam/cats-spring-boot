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
    void insertSingleRow() {
        repository.insertSingleRow(CatEntityTestData.dynamicCatEntity());
    }
}
