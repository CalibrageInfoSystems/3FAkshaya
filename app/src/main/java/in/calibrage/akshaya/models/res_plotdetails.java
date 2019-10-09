

package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class res_plotdetails {

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

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("farmerCode")
        @Expose
        private String farmerCode;
        @SerializedName("totalPalmArea")
        @Expose
        private Double totalPalmArea;
        @SerializedName("totalPlotArea")
        @Expose
        private Double totalPlotArea;
        @SerializedName("gpsPlotArea")
        @Expose
        private Double gpsPlotArea;
        @SerializedName("addressCode")
        @Expose
        private String addressCode;
        @SerializedName("surveyNumber")
        @Expose
        private String surveyNumber;
        @SerializedName("leftOutArea")
        @Expose
        private Double leftOutArea;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("dateofPlanting")
        @Expose
        private String dateofPlanting;
        @SerializedName("village")
        @Expose
        private String village;
        @SerializedName("mandal")
        @Expose
        private String mandal;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("addressLine1")
        @Expose
        private String addressLine1;
        @SerializedName("addressLine2")
        @Expose
        private Object addressLine2;
        @SerializedName("addressLine3")
        @Expose
        private Object addressLine3;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("address")
        @Expose
        private String address;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFarmerCode() {
            return farmerCode;
        }

        public void setFarmerCode(String farmerCode) {
            this.farmerCode = farmerCode;
        }

        public Double getTotalPalmArea() {
            return totalPalmArea;
        }

        public void setTotalPalmArea(Double totalPalmArea) {
            this.totalPalmArea = totalPalmArea;
        }

        public Double getTotalPlotArea() {
            return totalPlotArea;
        }

        public void setTotalPlotArea(Double totalPlotArea) {
            this.totalPlotArea = totalPlotArea;
        }

        public Double getGpsPlotArea() {
            return gpsPlotArea;
        }

        public void setGpsPlotArea(Double gpsPlotArea) {
            this.gpsPlotArea = gpsPlotArea;
        }

        public String getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(String addressCode) {
            this.addressCode = addressCode;
        }

        public String getSurveyNumber() {
            return surveyNumber;
        }

        public void setSurveyNumber(String surveyNumber) {
            this.surveyNumber = surveyNumber;
        }

        public Double getLeftOutArea() {
            return leftOutArea;
        }

        public void setLeftOutArea(Double leftOutArea) {
            this.leftOutArea = leftOutArea;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getDateofPlanting() {
            return dateofPlanting;
        }

        public void setDateofPlanting(String dateofPlanting) {
            this.dateofPlanting = dateofPlanting;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public Object getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(Object addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public Object getAddressLine3() {
            return addressLine3;
        }

        public void setAddressLine3(Object addressLine3) {
            this.addressLine3 = addressLine3;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }
}