package com.hsbc.rbcs.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * TransactionRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-28T22:23:39.956204700+08:00[Asia/Shanghai]")
public class TransactionRequest {

  @JsonProperty("transactionId")
  private String transactionId;

  @JsonProperty("sourceAccount")
  private String sourceAccount;

  @JsonProperty("destAccount")
  private String destAccount;

  @JsonProperty("amount")
  private Double amount;

  @JsonProperty("timestamp")
  private Long timestamp;

  public TransactionRequest transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Get transactionId
   * @return transactionId
  */
  @NotNull @Size(min = 1, max = 100) 
  @Schema(name = "transactionId", required = true)
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionRequest sourceAccount(String sourceAccount) {
    this.sourceAccount = sourceAccount;
    return this;
  }

  /**
   * Get sourceAccount
   * @return sourceAccount
  */
  @NotNull @Size(min = 1, max = 100) 
  @Schema(name = "sourceAccount", required = true)
  public String getSourceAccount() {
    return sourceAccount;
  }

  public void setSourceAccount(String sourceAccount) {
    this.sourceAccount = sourceAccount;
  }

  public TransactionRequest destAccount(String destAccount) {
    this.destAccount = destAccount;
    return this;
  }

  /**
   * Get destAccount
   * @return destAccount
  */
  @NotNull @Size(min = 1, max = 100) 
  @Schema(name = "destAccount", required = true)
  public String getDestAccount() {
    return destAccount;
  }

  public void setDestAccount(String destAccount) {
    this.destAccount = destAccount;
  }

  public TransactionRequest amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @NotNull 
  @Schema(name = "amount", required = true)
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionRequest timestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
  */
  @NotNull 
  @Schema(name = "timestamp", required = true)
  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionRequest transactionRequest = (TransactionRequest) o;
    return Objects.equals(this.transactionId, transactionRequest.transactionId) &&
        Objects.equals(this.sourceAccount, transactionRequest.sourceAccount) &&
        Objects.equals(this.destAccount, transactionRequest.destAccount) &&
        Objects.equals(this.amount, transactionRequest.amount) &&
        Objects.equals(this.timestamp, transactionRequest.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, sourceAccount, destAccount, amount, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionRequest {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    sourceAccount: ").append(toIndentedString(sourceAccount)).append("\n");
    sb.append("    destAccount: ").append(toIndentedString(destAccount)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

