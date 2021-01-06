package in.calibrage.akshaya.models;



        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Getvisit {

    @SerializedName("listResult")
    @Expose
    private List<ListResult> listResult = null;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("affectedRecords")
    @Expose
    private Integer affectedRecords;
    @SerializedName("endUserMessage")
    @Expose
    private String endUserMessage;
    @SerializedName("validationErrors")
    @Expose
    private List<Object> validationErrors = null;
    @SerializedName("exception")
    @Expose
    private Object exception;

    public List<ListResult> getListResult() {
        return listResult;
    }

    public void setListResult(List<ListResult> listResult) {
        this.listResult = listResult;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getAffectedRecords() {
        return affectedRecords;
    }

    public void setAffectedRecords(Integer affectedRecords) {
        this.affectedRecords = affectedRecords;
    }

    public String getEndUserMessage() {
        return endUserMessage;
    }

    public void setEndUserMessage(String endUserMessage) {
        this.endUserMessage = endUserMessage;
    }

    public List<Object> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<Object> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }



public class ListResult {

    @SerializedName("requestCode")
    @Expose
    private String requestCode;
    @SerializedName("farmerName")
    @Expose
    private String farmerName;
    @SerializedName("farmerCode")
    @Expose
    private String farmerCode;
    @SerializedName("plotCode")
    @Expose
    private String plotCode;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("reqCreatedDate")
    @Expose
    private String reqCreatedDate;
    @SerializedName("statusTypeId")
    @Expose
    private Integer statusTypeId;
    @SerializedName("statusType")
    @Expose
    private String statusType;
    @SerializedName("cropMaintainceDate")
    @Expose
    private Object cropMaintainceDate;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("issueType")
    @Expose
    private String issueType;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("isHavingImage")
    @Expose
    private String isHavingImage;
    @SerializedName("isHavingAudio")
    @Expose
    private String isHavingAudio;

    @SerializedName("plotVillage")
    @Expose
    private String plotVillage;
    @SerializedName("palmArea")
    @Expose
    private Double palmArea;

//     "plotVillage": "Chodavaram",
//             "palmArea": 0.58

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getReqCreatedDate() {
        return reqCreatedDate;
    }

    public void setReqCreatedDate(String reqCreatedDate) {
        this.reqCreatedDate = reqCreatedDate;
    }

    public Integer getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(Integer statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Object getCropMaintainceDate() {
        return cropMaintainceDate;
    }

    public void setCropMaintainceDate(Object cropMaintainceDate) {
        this.cropMaintainceDate = cropMaintainceDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getIsHavingImage() {
        return isHavingImage;
    }

    public void setIsHavingImage(String isHavingImage) {
        this.isHavingImage = isHavingImage;
    }

    public String getIsHavingAudio() {
        return isHavingAudio;
    }

    public void setIsHavingAudio(String isHavingAudio) {
        this.isHavingAudio = isHavingAudio;
    }

    public String getPlotVillage() {
        return plotVillage;
    }

    public void setPlotVillage(String plotVillage) {
        this.plotVillage = plotVillage;
    }

    public double getPalmArea() {
        return palmArea;
    }

    public void setPalmArea(double palmArea) {
        this.palmArea = palmArea;
    }
}}