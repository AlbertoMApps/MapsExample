package com.android4dev.navigationview.api;

import com.android4dev.navigationview.model.PlacesModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by TAE_user0 on 06/01/2016.
 */
public interface apiPlaces {
    @GET("/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=cruise&key=AIzaSyAYXKqge1mpt5WKiKdG1MZll1JslgcZlMg")
    public void getPlaces(Callback<PlacesModel> response);
}
