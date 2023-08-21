package com.banking.demo.controller;

import com.banking.demo.model.payload.CreateAccountRequest;
import com.banking.demo.model.payload.TransferRequest;
import com.banking.demo.service.impl.BankingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banking/v1")
@Tag(name = "Rest APIs for Banking Task")
public class BankingController {

    @Autowired
    private BankingServiceImpl bankingService;

    @Operation(summary = "Create Banking Account Rest API",
            description = "Customer id must be valid to create new Bank Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Http Status 201 Created"),
            @ApiResponse(responseCode = "404", description = "Http Status 404 Not Found Customer")
    })
    @PostMapping("/create-account")
    public ResponseEntity createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return bankingService.createAccount(createAccountRequest);
    }

    @Operation(summary = "Make Banking Transfer between two Account Rest API",
            description = "fromAccount and toAccounts ids must be valid, and must be sufficient balance for transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Http Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "Http Status 404 toAccount/fromAccount id not found"),
            @ApiResponse(responseCode = "400", description = "Http Status 400 Bad Request - Insufficient Funds")
    })
    @PostMapping("/transfers/{from-id}")
    public ResponseEntity transferAmount(@PathVariable("from-id") Integer id, @RequestBody TransferRequest transferRequest){
        if(!id.equals(transferRequest.getFromId()))
            return new ResponseEntity("Unauthorized action", HttpStatus.UNAUTHORIZED);
        return bankingService.transfer(transferRequest);
    }

    @Operation(summary = "Get all balance for customer with id",
            description = "Customer id must be valid to return his/her balances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Http Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "Http Status 404 Not Found Customer")
    })
    @GetMapping("/balances/{customer-id}")
    public ResponseEntity getBalance(@PathVariable("customer-id") Integer id){
        return bankingService.getBalance(id);
    }

    @Operation(summary = "Get all transfers for account with id",
            description = "BankAccount id must be valid to return it's transfers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Http Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "Http Status 404 Not Found BankAccount")
    })
    @GetMapping("/transfers/{account-id}")
    public ResponseEntity getTranfers(@PathVariable("account-id") Integer id){
        return bankingService.getTransferHistory(id);
    }
}
