package in.calibrage.akshaya.models;

import java.util.ArrayList;

public class MyReqModel {
    private String plotId;
    private String date;
    private String time;
    private String dateNTime;
    private String reqDate;
    private String comments;
    private String approveDate;
    private String status;
    private String name;
    private String mobileNumber;
    private String pin;
    private ArrayList<String> powers;

    public MyReqModel() {
        this.plotId = plotId;
        this.date = date;
        this.time = time;
        this.dateNTime = dateNTime;
        this.reqDate = reqDate;
        this.comments = comments;
        this.approveDate = approveDate;
        this.status = status;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.pin = pin;
    }

    public String getPlotId() {
        return plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateNTime() {
        return dateNTime;
    }

    public void setDateNTime(String dateNTime) {
        this.dateNTime = dateNTime;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ArrayList<String> getPowers() {
        return powers;
    }

    public void setPowers(ArrayList<String> powers) {
        this.powers = powers;
    }
}
