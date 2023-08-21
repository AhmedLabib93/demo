package com.banking.demo.model.payload;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {

    private Integer fromId;
    private Integer toId;
    private Double amount;
}
