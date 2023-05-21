package com.group6.donor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface ApiInterface {

    // Donor Fields

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

    // Inventory Fields

    @POST("get_inventory.php")
    Call<List<InventoryClass>> getInventory();

    @FormUrlEncoded
    @POST("add_inventory.php")
    Call<InventoryClass> insertInventory(
            @Field("key") String key,
            @Field("ItemName") String ItemName,
            @Field("Quantity") String Quantity,
            @Field("Description") String Description,
            @Field("Expiration") String Expiration,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_inventory.php")
    Call<InventoryClass> updateInventory(
            @Field("key") String key,
            @Field("ItemID") int ItemID,
            @Field("ItemName") String ItemName,
            @Field("Quantity") String Quantity,
            @Field("Description") String Description,
            @Field("Expiration") String Expiration,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_inventory.php")
    Call<InventoryClass> deleteInventory(
            @Field("key") String key,
            @Field("ItemID") int ItemID,
            @Field("picture") String picture);

    // DistributionFields

    @POST("get_distribution.php")
    Call<List<DistributionClass>> getDistribution();

    @FormUrlEncoded
    @POST("add_distribution.php")
    Call<DistributionClass> insertDistribution(
            @Field("key") String key,
            @Field("Recipient") String Recipient,
            @Field("DistributionLocation") String DistributionLocation,
            @Field("Quantity") String Quantity,
            @Field("Notes") String Notes,
            @Field("Status") int Status,
            @Field("DistributionDate") String DistributionDate,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_distribution.php")
    Call<DistributionClass> updateDistribution(
            @Field("key") String key,
            @Field("DistributionID") int DistributionID,
            @Field("Recipient") String Recipient,
            @Field("DistributionLocation") String DistributionLocation,
            @Field("Quantity") String Quantity,
            @Field("Notes") String Notes,
            @Field("Status") int Status,
            @Field("DistributionDate") String DistributionDate,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_distribution.php")
    Call<DistributionClass> deleteDistribution(
            @Field("key") String key,
            @Field("DistributionID") int DistributionID,
            @Field("picture") String picture);


}
