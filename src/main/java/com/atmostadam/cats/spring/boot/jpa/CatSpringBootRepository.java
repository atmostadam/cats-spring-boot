package com.atmostadam.cats.spring.boot.jpa;

import com.atmostadam.cats.api.entity.CatEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@EntityScan(basePackages = "com.atmostadam.cats.api")
public class CatSpringBootRepository {
    Logger logger = LoggerFactory.getLogger(CatSpringBootRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertSingleRow(CatEntity entity) {
        List<CatEntity> entities = queryByMicrochipNumber(entity.getMicrochipNumber());
        if (entities.size() > 0) {
            logger.info("Entity Already Exists: " + entities);
            return;
        }
        entityManager.persist(entity);
        logger.info("Entity Persisted: " + entity);
    }

    public List<CatEntity> queryByMicrochipNumber(Long microchipNumber) {
        Query namedQuery = entityManager.createNamedQuery("CatTable.queryByMicrochipNumber");
        namedQuery.setParameter("microchipNumber", microchipNumber);
        CatEntity entity;
        try {
            entity = (CatEntity) namedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return new ArrayList<>();
        }
        return List.of(entity);
    }
}
