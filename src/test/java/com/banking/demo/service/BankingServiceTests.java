package com.banking.demo.service;

import com.banking.demo.model.BankAccount;
import com.banking.demo.model.Customer;
import com.banking.demo.model.Transfer;
import com.banking.demo.repository.BankAccountRepository;
import com.banking.demo.repository.CustomerRepository;
import com.banking.demo.repository.TransferRepository;
import com.banking.demo.service.impl.BankingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BankingServiceTests {

    @InjectMocks
    private BankingServiceImpl bankingService;
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransferRepository transferRepository;

    private BankAccount account1;
    private BankAccount account2;
    private Transfer transfer;

    private Customer customer;

    @BeforeEach
    public void setup(){
        transfer = Transfer.builder()
                .id(1)
                .fromAccountId(1)
                .toAccountId(1)
                .amount(1000.0)
                .build();
        account1 = BankAccount.builder()
                .id(1)
                .balance(2000.0)
                .transfers(List.of(transfer))
                .build();
        account2 = BankAccount.builder()
                .id(2)
                .balance(2000.0)
                .build();
        customer = Customer.builder()
                .id(1)
                .name("Ahmed Labib")
                .accounts(List.of(account1, account2))
                .build();
    }

    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnListBankAccountBalance(){
        BDDMockito.given(customerRepository.findById(1)).willReturn(Optional.of(customer));
        List<BankAccount> list = (List<BankAccount>)bankingService.getBalance(customer.getId()).getBody();
        Assertions.assertThat(list).isNotEmpty();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void givenBankAccountId_whenGetBankAccountById_thenReturnListBankAccountTransfers(){
        BDDMockito.given(bankAccountRepository.findById(1)).willReturn(Optional.of(account1));
        List<Transfer> list = (List<Transfer>)bankingService.getTransferHistory(account1.getId()).getBody();
        Assertions.assertThat(list).isNotEmpty();
        Assertions.assertThat(list.size()).isEqualTo(1);
    }
}
