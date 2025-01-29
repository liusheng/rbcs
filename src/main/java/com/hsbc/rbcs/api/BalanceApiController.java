package com.hsbc.rbcs.api;

import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.HealthCheck200Response;
import com.hsbc.rbcs.model.TransactionRequest;
import com.hsbc.rbcs.model.TransactionResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-29T11:31:37.558517800+08:00[Asia/Shanghai]")
@Controller
@RequestMapping("${openapi.realtimeBalanceTransaction.base-path:}")
public class BalanceApiController implements BalanceApi {

    private final BalanceApiDelegate delegate;

    public BalanceApiController(@Autowired(required = false) BalanceApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new BalanceApiDelegate() {});
    }

    @Override
    public BalanceApiDelegate getDelegate() {
        return delegate;
    }

}
