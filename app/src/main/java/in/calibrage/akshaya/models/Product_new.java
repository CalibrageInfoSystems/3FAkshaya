package in.calibrage.akshaya.models;

import java.io.Serializable;

public class Product_new implements Serializable {
    private Integer Quandity;
    private String Productname;
    private Double amount;
    private double withGSTamount;
    private Integer gst;
    private double eachproductcost;
    private double size;
    private Integer ProductID;

    public Product_new(Integer quandity, String productname, Double amount, double withGSTamount, Integer gst, double eachproductcost, int productID,double size) {
        Quandity = quandity;
        Productname = productname;
        this.amount = amount;
        this.withGSTamount = withGSTamount;
        this.gst = gst;
        this.eachproductcost = eachproductcost;
        this.size =size;
        ProductID = productID;
    }

    public Integer getQuandity() {
        return Quandity;
    }

    public void setQuandity(Integer quandity) {
        Quandity = quandity;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public double getWithGSTamount() {
        return withGSTamount;
    }

    public void setWithGSTamount(double withGSTamount) {
        this.withGSTamount = withGSTamount;
    }

    public Integer getGst() {
        return gst;
    }

    public void setGst(Integer gst) {
        this.gst = gst;
    }

    public double getEachproductcost() {
        return eachproductcost;
    }

    public void setEachproductcost(double eachproductcost) {
        this.eachproductcost = eachproductcost;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
