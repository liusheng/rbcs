package com.hsbc.rbcs.infrastructure.mapper;

import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.model.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "amount",  expression = "java(entity.getAmount().doubleValue())")
    @Mapping(target = "timestamp",  expression = "java(entity.getTimestamp().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    TransactionResponse entityToResponse(Transaction entity);
}
