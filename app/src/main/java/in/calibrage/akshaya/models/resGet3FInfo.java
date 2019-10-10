
package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class resGet3FInfo {

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

        @SerializedName("importantContacts")
        @Expose
        private ImportantContacts importantContacts;
        @SerializedName("importantPlaces")
        @Expose
        private ImportantPlaces importantPlaces;

        public ImportantContacts getImportantContacts() {
            return importantContacts;
        }

        public void setImportantContacts(ImportantContacts importantContacts) {
            this.importantContacts = importantContacts;
        }

        public ImportantPlaces getImportantPlaces() {
            return importantPlaces;
        }

        public void setImportantPlaces(ImportantPlaces importantPlaces) {
            this.importantPlaces = importantPlaces;
        }

    }

    public class CollectionCenter {

        @SerializedName("collectionCenter")
        @Expose
        private String collectionCenter;
        @SerializedName("villageName")
        @Expose
        private String villageName;
        @SerializedName("mandalName")
        @Expose
        private String mandalName;
        @SerializedName("districtName")
        @Expose
        private String districtName;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("isMill")
        @Expose
        private Boolean isMill;

        public String getCollectionCenter() {
            return collectionCenter;
        }

        public void setCollectionCenter(String collectionCenter) {
            this.collectionCenter = collectionCenter;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }

        public String getMandalName() {
            return mandalName;
        }

        public void setMandalName(String mandalName) {
            this.mandalName = mandalName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Boolean getIsMill() {
            return isMill;
        }

        public void setIsMill(Boolean isMill) {
            this.isMill = isMill;
        }

    }


    public class Godown {

        @SerializedName("godown")
        @Expose
        private String godown;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;

        public String getGodown() {
            return godown;
        }

        public void setGodown(String godown) {
            this.godown = godown;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

    }

    public class ImportantContacts {

        @SerializedName("villageId")
        @Expose
        private Integer villageId;
        @SerializedName("clusterId")
        @Expose
        private Integer clusterId;
        @SerializedName("clusterOfficerName")
        @Expose
        private String clusterOfficerName;
        @SerializedName("clusterOfficerContactNumber")
        @Expose
        private String clusterOfficerContactNumber;
        @SerializedName("clusterOfficerManagerName")
        @Expose
        private String clusterOfficerManagerName;
        @SerializedName("stateHeadName")
        @Expose
        private String stateHeadName;

        public Integer getVillageId() {
            return villageId;
        }

        public void setVillageId(Integer villageId) {
            this.villageId = villageId;
        }

        public Integer getClusterId() {
            return clusterId;
        }

        public void setClusterId(Integer clusterId) {
            this.clusterId = clusterId;
        }

        public String getClusterOfficerName() {
            return clusterOfficerName;
        }

        public void setClusterOfficerName(String clusterOfficerName) {
            this.clusterOfficerName = clusterOfficerName;
        }

        public String getClusterOfficerContactNumber() {
            return clusterOfficerContactNumber;
        }

        public void setClusterOfficerContactNumber(String clusterOfficerContactNumber) {
            this.clusterOfficerContactNumber = clusterOfficerContactNumber;
        }

        public String getClusterOfficerManagerName() {
            return clusterOfficerManagerName;
        }

        public void setClusterOfficerManagerName(String clusterOfficerManagerName) {
            this.clusterOfficerManagerName = clusterOfficerManagerName;
        }

        public String getStateHeadName() {
            return stateHeadName;
        }

        public void setStateHeadName(String stateHeadName) {
            this.stateHeadName = stateHeadName;
        }

    }

    public class ImportantPlaces {

        @SerializedName("collectionCenters")
        @Expose
        private List<CollectionCenter> collectionCenters = null;
        @SerializedName("mills")
        @Expose
        private List<Mill> mills = null;
        @SerializedName("godowns")
        @Expose
        private List<Godown> godowns = null;

        public List<CollectionCenter> getCollectionCenters() {
            return collectionCenters;
        }

        public void setCollectionCenters(List<CollectionCenter> collectionCenters) {
            this.collectionCenters = collectionCenters;
        }

        public List<Mill> getMills() {
            return mills;
        }

        public void setMills(List<Mill> mills) {
            this.mills = mills;
        }

        public List<Godown> getGodowns() {
            return godowns;
        }

        public void setGodowns(List<Godown> godowns) {
            this.godowns = godowns;
        }

    }


    public class Mill {

        @SerializedName("mill")
        @Expose
        private String mill;
        @SerializedName("villageName")
        @Expose
        private String villageName;
        @SerializedName("mandalName")
        @Expose
        private String mandalName;
        @SerializedName("districtName")
        @Expose
        private String districtName;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("isMill")
        @Expose
        private Boolean isMill;

        public String getMill() {
            return mill;
        }

        public void setMill(String mill) {
            this.mill = mill;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }

        public String getMandalName() {
            return mandalName;
        }

        public void setMandalName(String mandalName) {
            this.mandalName = mandalName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Boolean getIsMill() {
            return isMill;
        }

        public void setIsMill(Boolean isMill) {
            this.isMill = isMill;
        }

    }


}