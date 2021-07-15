package iss.workshop.ca;

public class CardObject {

    // attributes
    private String url;
    private int id;
    private boolean matched;

    // constructor
    public CardObject(String url, int id, boolean matched)
    {
        this.url = url;
        this.id = id;
        this.matched = matched;
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

    public boolean getMatched() {return matched;}

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
