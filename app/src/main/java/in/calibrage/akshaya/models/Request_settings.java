package in.calibrage.akshaya.models;

public class Request_settings {
    String name;
    int image;
    public Request_settings(String name, int image) {
        this.name = name;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public Integer getImage() {
        return image;
    }


}


