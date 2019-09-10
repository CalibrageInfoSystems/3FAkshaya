package in.calibrage.akshaya.service;

import com.google.gson.JsonObject;

import in.calibrage.akshaya.models.CollectionResponceModel;
import in.calibrage.akshaya.models.LerningsModel;
import in.calibrage.akshaya.models.collectionRequestModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {



    @GET
    Observable<LerningsModel> getlernings(@Url String url);


    @POST(APIConstantURL.Collection)
    Observable<CollectionResponceModel> postcollection(@Body JsonObject data);

 /*
    // PASSWORD RESET
    @PUT(APIConstantURL.PASSWORDRESET)
    Observable<String> putpassword(@Body JsonObject data);


    // Login
    @POST(APIConstantURL.RegisterAccount)
    Observable<String> postRegisterAccount(@Body JsonObject data);

    *//*
     * GetAccountOwnerDetails
     * *//*
    @GET
    Observable<GetAccountOwnerDetailsAPIResponse> GetAccountOwnerDetails(@Url String url, @Header("Authorization") String Authorization);

    *//*
     * GetAllStates
     * *//*
    @GET
    Observable<ArrayList<GetAllStatesAPIResponse>> GetAllStates(@Url String url, @Header("Authorization") String Authorization);
*/
}
