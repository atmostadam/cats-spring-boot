package com.atmostadam.cats.spring.boot.jpa;

import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.github.javafaker.Faker;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.atmostadam.cats.spring.boot.test.CatTestValues.catEntityTestData;
import static com.atmostadam.cats.spring.boot.test.CatTestValues.randomCatEntityTestData;

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
