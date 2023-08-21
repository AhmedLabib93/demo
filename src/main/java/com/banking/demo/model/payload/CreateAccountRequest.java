package com.banking.demo.model.payload;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    private Integer customerId;
    private Double initialBalance;

}
