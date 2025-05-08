package com.app.jmd.retrofit;
import com.app.jmd.mode.BalanceStockMainModel;
import com.app.jmd.mode.DataMainModel;
import com.app.jmd.mode.DesignDataModel;
import com.app.jmd.mode.DesignDetailsMainModel;
import com.app.jmd.mode.FabicatorModel;
import com.app.jmd.mode.GetDesignModel;
import com.app.jmd.mode.GetUniqueLotModel;
import com.app.jmd.mode.GetUniqueLotModelsPress;
import com.app.jmd.mode.GoDownModel;
import com.app.jmd.mode.JobWorkStockMainModel;
import com.app.jmd.mode.LoginMainModel;
import com.app.jmd.mode.MasterMainModel;
import com.app.jmd.mode.PendingProductionModel;
import com.app.jmd.mode.SaveFabCustomModel;
import com.app.jmd.mode.SaveFabModel;
import com.app.jmd.mode.SizeDataModel;
import com.app.jmd.mode.SubmitDataProductionModel;
import com.app.jmd.mode.UploadCostSheetModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API_Interface {



    ////////////////////////////for production///////////

    @Headers({"Accept: application/json"})
    @POST("Production/SaveProduction")
    Call<SubmitDataProductionModel> saveDetailsProductions(@Body DataMainModel jsonObject);

    @Headers({"Accept: application/json"})
    @POST("Production/SaveFabricatorIssue")
    Call<SubmitDataProductionModel> saveDetailsFabIssue(@Body SaveFabModel jsonObject);

    @Headers({"Accept: application/json"})
    @POST("Production/SaveFabricatorIssue")
    Call<SubmitDataProductionModel> saveDetailsFabIssueDetails(@Body SaveFabCustomModel jsonObject);

    @Headers({"Accept: application/json"})
    @POST("Production/SavePressIssue")
    Call<SubmitDataProductionModel> saveDetailsPressIssue(@Body SaveFabCustomModel jsonObject);


    ///////////////jmd/////////////////

    @FormUrlEncoded
    @POST("user/userlogin")
    Call<LoginMainModel> getLogin(@Field("appmobileno") String appmobileno,
                                       @Field("apppassword") String apppassword);

    @FormUrlEncoded
    @POST("costsheet/getCostDesign")
    Call<GetDesignModel> getDesignApi(@Field("designcode") String designcode,
                                      @Field("designname") String designname);

    @FormUrlEncoded
    @POST("costsheet/getCostDetail")
    Call<DesignDetailsMainModel> getDesignDetails(@Field("designcode") String designcode);

    @FormUrlEncoded
    @POST("costsheet/UploadCostsheet")
    Call<UploadCostSheetModel> uploadCostSheet(@Field("designcode") String designcode,
                                               @Field("Img") String Img);

    @FormUrlEncoded
    @POST("Production/getParty")
    Call<MasterMainModel> getMasterData(@Field("groupcode") String groupcode,
                                        @Field("partycode") String partycode);
    @FormUrlEncoded
    @POST("Production/GetMasterNameforLotNo")
    Call<MasterMainModel> getMasterDataSpecificData(@Field("lotNo") String lotNo);

    @FormUrlEncoded
    @POST("Production/getDesign")
    Call<DesignDataModel> getDesignData(@Field("designcode") String designcode,
                                        @Field("designname") String designname);

    @FormUrlEncoded
    @POST("Production/getSize")
    Call<SizeDataModel> getSizeData(@Field("sizecode") String sizecode,
                                    @Field("sizename") String sizename);


    @FormUrlEncoded
    @POST("Production/PendingProduction")
    Call<PendingProductionModel> allPendingProduction(@Field("Design") String Design,
                                                      @Field("Manager") String Manager,
                                                      @Field("Master") String Master);

    @FormUrlEncoded
    @POST("Production/GetLotDetailForFabric")
    Call<FabicatorModel> getFabricatorIssue(@Field("LOTNO") String LOTNO,@Field("partycode") String partycode);

    @FormUrlEncoded
    @POST("Production/GetLotDetailForPress")
    Call<FabicatorModel> getPressIssue(@Field("LOTNO") String LOTNO,@Field("partycode") String partycode);


    @FormUrlEncoded
    @POST("Production/GetLotsForFabric")
    Call<GetUniqueLotModel> getFabricatorUniqueLots(@Field("LOTNO") String LOTNO, @Field("partycode") String partycode);

    @FormUrlEncoded
    @POST("Production/GetLotsForPress")
    Call<GetUniqueLotModelsPress> getPressUniqueLots(@Field("LOTNO") String LOTNO, @Field("partycode") String partycode);


    @FormUrlEncoded
    @POST("Production/getGodown")
    Call<GoDownModel> getGoDownData(@Field("godowncode") String godowncode,
                                    @Field("godownname") String godownname);

    @FormUrlEncoded
    @POST("Report/CompanyFinidhedStock")
    Call<BalanceStockMainModel> allBalanceStock(@Field("designcode") String designcode,
                                                @Field("sizecode") String sizecode,
                                                @Field("fromdate") String fromdate,
                                                @Field("todate") String todate);

    @FormUrlEncoded
    @POST("Report/JobworkStock")
    Call<JobWorkStockMainModel> allJobWorkStock(
                                                @Field("groupcode") String groupcode,
                                                @Field("partycode") String partycode,
                                                @Field("designcode") String designcode,
                                                @Field("todate") String todate);










}
