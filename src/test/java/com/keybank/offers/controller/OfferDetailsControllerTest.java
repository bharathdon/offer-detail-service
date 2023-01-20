package com.keybank.offers.controller;

import com.keybank.offers.exception.BussinessException;
import com.keybank.offers.exception.SystemException;
import com.keybank.offers.model.Offers;
import com.keybank.offers.model.OffersRequest;
import com.keybank.offers.model.OffersResponse;
import com.keybank.offers.model.StatusBlock;
import com.keybank.offers.service.IOffersDetailsService;
import com.keybank.offers.validator.OffersDetailsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OfferDetailsController.class)
@ContextConfiguration(classes = {OfferDetailsController.class})
class OfferDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OffersDetailsValidator offersDetailsValidator;

    @MockBean
    IOffersDetailsService offersDetailsService;

    @Test
    void test_getOffers_success_scenario() throws Exception {

        Mockito.when(offersDetailsService.getOffers(Mockito.any(OffersRequest.class))).thenReturn(buildMockOfferResp());


        String path = "/v1/offers/1234567891234567/123/BHARATH/12-25";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(path).header("client_id", "web")
                .header("channel_id", "online")
                .header("request_id", "123")
                .header("message_TS", "12-23");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse mvcResultResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), mvcResultResponse.getStatus());


    }

    private OffersResponse buildMockOfferResp() {
        OffersResponse offersResponse = new OffersResponse();

        StatusBlock statusBlock = new StatusBlock();
        statusBlock.setResponseCode("0");
        statusBlock.setResponseMessage("success");

        ArrayList offers = new ArrayList<>();

        Offers offers1 = new Offers();
        offers1.setOfferId("1234");
        offers1.setOffersType("moffers");
        offers1.setStock("yes");
        offers1.setCreationDate(new Date("12-10-2022"));

        offers.add(offers1);

        offersResponse.setStatusBlock(statusBlock);
        offersResponse.setOffers(offers);

        return offersResponse;
    }

}