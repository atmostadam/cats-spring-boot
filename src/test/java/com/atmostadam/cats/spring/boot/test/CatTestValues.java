package com.atmostadam.cats.spring.boot.test;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.exception.CatException;
import com.atmostadam.cats.api.exception.CatRuntimeException;
import com.atmostadam.cats.api.model.*;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.atmostadam.cats.api.util.CatApiUtils.convertToJsonNode;

public final class CatTestValues {
    private static final Faker faker = Faker.instance();

    private CatTestValues() {}

    public static final String TEST_ADOPT_ID = UUID.randomUUID().toString();
    public static final String TEST_FIRST_NAME = "TEST FIRST NAME";
    public static final String TEST_LAST_NAME = "TEST LAST NAME";
    public static final String TEST_BUSINESS_NAME = "TEST BUSINESS NAME";
    public static final String TEST_CAGE_ID = "CAGE-1";
    public static final int TEST_HEIGHT = 1;
    public static final int TEST_WIDTH = 1;
    public static final int TEST_DEPTH = 1;
    public static final int TEST_NUM_OF_SHELVES = 1;
    public static final int TEST_MAX_CATS = 1;
    public static final boolean TEST_BUILT_IN_TOYS = true;
    public static final boolean TEST_BUILT_IN_LITTERBOX = true;
    public static final boolean TEST_BUILT_IN_FOOD = true;
    public static final boolean TEST_BUILT_IN_WATER = true;
    public static final boolean TEST_BUILT_IN_BED = true;
    public static final List<Cage> TEST_CONNECTIONS = new ArrayList<>();
    public static final String TEST_NAME = "Shelley";
    public static final String TEST_BREED = "Domestic Shorthair";
    public static final String TEST_TYPE = "Calico";
    public static final String TEST_PRIMARY_COLOR = "Orange";
    public static final String TEST_SEX = "F";
    public static final int TEST_AGE = 2;
    public static final boolean TEST_DECEASED = false;
    public static final boolean TEST_NEUTERED = true;
    public static final Long TEST_MICROCHIP_NUMBER = 431654132132657L;
    public static final Long TEST_MICROCHIP_NUMBER_2 = 431654142132657L;
    public static final Long TEST_MICROCHIP_NUMBER_3 = 431666132132657L;
    public static final String TEST_REQUEST_ID = "d1e2a63e-7c43-47ba-8721-ab872640b0b1";
    public static final String TEST_MESSAGE = "TEST MESSAGE";
    public static final CatRuntimeException TEST_CAT_EXCEPTION = new CatRuntimeException(TEST_MESSAGE);
    public static final String TEST_STACK_TRACE = ExceptionUtils.getStackTrace(TEST_CAT_EXCEPTION);

    public static CatEntity catEntityTestData() {
        return new CatEntity()
                .setMicrochipNumber(TEST_MICROCHIP_NUMBER)
                .setName(TEST_NAME)
                .setBreed(TEST_BREED)
                .setType(TEST_TYPE)
                .setPrimaryColor(TEST_PRIMARY_COLOR)
                .setSex(TEST_SEX)
                .setAge(TEST_AGE)
                .setDeceased(TEST_DECEASED)
                .setNeutered(TEST_NEUTERED)
                .setCreatedTimestamp(Timestamp.from(Instant.now()))
                .setUpdatedTimestamp(Timestamp.from(Instant.now()));
    }

    public static CatEntity randomCatEntityTestData() {
        return new CatEntity()
                .setName(faker.cat().name())
                .setBreed(faker.cat().breed())
                .setType(faker.demographic().demonym())
                .setPrimaryColor(faker.color().name())
                .setSex(faker.demographic().sex().substring(0,1))
                .setAge(faker.number().numberBetween(0, 38))
                .setDeceased(faker.bool().bool())
                .setNeutered(faker.bool().bool())
                .setMicrochipNumber(faker.number().numberBetween(100000000L, 999999999999999L))
                .setCreatedTimestamp(Timestamp.from(Instant.now()))
                .setUpdatedTimestamp(Timestamp.from(Instant.now()));
    }

    public static Cat catTestData() {
        return new Cat()
                .setMicrochipNumber(TEST_MICROCHIP_NUMBER)
                .setName(TEST_NAME)
                .setBreed(TEST_BREED)
                .setType(TEST_TYPE)
                .setPrimaryColor(TEST_PRIMARY_COLOR)
                .setSex(TEST_SEX)
                .setAge(TEST_AGE)
                .setDeceased(TEST_DECEASED)
                .setNeutered(TEST_NEUTERED);
    }

    public static Cat randomCatTestData() {
        return new Cat()
                .setName(faker.cat().name())
                .setBreed(faker.cat().breed())
                .setType(faker.demographic().demonym())
                .setPrimaryColor(faker.color().name())
                .setSex(faker.demographic().sex().substring(0,1))
                .setAge(faker.number().numberBetween(0, 38))
                .setDeceased(faker.bool().bool())
                .setNeutered(faker.bool().bool())
                .setMicrochipNumber(faker.number().numberBetween(100000000L, 999999999999999L));
    }

    public static CatRequest catRequestTestData() {
        return new CatRequest()
                .setDelivery(deliveryTestData())
                .setLocation(locationTestData())
                .setAdopt(adoptTestData())
                .addCat(catTestData())
                .addMicrochipNumber(TEST_MICROCHIP_NUMBER);
    }

    public static Delivery deliveryTestData() {
        return new Delivery()
                .setDeliverTo(locationTestData());
    }

    public static Location locationTestData() {
        return new Location()
                .setCage(cageTestData());
    }

    public static Cage cageTestData() {
        return new Cage()
                .setId(TEST_CAGE_ID)
                .setHeight(TEST_HEIGHT)
                .setWidth(TEST_WIDTH)
                .setDepth(TEST_DEPTH)
                .setNumOfShelves(TEST_NUM_OF_SHELVES)
                .setMaxCats(TEST_MAX_CATS)
                .setBuiltInToys(TEST_BUILT_IN_TOYS)
                .setBuiltInLitterbox(TEST_BUILT_IN_LITTERBOX)
                .setBuiltInFood(TEST_BUILT_IN_FOOD)
                .setBuiltInWater(TEST_BUILT_IN_WATER)
                .setBuiltInBed(TEST_BUILT_IN_BED)
                .setConnections(TEST_CONNECTIONS);
    }

    public static Adopt adoptTestData() {
        return new Adopt()
                .setId(TEST_ADOPT_ID)
                .setFirstName(TEST_FIRST_NAME)
                .setLastName(TEST_LAST_NAME)
                .setBusinessName(TEST_BUSINESS_NAME);
    }

    public static CatResponse catResponseTestData() {
        return new CatResponse()
                .setMessage(TEST_MESSAGE)
                .setStackTrace(TEST_STACK_TRACE)
                .addCat(catTestData());
    }

    public static JsonNode catResponseNodeTestData() {
        return convertToJsonNode(catResponseTestData());
    }

    public static JsonNode catResponseNodeTestData(String message) {
        return convertToJsonNode(new CatResponse()
                .setMessage(message)
                .addCat(catTestData()));
    }

    public static ResponseEntity<CatResponse> responseEntityTestData() {
        return catResponseTestData().newResponseEntity(TEST_REQUEST_ID, HttpStatus.OK);
    }
}
