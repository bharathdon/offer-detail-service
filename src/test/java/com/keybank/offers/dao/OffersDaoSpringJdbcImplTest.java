package com.keybank.offers.dao;

import com.keybank.offers.exception.BussinessException;
import com.keybank.offers.exception.SystemException;
import com.keybank.offers.model.OffersDaoRequest;
import com.keybank.offers.model.OffersDaoResponse;
import com.keybank.offers.util.OffersDetailsConstant;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class OffersDaoSpringJdbcImplTest {


    @MockBean
    DriverManager driverManager;

    @MockBean
    Connection mockConnection;

    @MockBean
    CallableStatement mockCallableStatement;

    @MockBean
    ResultSet mockResultSet;

    @InjectMocks
    OffersDaoSpringJdbcImpl offerDetailsSvcImpl;

    @SneakyThrows
    @Test
    void testGetOffers() throws BussinessException, SystemException {

        Mockito.when(DriverManager.getConnection(Mockito.any(String.class),
                Mockito.any(String.class), Mockito.any(String.class))).thenReturn(mockConnection);

        Mockito.when(mockConnection.prepareCall(Mockito.anyString())).thenReturn(mockCallableStatement);

        Mockito.when(mockCallableStatement.execute()).thenReturn(true);

        Mockito.when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet);

        Mockito.when(mockCallableStatement.getString(7)).thenReturn("0");
        Mockito.when(mockCallableStatement.getString(8)).thenReturn("success");
        Mockito.when(mockResultSet.next()).thenReturn(true);

        Mockito.when(mockResultSet.getString(OffersDetailsConstant.OFFER_ID)).thenReturn("111");
        Mockito.when(mockResultSet.getString(OffersDetailsConstant.OFFER_NAME)).thenReturn("rebulic offer");
        Mockito.when(mockResultSet.getString(OffersDetailsConstant.EXP_DATE)).thenReturn("12/2023");
        Mockito.when(mockResultSet.getString(OffersDetailsConstant.OFFER_CREATION_DATE)).thenReturn("10-DEC-2022");
        Mockito.when(mockResultSet.getString(OffersDetailsConstant.OFFER_DESC)).thenReturn("good offer");
        Mockito.when(mockResultSet.getString(OffersDetailsConstant.OFFER_CODE)).thenReturn("0012XC");


        OffersDaoRequest offerDaoRequest = new OffersDaoRequest();
        offerDaoRequest.setCardNum("345435435");
        offerDaoRequest.setClientId("web");
        offerDaoRequest.setChannelId("online");
        offerDaoRequest.setCvv("123");
        offerDaoRequest.setExpDate("12/2023");
        offerDaoRequest.setNameOnCard("sreenu");

        OffersDaoResponse offersResp = offerDetailsSvcImpl.getOffers(offerDaoRequest);

        assertEquals("0", offersResp.getResponeCode());

    }
}