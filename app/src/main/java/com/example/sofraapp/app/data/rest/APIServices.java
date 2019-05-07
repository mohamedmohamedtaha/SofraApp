package com.example.sofraapp.app.data.rest;

import com.example.sofraapp.app.data.model.cycleClient.addreview.AddReview;
import com.example.sofraapp.app.data.model.cycleClient.loginclient.LoginClient;
import com.example.sofraapp.app.data.model.cycleClient.myordersasuser.MyOrdersAsUser;
import com.example.sofraapp.app.data.model.cycleClient.newpassword.NewPassword;
import com.example.sofraapp.app.data.model.cycleClient.notifications.Notifications;
import com.example.sofraapp.app.data.model.cycleClient.profile.EditProfile;
import com.example.sofraapp.app.data.model.cycleClient.profile.getuserprofile.GetUserProfile;
import com.example.sofraapp.app.data.model.cycleClient.register.Register;
import com.example.sofraapp.app.data.model.cycleClient.resetpassword.ResetPassword;
import com.example.sofraapp.app.data.model.cycleRestaurant.addproduct.AddProduct;
import com.example.sofraapp.app.data.model.cycleRestaurant.myorders.MyOrders;
import com.example.sofraapp.app.data.model.cycleRestaurant.myproduct.MyProduct;
import com.example.sofraapp.app.data.model.cycleRestaurant.offers.myoffers.MyOffers;
import com.example.sofraapp.app.data.model.cycleRestaurant.offers.newoffer.NewOffer;
import com.example.sofraapp.app.data.model.general.categories.Categories;
import com.example.sofraapp.app.data.model.general.cities.Cities;
import com.example.sofraapp.app.data.model.general.contact.Contact;
import com.example.sofraapp.app.data.model.general.offers.Offers;
import com.example.sofraapp.app.data.model.general.regions.Regions;
import com.example.sofraapp.app.data.model.general.restaurantdetails.RestaurantDetails;
import com.example.sofraapp.app.data.model.general.restaurantitems.RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Restaurants;
import com.example.sofraapp.app.data.model.general.reviews.Reviews;
import com.example.sofraapp.app.data.model.general.settings.Settings;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIServices {

    //   ---------------------  for User  ---------------------------------------
    @POST("client/login")
    @FormUrlEncoded
    Call<LoginClient> getLogin(@Field("email") String email, @Field("password") String password);

    @POST("client/profile")
    @FormUrlEncoded
    Call<EditProfile> getProfileAndEdit(@Field("api_token") String api_token, @Field("name") String name, @Field("phone") String number, @Field("email") String email,
                                        @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                                        @Field("address") String address, @Field("region_id") int region_id);

    @POST("client/profile")
    @FormUrlEncoded
    Call<GetUserProfile> getUserProfile(@Field("api_token") String api_token);

    @POST("client/register")
    @FormUrlEncoded
    Call<Register> getRegister(@Field("name") String name, @Field("email") String email,
                               @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                               @Field("phone") String number, @Field("address") String address, @Field("region_id") int region_id);

    @Multipart
    @POST("restaurant/register")
    Call<com.example.sofraapp.app.data.model.cycleRestaurant.cyclelogin.register.Register> getRegisterAsRestaurant(@Part("name") RequestBody name
            , @Part("email") RequestBody email
            , @Part("password") RequestBody password
            , @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone
            , @Part("address") RequestBody address
            , @Part("region_id") RequestBody region_id
            , @Part("whatsapp") RequestBody whatsapp
            , @Part("categories[0]") RequestBody categories
            , @Part("delivery_period") RequestBody delivery_period
            , @Part("delivery_cost") RequestBody delivery_cost
            , @Part("minimum_charger") RequestBody minimum_charger
            , @Part MultipartBody.Part photo
            , @Part("availability") RequestBody availability);

    @Multipart
    @POST("restaurant/new-offer")
    Call<NewOffer> getNewOffer(@Part("description") RequestBody description
            , @Part("price") RequestBody price
            , @Part("starting_at") RequestBody starting_at
            , @Part("name") RequestBody name
            , @Part MultipartBody.Part photo
            , @Part("ending_at") RequestBody ending_at
            , @Part("api_token") RequestBody api_token);

    @GET("categories")
    Call<Categories> getCategories();

    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getResetPassword(@Field("email") String email);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<NewPassword> getNewPassword(@Field("code") String code, @Field("password") String password,
                                     @Field("password_confirmation") String password_confirmation);

    @GET("cities")
    Call<Cities> getCities();

    @GET("regions")
    Call<Regions> getRegions(@Query("city_id") int city_id);

    @GET("client/my-orders")
    Call<MyOrdersAsUser> getMyOrdersAsUser(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);

    @GET("offers")
    Call<Offers> getOffers(@Query("page") int page);

    @POST("contact")
    @FormUrlEncoded
    Call<Contact> getContact(@Field("name") String name, @Field("email") String email,
                             @Field("phone") String phone, @Field("type") String type, @Field("content") String content);

    @GET("settings")
    Call<Settings> getSettings();

    @GET("client/notifications")
    Call<Notifications> getNotifications(@Query("api_token") String api_token);

    @GET("restaurants")
    Call<Restaurants> getRestaurants(@Query("page") int page);

    @GET("restaurant")
    Call<RestaurantDetails> getRestaurantDetails(@Query("restaurant_id") int restaurant_id);

    @GET("items")
    Call<RestaurantItems> getRestaurantItems(@Query("restaurant_id") int restaurant_id, @Query("page") int page);

    @GET("restaurant/reviews")
    Call<Reviews> getReviews(@Query("api_token") String api_token, @Query("restaurant_id") int restaurant_id, @Query("page") int page);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddReview> getAddReview(@Field("rate") int rate, @Field("comment") String comment,
                                 @Field("restaurant_id") int restaurant_id, @Field("api_token") String api_token);

    @GET("restaurant/my-items")
    Call<MyProduct> getMyProducts(@Query("api_token") String api_token, @Query("page") int page);

    @Multipart
    @POST("restaurant/new-item")
    Call<AddProduct> getAddProduct(@Part("description") RequestBody description,
                                   @Part("price") RequestBody price,
                                   @Part("preparing_time") RequestBody preparing_time,
                                   @Part("name") RequestBody name,
                                   @Part MultipartBody.Part photo,
                                   @Part("api_token") RequestBody api_token);

    @GET("restaurant/my-offers")
    Call<MyOffers> getMyOffers(@Query("api_token") String api_token, @Query("page") int page);

    @GET("restaurant/my-orders")
    Call<MyOrders> getMyOrders(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);


}




















