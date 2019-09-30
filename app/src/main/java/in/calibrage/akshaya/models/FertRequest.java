package in.calibrage.akshaya.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FertRequest
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("requestTypeId")
    @Expose
    private Integer requestTypeId;
    @SerializedName("farmerCode")
    @Expose
    private String farmerCode;
    @SerializedName("plotCode")
    @Expose
    private Object plotCode;
    @SerializedName("requestCreatedDate")
    @Expose
    private String requestCreatedDate;
    @SerializedName("statusTypeId")
    @Expose
    private Integer statusTypeId;
    @SerializedName("isFarmerRequest")
    @Expose
    private Boolean isFarmerRequest;
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
    @SerializedName("godownId")
    @Expose
    private Integer godownId;
    @SerializedName("paymentModeType")
    @Expose
    private Integer paymentModeType;
    @SerializedName("fileName")
    @Expose
    private Object fileName;
    @SerializedName("fileLocation")
    @Expose
    private Object fileLocation;
    @SerializedName("fileExtension")
    @Expose
    private Object fileExtension;
    @SerializedName("totalCost")
    @Expose
    private Double totalCost;
    @SerializedName("subcidyAmount")
    @Expose
    private Double subcidyAmount;
    @SerializedName("paybleAmount")
    @Expose
    private Double paybleAmount;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("cropMaintainceDate")
    @Expose
    private String cropMaintainceDate;
    @SerializedName("issueTypeId")
    @Expose
    private Integer issueTypeId;
    @SerializedName("requestProductDetails")
    @Expose
    private List<RequestProductDetail> requestProductDetails = null;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(Integer requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    public String getFarmerCode() {
        return farmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        this.farmerCode = farmerCode;
    }

    public Object getPlotCode() {
        return plotCode;
    }

    public void setPlotCode(Object plotCode) {
        this.plotCode = plotCode;
    }

    public String getRequestCreatedDate() {
        return requestCreatedDate;
    }

    public void setRequestCreatedDate(String requestCreatedDate) {
        this.requestCreatedDate = requestCreatedDate;
    }

    public Integer getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(Integer statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public Boolean getIsFarmerRequest() {
        return isFarmerRequest;
    }

    public void setIsFarmerRequest(Boolean isFarmerRequest) {
        this.isFarmerRequest = isFarmerRequest;
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

    public Integer getGodownId() {
        return godownId;
    }

    public void setGodownId(Integer godownId) {
        this.godownId = godownId;
    }

    public Integer getPaymentModeType() {
        return paymentModeType;
    }

    public void setPaymentModeType(Integer paymentModeType) {
        this.paymentModeType = paymentModeType;
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

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getSubcidyAmount() {
        return subcidyAmount;
    }

    public void setSubcidyAmount(Double subcidyAmount) {
        this.subcidyAmount = subcidyAmount;
    }

    public Double getPaybleAmount() {
        return paybleAmount;
    }

    public void setPaybleAmount(Double paybleAmount) {
        this.paybleAmount = paybleAmount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCropMaintainceDate() {
        return cropMaintainceDate;
    }

    public void setCropMaintainceDate(String cropMaintainceDate) {
        this.cropMaintainceDate = cropMaintainceDate;
    }

    public Integer getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Integer issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public List<RequestProductDetail> getRequestProductDetails() {
        return requestProductDetails;
    }

    public void setRequestProductDetails(List<RequestProductDetail> requestProductDetails) {
        this.requestProductDetails = requestProductDetails;
    }


    public static class RequestProductDetail {

        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("bagCost")
        @Expose
        private Double bagCost;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("gstPersentage")
        @Expose
        private Double gstPersentage;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getBagCost() {
            return bagCost;
        }

        public void setBagCost(Double bagCost) {
            this.bagCost = bagCost;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Double getGstPersentage() {
            return gstPersentage;
        }

        public void setGstPersentage(Double gstPersentage) {
            this.gstPersentage = gstPersentage;
        }


    }
}