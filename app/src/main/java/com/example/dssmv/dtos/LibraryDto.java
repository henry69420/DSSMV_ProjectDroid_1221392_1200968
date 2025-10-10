package com.example.dssmv.dtos;

public class LibraryDto {
    private String id;
    private String name;
    private String address;
    private boolean open;
    private String openDays;
    private String openTime;
    private String closeTime;
    private String openStatement;

    public LibraryDto(String id, String name, String address, boolean open, String openDays, String openTime, String closeTime, String openStatement) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.open = open;
        this.openDays = openDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openStatement = openStatement;
    }

    public LibraryDto() {

    }


    public LibraryDto(String name, String address,String openDays, String openTime, String closeTime) {
        this.name = name;
        this.address = address;
        this.openDays = openDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenStatement() {
        return openStatement;
    }

    public void setOpenStatement(String openStatement) {
        this.openStatement = openStatement;
    }
}
