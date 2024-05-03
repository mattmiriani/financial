package com.matt.financial.application.records;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static java.util.Objects.isNull;

public record PageableRecord(
        Integer pageNumber,
        Integer pageSize,
        String sortField,
        Sort.Direction sortDir
) {
    public PageableRecord {
        pageNumber = isNull(pageNumber) ? 0 : pageNumber;
        pageSize = isNull(pageSize) ? 20 : pageSize;
        sortDir = isNull(sortDir) ? Sort.Direction.ASC : sortDir;
    }

    public Optional<Integer> getPageNumber() {
        return Optional.ofNullable(pageNumber);
    }

    public Optional<Integer> getPageSize() {
        return Optional.ofNullable(pageSize);
    }

    public Optional<String> getSortField() {
        return Optional.ofNullable(sortField);
    }

    public Optional<Sort.Direction> getSortDir() {
        return Optional.ofNullable(sortDir);
    }

    public Sort getSort() {
        if (getSortDir().isPresent() && getSortField().isPresent()) {
            return Sort.by(getSortDir().get(), getSortField().get());
        }

        return Sort.unsorted();
    }

    public Pageable getPageable() {
        return PageRequest.of(pageNumber, pageSize, getSort());
    }
}
