package com.example.sofraapp.app.data.rest;

import com.example.sofraapp.app.data.model.client.addreview.AddReview;
import com.example.sofraapp.app.data.model.client.cycleClient.forgetpassword.newpassword.NewPassword;
import com.example.sofraapp.app.data.model.client.cycleClient.forgetpassword.resetpassword.ResetPassword;
import com.example.sofraapp.app.data.model.client.cycleClient.loginclient.LoginClient;
import com.example.sofraapp.app.data.model.client.cycleClient.notifications.Notifications;
import com.example.sofraapp.app.data.model.client.cycleClient.profile.editprofile.EditProfile;
import com.example.sofraapp.app.data.model.client.cycleClient.profile.getuserprofile.GetUserProfile;
import com.example.sofraapp.app.data.model.client.cycleClient.registerclinet.Register;
import com.example.sofraapp.app.data.model.client.order.confirmorder.ConfirmOrder;
import com.example.sofraapp.app.data.model.client.order.declineorder.DeclineOrder;
import com.example.sofraapp.app.data.model.client.order.myordersasuser.MyOrdersAsUser;
import com.example.sofraapp.app.data.model.client.order.neworder.NewOrder;
import com.example.sofraapp.app.data.model.client.order.showorder.ShowOrder;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.login.Login;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.cyclelogin.registerasrestaurant.RegisterAsRestaurant;
import com.example.sofraapp.app.data.model.restaurant.fooditem.myitems.MyItems;
import com.example.sofraapp.app.data.model.restaurant.fooditem.newitem.NewItem;
import com.example.sofraapp.app.data.model.restaurant.order.acceptorder.AcceptOrder;
import com.example.sofraapp.app.data.model.restaurant.order.myorders.MyOrders;
import com.example.sofraapp.app.data.model.restaurant.offers.myoffers.MyOffers;
import com.example.sofraapp.app.data.model.restaurant.offers.newoffer.NewOffer;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.changestate.ChangeState;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.commissions.Commissions;
import com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.profile.restaurantprofile.RestaurantProfile;
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
import com.example.sofraapp.app.data.model.restaurant.order.rejectorder.RejectOrder;

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

    @POST("client/register")
    @FormUrlEncoded
    Call<Register> getRegister(@Field("name") String name, @Field("email") String email,
                               @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                               @Field("phone") String number, @Field("address") String address, @Field("region_id") int region_id);

    @POST("client/profile")
    @FormUrlEncoded
    Call<EditProfile> getProfileAndEdit(@Field("api_token") String api_token,
                                        @Field("name") String name,
                                        @Field("phone") String number,
                                        @Field("email") String email,
                                        @Field("password") String password,
                                        @Field("password_confirmation") String password_confirmation,
                                        @Field("address") String address,
                                        @Field("region_id") int region_id);

    @POST("client/profile")
    @FormUrlEncoded
    Call<GetUserProfile> getUserProfile(@Field("api_token") String api_token);


    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getResetPassword(@Field("email") String email);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<NewPassword> getNewPassword(@Field("code") String code, @Field("password") String password,
                                     @Field("password_confirmation") String password_confirmation);

    @GET("client/notifications")
    Call<Notifications> getNotifications(@Query("api_token") String api_token);

    //   --------------------- End User  ---------------------------------------


    //   --------------------- Start Order Client  ---------------------------------------

    @GET("client/show-order")
    Call<ShowOrder> getShowOrder(@Query("api_token") String api_token, @Query("order_id") int order_id);

    @GET("client/my-orders")
    Call<MyOrdersAsUser> getMyOrdersAsUser(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);


    @POST("client/new-order")
    @FormUrlEncoded
    Call<NewOrder> NewOrder(@Field("restaurant_id") String restaurant_id, @Field("note") String note,
                            @Field("address") String address
            , @Field("payment_method_id") String payment_method_id
            , @Field("phone") String phone
            , @Field("name") String name
            , @Field("api_token") String api_token
            , @Field("items[0]") String items
            , @Field("quantities[0]") String quantities
            , @Field("notes[0]") String notes);

    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<ConfirmOrder> ConfirmOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);

    @POST("client/decline-order")
    @FormUrlEncoded
    Call<DeclineOrder> declineOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);

    //   --------------------- End Order Client  ---------------------------------------

    //   --------------------- Start Restaurant  ---------------------------------------

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<Login> getLoginRestaurant(@Field("email") String email, @Field("password") String password);

    @Multipart
    @POST("restaurant/register")
    Call<RegisterAsRestaurant> getRegisterAsRestaurant(@Part("name") RequestBody name
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

    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<RestaurantProfile> getProfileAndEditRestaurant(@Field("email") String email
            , @Field("password") String password
            , @Field("password_confirmation") String password_confirmation
            , @Field("name") String name
            , @Field("phone") String phone
            , @Field("address") String address, @Field("region_id") int region_id
            , @Field("categotries[0]") int categotries
            , @Field("delivery_method_id") int delivery_method_id
            , @Field("delivery_cost") int delivery_cost
            , @Field("minimum_charger") int minimum_charger
            , @Field("availability") int availability
            , @Part MultipartBody.Part photo, @Field("api_token") String api_token);

    @GET("restaurant/commissions")
    Call<Commissions> getCommissions(@Query("api_token") String api_token);

    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<ChangeState>changeState(@Field("state") String state, @Field("api_token") String api_token);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.resetpassword.ResetPassword>
    getResetPasswordRestaurant(@Field("email") String email, @Field("password") String password,
                               @Field("password_confirmation") String password_confirmation);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<com.example.sofraapp.app.data.model.restaurant.cycleRestaurant.forgetpassword.newpassword.NewPassword>
    getNewPasswordRestaurant(@Field("code") String code, @Field("password") String password,
                             @Field("password_confirmation") String password_confirmation);


    //   --------------------- End Restaurant  ---------------------------------------


    @Multipart
    @POST("restaurant/new-offer")
    Call<NewOffer> getNewOffer(@Part("description") RequestBody description,
                               @Part("price") RequestBody price,
                               @Part("starting_at") RequestBody starting_at,
                               @Part("name") RequestBody name,
                               @Part MultipartBody.Part photo,
                               @Part("ending_at") RequestBody ending_at,
                               @Part("api_token") RequestBody api_token);

    @GET("categories")
    Call<Categories> getCategories();


    @GET("cities")
    Call<Cities> getCities();

    @GET("regions")
    Call<Regions> getRegions(@Query("city_id") int city_id);


    @GET("offers")
    Call<Offers> getOffers(@Query("page") int page);

    @POST("contact")
    @FormUrlEncoded
    Call<Contact> getContact(@Field("name") String name, @Field("email") String email,
                             @Field("phone") String phone, @Field("type") String type, @Field("content") String content);

    @GET("settings")
    Call<Settings> getSettings();

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
    Call<MyItems> getMyProducts(@Query("api_token") String api_token, @Query("page") int page);

    @Multipart
    @POST("restaurant/new-item")
    Call<NewItem> getAddProduct(@Part("description") RequestBody description,
                                @Part("price") RequestBody price,
                                @Part("preparing_time") RequestBody preparing_time,
                                @Part("name") RequestBody name,
                                @Part MultipartBody.Part photo,
                                @Part("api_token") RequestBody api_token);

    @GET("restaurant/my-orders")
    Call<MyOrders> getMyOrders(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);

    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<AcceptOrder> acceptOrder(@Field("api_token") String api_token, @Field("order_id") String order_id);

    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<RejectOrder> rejectOrder(@Field("api_token") String api_token, @Field("order_id") String order_id);

    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<ConfirmOrder> confirmOrder(@Field("api_token") String api_token, @Field("order_id") String order_id);


    @GET("restaurant/my-offers")
    Call<MyOffers> getMyOffers(@Query("api_token") String api_token, @Query("page") int page);


}




















