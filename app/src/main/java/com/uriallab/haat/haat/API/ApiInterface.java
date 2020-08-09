package com.uriallab.haat.haat.API;

import com.uriallab.haat.haat.DataModels.MessageResponseModel;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Multipart
    @POST("Order/SendMessage")
    Call<MessageResponseModel> sendMessageFile(@Part("ChatID") RequestBody chatId,
                                               @Part("Order_ID") RequestBody  orderId,
                                               @Part("type") RequestBody  type,
                                               @Part MultipartBody.Part filePart
    );

    @Multipart
    @POST("Order/SendMessage")
    Call<MessageResponseModel> sendMessageTxt(@Part("ChatID") RequestBody chatId,
                                     @Part("Order_ID") RequestBody  orderId,
                                     @Part("type") RequestBody  type,
                                     @Part("message") RequestBody message
    );

}