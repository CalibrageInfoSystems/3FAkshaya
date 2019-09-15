package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stand_recom_model {


    @SerializedName("fertilizer")
    @Expose
    private String fertilizer;
    @SerializedName("uoM")
    @Expose
    private String uoM;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getUoM() {
        return uoM;
    }

    public void setUoM(String uoM) {
        this.uoM = uoM;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}