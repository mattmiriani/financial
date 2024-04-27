package com.matt.financecontrol.model.specification;

import com.matt.financecontrol.validations.StringValidator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;

public class SpecificationUtil {
    private SpecificationUtil() {
    }

    public static Expression<String> prepareAtributeForLike(CriteriaBuilder cb, Path<String> atribute) {
        return cb.function("unaccent", String.class, cb.lower(atribute));
    }

    public static String prepareStringForLike(String s) {
        return "%" + StringValidator.removeAccentLower(s) + "%";
    }
}
