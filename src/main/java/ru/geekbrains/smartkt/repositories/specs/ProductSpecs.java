package ru.geekbrains.smartkt.repositories.specs;

import ru.geekbrains.smartkt.dao.items.Item;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
    public static Specification<Item> intGreaterThanOrEqualTo(String field, Integer value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);
    }

    public static Specification<Item> intLessThanOrEqualTo(String field, Integer value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(field), value);
    }

    public static Specification<Item> textLike(String field, String textPart) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), String.format("%%%s%%", textPart));
    }

    public static Specification<Item> textLikeIgnoreCase(String field, String textPart) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)),
                                String.format("%%%s%%", textPart).toLowerCase());
    }
}