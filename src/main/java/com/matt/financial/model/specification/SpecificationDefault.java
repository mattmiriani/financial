package com.matt.financial.model.specification;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.config.tools.StringTools;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.UUID;

public interface SpecificationDefault<T extends Serializable> {

    default Specification<T> id(Object id) {
        if (id instanceof Long idLong) {
            return id(idLong);
        } else if (id instanceof UUID idUUID) {
            return id(idUUID);
        }

        throw new FinancialBusinessException("Invalid id type: " + id.getClass().getSimpleName());
    }

    private Specification<T> id(Long id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    private Specification<T> id(UUID id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }


    default Specification<T> name(String name) {
        return (root, query, cb) -> cb.like(cb.function("unaccent", String.class, cb.lower(root.get("name"))),
                "%" + StringTools.removeAccentLower(name) + "%");
    }

    default Specification<T> isActive(Boolean active) {
        return (root, query, cb) -> cb.equal(root.get("active"), active);
    }

    default SpecificationBuilder<T> builder() {
        return new SpecificationBuilder<>();
    }
}
