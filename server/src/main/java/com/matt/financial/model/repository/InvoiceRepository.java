package com.matt.financial.model.repository;

import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {

    Optional<Invoice> findByNameAndMonth_Id(@Param("name") String name,
                                            @Param("monthId") UUID monthId);
}
