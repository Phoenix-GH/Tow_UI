package com.noname.akio.named.clientAPI.Request;

public class TowRequest {

    private String tow_date;
    public String getTow_date() {
        return tow_date;
    }
    public void setTow_date(String tow_date) {
        this.tow_date = tow_date;
    }

    private String make;
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }

    private String model;
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    private String color;
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    private String license;


    public String getLicense() {
        return make;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    private String towed_from;

    public String getTowed_from() {
        return towed_from;
    }

    public void setTowed_from(String towed_from) {
        this.towed_from = towed_from;
    }

    private String reason;

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    private int storage_id;

    public int getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(int storage_id) {
        this.storage_id = storage_id;
    }

    private int driver_id;

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    private int price_id;
    public int getPrice_id() {
        return price_id;
    }
    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    private int contract_id;
    public int getContract_id() {
        return contract_id;
    }
    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    private String notes;
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String vin;
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
}
