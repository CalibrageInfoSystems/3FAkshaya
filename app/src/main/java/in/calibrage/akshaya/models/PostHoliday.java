package in.calibrage.akshaya.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostHoliday {

    @SerializedName("preferredDate")
    @Expose
    private String preferredDate;

    public String getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(String preferredDate) {
        this.preferredDate = preferredDate;
    }

}