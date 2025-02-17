package org.hankki.hankkiserver.util;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void cleanUp() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = FALSE").executeUpdate();
        for (String tableName : getTableNames()) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
//            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " + tableName + "_ID" + " RESTART WITH 1")
//                    .executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = TRUE").executeUpdate();
    }

    private List<String> getTableNames() {
        return entityManager.getMetamodel().getEntities().stream()
                .filter(entity -> entity.getJavaType().isAnnotationPresent(Entity.class))
                .map(entityType -> {
                    Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
                    if (tableAnnotation != null && !tableAnnotation.name().isBlank()) {
                        return tableAnnotation.name();
                    } else {
                        return camelToSnake(entityType.getName());
                    }
                })
                .toList();
    }

    private String camelToSnake(String camel) {
        StringBuilder snake = new StringBuilder();
        for (char c : camel.toCharArray()) {
            if (Character.isUpperCase(c)) {
                snake.append("_");
            }
            snake.append(Character.toLowerCase(c));
        }
        if (snake.charAt(0) == '_') {
            snake.deleteCharAt(0);
        }
        return snake.toString();
    }
}
