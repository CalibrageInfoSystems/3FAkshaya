package in.calibrage.akshaya.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabourTermsNCondtionsModel {

    @SerializedName("result")
    @Expose
    private Result result;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
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


    public class Pruning {

        @SerializedName("serviceTypeId")
        @Expose
        private Integer serviceTypeId;
        @SerializedName("statusType")
        @Expose
        private String statusType;
        @SerializedName("measurement")
        @Expose
        private String measurement;
        @SerializedName("price")
        @Expose
        private Double price;

        public Integer getServiceTypeId() {
            return serviceTypeId;
        }

        public void setServiceTypeId(Integer serviceTypeId) {
            this.serviceTypeId = serviceTypeId;
        }

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }

        public String getMeasurement() {
            return measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

    }

    public class Harvesting {

        @SerializedName("serviceTypeId")
        @Expose
        private Integer serviceTypeId;
        @SerializedName("statusType")
        @Expose
        private String statusType;
        @SerializedName("measurement")
        @Expose
        private String measurement;
        @SerializedName("price")
        @Expose
        private Double price;

        public Integer getServiceTypeId() {
            return serviceTypeId;
        }

        public void setServiceTypeId(Integer serviceTypeId) {
            this.serviceTypeId = serviceTypeId;
        }

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }

        public String getMeasurement() {
            return measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

    }


    public class Result {

        @SerializedName("pruning")
        @Expose
        private List<Pruning> pruning = null;
        @SerializedName("harvesting")
        @Expose
        private List<Harvesting> harvesting = null;
        @SerializedName("unknown1")
        @Expose
        private List<Object> unknown1 = null;
        @SerializedName("unknown2")
        @Expose
        private List<Object> unknown2 = null;

        public List<Pruning> getPruning() {
            return pruning;
        }

        public void setPruning(List<Pruning> pruning) {
            this.pruning = pruning;
        }

        public List<Harvesting> getHarvesting() {
            return harvesting;
        }

        public void setHarvesting(List<Harvesting> harvesting) {
            this.harvesting = harvesting;
        }

        public List<Object> getUnknown1() {
            return unknown1;
        }

        public void setUnknown1(List<Object> unknown1) {
            this.unknown1 = unknown1;
        }

        public List<Object> getUnknown2() {
            return unknown2;
        }

        public void setUnknown2(List<Object> unknown2) {
            this.unknown2 = unknown2;
        }

    }
}