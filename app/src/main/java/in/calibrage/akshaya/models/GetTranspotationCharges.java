package in.calibrage.akshaya.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTranspotationCharges {
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

        @SerializedName("collectionCode")
        @Expose
        private String collectionCode;
        @SerializedName("farmerCode")
        @Expose
        private String farmerCode;
        @SerializedName("farmerName")
        @Expose
        private String farmerName;
        @SerializedName("village")
        @Expose
        private String village;
        @SerializedName("mandal")
        @Expose
        private String mandal;
        @SerializedName("rate")
        @Expose
        private int rate;
        @SerializedName("qty")
        @Expose
        private Double qty;
        @SerializedName("trpt")
        @Expose
        private Double trpt;
        @SerializedName("receiptGeneratedDate")
        @Expose
        private String receiptGeneratedDate;
        @SerializedName("status")
        @Expose
        private Object status;

        public String getCollectionCode() {
            return collectionCode;
        }

        public void setCollectionCode(String collectionCode) {
            this.collectionCode = collectionCode;
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

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public Double getQty() {
            return qty;
        }

        public void setQty(Double qty) {
            this.qty = qty;
        }

        public Double getTrpt() {
            return trpt;
        }

        public void setTrpt(Double trpt) {
            this.trpt = trpt;
        }

        public String getReceiptGeneratedDate() {
            return receiptGeneratedDate;
        }

        public void setReceiptGeneratedDate(String receiptGeneratedDate) {
            this.receiptGeneratedDate = receiptGeneratedDate;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

    }
}
