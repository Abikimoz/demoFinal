package org.example.demofinal.models;

public class Org {
    private int id;
    private String orgType;
    private String name;
    private String ceo;
    private String phone;
    private int rating;

    public Org(int id, String orgType, String name, String ceo, String phone, String email, String address,
               int rating) {
        this.id = id;
        this.orgType = orgType;
        this.name = name;
        this.ceo = ceo;
        this.phone = phone;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getOrgType() {
        return orgType;
    }

    public String getCeo() {
        return ceo;
    }

    public String getPhone() {
        return phone;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }
}
