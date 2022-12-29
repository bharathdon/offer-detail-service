package com.keybank.offers.validator;

import com.keybank.offers.exception.OffersRequestInvalidException;
import com.keybank.offers.model.OffersRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OffersDetailsValidatorTest {
    OffersDetailsValidator offersDetailsValidator = null;
    OffersRequest offersRequest = null;

    @BeforeEach
    void setUp() {
        offersDetailsValidator = new OffersDetailsValidator();
        offersRequest = buildOffersRequest();
    }

    private OffersRequest buildOffersRequest() {
        OffersRequest offersRequest = new OffersRequest();
        offersRequest.setRequestId("web");
        offersRequest.setChannelId("abc123");
        offersRequest.setNameOnCard("abcd");
        offersRequest.setCardNum("1234567890123456");
        offersRequest.setMessageTimestamp(new Date().toString());
        offersRequest.setExpDate("12-23");
        offersRequest.setCvv("123");
        return offersRequest;
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    public void test_clientid_null_scenario() {
        offersRequest.setClientId(null);

        try {
            offersDetailsValidator.validateRequest(offersRequest);
        } catch (OffersRequestInvalidException e) {
            assertEquals("ofs006", e.getResponseCode());
        }
    }

    @Test
    void test_clientid_empty_scenario(){
        offersRequest.setClientId("");
        try {
            offersDetailsValidator.validateRequest(offersRequest);
        } catch (OffersRequestInvalidException e) {
            assertEquals("ofs006",e.getResponseCode());
        }
    }


}