package com.group6.donor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haerul on 15/03/18.
 */

public interface ApiInterface {

    @POST("get_donors.php")
    Call<List<DonorsClass>> getDonors();

    @FormUrlEncoded
    @POST("add_donor.php")
    Call<DonorsClass> insertDonor(
            @Field("key") String key,
            @Field("name") String name,
            @Field("Address") String Address,
            @Field("ContactNumber") String ContactNumber,
            @Field("Email") String Email,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_donor.php")
    Call<DonorsClass> updateDonor(
            @Field("key") String key,
            @Field("DonorID") int DonorID,
            @Field("name") String name,
            @Field("Address") String Address,
            @Field("ContactNumber") String ContactNumber,
            @Field("Email") String Email,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_donor.php")
    Call<DonorsClass> deleteDonor(
            @Field("key") String key,
            @Field("DonorID") int DonorID,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_love.php")
    Call<DonorsClass> updateLove(
            @Field("key") String key,
            @Field("DonorID") int DonorID,
            @Field("love") boolean love);

}
