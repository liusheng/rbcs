package com.hsbc.rbcs.infrastructure.mapper;

import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.model.AccountBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "accountNumber", target = "account")
    @Mapping(target = "balance",  expression = "java(entity.getBalance().doubleValue())")
    AccountBalance entityToAccountBalance(Account entity);
}
