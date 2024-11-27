package com.prixbanque.transaction_service.http;

import com.prixbanque.transaction_service.http.model.Account;
import com.prixbanque.transaction_service.http.model.Balance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{id}")
    Account getAccountById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/{id}/balance")
    void updateBalance(@PathVariable("id") Long id, @RequestBody Balance balance);
}
