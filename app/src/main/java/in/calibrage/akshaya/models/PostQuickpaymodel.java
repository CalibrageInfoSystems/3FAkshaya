 package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostQuickpaymodel {

    @SerializedName("farmerCode")
    @Expose
    private String farmerCode;
    @SerializedName("isFarmerRequest")
    @Expose
    private Boolean isFarmerRequest;
    @SerializedName("reqCreatedDate")
    @Expose
    private String reqCreatedDate;
    @SerializedName("cost")
    @Expose
    private Double cost;
    @SerializedName("netWeight")
    @Expose
    private Double netWeight;
    @SerializedName("closingBalance")
    @Expose
    private Double closingBalance;
    @SerializedName("collectionIds")
    @Expose
    private String collectionIds;
    @SerializedName("fileName")
    @Expose
    private Object fileName;
    @SerializedName("fileLocation")
    @Expose
    private String fileLocation;
    @SerializedName("fileExtension")
    @Expose
    private String fileExtension;
    @SerializedName("createdByUserId")
    @Expose
    private Object createdByUserId;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedByUserId")
    @Expose
    private Object updatedByUserId;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;

    public String getFarmerCode() {
        return farmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        this.farmerCode = farmerCode;
    }

    public Boolean getIsFarmerRequest() {
        return isFarmerRequest;
    }

    public void setIsFarmerRequest(Boolean isFarmerRequest) {
        this.isFarmerRequest = isFarmerRequest;
    }

    public String getReqCreatedDate() {
        return reqCreatedDate;
    }

    public void setReqCreatedDate(String reqCreatedDate) {
        this.reqCreatedDate = reqCreatedDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(String collectionIds) {
        this.collectionIds = collectionIds;
    }

    public Object getFileName() {
        return fileName;
    }

    public void setFileName(Object fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Object getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Object createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Object updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}