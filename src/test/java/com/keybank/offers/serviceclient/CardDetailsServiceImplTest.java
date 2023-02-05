package com.keybank.offers.serviceclient;

import com.keybank.offers.model.CardDetailsRequest;
import com.keybank.offers.model.CardDetailsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class CardDetailsServiceImplTest {

    @MockBean
    RestTemplate restTemplate;

    @InjectMocks
    CardDetailsServiceClientImpl cardDetailsSvcClient;

    @Test
    void testGetCardDetails_Success_Scenario() {

        Mockito.when(restTemplate.exchange(Mockito.any(String.class),
                Mockito.<HttpMethod>any(),
                Mockito.<HttpEntity> any(),
                Mockito.<Class<CardDetailsResponse>> any(),
                Mockito.<String, Object> anyMap())).thenReturn(buildCardDetailsResp());

        CardDetailsRequest cardDetailsRequest = new CardDetailsRequest();
        cardDetailsRequest.setCardNum("76554321212");
        cardDetailsRequest.setCvv("123");
        cardDetailsRequest.setExpDate("12/2023");
        cardDetailsRequest.setNameOnCard("sreenu");

        CardDetailsResponse cardDetailsResp = cardDetailsSvcClient.getCardDetails(cardDetailsRequest);


        assertEquals("active", cardDetailsResp.getStatus());



    }

    /**
     * @return
     * ResponseEntity<CardDetailsResponse>
     */
    private ResponseEntity<CardDetailsResponse> buildCardDetailsResp() {

        CardDetailsResponse cardDetailsResp = new CardDetailsResponse();
        cardDetailsResp.setProductCode("p001");
        cardDetailsResp.setEnrollmentDate("24-03-2008");
        cardDetailsResp.setRenewal("yes");
        cardDetailsResp.setEnrollmentDate("26-01-2023");
        cardDetailsResp.setStatus("active");

        ResponseEntity<CardDetailsResponse> cardDetailsRespEntity = new ResponseEntity<CardDetailsResponse>(HttpStatus.OK);

        return cardDetailsRespEntity;
    }

}