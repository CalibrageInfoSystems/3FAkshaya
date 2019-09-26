package in.calibrage.akshaya.models;

public class product {

    private String name;
    private String quantity;
    private String amount;
    private String id;
    private int gst;


    public product(String name, String quantity, String amount, int gst) {
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
        this.id = id;
        this.gst=gst;

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String location) {
        this.amount = location;
    }


    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public int getgst() {
        return gst;
    }

    public void setgst(int gst) {
        this.gst = gst;
    }
}


