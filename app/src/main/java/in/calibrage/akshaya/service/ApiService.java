package in.calibrage.akshaya.service;

import com.google.gson.JsonObject;

import in.calibrage.akshaya.models.ActiveGodownsModel;
import in.calibrage.akshaya.models.CollectionResponceModel;
import in.calibrage.akshaya.models.CropResponseModel;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.FarmerResponceModel;
import in.calibrage.akshaya.models.GetAmount;
import in.calibrage.akshaya.models.GetEncyclopediaDetails;
import in.calibrage.akshaya.models.GetIssueModel;
import in.calibrage.akshaya.models.GetquickpayDetailsModel;
import in.calibrage.akshaya.models.LabourDuration;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.models.LabourTermsNCondtionsModel;
import in.calibrage.akshaya.models.Labourservicetype;
import in.calibrage.akshaya.models.LerningsModel;
import in.calibrage.akshaya.models.LoanResponse;
import in.calibrage.akshaya.models.LobourResponse;
import in.calibrage.akshaya.models.PaymentResponseModel;
import in.calibrage.akshaya.models.PaymentsType;
import in.calibrage.akshaya.models.QuickPayModel;
import in.calibrage.akshaya.models.QuickPayResponce;
import in.calibrage.akshaya.models.RecomPlotcodes;
import in.calibrage.akshaya.models.SpinnerModel;
import in.calibrage.akshaya.models.Stand_recom_model;
import in.calibrage.akshaya.models.VisitresponseModel;
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


    @POST(APIConstantURL.post_labour)
    Observable<LobourResponse> postLabour(@Body JsonObject data);

    @POST(APIConstantURL.Post_Loan)
    Observable<LoanResponse> postLoan(@Body JsonObject data);

    @POST(APIConstantURL.labour_amount)
    Observable<GetAmount> postservice_amount(@Body JsonObject data);


    @GET
    Observable<QuickPayModel> getquick(@Url String url);


    @GET
    Observable<LabourTermsNCondtionsModel> getterms(@Url String url);

    @GET
    Observable<GetquickpayDetailsModel> getquickpaydetails(@Url String url);


    @GET
    Observable<LabourRecommendationsModel> getvisitplotdetails(@Url String url);


    @GET
    Observable<GetIssueModel> getIssuestypes(@Url String url);

    @POST(APIConstantURL.post_quickpay)
    Observable<QuickPayResponce> postquickpay(@Body JsonObject data);



    @POST(APIConstantURL.post_visit)
    Observable<VisitresponseModel> postvisit(@Body JsonObject data);

    @GET
    Observable<ActiveGodownsModel> getActiveGodowns(@Url String url);

    @GET
    Observable<PaymentsType> getpaymentModes(@Url String url);

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
