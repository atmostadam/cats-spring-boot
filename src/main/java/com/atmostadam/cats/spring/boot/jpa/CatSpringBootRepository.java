package com.atmostadam.cats.spring.boot.jpa;

import com.atmostadam.cats.api.entity.CatEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@EntityScan(basePackages = "com.atmostadam.cats.api")
public class CatSpringBootRepository {
    private static final Logger logger = LoggerFactory.getLogger(CatSpringBootRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    public CatEntity querySingleRowByMicrochipNumber(Long microchipNumber) {
        Query namedQuery = entityManager.createNamedQuery("CatTable.queryByMicrochipNumber");
        namedQuery.setParameter("microchipNumber", microchipNumber);
        CatEntity entity;
        try {
            entity = (CatEntity) namedQuery.getSingleResult();
            logger.info("Entity Queried in Database: [{}]", entity);
        } catch (NoResultException nre) {
            entity = null;
            logger.info("Entity Not Found in Database When Querying By: [{}]", entity);
        }
        return entity;
    }

    @Transactional
    public void insertSingleRow(CatEntity entity) {
        entityManager.persist(entity);
        logger.info("Entity Added to Database: [{}]", entity);
    }

    @Transactional
    public void updateSingleRow(CatEntity entity) {
        entityManager.persist(entity);
        logger.info("Entity Updated in Database: [{}]", entity);
    }

    @Transactional
    public void deleteSingleRow(CatEntity entity) {
        entityManager.remove(entity);
        logger.info("Entity Deleted in Database: [{}]", entity);
    }
}
