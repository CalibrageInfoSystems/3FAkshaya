package in.calibrage.akshaya.models;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class labour_req_response {

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


    public class LabourDetails {

        @SerializedName("requestCode")
        @Expose
        private String requestCode;
        @SerializedName("requestTypeId")
        @Expose
        private Integer requestTypeId;
        @SerializedName("requestType")
        @Expose
        private String requestType;
        @SerializedName("farmerCode")
        @Expose
        private String farmerCode;
        @SerializedName("farmerName")
        @Expose
        private String farmerName;
        @SerializedName("plotCode")
        @Expose
        private Object plotCode;
        @SerializedName("reqCreatedDate")
        @Expose
        private String reqCreatedDate;
        @SerializedName("statusTypeId")
        @Expose
        private Integer statusTypeId;
        @SerializedName("statusType")
        @Expose
        private String statusType;
        @SerializedName("isFarmerRequest")
        @Expose
        private Boolean isFarmerRequest;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("durationId")
        @Expose
        private Integer durationId;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("frequencyId")
        @Expose
        private Object frequencyId;
        @SerializedName("frequency")
        @Expose
        private Object frequency;
        @SerializedName("leaderId")
        @Expose
        private Object leaderId;
        @SerializedName("leader")
        @Expose
        private Object leader;
        @SerializedName("pin")
        @Expose
        private Integer pin;
        @SerializedName("jobDoneDate")
        @Expose
        private Object jobDoneDate;
        @SerializedName("createdByUserId")
        @Expose
        private Object createdByUserId;
        @SerializedName("createdBy")
        @Expose
        private Object createdBy;
        @SerializedName("createdDate")
        @Expose
        private Object createdDate;
        @SerializedName("updatedByUserId")
        @Expose
        private Object updatedByUserId;
        @SerializedName("updatedBy")
        @Expose
        private Object updatedBy;
        @SerializedName("updatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("netWeight")
        @Expose
        private String netWeight;
        @SerializedName("collectionIds")
        @Expose
        private Object collectionIds;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("harvestingAmount")
        @Expose
        private Double harvestingAmount;
        @SerializedName("pruningAmount")
        @Expose
        private Double pruningAmount;
        @SerializedName("unKnown1Amount")
        @Expose
        private Double unKnown1Amount;
        @SerializedName("unKnown2Amount")
        @Expose
        private Double unKnown2Amount;
        @SerializedName("treesCount")
        @Expose
        private Object treesCount;
        @SerializedName("serviceTypeIds")
        @Expose
        private String serviceTypeIds;
        @SerializedName("serviceTypes")
        @Expose
        private String serviceTypes;
        @SerializedName("plotDetails")
        @Expose
        private Object plotDetails;

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

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
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

        public Object getPlotCode() {
            return plotCode;
        }

        public void setPlotCode(Object plotCode) {
            this.plotCode = plotCode;
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

        public Boolean getIsFarmerRequest() {
            return isFarmerRequest;
        }

        public void setIsFarmerRequest(Boolean isFarmerRequest) {
            this.isFarmerRequest = isFarmerRequest;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public Integer getDurationId() {
            return durationId;
        }

        public void setDurationId(Integer durationId) {
            this.durationId = durationId;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public Object getFrequencyId() {
            return frequencyId;
        }

        public void setFrequencyId(Object frequencyId) {
            this.frequencyId = frequencyId;
        }

        public Object getFrequency() {
            return frequency;
        }

        public void setFrequency(Object frequency) {
            this.frequency = frequency;
        }

        public Object getLeaderId() {
            return leaderId;
        }

        public void setLeaderId(Object leaderId) {
            this.leaderId = leaderId;
        }

        public Object getLeader() {
            return leader;
        }

        public void setLeader(Object leader) {
            this.leader = leader;
        }

        public Integer getPin() {
            return pin;
        }

        public void setPin(Integer pin) {
            this.pin = pin;
        }

        public Object getJobDoneDate() {
            return jobDoneDate;
        }

        public void setJobDoneDate(Object jobDoneDate) {
            this.jobDoneDate = jobDoneDate;
        }

        public Object getCreatedByUserId() {
            return createdByUserId;
        }

        public void setCreatedByUserId(Object createdByUserId) {
            this.createdByUserId = createdByUserId;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getUpdatedByUserId() {
            return updatedByUserId;
        }

        public void setUpdatedByUserId(Object updatedByUserId) {
            this.updatedByUserId = updatedByUserId;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(String netWeight) {
            this.netWeight = netWeight;
        }

        public Object getCollectionIds() {
            return collectionIds;
        }

        public void setCollectionIds(Object collectionIds) {
            this.collectionIds = collectionIds;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public Double getHarvestingAmount() {
            return harvestingAmount;
        }

        public void setHarvestingAmount(Double harvestingAmount) {
            this.harvestingAmount = harvestingAmount;
        }

        public Double getPruningAmount() {
            return pruningAmount;
        }

        public void setPruningAmount(Double pruningAmount) {
            this.pruningAmount = pruningAmount;
        }

        public Double getUnKnown1Amount() {
            return unKnown1Amount;
        }

        public void setUnKnown1Amount(Double unKnown1Amount) {
            this.unKnown1Amount = unKnown1Amount;
        }

        public Double getUnKnown2Amount() {
            return unKnown2Amount;
        }

        public void setUnKnown2Amount(Double unKnown2Amount) {
            this.unKnown2Amount = unKnown2Amount;
        }

        public Object getTreesCount() {
            return treesCount;
        }

        public void setTreesCount(Object treesCount) {
            this.treesCount = treesCount;
        }

        public String getServiceTypeIds() {
            return serviceTypeIds;
        }

        public void setServiceTypeIds(String serviceTypeIds) {
            this.serviceTypeIds = serviceTypeIds;
        }

        public String getServiceTypes() {
            return serviceTypes;
        }

        public void setServiceTypes(String serviceTypes) {
            this.serviceTypes = serviceTypes;
        }

        public Object getPlotDetails() {
            return plotDetails;
        }

        public void setPlotDetails(Object plotDetails) {
            this.plotDetails = plotDetails;
        }

    }


    public class ListResult {

        @SerializedName("labourDetails")
        @Expose
        private LabourDetails labourDetails;
        @SerializedName("plotDetails")
        @Expose
        private PlotDetails plotDetails;

        public LabourDetails getLabourDetails() {
            return labourDetails;
        }

        public void setLabourDetails(LabourDetails labourDetails) {
            this.labourDetails = labourDetails;
        }

        public PlotDetails getPlotDetails() {
            return plotDetails;
        }

        public void setPlotDetails(PlotDetails plotDetails) {
            this.plotDetails = plotDetails;
        }


        public class PlotDetails {

            @SerializedName("plotCode")
            @Expose
            private String plotCode;
            @SerializedName("landMark")
            @Expose
            private String landMark;
            @SerializedName("plotSize")
            @Expose
            private Double plotSize;
            @SerializedName("fullAddress")
            @Expose
            private String fullAddress;
            @SerializedName("plotVillageId")
            @Expose
            private Integer plotVillageId;
            @SerializedName("plotVillageName")
            @Expose
            private String plotVillageName;
            @SerializedName("plotMandalId")
            @Expose
            private Integer plotMandalId;
            @SerializedName("plotMandalName")
            @Expose
            private String plotMandalName;
            @SerializedName("plotDistrictId")
            @Expose
            private Integer plotDistrictId;
            @SerializedName("plotDistrictName")
            @Expose
            private String plotDistrictName;
            @SerializedName("plotStateId")
            @Expose
            private Integer plotStateId;
            @SerializedName("plotStateName")
            @Expose
            private String plotStateName;
            @SerializedName("plotCountryId")
            @Expose
            private Integer plotCountryId;
            @SerializedName("plotCountryName")
            @Expose
            private String plotCountryName;

            public String getPlotCode() {
                return plotCode;
            }

            public void setPlotCode(String plotCode) {
                this.plotCode = plotCode;
            }

            public String getLandMark() {
                return landMark;
            }

            public void setLandMark(String landMark) {
                this.landMark = landMark;
            }

            public Double getPlotSize() {
                return plotSize;
            }

            public void setPlotSize(Double plotSize) {
                this.plotSize = plotSize;
            }

            public String getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
            }

            public Integer getPlotVillageId() {
                return plotVillageId;
            }

            public void setPlotVillageId(Integer plotVillageId) {
                this.plotVillageId = plotVillageId;
            }

            public String getPlotVillageName() {
                return plotVillageName;
            }

            public void setPlotVillageName(String plotVillageName) {
                this.plotVillageName = plotVillageName;
            }

            public Integer getPlotMandalId() {
                return plotMandalId;
            }

            public void setPlotMandalId(Integer plotMandalId) {
                this.plotMandalId = plotMandalId;
            }

            public String getPlotMandalName() {
                return plotMandalName;
            }

            public void setPlotMandalName(String plotMandalName) {
                this.plotMandalName = plotMandalName;
            }

            public Integer getPlotDistrictId() {
                return plotDistrictId;
            }

            public void setPlotDistrictId(Integer plotDistrictId) {
                this.plotDistrictId = plotDistrictId;
            }

            public String getPlotDistrictName() {
                return plotDistrictName;
            }

            public void setPlotDistrictName(String plotDistrictName) {
                this.plotDistrictName = plotDistrictName;
            }

            public Integer getPlotStateId() {
                return plotStateId;
            }

            public void setPlotStateId(Integer plotStateId) {
                this.plotStateId = plotStateId;
            }

            public String getPlotStateName() {
                return plotStateName;
            }

            public void setPlotStateName(String plotStateName) {
                this.plotStateName = plotStateName;
            }

            public Integer getPlotCountryId() {
                return plotCountryId;
            }

            public void setPlotCountryId(Integer plotCountryId) {
                this.plotCountryId = plotCountryId;
            }

            public String getPlotCountryName() {
                return plotCountryName;
            }

            public void setPlotCountryName(String plotCountryName) {
                this.plotCountryName = plotCountryName;
            }

        }
    }
}