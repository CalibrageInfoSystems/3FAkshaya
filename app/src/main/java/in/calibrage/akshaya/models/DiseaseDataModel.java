package in.calibrage.akshaya.models;

public class DiseaseDataModel {
    private String Disease;
    private String Chemical;
    private String UOMName;
    private String Dosage;
    private String Comments;

    public DiseaseDataModel() {
        Disease = Disease;
        Chemical = Chemical;
        UOMName = UOMName;
        Dosage = Dosage;
        Comments = Comments;
    }

    public String getDisease() {
        return Disease;
    }

    public void setDisease(String disease) {
        Disease = disease;
    }

    public String getChemical() {
        return Chemical;
    }

    public void setChemical(String chemical) {
        Chemical = chemical;
    }

    public String getUOMName() {
        return UOMName;
    }

    public void setUOMName(String UOMName) {
        this.UOMName = UOMName;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}