package com.atmostadam.cats.spring.boot.data;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.Cat;
import com.github.javafaker.Faker;

import java.sql.Timestamp;
import java.time.Instant;

public final class CatTestValues {
    private static final Faker faker = Faker.instance();

    private CatTestValues() {}

    public static Cat randomCatTestData() {
        return new Cat()
                .setName(faker.cat().name())
                .setBreed(faker.cat().breed())
                .setType(faker.demographic().demonym())
                .setPrimaryColor(faker.color().name())
                .setSex(faker.demographic().sex().substring(0, 1))
                .setAge(faker.number().numberBetween(0, 38))
                .setDeceased(faker.bool().bool())
                .setNeutered(faker.bool().bool())
                .setMicrochipNumber(faker.number().numberBetween(100000000L, 999999999999999L));
    }

    public static CatEntity randomCatEntityTestData() {
            return new CatEntity()
                    .setName(faker.cat().name())
                    .setBreed(faker.cat().breed())
                    .setType(faker.demographic().demonym())
                    .setPrimaryColor(faker.color().name())
                    .setSex(faker.demographic().sex().substring(0, 1))
                    .setAge(faker.number().numberBetween(0, 38))
                    .setDeceased(faker.bool().bool())
                    .setNeutered(faker.bool().bool())
                    .setMicrochipNumber(faker.number().numberBetween(100000000L, 999999999999999L))
                    .setCreatedTimestamp(Timestamp.from(Instant.now()))
                    .setUpdatedTimestamp(Timestamp.from(Instant.now()));
        }
}
