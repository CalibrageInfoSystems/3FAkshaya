
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
    private Integer createdByUserId;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedByUserId")
    @Expose
    private Integer updatedByUserId;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("harvestingAmount")
    @Expose
    private Double harvestingAmount;
    @SerializedName("pruningAmount")
    @Expose
    private Double pruningAmount;
    @SerializedName("unKnown1Amount")
    @Expose
    private Double unKnown1Amount;
    @SerializedName("unKnown2Amount")
    @Expose
    private Double unKnown2Amount;

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

    public Boolean getIsFarmerRequest() {
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

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Integer updatedByUserId) {
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

    public Double getHarvestingAmount() {
        return harvestingAmount;
    }

    public void setHarvestingAmount(Double harvestingAmount) {
        this.harvestingAmount = harvestingAmount;
    }

    public Double getPruningAmount() {
        return pruningAmount;
    }

    public void setPruningAmount(Double pruningAmount) {
        this.pruningAmount = pruningAmount;
    }

    public Double getUnKnown1Amount() {
        return unKnown1Amount;
    }

    public void setUnKnown1Amount(Double unKnown1Amount) {
        this.unKnown1Amount = unKnown1Amount;
    }

    public Double getUnKnown2Amount() {
        return unKnown2Amount;
    }

    public void setUnKnown2Amount(Double unKnown2Amount) {
        this.unKnown2Amount = unKnown2Amount;
    }

}