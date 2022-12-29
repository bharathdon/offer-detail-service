package com.keybank.offers.serviceclient;

import com.keybank.offers.model.CardDetailsRequest;
import com.keybank.offers.model.CardDetailsResponse;

public interface ICardDetailsServiceClient {

    public CardDetailsResponse getCardDetails(CardDetailsRequest  cardDetailsRequest);
}
