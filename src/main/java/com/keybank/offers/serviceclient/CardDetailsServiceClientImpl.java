package com.keybank.offers.serviceclient;

import com.keybank.offers.model.CardDetailsRequest;
import com.keybank.offers.model.CardDetailsResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;


@Component
public class CardDetailsServiceClientImpl implements ICardDetailsServiceClient {

   /* @Autowired
    private RestTemplate restTemplate;
*/

    @Override
    public CardDetailsResponse getCardDetails(CardDetailsRequest cardDetailsRequest) {
        CardDetailsResponse cardDetailsResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:9000/v1/card/" + cardDetailsRequest.getCardNum();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("client_id", "web");
        httpHeaders.add("request_id", UUID.randomUUID().toString());
        httpHeaders.add("message_ts", new Date().toString());

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<CardDetailsResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CardDetailsResponse.class);
        if (responseEntity.getStatusCode().value() == 200) {
            cardDetailsResponse = responseEntity.getBody();
            System.out.println(cardDetailsResponse);
        }

        return cardDetailsResponse;
    }

    public static void main(String[] args) {
        CardDetailsServiceClientImpl cardDetailsService = new CardDetailsServiceClientImpl();

        CardDetailsRequest cardDetailsRequest = new CardDetailsRequest();
        cardDetailsRequest.setCardNum("123456788");
        cardDetailsRequest.setNameOnCard("abcd");
        cardDetailsRequest.setExpDate("12-23");
        cardDetailsRequest.setCvv("123");

        cardDetailsService.getCardDetails(cardDetailsRequest);
    }
}
