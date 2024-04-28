package com.matt.financecontrol.application.records;


import java.util.UUID;

public record TokenRecord(
        UUID subjectId,
        String token
) {

}
