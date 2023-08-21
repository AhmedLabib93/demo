package com.banking.demo.service;

import com.banking.demo.model.payload.CreateAccountRequest;
import com.banking.demo.model.payload.TransferRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface BankingService {

    ResponseEntity createAccount(CreateAccountRequest createAccountRequest);

    ResponseEntity transfer(TransferRequest transferRequest);

    ResponseEntity getBalance(Integer customerId);

    ResponseEntity getTransferHistory(Integer accountId);
}
