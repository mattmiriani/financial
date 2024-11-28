package com.matt.financial.application.records;


import java.util.UUID;

public record TokenRecord(
        UUID subjectId,
        String token
) {

}
