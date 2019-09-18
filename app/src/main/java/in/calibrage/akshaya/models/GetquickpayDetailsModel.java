package in.calibrage.akshaya.models;


        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class GetquickpayDetailsModel {

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

        @SerializedName("quantity")
        @Expose
        private Double quantity;
        @SerializedName("ffbFlatCharge")
        @Expose
        private Object ffbFlatCharge;
        @SerializedName("ffbCost")
        @Expose
        private Double ffbCost;
        @SerializedName("convenienceCharge")
        @Expose
        private Object convenienceCharge;
        @SerializedName("closingBalance")
        @Expose
        private Integer closingBalance;
        @SerializedName("quickPay")
        @Expose
        private Double quickPay;
        @SerializedName("total")
        @Expose
        private Object total;

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public Object getFfbFlatCharge() {
            return ffbFlatCharge;
        }

        public void setFfbFlatCharge(Object ffbFlatCharge) {
            this.ffbFlatCharge = ffbFlatCharge;
        }

        public Double getFfbCost() {
            return ffbCost;
        }

        public void setFfbCost(Double ffbCost) {
            this.ffbCost = ffbCost;
        }

        public Object getConvenienceCharge() {
            return convenienceCharge;
        }

        public void setConvenienceCharge(Object convenienceCharge) {
            this.convenienceCharge = convenienceCharge;
        }

        public Integer getClosingBalance() {
            return closingBalance;
        }

        public void setClosingBalance(Integer closingBalance) {
            this.closingBalance = closingBalance;
        }

        public Double getQuickPay() {
            return quickPay;
        }

        public void setQuickPay(Double quickPay) {
            this.quickPay = quickPay;
        }

        public Object getTotal() {
            return total;
        }

        public void setTotal(Object total) {
            this.total = total;
        }

    }
}