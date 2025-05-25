package org.example.demofinal.models;

public class Org {
    private int id;
    private String orgType;
    private String name;
    private String ceo;
    private String phone;
    private String email;
    private String address;
    private int rating;

    public Org(int id, String orgType, String name, String ceo, String phone, String email, String address,
               int rating) {
        this.id = id;
        this.orgType = orgType;
        this.name = name;
        this.ceo = ceo;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
