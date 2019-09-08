package in.calibrage.akshaya.service;

import java.util.ArrayList;

import in.calibrage.akshaya.models.LerningsModel;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class ApiService {



  /*  @GET
    Observable<ArrayList<LerningsModel>> GetAccountOwnerDetails(@Url String url);
*/


   /* @POST(APIConstantURL.LOGINPage)
    Observable<LoginPostAPIResponse> postLogin(@Body JsonObject data);


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
