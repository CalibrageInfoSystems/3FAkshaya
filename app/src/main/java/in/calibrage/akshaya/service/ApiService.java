package in.calibrage.akshaya.service;

import com.google.gson.JsonObject;

import in.calibrage.akshaya.models.CollectionResponceModel;
import in.calibrage.akshaya.models.CropResponseModel;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.FarmerResponceModel;
import in.calibrage.akshaya.models.GetEncyclopediaDetails;
import in.calibrage.akshaya.models.LabourDuration;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.models.Labourservicetype;
import in.calibrage.akshaya.models.LerningsModel;
import in.calibrage.akshaya.models.LobourResponse;
import in.calibrage.akshaya.models.PaymentResponseModel;
import in.calibrage.akshaya.models.RecomPlotcodes;
import in.calibrage.akshaya.models.SpinnerModel;
import in.calibrage.akshaya.models.Stand_recom_model;
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


    @GET
    Observable<RecomPlotcodes> getplots(@Url String url);

    @GET
    Observable<FarmerResponceModel> getFormerOTP(@Url String url);

    @GET
    Observable<FarmerOtpResponceModel> getFormerdetails(@Url String url);

    @GET
    Observable<CropResponseModel> getCropmaintaindetails(@Url String url);


    @POST(APIConstantURL.payment_history)
    Observable<PaymentResponseModel> postpayment(@Body JsonObject data);


    @GET
    Observable<GetEncyclopediaDetails> getEncyclopediaDetails(@Url String url);

    @GET
    Observable<SpinnerModel> getSpinnerDetails(@Url String url);

    @GET
    Observable<Stand_recom_model> GetRecommendationdetails(@Url String url);


    @GET
    Observable<LabourRecommendationsModel> getrecommdetails(@Url String url);

    @GET
    Observable<LabourDuration> getLabourduration(@Url String url);

    @GET
    Observable<Labourservicetype> getLabourService(@Url String url);


    @POST(APIConstantURL.Collection)
    Observable<LobourResponse> postLabour(@Body JsonObject data);



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
