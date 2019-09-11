package in.calibrage.akshaya.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropResponseModel {

    @SerializedName("healthPlantationData")
    @Expose
    private HealthPlantationData healthPlantationData;
    @SerializedName("nutrientData")
    @Expose
    private List<Object> nutrientData = null;
    @SerializedName("uprootmentData")
    @Expose
    private UprootmentData uprootmentData;
    @SerializedName("pestData")
    @Expose
    private List<Object> pestData = null;
    @SerializedName("fertilizerRecommendationDetails")
    @Expose
    private List<Object> fertilizerRecommendationDetails = null;
    @SerializedName("diseaseData")
    @Expose
    private List<Object> diseaseData = null;

    public HealthPlantationData getHealthPlantationData() {
        return healthPlantationData;
    }

    public void setHealthPlantationData(HealthPlantationData healthPlantationData) {
        this.healthPlantationData = healthPlantationData;
    }

    public List<Object> getNutrientData() {
        return nutrientData;
    }

    public void setNutrientData(List<Object> nutrientData) {
        this.nutrientData = nutrientData;
    }

    public UprootmentData getUprootmentData() {
        return uprootmentData;
    }

    public void setUprootmentData(UprootmentData uprootmentData) {
        this.uprootmentData = uprootmentData;
    }

    public List<Object> getPestData() {
        return pestData;
    }

    public void setPestData(List<Object> pestData) {
        this.pestData = pestData;
    }

    public List<Object> getFertilizerRecommendationDetails() {
        return fertilizerRecommendationDetails;
    }

    public void setFertilizerRecommendationDetails(List<Object> fertilizerRecommendationDetails) {
        this.fertilizerRecommendationDetails = fertilizerRecommendationDetails;
    }

    public List<Object> getDiseaseData() {
        return diseaseData;
    }

    public void setDiseaseData(List<Object> diseaseData) {
        this.diseaseData = diseaseData;
    }
    public class HealthPlantationData {

        @SerializedName("plotCode")
        @Expose
        private String plotCode;
        @SerializedName("plantationStateTypeId")
        @Expose
        private Integer plantationStateTypeId;
        @SerializedName("plantationState")
        @Expose
        private String plantationState;
        @SerializedName("treesAppearanceTypeId")
        @Expose
        private Integer treesAppearanceTypeId;
        @SerializedName("treesAppearance")
        @Expose
        private String treesAppearance;
        @SerializedName("treeGirthTypeId")
        @Expose
        private Integer treeGirthTypeId;
        @SerializedName("treeGirth")
        @Expose
        private String treeGirth;
        @SerializedName("treeHeightTypeId")
        @Expose
        private Integer treeHeightTypeId;
        @SerializedName("treeHeight")
        @Expose
        private String treeHeight;
        @SerializedName("fruitColorTypeId")
        @Expose
        private Integer fruitColorTypeId;
        @SerializedName("fruitColor")
        @Expose
        private String fruitColor;
        @SerializedName("fruitSizeTypeId")
        @Expose
        private Integer fruitSizeTypeId;
        @SerializedName("fruitSize")
        @Expose
        private String fruitSize;
        @SerializedName("fruitHyegieneTypeId")
        @Expose
        private Integer fruitHyegieneTypeId;
        @SerializedName("fruitHyegiene")
        @Expose
        private String fruitHyegiene;
        @SerializedName("plantationTypeId")
        @Expose
        private Integer plantationTypeId;
        @SerializedName("plantationType")
        @Expose
        private String plantationType;
        @SerializedName("plantationPictureLocation")
        @Expose
        private String plantationPictureLocation;
        @SerializedName("isActive")
        @Expose
        private String isActive;
        @SerializedName("updatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("cropMaintenanceCode")
        @Expose
        private String cropMaintenanceCode;

        public String getPlotCode() {
            return plotCode;
        }

        public void setPlotCode(String plotCode) {
            this.plotCode = plotCode;
        }

        public Integer getPlantationStateTypeId() {
            return plantationStateTypeId;
        }

        public void setPlantationStateTypeId(Integer plantationStateTypeId) {
            this.plantationStateTypeId = plantationStateTypeId;
        }

        public String getPlantationState() {
            return plantationState;
        }

        public void setPlantationState(String plantationState) {
            this.plantationState = plantationState;
        }

        public Integer getTreesAppearanceTypeId() {
            return treesAppearanceTypeId;
        }

        public void setTreesAppearanceTypeId(Integer treesAppearanceTypeId) {
            this.treesAppearanceTypeId = treesAppearanceTypeId;
        }

        public String getTreesAppearance() {
            return treesAppearance;
        }

        public void setTreesAppearance(String treesAppearance) {
            this.treesAppearance = treesAppearance;
        }

        public Integer getTreeGirthTypeId() {
            return treeGirthTypeId;
        }

        public void setTreeGirthTypeId(Integer treeGirthTypeId) {
            this.treeGirthTypeId = treeGirthTypeId;
        }

        public String getTreeGirth() {
            return treeGirth;
        }

        public void setTreeGirth(String treeGirth) {
            this.treeGirth = treeGirth;
        }

        public Integer getTreeHeightTypeId() {
            return treeHeightTypeId;
        }

        public void setTreeHeightTypeId(Integer treeHeightTypeId) {
            this.treeHeightTypeId = treeHeightTypeId;
        }

        public String getTreeHeight() {
            return treeHeight;
        }

        public void setTreeHeight(String treeHeight) {
            this.treeHeight = treeHeight;
        }

        public Integer getFruitColorTypeId() {
            return fruitColorTypeId;
        }

        public void setFruitColorTypeId(Integer fruitColorTypeId) {
            this.fruitColorTypeId = fruitColorTypeId;
        }

        public String getFruitColor() {
            return fruitColor;
        }

        public void setFruitColor(String fruitColor) {
            this.fruitColor = fruitColor;
        }

        public Integer getFruitSizeTypeId() {
            return fruitSizeTypeId;
        }

        public void setFruitSizeTypeId(Integer fruitSizeTypeId) {
            this.fruitSizeTypeId = fruitSizeTypeId;
        }

        public String getFruitSize() {
            return fruitSize;
        }

        public void setFruitSize(String fruitSize) {
            this.fruitSize = fruitSize;
        }

        public Integer getFruitHyegieneTypeId() {
            return fruitHyegieneTypeId;
        }

        public void setFruitHyegieneTypeId(Integer fruitHyegieneTypeId) {
            this.fruitHyegieneTypeId = fruitHyegieneTypeId;
        }

        public String getFruitHyegiene() {
            return fruitHyegiene;
        }

        public void setFruitHyegiene(String fruitHyegiene) {
            this.fruitHyegiene = fruitHyegiene;
        }

        public Integer getPlantationTypeId() {
            return plantationTypeId;
        }

        public void setPlantationTypeId(Integer plantationTypeId) {
            this.plantationTypeId = plantationTypeId;
        }

        public String getPlantationType() {
            return plantationType;
        }

        public void setPlantationType(String plantationType) {
            this.plantationType = plantationType;
        }

        public String getPlantationPictureLocation() {
            return plantationPictureLocation;
        }

        public void setPlantationPictureLocation(String plantationPictureLocation) {
            this.plantationPictureLocation = plantationPictureLocation;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getCropMaintenanceCode() {
            return cropMaintenanceCode;
        }

        public void setCropMaintenanceCode(String cropMaintenanceCode) {
            this.cropMaintenanceCode = cropMaintenanceCode;
        }

    }
    public class UprootmentData {

        @SerializedName("plotCode")
        @Expose
        private String plotCode;
        @SerializedName("seedsPlanted")
        @Expose
        private Integer seedsPlanted;
        @SerializedName("plamsCount")
        @Expose
        private Integer plamsCount;
        @SerializedName("prevPalmsCount")
        @Expose
        private Integer prevPalmsCount;
        @SerializedName("isTreesMissing")
        @Expose
        private String isTreesMissing;
        @SerializedName("missingTreesCount")
        @Expose
        private Integer missingTreesCount;
        @SerializedName("reasonType")
        @Expose
        private Object reasonType;
        @SerializedName("reasonTypeId")
        @Expose
        private Object reasonTypeId;
        @SerializedName("comments")
        @Expose
        private Object comments;
        @SerializedName("isActive")
        @Expose
        private String isActive;
        @SerializedName("updatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("expectedPlamsCount")
        @Expose
        private Integer expectedPlamsCount;
        @SerializedName("cropMaintenanceCode")
        @Expose
        private Object cropMaintenanceCode;

        public String getPlotCode() {
            return plotCode;
        }

        public void setPlotCode(String plotCode) {
            this.plotCode = plotCode;
        }

        public Integer getSeedsPlanted() {
            return seedsPlanted;
        }

        public void setSeedsPlanted(Integer seedsPlanted) {
            this.seedsPlanted = seedsPlanted;
        }

        public Integer getPlamsCount() {
            return plamsCount;
        }

        public void setPlamsCount(Integer plamsCount) {
            this.plamsCount = plamsCount;
        }

        public Integer getPrevPalmsCount() {
            return prevPalmsCount;
        }

        public void setPrevPalmsCount(Integer prevPalmsCount) {
            this.prevPalmsCount = prevPalmsCount;
        }

        public String getIsTreesMissing() {
            return isTreesMissing;
        }

        public void setIsTreesMissing(String isTreesMissing) {
            this.isTreesMissing = isTreesMissing;
        }

        public Integer getMissingTreesCount() {
            return missingTreesCount;
        }

        public void setMissingTreesCount(Integer missingTreesCount) {
            this.missingTreesCount = missingTreesCount;
        }

        public Object getReasonType() {
            return reasonType;
        }

        public void setReasonType(Object reasonType) {
            this.reasonType = reasonType;
        }

        public Object getReasonTypeId() {
            return reasonTypeId;
        }

        public void setReasonTypeId(Object reasonTypeId) {
            this.reasonTypeId = reasonTypeId;
        }

        public Object getComments() {
            return comments;
        }

        public void setComments(Object comments) {
            this.comments = comments;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public Integer getExpectedPlamsCount() {
            return expectedPlamsCount;
        }

        public void setExpectedPlamsCount(Integer expectedPlamsCount) {
            this.expectedPlamsCount = expectedPlamsCount;
        }

        public Object getCropMaintenanceCode() {
            return cropMaintenanceCode;
        }

        public void setCropMaintenanceCode(Object cropMaintenanceCode) {
            this.cropMaintenanceCode = cropMaintenanceCode;
        }
    }
}