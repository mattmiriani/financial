package com.matt.financial.application.graphqlcontroller;

import com.matt.financial.application.records.PageableRecord;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.service.MonthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class MonthGraphQLController {

    private final MonthService monthService;

    @QueryMapping
    public Page<Month> findAllMonth(@Argument Month month,
                                    @Argument PageableRecord pageableRecord) {
        return monthService.findAll(month, pageableRecord.getPageable());
    }

    @QueryMapping
    public Month findMonthById(@Argument UUID monthId) {
        return monthService.findById(monthId);
    }
}
