package com.keybank.offers.service;

import com.keybank.offers.builder.OffersDetailsRequestBuilder;
import com.keybank.offers.builder.OffersDetailsResponseBuilder;
import com.keybank.offers.dao.IOfferDetailsDao;
import com.keybank.offers.exception.BussinessException;
import com.keybank.offers.exception.SystemException;
import com.keybank.offers.model.*;
import com.keybank.offers.serviceclient.ICardDetailsServiceClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class OffersDetailsServiceImplTest {

    @MockBean
    ICardDetailsServiceClient cardDetailsServiceClient;

    @MockBean
    IOfferDetailsDao offersDao;

    @MockBean
    OffersDetailsRequestBuilder offersDetailsRequestBuilder;

    @MockBean
    OffersDetailsResponseBuilder offersDetailsResponseBuilder;

    @InjectMocks
    OffersDetailsServiceImpl offerDetailsServiceImpl;

    OffersRequest offersRequest;

    @BeforeEach
    void setUp() throws Exception {

        offersRequest = buildOfferDetailsRequest();
    }

    /**
     * @return
     * CardDetailsRequest
     */
    private OffersRequest buildOfferDetailsRequest() {

        OffersRequest offerDetailsReq = new OffersRequest();

        offerDetailsReq.setCardNum("43434343434");
        offerDetailsReq.setCvv("123");
        offerDetailsReq.setExpDate("12-2023");
        offerDetailsReq.setNameOnCard("Sreenu");



        return offerDetailsReq;
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @DisplayName("offers details success scenario")
    @Test
    void testGetOffers_success_scenario() throws BussinessException, SystemException, ParseException {

        //calling original methods as it doesn't contain any risky code
        Mockito.when(offersDetailsRequestBuilder.buildCardDetailsReqest(Mockito.any(OffersRequest.class))).thenCallRealMethod();

        //apply mock response . i.e. apply expectation when calling getCardDetails on mock obj
        Mockito.when(cardDetailsServiceClient.getCardDetails(Mockito.any(CardDetailsRequest.class))).thenReturn(buildCardDetailsResp());

        //calling original methods as it doesn't contain any risky code
        Mockito.when(offersDetailsRequestBuilder.buildOffersDaoRequest(Mockito.any(OffersRequest.class))).thenCallRealMethod();

        //applying mock response. i.e. apply expectation when calling getOffers on mock obj

        Mockito.when(offersDao.getOffers(Mockito.any(OffersDaoRequest.class))).thenReturn(buildMockDaoResp());


        OffersResponse response = offerDetailsServiceImpl.getOffers(offersRequest);

        assertNotNull(response);
        assertEquals("0", response.getStatusBlock().getResponseCode());

    }

    //TC2: offer details only moffers scenarios

    //TC3: offer details only goffers scenarios

    //TC4: offer details only poffers scenarios

    //TC5: The combination of different offers scenarios

    //TC6: Test in case of any exception will come from CardDetailsService

    @DisplayName("offers card details business exception scenario")
    @Test
    @SneakyThrows
    public void testGetOffers_BusinessException_scenario()  {

        //calling original methods as it doesn't contain any risky code
        Mockito.when(offersDetailsRequestBuilder.buildCardDetailsReqest(Mockito.any(OffersRequest.class))).thenCallRealMethod();

        //apply mock response . i.e. apply expectation when calling getCardDetails on mock obj
        Mockito.when(cardDetailsServiceClient.getCardDetails(Mockito.any(CardDetailsRequest.class))).thenReturn(buildCardDetailsResp());

        //calling original methods as it doesn't contain any risky code
        Mockito.when(offersDetailsRequestBuilder.buildOffersDaoRequest(Mockito.any(OffersRequest.class))).thenCallRealMethod();

        //applying mock response. i.e. apply expectation when calling getOffers on mock obj

        Mockito.when(offersDao.getOffers(Mockito.any(OffersDaoRequest.class))).thenThrow(new BussinessException("101", "no offers found"));


        OffersResponse response = offerDetailsServiceImpl.getOffers(offersRequest);

        assertNotNull(response);
        assertEquals("0", response.getStatusBlock().getResponseCode());

    }

    //TC7: Test in case of any exception will come from OffersDao

    @DisplayName("offers card details service exception scenario")
    @SneakyThrows
    @Test
    void testGetOffers_ServiceException_scenario() {

        //calling original methods as it doesn't contain any risky code
        Mockito.when(offersDetailsRequestBuilder.buildCardDetailsReqest(Mockito.any(OffersRequest.class))).thenCallRealMethod();

        //apply mock response . i.e. apply expectation when calling getCardDetails on mock obj
        Mockito.when(cardDetailsServiceClient.getCardDetails(Mockito.any(CardDetailsRequest.class))).thenReturn(buildCardDetailsResp());

        //calling original methods as it doesn't contain any risky code
        Mockito.when(offersDetailsRequestBuilder.buildOffersDaoRequest(Mockito.any(OffersRequest.class))).thenCallRealMethod();

        //applying mock response. i.e. apply expectation when calling getOffers on mock obj

        Mockito.when(offersDao.getOffers(Mockito.any(OffersDaoRequest.class))).thenThrow(new SystemException("500","internal service error"));


        OffersResponse response = offerDetailsServiceImpl.getOffers(offersRequest);

        assertNotNull(response);
        assertEquals("0", response.getStatusBlock().getResponseCode());

    }

    /**
     * @return
     * OfferDaoResponse
     */
    private OffersDaoResponse buildMockDaoResp() {

        OffersDaoResponse offerDaoResp = new OffersDaoResponse();

        offerDaoResp.setResponeCode("0");
        offerDaoResp.setResponseMessage("success");


        List<OffersDao> offersDaoList = new ArrayList<OffersDao>();

        OffersDao offersDao = new OffersDao();
        offersDao.setOfferId("1234");
        offersDao.setName("mobile");

        OffersDao offersDao1 = new OffersDao();
        offersDao1.setOfferId("1234");
        offersDao1.setName("mobile");

        offersDaoList.add(offersDao);
        offersDaoList.add(offersDao1);

        offerDaoResp.setOffersDao(offersDaoList);


        return offerDaoResp;
    }

    /**
     * @return
     * CardDetailsResponse
     */
    private CardDetailsResponse buildCardDetailsResp() {

        CardDetailsResponse cardDetailsResp = new CardDetailsResponse();

        cardDetailsResp.setProductCode("p001");
        cardDetailsResp.setEnrollmentDate("24-03-2008");
        cardDetailsResp.setRenewal("true");
        cardDetailsResp.setEnrollmentDate("25-01-2023");
        cardDetailsResp.setStatus("active");

        return cardDetailsResp;
    }

}