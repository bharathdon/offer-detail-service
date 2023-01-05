/*
package com.keybank.offers.dao;


import com.keybank.offers.controller.OffersDetailsControllerAdvice;
import com.keybank.offers.exception.BussinessException;
import com.keybank.offers.exception.SystemException;
import com.keybank.offers.model.OffersDao;
import com.keybank.offers.model.OffersDaoRequest;
import com.keybank.offers.model.OffersDaoResponse;
import com.keybank.offers.util.OffersDetailsConstant;
import com.keybank.offers.util.OffersDetailsErrorCodesEnum;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class OffersDaoSpringJdbcImpl extends StoredProcedure implements RowMapper, IOfferDetailsDao {
    @Autowired
    public OffersDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "GET_OFFER_DETAILS_V1");
        compileStoredProcedureParam();
    }

    private void compileStoredProcedureParam() {

        declareParameter(new SqlParameter(OffersDetailsConstant.CLIENT_ID, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.CHANNEL_ID, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.NAME_ON_CARD, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.CVV, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.CARDNUMBER, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.EXP_DATE, Types.VARCHAR));

        declareParameter(new SqlOutParameter(OffersDetailsConstant.RESP_CODE_OUT, Types.VARCHAR));
        declareParameter(new SqlOutParameter(OffersDetailsConstant.RESP_MSG_OUT, Types.VARCHAR));

        SqlReturnResultSet offersResult = new SqlReturnResultSet("offersResult", this::mapRow);

        declareParameter(new SqlReturnResultSet("offersResult", this::mapRow));

        compile();
    }


    @Override
    public OffersDaoResponse getOffers(OffersDaoRequest offerDaoRequest) throws BussinessException, SystemException {
        OffersDaoResponse offersDaoResponse = new OffersDaoResponse();
        HashMap<String, Object> spInput = new HashMap<>();
        System.out.println("offersdao request : \n" + offerDaoRequest);

        spInput.put(OffersDetailsConstant.CLIENT_ID, offerDaoRequest.getClientId());
        spInput.put(OffersDetailsConstant.CHANNEL_ID, offerDaoRequest.getChannelId());
        spInput.put(OffersDetailsConstant.CARDNUMBER, offerDaoRequest.getCardNum());
        spInput.put(OffersDetailsConstant.CVV, offerDaoRequest.getCvv());
        spInput.put(OffersDetailsConstant.NAME_ON_CARD, offerDaoRequest.getNameOnCard());
        spInput.put(OffersDetailsConstant.EXP_DATE, offerDaoRequest.getExpDate());

        System.out.println("spinmpout : " + spInput);
        Map<String, Object> spOutput = super.execute(spInput);

        String dbResponseCode = null;
        String dbResponseMsg = null;
        System.out.println("spoutput : +" + spOutput);
        if (spOutput != null && spOutput.get(OffersDetailsConstant.RESP_CODE_OUT) != null && spOutput.get(OffersDetailsConstant.RESP_MSG_OUT) != null) {
            dbResponseCode = spOutput.get(OffersDetailsConstant.RESP_CODE_OUT).toString();
            dbResponseMsg = spOutput.get(OffersDetailsConstant.RESP_MSG_OUT).toString();
        }

        ArrayList<OffersDao> offersDaos = new ArrayList<>();
        if (OffersDetailsConstant.SUCCESS_RESP_CODE.contentEquals(dbResponseCode)) {
            offersDaos = (ArrayList<OffersDao>) spOutput.get("offersResult");

            offersDaoResponse.setOffersDao(offersDaos);
            offersDaoResponse.setResponeCode(dbResponseCode);
            offersDaoResponse.setResponseMessage(dbResponseMsg);

        } else if (OffersDetailsErrorCodesEnum.checkErrorCode(dbResponseCode, OffersDetailsConstant.DATA_ERROR)) {
            throw new BussinessException(dbResponseCode, dbResponseMsg);
        } else if (OffersDetailsErrorCodesEnum.checkErrorCode(dbResponseCode, OffersDetailsConstant.SYSTEM_ERROR)) {
            throw new SystemException(dbResponseCode, dbResponseMsg);
        } else {
            throw new SystemException(OffersDetailsConstant.GENERIC_RESP_CODE, OffersDetailsConstant.GENERIC_RESP_MSG);
        }

        return offersDaoResponse;
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        OffersDao offersDao = new OffersDao();

        offersDao.setOfferId(rs.getString(OffersDetailsConstant.OFFER_ID));
        offersDao.setOffersType(rs.getString(OffersDetailsConstant.OFFER_TYPE));
        offersDao.setOffersCode(rs.getString(OffersDetailsConstant.OFFER_CODE));
        offersDao.setStock(rs.getString(OffersDetailsConstant.OFFER_STOCK));
        offersDao.setName(rs.getString(OffersDetailsConstant.OFFER_NAME));
        offersDao.setExpDate(rs.getString(OffersDetailsConstant.EXP_DATE));
        offersDao.setDescription(rs.getString(OffersDetailsConstant.OFFER_DESC));

        return offersDao;
    }
}*/



/* Copyright @ 2022, Keybank pvt ltd. All Rights are reserved. You should not disclose the
 * information outside, otherwise terms and conditions will apply
 *
 */

package com.keybank.offers.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import com.keybank.offers.exception.BussinessException;
import com.keybank.offers.exception.SystemException;
import com.keybank.offers.model.OffersDao;
import com.keybank.offers.model.OffersDaoRequest;
import com.keybank.offers.model.OffersDaoResponse;
import com.keybank.offers.util.OffersDetailsConstant;
import com.keybank.offers.util.OffersDetailsErrorCodesEnum;



@Component
@Primary
public class OffersDaoSpringJdbcImpl extends StoredProcedure implements RowMapper, IOfferDetailsDao {

    @Autowired
    public OffersDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate) {

        super(jdbcTemplate, OffersDetailsConstant.STORED_PROCEDURE_NAME);
        compileStoredProcedureParam();

    }

    private void compileStoredProcedureParam() {

        // register input params
        declareParameter(new SqlParameter(OffersDetailsConstant.CLIENT_ID, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.CHANNEL_ID, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.CARDNUMBER, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.CVV, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.NAME_ON_CARD, Types.VARCHAR));
        declareParameter(new SqlParameter(OffersDetailsConstant.EXP_DATE, Types.VARCHAR));

        // register output params
        declareParameter(new SqlOutParameter(OffersDetailsConstant.RESP_CODE_OUT, Types.VARCHAR));
        declareParameter(new SqlOutParameter(OffersDetailsConstant.RESP_MSG_OUT, Types.VARCHAR));

        // register Resultset

        declareParameter(new SqlReturnResultSet(OffersDetailsConstant.OFFERS_RESULT_SET_NAME, this));

        compile();

    }


    public OffersDaoResponse getOffers(OffersDaoRequest offerDaoRequest) throws BussinessException, SystemException {

        System.out.println("spring jdbc offers dao getOffers -- start");

        OffersDaoResponse daoResp = new OffersDaoResponse();

        // prepare the sp input
        Map<String, Object> spInput = new HashMap<String, Object>();
        System.out.println("client id is : " + offerDaoRequest.getClientId());
        spInput.put(OffersDetailsConstant.CLIENT_ID, offerDaoRequest.getClientId());
        spInput.put(OffersDetailsConstant.CHANNEL_ID, offerDaoRequest.getChannelId());
        spInput.put(OffersDetailsConstant.CARDNUMBER, offerDaoRequest.getCardNum());
        spInput.put(OffersDetailsConstant.CVV, offerDaoRequest.getCvv());
        spInput.put(OffersDetailsConstant.NAME_ON_CARD, offerDaoRequest.getNameOnCard());
        spInput.put(OffersDetailsConstant.EXP_DATE, offerDaoRequest.getExpDate());

        // invoke stored procedure
        Map<String, Object> spOutput = super.execute(spInput);
        System.out.println("spOutput :" + spOutput);
        String dbResponseCode = null;
        String dbResponseMsg = null;

        if (spOutput != null && spOutput.get(OffersDetailsConstant.RESP_CODE_OUT) != null
                && spOutput.get(OffersDetailsConstant.RESP_MSG_OUT) != null) {

            dbResponseCode = spOutput.get(OffersDetailsConstant.RESP_CODE_OUT).toString();
            dbResponseMsg = spOutput.get(OffersDetailsConstant.RESP_MSG_OUT).toString();
            System.out.println("dbResponseCode :" + dbResponseCode);
            System.out.println("dbResponseMsg :" + dbResponseMsg);
        }

        List<OffersDao> offersDao = new ArrayList<OffersDao>();

        // handling database response
        if (OffersDetailsConstant.SUCCESS_RESP_CODE.contentEquals(dbResponseCode)) {

            // Prepare the dao resposne with the help of resultset

            offersDao = (List<OffersDao>) spOutput.get(OffersDetailsConstant.OFFERS_RESULT_SET_NAME);
            daoResp.setOffersDao(offersDao);
            daoResp.setResponeCode(dbResponseCode);
            daoResp.setResponseMessage(dbResponseMsg);

        } else if (OffersDetailsErrorCodesEnum.checkErrorCode(dbResponseCode, OffersDetailsConstant.DATA_ERROR)) {

            throw new BussinessException(dbResponseCode, dbResponseMsg);
        } else if (OffersDetailsErrorCodesEnum.checkErrorCode(dbResponseCode, OffersDetailsConstant.SYSTEM_ERROR)) {
            throw new SystemException(dbResponseCode, dbResponseMsg);
        } else {
            throw new SystemException(OffersDetailsConstant.GENERIC_RESP_CODE, OffersDetailsConstant.GENERIC_RESP_MSG);
        }

        System.out.println("offers dao getOffers -- exit");
        return daoResp;
    }

    public OffersDao mapRow(ResultSet rs, int rowNum) throws SQLException {

        System.out.println("entered into mapRow");

        OffersDao offersDao = new OffersDao();

        offersDao.setOfferId(rs.getString(OffersDetailsConstant.OFFER_ID));
        offersDao.setName(rs.getString(OffersDetailsConstant.OFFER_NAME));
        offersDao.setOffersCode(rs.getString(OffersDetailsConstant.OFFER_CODE));
        offersDao.setDescription(rs.getString(OffersDetailsConstant.OFFER_DESC));
        offersDao.setExpDate(rs.getString(OffersDetailsConstant.OFFER_EXPIRY_DATE));
        offersDao.setCreationDate(rs.getString(OffersDetailsConstant.OFFER_CREATION_DATE));
        offersDao.setImageUrl(rs.getURL(OffersDetailsConstant.OFFER_IMAGE_URL).toString());
        offersDao.setStock(rs.getString(OffersDetailsConstant.OFFER_STOCK));
        offersDao.setOffersType(rs.getString(OffersDetailsConstant.OFFER_TYPE));

        return offersDao;
    }

}
