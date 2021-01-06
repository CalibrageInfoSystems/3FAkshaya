

package in.calibrage.akshaya.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resquickpay {

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
        @SerializedName("requestType")
        @Expose
        private String requestType;
        @SerializedName("stateName")
        @Expose
        private String stateName;
        @SerializedName("clusterCategory")
        @Expose
        private Object clusterCategory;
        @SerializedName("reqCreatedDate")
        @Expose
        private String reqCreatedDate;
        @SerializedName("statusTypeId")
        @Expose
        private Integer statusTypeId;
        @SerializedName("statusType")
        @Expose
        private String statusType;
        @SerializedName("approvedDate")
        @Expose
        private String approvedDate;
        @SerializedName("totalCost")
        @Expose
        private Double totalCost;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("comments")
        @Expose
        private Object comments;
        @SerializedName("weighbridgeSlipVerified")
        @Expose
        private Boolean weighbridgeSlipVerified;
        @SerializedName("requestsVerified")
        @Expose
        private Boolean requestsVerified;
        @SerializedName("duesVerified")
        @Expose
        private Boolean duesVerified;
        @SerializedName("updatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("netWeight")
        @Expose
        private Double netWeight;
        @SerializedName("stateCode")
        @Expose
        private String stateCode;

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

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public Object getClusterCategory() {
            return clusterCategory;
        }

        public void setClusterCategory(Object clusterCategory) {
            this.clusterCategory = clusterCategory;
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

        public String getApprovedDate() {
            return approvedDate;
        }

        public void setApprovedDate(String approvedDate) {
            this.approvedDate = approvedDate;
        }

        public Double getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(Double totalCost) {
            this.totalCost = totalCost;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public Object getComments() {
            return comments;
        }

        public void setComments(Object comments) {
            this.comments = comments;
        }

        public Boolean getWeighbridgeSlipVerified() {
            return weighbridgeSlipVerified;
        }

        public void setWeighbridgeSlipVerified(Boolean weighbridgeSlipVerified) {
            this.weighbridgeSlipVerified = weighbridgeSlipVerified;
        }

        public Boolean getRequestsVerified() {
            return requestsVerified;
        }

        public void setRequestsVerified(Boolean requestsVerified) {
            this.requestsVerified = requestsVerified;
        }

        public Boolean getDuesVerified() {
            return duesVerified;
        }

        public void setDuesVerified(Boolean duesVerified) {
            this.duesVerified = duesVerified;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public Double getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(Double netWeight) {
            this.netWeight = netWeight;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }}}