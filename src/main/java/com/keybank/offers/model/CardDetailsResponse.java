package com.keybank.offers.model;

import lombok.Data;

@Data
public class CardDetailsResponse {

    private String cardNum;
    private String cvv;
    private String expDate;
    private String nameOnCard;
    private String enrollmentDate;
    private String status;
    public Boolean isPrimary;
    private String productCode;
    private String productType;
    private String renewal;
}
