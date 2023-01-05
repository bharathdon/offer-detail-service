package com.keybank.offers.builder;

import com.keybank.offers.model.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component

public class OffersDetailsResponseBuilder {


    public OffersResponse buildResponsePath(OffersDaoResponse offersDaoResponse) throws ParseException {
        StatusBlock statusBlock = new StatusBlock();
        statusBlock.setResponseCode(offersDaoResponse.getResponeCode());
        statusBlock.setResponseMessage(offersDaoResponse.getResponseMessage());

        List offersList = new ArrayList<Offers>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //List<OffersDao> offersDao = offersDaoResponse.getOffersDao();
        for (OffersDao offersDao : offersDaoResponse.getOffersDao()
        ) {
            Offers offers = new Offers();
            offers.setName(offersDao.getName());
            offers.setOffersType(offersDao.getOffersType());
            offers.setOfferId(offersDao.getOfferId());
            offers.setExpDate(offersDao.getExpDate());
            offers.setDescription(offersDao.getDescription());
            String imageUrl = offersDao.getImageUrl();
             offers.setImageUrl(imageUrl);
            String creationDate = offersDao.getCreationDate();

            offers.setCreationDate(new SimpleDateFormat("dd-MM-YYYY").parse(offersDao.getCreationDate()));
            offers.setStock(offersDao.getStock());
            offersList.add(offers);

        }


        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setStatusBlock(statusBlock);
        offersResponse.setOffers(offersList);
        return offersResponse;
    }
}
