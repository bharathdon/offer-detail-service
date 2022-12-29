package com.keybank.offers.dao;

import com.keybank.offers.exception.BussinessException;
import com.keybank.offers.exception.SystemException;
import com.keybank.offers.model.OffersDao;
import com.keybank.offers.model.OffersDaoRequest;
import com.keybank.offers.model.OffersDaoResponse;
import com.keybank.offers.util.OffersDetailsConstant;
import com.keybank.offers.util.OffersDetailsErrorCodesEnum;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

//@Component
public class OfferDetailsDaoImpl implements IOfferDetailsDao
{
    @Override
    public OffersDaoResponse getOffers(OffersDaoRequest offerDaoRequest) throws BussinessException, SystemException {
        OffersDaoResponse offersDaoResponse = null;
        ArrayList<OffersDao> offersDaoArrayList = new ArrayList<>();
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms", "root", "password");

            String sql = "CALL GET_OFFER_DETAILS_V1(?,?,?,?,?,?,?,?)";

            CallableStatement cstmt = connection.prepareCall(sql);

            cstmt.setString(1, "WEB");
            cstmt.setString(2, "ONLINE");
            cstmt.setString(3, "1234567890123456");
            cstmt.setString(4, "123");
            cstmt.setString(5, "bharath");
            cstmt.setString(6, "12/2022");

            cstmt.registerOutParameter(7, Types.VARCHAR);
            cstmt.registerOutParameter(8, Types.VARCHAR);

            boolean b = cstmt.execute();

            ResultSet rs = cstmt.executeQuery();

            // get the out params data

            String dbResponseCode = cstmt.getString(7);
            String dbResponseMessage = cstmt.getString(8);

            System.out.println("dbrespCode is :" + dbResponseCode + ":::" + "dbrespMsg:" + dbResponseMessage);

            if (OffersDetailsConstant.SUCCESS_RESP_CODE.equals(dbResponseCode)) {

                offersDaoResponse = new OffersDaoResponse();

                offersDaoResponse.setResponeCode(dbResponseCode);
                offersDaoResponse.setResponseMessage(dbResponseMessage);

                while (rs.next()) {

                    OffersDao offersDao = new OffersDao();
                    offersDao.setOfferId(OffersDetailsConstant.OFFER_ID);
                    offersDao.setOffersType(OffersDetailsConstant.OFFER_TYPE);
                   // offersDao.setImageUrl(OffersDetailsConstant.OFFER_IMAGE_URL);
                    offersDao.setCreationDate(OffersDetailsConstant.OFFER_CREATION_DATE);
                    offersDao.setDescription(OffersDetailsConstant.OFFER_DESC);
                    offersDao.setExpDate(OffersDetailsConstant.OFFER_EXPIRY_DATE);
                    offersDao.setName(OffersDetailsConstant.OFFER_NAME);
                    offersDao.setStock(OffersDetailsConstant.OFFER_STOCK);
                    offersDao.setOffersCode(OffersDetailsConstant.OFFER_CODE);

                    offersDaoArrayList.add(offersDao);
                }
                offersDaoResponse.setOffersDao(offersDaoArrayList);


            } else if (OffersDetailsErrorCodesEnum.checkErrorCode(dbResponseCode,"Data Error")) {
                throw new BussinessException(dbResponseCode, dbResponseMessage);
            } else if (OffersDetailsErrorCodesEnum.checkErrorCode(dbResponseCode, "System Error")) {
                throw new SystemException(dbResponseCode, dbResponseMessage);
            } else {
                throw new SystemException(OffersDetailsConstant.GENERIC_RESP_CODE, OffersDetailsConstant.GENERIC_RESP_MSG);
            }
        } catch (BussinessException bussinessException) {
            bussinessException.printStackTrace();
            throw bussinessException;
        } catch (SystemException systemException) {
            systemException.printStackTrace();
            throw systemException;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offersDaoResponse;
    }
}
