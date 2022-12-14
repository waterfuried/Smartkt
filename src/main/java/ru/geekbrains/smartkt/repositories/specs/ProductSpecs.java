package ru.geekbrains.smartkt.repositories.specs;

import ru.geekbrains.smartkt.dao.items.StoredItem;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
    public static Specification<StoredItem> intGreaterThanOrEqualTo(String field, Integer value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);
    }

    public static Specification<StoredItem> intLessThanOrEqualTo(String field, Integer value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(field), value);
    }

    public static Specification<StoredItem> textLike(String field, String textPart) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), String.format("%%%s%%", textPart));
    }

    public static Specification<StoredItem> textLikeIgnoreCase(String field, String textPart) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)),
                                String.format("%%%s%%", textPart).toLowerCase());
    }
}