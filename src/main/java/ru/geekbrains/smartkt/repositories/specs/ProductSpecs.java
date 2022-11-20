package ru.geekbrains.smartkt.repositories.specs;

import ru.geekbrains.smartkt.dao.Product;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
    public static Specification<Product> intGreaterThanOrEqualTo(String field, Integer value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);
    }

    public static Specification<Product> intLessThanOrEqualTo(String field, Integer value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(field), value);
    }

    public static Specification<Product> textLike(String field, String textPart) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)),
                                String.format("%%%s%%", textPart).toLowerCase());
    }
}