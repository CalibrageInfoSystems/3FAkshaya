

        package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



        import java.util.List;

public class Resproduct {

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
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("farmerCode")
        @Expose
        private String farmerCode;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("gstPercentage")
        @Expose
        private int gstPercentage;
        @SerializedName("cgstPercentage")
        @Expose
        private double cgstPercentage;
        @SerializedName("sgstPercentage")
        @Expose
        private double sgstPercentage;
        @SerializedName("cgst")
        @Expose
        private Double cgst;
        @SerializedName("sgst")
        @Expose
        private Double sgst;
        @SerializedName("totalAmount")
        @Expose
        private Double totalAmount;

        public String getRequestCode() {
            return requestCode;
        }

        public void setRequestCode(String requestCode) {
            this.requestCode = requestCode;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getFarmerCode() {
            return farmerCode;
        }

        public void setFarmerCode(String farmerCode) {
            this.farmerCode = farmerCode;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public int getGstPercentage() {
            return gstPercentage;
        }

        public void setGstPercentage(int gstPercentage) {
            this.gstPercentage = gstPercentage;
        }

        public Double getCgst() {
            return cgst;
        }

        public void setCgst(Double cgst) {
            this.cgst = cgst;
        }

        public Double getSgst() {
            return sgst;
        }

        public void setSgst(Double sgst) {
            this.sgst = sgst;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getCgstPercentage() {
            return cgstPercentage;
        }

        public void setCgstPercentage(double cgstPercentage) {
            this.cgstPercentage = cgstPercentage;
        }

        public double getSgstPercentage() {
            return sgstPercentage;
        }

        public void setSgstPercentage(double sgstPercentage) {
            this.sgstPercentage = sgstPercentage;
        }
    }

}