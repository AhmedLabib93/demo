package com.banking.demo.service.impl;

import com.banking.demo.model.BankAccount;
import com.banking.demo.model.Customer;
import com.banking.demo.model.Transfer;
import com.banking.demo.model.payload.CreateAccountRequest;
import com.banking.demo.model.payload.TransferRequest;
import com.banking.demo.repository.BankAccountRepository;
import com.banking.demo.repository.CustomerRepository;
import com.banking.demo.repository.TransferRepository;
import com.banking.demo.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankingServiceImpl implements BankingService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransferRepository transferRepository;
    public ResponseEntity createAccount(CreateAccountRequest createAccountRequest) {
        Integer customerId = createAccountRequest.getCustomerId();
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty())
            return new ResponseEntity("Customer with id " + customerId + " not found", HttpStatus.NOT_FOUND);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(createAccountRequest.getInitialBalance());

        BankAccount newAccount = bankAccountRepository.save(bankAccount);
        customer.get().getAccounts().add(newAccount);
        customerRepository.save(customer.get());

        return new ResponseEntity(newAccount, HttpStatus.CREATED);
    }

    public ResponseEntity transfer(TransferRequest transferRequest) {
        //validate fromAccount, toAccount and check for sufficient funds
        Optional<BankAccount> fromAccount = bankAccountRepository.findById(transferRequest.getFromId());
        if(fromAccount.isEmpty())
            return new ResponseEntity("BankAccount with id " + transferRequest.getFromId()
                    + " not found", HttpStatus.NOT_FOUND);
        Optional<BankAccount> toAccount = bankAccountRepository.findById(transferRequest.getToId());
        if(toAccount.isEmpty())
            return new ResponseEntity("BankAccount with id " + transferRequest.getToId()
                    + " not found", HttpStatus.NOT_FOUND);
        if(fromAccount.get().getBalance() < transferRequest.getAmount())
            return new ResponseEntity("Insufficient balance for transfer", HttpStatus.BAD_REQUEST);

        //make transfer
        fromAccount.get().setBalance(fromAccount.get().getBalance() - transferRequest.getAmount());
        toAccount.get().setBalance(toAccount.get().getBalance() + transferRequest.getAmount());
        Transfer transfer = new Transfer();
        transfer.setAmount(transferRequest.getAmount());
        transfer.setFromAccountId(fromAccount.get().getId());
        transfer.setToAccountId(toAccount.get().getId());
        fromAccount.get().getTransfers().add(transfer);

        //update database
        transferRepository.save(transfer);
        bankAccountRepository.save(toAccount.get());
        bankAccountRepository.save(fromAccount.get());

        return ResponseEntity.ok(transfer);
    }

    public ResponseEntity getBalance(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty())
            return new ResponseEntity("Customer with id " + customerId + " not found", HttpStatus.NOT_FOUND);
        List<BankAccount> accounts = customer.get().getAccounts();
        return ResponseEntity.ok(accounts);
    }

    public ResponseEntity getTransferHistory(Integer accountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        if(bankAccount.isEmpty())
            return new ResponseEntity("BankAccount with id " + accountId + " not found", HttpStatus.NOT_FOUND);
        List<Transfer> transfers = bankAccount.get().getTransfers();
        return ResponseEntity.ok(transfers);
    }

}
