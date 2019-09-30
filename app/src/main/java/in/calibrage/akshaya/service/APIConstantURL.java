package in.calibrage.akshaya.service;

public interface APIConstantURL {
    //public static  final  String LOCAL_URL="http://183.82.103.171:9096/api/";
     public static  final  String LOCAL_URL="http://183.82.111.111/3FFarmerAPI/api/";
    String LookUpCategory = "GetActiveLookUp/9";
    String banner ="Banner";
    String Collection ="Collection";
    String Recommede_plots ="Farmer/GetActivePlotsByFarmerCode/";
    String Farmer_ID_CHECK ="Farmer/";
    String Farmer_otp ="Farmer/";
    String payment_history ="Payment/GetVendorLedger";
    String GetEncyclopediaDetails = "Encyclopedia/GetEncyclopediaDetails/";
    String GetRecommendationAges = "GetRecommendationAges";
    String GetActivePlotsByFarmerCode = "Farmer/GetActivePlotsByFarmerCode/";
    String GetLabourDuration = "TypeCdDmt/7";
    String GetLabourServicetype = " TypeCdDmt/6";
    String post_labour = "LabourRequest/AddLabourRequestHeader";
    String Post_Loan = " RequestHeader/AddRequestHeader";
    String labour_amount = "LabourServiceCost/GetLabourServiceCostCalculation";
    String GetUnPayedCollectionsByFarmerCode ="Farmer/GetUnPayedCollectionsByFarmerCode/";
    String GetLabourTermsandConditions ="LabourServiceCost/GetLabourServiceCost/null";
    String GetQuickpayDetails ="QuickPayRequest/GetQuickpayDetails/";
    String GetIssue = "TypeCdDmt/10";
    String post_quickpay = "QuickPayRequest/AddQuickpayRequest";
    String post_visit = "RequestHeader/AddVisitRequest";
    String GetActiveGodowns = "Godown/GetActiveGodowns";
    String GetPaymentsTypeByFarmerCode = "Farmer/GetPaymentsTypeByFarmerCode/";
    String GetBannerByStateCode = "Banner/GetBannerByStateCode/AP";
    String Post_fert = "FertilizerRequest";
    String Getfarmersubsidy= "Farmer/GetPaymentsTypeByFarmerCode/";

//   http://183.82.111.111/3FFarmerAPI/api/LabourRequest/GetLabourTermsandConditions/null


}
