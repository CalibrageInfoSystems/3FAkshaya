package in.calibrage.akshaya.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddLabourRequestHeader {

    @SerializedName("farmerCode")
    @Expose
    private String farmerCode;
    @SerializedName("plotCode")
    @Expose
    private String plotCode;
    @SerializedName("isFarmerRequest")
    @Expose
    private Boolean isFarmerRequest;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("preferredDate")
    @Expose
    private String preferredDate;
    @SerializedName("durationId")
    @Expose
    private Integer durationId;
    @SerializedName("plotVillage")
    @Expose
    private String plotVillage;
    @SerializedName("plotMandal")
    @Expose
    private String plotMandal;
    @SerializedName("plotDistrict")
    @Expose
    private String plotDistrict;
    @SerializedName("plotState")
    @Expose
    private String plotState;
    @SerializedName("serviceTypes")
    @Expose
    private String serviceTypes;
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
    @SerializedName("amount")
    @Expose
    private Double amount;

    public String getFarmerCode() {
        return farmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        this.farmerCode = farmerCode;
    }

    public String getPlotCode() {
        return plotCode;
    }

    public void setPlotCode(String plotCode) {
        this.plotCode = plotCode;
    }

    public Boolean getIsFarmerRequest(boolean b) {
        return isFarmerRequest;
    }

    public void setIsFarmerRequest(Boolean isFarmerRequest) {
        this.isFarmerRequest = isFarmerRequest;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(String preferredDate) {
        this.preferredDate = preferredDate;
    }

    public Integer getDurationId() {
        return durationId;
    }

    public void setDurationId(Integer durationId) {
        this.durationId = durationId;
    }

    public String getPlotVillage() {
        return plotVillage;
    }

    public void setPlotVillage(String plotVillage) {
        this.plotVillage = plotVillage;
    }

    public String getPlotMandal() {
        return plotMandal;
    }

    public void setPlotMandal(String plotMandal) {
        this.plotMandal = plotMandal;
    }

    public String getPlotDistrict() {
        return plotDistrict;
    }

    public void setPlotDistrict(String plotDistrict) {
        this.plotDistrict = plotDistrict;
    }

    public String getPlotState() {
        return plotState;
    }

    public void setPlotState(String plotState) {
        this.plotState = plotState;
    }

    public String getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(String serviceTypes) {
        this.serviceTypes = serviceTypes;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}