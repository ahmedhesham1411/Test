package com.uriallab.haat.haat.Test;

import com.uriallab.haat.haat.DataModels.CategoryModel;
import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.DataModels.ServerStoresModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkInterface {
    @POST("Authorization/Login")
    Observable<List<String>> GetMarqee();

   /* @POST("Authorization/Login")
    Observable<TestResponse> LoginTest(@Body Rate_request rate_request);
*/

    @GET("Data/GetStors")
    Observable<ServerStoresModel> GetStory(@Query("lat") Double latitude,
                                           @Query("lng") Double longitude);

    @GET("Client/GetCategory")
    Observable<CategoryModel> GetCategories();

    @GET("nearbysearch/json")
    Observable<GoogleStoresModel> GetSubCategories(
                    @Query("key") String key,
                    @Query("location") String location,
                    @Query("rankby") String distance,
                    @Query("type") String type,
                    @Query("language") String language);

    @GET("nearbysearch/json")
    Observable<GoogleStoresModel> GetSubCategoriesPaginate(
            @Query("key") String key,
            @Query("location") String location,
            @Query("rankby") String distance,
            @Query("pagetoken") String pagetoken,
            @Query("type") String type,
            @Query("language") String language);
}
