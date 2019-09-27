package in.calibrage.akshaya.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAmount {

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


    public class Result {

        @SerializedName("harvestCost")
        @Expose
        private Double harvestCost;
        @SerializedName("prunningCost")
        @Expose
        private Double prunningCost;
        @SerializedName("unKnown1Cost")
        @Expose
        private Double unKnown1Cost;
        @SerializedName("unKnown2Cost")
        @Expose
        private Double unKnown2Cost;

        public Double getHarvestCost() {
            return harvestCost;
        }

        public void setHarvestCost(Double harvestCost) {
            this.harvestCost = harvestCost;
        }

        public Double getPrunningCost() {
            return prunningCost;
        }

        public void setPrunningCost(Double prunningCost) {
            this.prunningCost = prunningCost;
        }

        public Double getUnKnown1Cost() {
            return unKnown1Cost;
        }

        public void setUnKnown1Cost(Double unKnown1Cost) {
            this.unKnown1Cost = unKnown1Cost;
        }

        public Double getUnKnown2Cost() {
            return unKnown2Cost;
        }

        public void setUnKnown2Cost(Double unKnown2Cost) {
            this.unKnown2Cost = unKnown2Cost;
        }

    }
}