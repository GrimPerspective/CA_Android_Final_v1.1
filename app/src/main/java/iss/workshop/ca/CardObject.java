package iss.workshop.ca;

public class CardObject {

    // attributes
    private String url;
    private int id;

    // constructor
    public CardObject(String url, int id)
    {
        this.url = url;
        this.id = id;
    }

    // getters
    public String getUrl()
    {
        return url;
    }

    public int getId()
    {
        return id;
    }

}
