package com.keybank.offers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true)
public class OffersDao {

    private String offerId;
    private String name;
    private String description;
    private String expDate;
    private String imageUrl;
    private String creationDate;
    private String stock;
    private String offersType;
    private String offersCode;


}
