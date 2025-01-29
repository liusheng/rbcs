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
 * AccountBalance
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-29T11:31:37.558517800+08:00[Asia/Shanghai]")
public class AccountBalance {

  @JsonProperty("account")
  private String account;

  @JsonProperty("balance")
  private Double balance;

  public AccountBalance account(String account) {
    this.account = account;
    return this;
  }

  /**
   * Get account
   * @return account
  */
  @Size(min = 1, max = 100) 
  @Schema(name = "account", required = false)
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public AccountBalance balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  */
  
  @Schema(name = "balance", required = false)
  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountBalance accountBalance = (AccountBalance) o;
    return Objects.equals(this.account, accountBalance.account) &&
        Objects.equals(this.balance, accountBalance.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountBalance {\n");
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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

