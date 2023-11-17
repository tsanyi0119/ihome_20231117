package com.example.smarthomeandroid.utils.api;

import androidx.annotation.Nullable;

import com.example.smarthomeandroid.utils.api.soap.request.DeviceAddRequest;
import com.example.smarthomeandroid.utils.api.soap.request.DeviceDeleteRequest;
import com.example.smarthomeandroid.utils.api.soap.request.DeviceRenewRequest;
import com.example.smarthomeandroid.utils.api.soap.request.ForgotPasswordRequest;
import com.example.smarthomeandroid.utils.api.soap.request.HomeAddRequest;
import com.example.smarthomeandroid.utils.api.soap.request.HomeReNewRequest;
import com.example.smarthomeandroid.utils.api.soap.request.LoginRequest;
import com.example.smarthomeandroid.utils.api.soap.request.PhotoRequest;
import com.example.smarthomeandroid.utils.api.soap.request.RegisterRequest;
import com.example.smarthomeandroid.utils.api.soap.request.RegisterVerifyRequest;
import com.example.smarthomeandroid.utils.api.soap.request.ResetPasswordRequest;
import com.example.smarthomeandroid.utils.api.soap.request.RoomAddRequest;
import com.example.smarthomeandroid.utils.api.soap.request.RoomDeleteRequest;
import com.example.smarthomeandroid.utils.api.soap.request.RoomRenewRequest;
import com.example.smarthomeandroid.utils.api.soap.response.DeviceQtyResponse;
import com.example.smarthomeandroid.utils.api.soap.response.DeviceResponse;
import com.example.smarthomeandroid.utils.api.soap.response.HomeResponse;
import com.example.smarthomeandroid.utils.api.soap.response.LoginResponse;
import com.example.smarthomeandroid.utils.api.soap.response.PhotoResponse;
import com.example.smarthomeandroid.utils.api.soap.response.RegisterResponse;
import com.example.smarthomeandroid.utils.api.soap.response.RegisterVerifyResponse;
import com.example.smarthomeandroid.utils.api.soap.response.RoomResponse;
import com.example.smarthomeandroid.utils.api.soap.response.StatusResponse;
import com.example.smarthomeandroid.utils.api.soap.response.WeatherResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * Authentication
     * */
    @POST("auth/register")
    Observable<Response<RegisterResponse>> register(@Body RegisterRequest registerRequest);

    @POST("auth/verification/check")
    Observable<Response<RegisterVerifyResponse>> registerVerify(@Body RegisterVerifyRequest registerVerifyRequest);

    @POST("auth/login")
    Observable<Response<LoginResponse>> login(@Body LoginRequest loginRequest);

    @POST("auth/logout")
    Observable<Response<LoginResponse>> logout();

    @POST("auth/password/forgot")
    Observable<Response<StatusResponse>>  forgot(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("auth/password/reset")
    Observable<Response<StatusResponse>>  resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @POST("auth/refresh-token")
    Observable<Response<LoginResponse>> refreshToken();


    /**
     * SmartHome
     */
    @GET("home/data")
    Observable<Response<HomeResponse>> homeData();

    @POST("home/add")
    Observable<Response<StatusResponse>> addHome(@Body HomeAddRequest addHomeRequest);

    @PATCH("home/reset")
    Observable<Response<StatusResponse>> renewHome(@Body HomeReNewRequest homeReNewRequest);

    @DELETE("home/delete")
    Observable<Response<StatusResponse>> deleteHome(@Query("homeId") Long homeId);


    @GET("room/data")
    Observable<Response<RoomResponse>> roomData(@Query("homeId") Long homeId);

    @POST("room/add")
    Observable<Response<StatusResponse>> addRoom(@Body RoomAddRequest roomAddRequest);

    @PATCH("room/reset")
    Observable<Response<StatusResponse>> renewRoom(@Body RoomRenewRequest roomRenewRequest);

    @DELETE("room/delete")
    Observable<Response<StatusResponse>> deleteRoom(@Body RoomDeleteRequest roomDeleteRequest);


    @GET("device/qty")
    Observable<Response<DeviceQtyResponse>> qtyDevice(@Query("roomId") Long roomId);

    @GET("device/data")
    Observable<Response<DeviceResponse>> deviceData(@Query("roomId") Long roomId, @Query("deviceType")@Nullable String type);

    @POST("device/add")
    Observable<Response<StatusResponse>> addDevice(@Body DeviceAddRequest deviceAddRequest);

    @PATCH("device/reset")
    Observable<Response<StatusResponse>> renewDevice(@Body DeviceRenewRequest deviceRenewRequest);

    @DELETE("device/delete")
    Observable<Response<StatusResponse>> deleteDevice(@Body DeviceDeleteRequest deviceDeleteRequest);


    @POST("photo")
    Observable<Response<StatusResponse>> uploadPhoto(@Body PhotoRequest photo);

    @GET("photo")
    Observable<Response<PhotoResponse>> photoBase64();

    @GET("monitor/videos/name")
    Observable<Response<List<String>>> videoName();

    @GET("monitor/videos/{fileName}")
    Observable<ResponseBody> videoData(@Path("fileName")String fileName);


    /**
     * Weather
     * */
    @GET("F-D0047-093")
    Observable<Response<WeatherResponse>> weather(@Query("Authorization") String token,
                                                  @Query("locationId") String locationId,
                                                  @Query("locationName")String locationName);
}
