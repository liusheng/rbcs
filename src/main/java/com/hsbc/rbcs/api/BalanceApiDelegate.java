package com.hsbc.rbcs.api;

import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.TransactionRequest;
import com.hsbc.rbcs.model.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * A delegate to be called by the {@link BalanceApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T18:22:05.955418700+08:00[Asia/Shanghai]")
public interface BalanceApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /balance/v1/transaction : Financial transaction between two accounts
     *
     * @param transactionRequest  (required)
     * @return Financial transaction successfully (status code 200)
     *         or Invalid input (status code 400)
     *         or Service exception (status code 500)
     * @see BalanceApi#balanceTransaction
     */
    default ResponseEntity<TransactionResponse> balanceTransaction(TransactionRequest transactionRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\", \"status\" : \"status\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /balance/v1/health : health check for balance service
     *
     * @return health check for balance service successfully (status code 200)
     *         or Invalid input (status code 400)
     *         or Service exception (status code 500)
     * @see BalanceApi#healthCheck
     */
    default ResponseEntity<String> healthCheck() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "\"Health check OK.\"";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /balance/v1/query-account : Query financial by account
     *
     * @param account account (required)
     * @return Query financial by account successfully (status code 200)
     *         or Invalid input (status code 400)
     *         or Service exception (status code 500)
     * @see BalanceApi#queryBalance
     */
    default ResponseEntity<AccountBalance> queryBalance(String account) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"balance\" : 0.8008281904610115, \"account\" : \"account\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}