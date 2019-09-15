package in.calibrage.akshaya.models;

import java.util.ArrayList;

public class GetRecommendationsByAgeModel {
    private String fertilizer;
    private String uoM;
    private String year;
    private String remarks;
    private ArrayList<String> powers;
    public GetRecommendationsByAgeModel() {
        this.fertilizer = fertilizer;
        this.uoM = uoM;
        this.year = year;
        this.remarks = remarks;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public ArrayList<String> getPowers() {
        return powers;
    }

    public void setPowers(ArrayList<String> powers) {
        this.powers = powers;
    }
}
