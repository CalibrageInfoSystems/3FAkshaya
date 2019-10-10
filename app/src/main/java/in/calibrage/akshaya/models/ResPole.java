package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResPole {
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
        @SerializedName("requestTypeId")
        @Expose
        private Integer requestTypeId;
        @SerializedName("godownId")
        @Expose
        private Integer godownId;
        @SerializedName("goDownName")
        @Expose
        private String goDownName;
        @SerializedName("pin")
        @Expose
        private Object pin;
        @SerializedName("statusTypeId")
        @Expose
        private Integer statusTypeId;
        @SerializedName("totalCost")
        @Expose
        private Double totalCost;
        @SerializedName("subsidyAmount")
        @Expose
        private Double subsidyAmount;
        @SerializedName("paybleAmount")
        @Expose
        private Double paybleAmount;
        @SerializedName("farmerCode")
        @Expose
        private String farmerCode;
        @SerializedName("farmerName")
        @Expose
        private String farmerName;
        @SerializedName("reqCreatedDate")
        @Expose
        private String reqCreatedDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("paymentModeTypeId")
        @Expose
        private Integer paymentModeTypeId;
        @SerializedName("paymentMode")
        @Expose
        private String paymentMode;
        @SerializedName("headerTotalCost")
        @Expose
        private Object headerTotalCost;
        @SerializedName("fileName")
        @Expose
        private Object fileName;
        @SerializedName("fileLocation")
        @Expose
        private Object fileLocation;
        @SerializedName("fileExtension")
        @Expose
        private Object fileExtension;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("village")
        @Expose
        private String village;
        @SerializedName("mandal")
        @Expose
        private String mandal;
        @SerializedName("updatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("currentNetWeight")
        @Expose
        private Object currentNetWeight;
        @SerializedName("previousNetWeight")
        @Expose
        private Object previousNetWeight;
        @SerializedName("usageAmount")
        @Expose
        private Double usageAmount;
        @SerializedName("maxLimit")
        @Expose
        private Double maxLimit;
        @SerializedName("gstn")
        @Expose
        private String gstn;

        public String getRequestCode() {
            return requestCode;
        }

        public void setRequestCode(String requestCode) {
            this.requestCode = requestCode;
        }

        public Integer getRequestTypeId() {
            return requestTypeId;
        }

        public void setRequestTypeId(Integer requestTypeId) {
            this.requestTypeId = requestTypeId;
        }

        public Integer getGodownId() {
            return godownId;
        }

        public void setGodownId(Integer godownId) {
            this.godownId = godownId;
        }

        public String getGoDownName() {
            return goDownName;
        }

        public void setGoDownName(String goDownName) {
            this.goDownName = goDownName;
        }

        public Object getPin() {
            return pin;
        }

        public void setPin(Object pin) {
            this.pin = pin;
        }

        public Integer getStatusTypeId() {
            return statusTypeId;
        }

        public void setStatusTypeId(Integer statusTypeId) {
            this.statusTypeId = statusTypeId;
        }

        public Double getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(Double totalCost) {
            this.totalCost = totalCost;
        }

        public Double getSubsidyAmount() {
            return subsidyAmount;
        }

        public void setSubsidyAmount(Double subsidyAmount) {
            this.subsidyAmount = subsidyAmount;
        }

        public Double getPaybleAmount() {
            return paybleAmount;
        }

        public void setPaybleAmount(Double paybleAmount) {
            this.paybleAmount = paybleAmount;
        }

        public String getFarmerCode() {
            return farmerCode;
        }

        public void setFarmerCode(String farmerCode) {
            this.farmerCode = farmerCode;
        }

        public String getFarmerName() {
            return farmerName;
        }

        public void setFarmerName(String farmerName) {
            this.farmerName = farmerName;
        }

        public String getReqCreatedDate() {
            return reqCreatedDate;
        }

        public void setReqCreatedDate(String reqCreatedDate) {
            this.reqCreatedDate = reqCreatedDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getPaymentModeTypeId() {
            return paymentModeTypeId;
        }

        public void setPaymentModeTypeId(Integer paymentModeTypeId) {
            this.paymentModeTypeId = paymentModeTypeId;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public Object getHeaderTotalCost() {
            return headerTotalCost;
        }

        public void setHeaderTotalCost(Object headerTotalCost) {
            this.headerTotalCost = headerTotalCost;
        }

        public Object getFileName() {
            return fileName;
        }

        public void setFileName(Object fileName) {
            this.fileName = fileName;
        }

        public Object getFileLocation() {
            return fileLocation;
        }

        public void setFileLocation(Object fileLocation) {
            this.fileLocation = fileLocation;
        }

        public Object getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(Object fileExtension) {
            this.fileExtension = fileExtension;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getMandal() {
            return mandal;
        }

        public void setMandal(String mandal) {
            this.mandal = mandal;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public Object getCurrentNetWeight() {
            return currentNetWeight;
        }

        public void setCurrentNetWeight(Object currentNetWeight) {
            this.currentNetWeight = currentNetWeight;
        }

        public Object getPreviousNetWeight() {
            return previousNetWeight;
        }

        public void setPreviousNetWeight(Object previousNetWeight) {
            this.previousNetWeight = previousNetWeight;
        }

        public Double getUsageAmount() {
            return usageAmount;
        }

        public void setUsageAmount(Double usageAmount) {
            this.usageAmount = usageAmount;
        }

        public Double getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(Double maxLimit) {
            this.maxLimit = maxLimit;
        }

        public String getGstn() {
            return gstn;
        }

        public void setGstn(String gstn) {
            this.gstn = gstn;
        }

    }
}