package com.example.farmbnb_cw1;

public class Customer {

    //customer object
    private String lFullName;
    private String lAddressLine1;
    private String lAddressLine2;
    private String lCity;
    private String lPostCode;

    public Customer(String FullName, String AddressLine1, String AddressLine2, String City, String PostCode) {
        lFullName = FullName;
        lAddressLine1 = AddressLine1;
        lAddressLine2 = AddressLine2;
        lCity = City;
        lPostCode = PostCode;
    }

    public String getFullName() {
        return lFullName;
    }

    public void setFullName(String FullName) {
        lFullName = FullName;
    }

    public String getlAddressLine1() {
        return lAddressLine1;
    }

    public void setlAddressLine1(String AddressLine1) {
        lAddressLine1 = AddressLine1;
    }

    public String getlAddressLine2() {
        return lAddressLine2;
    }

    public void setlAddressLine2(String AddressLine2) {
        lAddressLine2 = AddressLine2;
    }

    public String getCity() {
        return lCity;
    }

    public void setCity(String City) {
        lCity = City;
    }

    public String getPostCode() {
        return lPostCode;
    }

    public void getPostCode(String PostCode){
        lPostCode = PostCode;
    }
}