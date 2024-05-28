package com.matt.financial.model.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.repository.MonthRepository;
import com.matt.financial.model.specification.MonthSpecification;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
@Transactional(readOnly = true)
public class MonthService {

    private final MonthRepository monthRepository;
    private final MonthSpecification monthSpecification;

    public Page<Month> findAll(Month month, Pageable pageable) {
        return monthRepository.findAll(monthSpecification.filter(month), pageable);
    }

    public Month findById(UUID monthId) {
        return monthRepository.findById(monthId).orElseThrow(
                () -> new FinancialBusinessException("Month not found")
        );
    }
}
