package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

public class Contact{
    String id;
    String name;
    String contactNo;
    String message;
    boolean shareLocation;

    public Contact(String id, String name, String contactNo, String message, boolean shareLocation) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.message = message;
        this.shareLocation = shareLocation;
    }

    public void updateContactName(String name){this.name = name;}

    public void updateContactNo(String contactNo){this.contactNo = contactNo;}

    public void updateMessage(String message){this.message = message;}

    public void updateShareLocation(boolean shareLocation){this.shareLocation = shareLocation;}

    public void updateContact(String name, String contactNo, String message, boolean shareLocation) {
        this.name = name;
        this.contactNo = contactNo;
        this.message = message;
        this.shareLocation = shareLocation;
    }

    public String getContactID(){return this.id;}

    public String getContactName(){return this.name;}

    public String getContactNo(){return this.contactNo;}

    public String getMessage(){return this.message;}

    public boolean getShareLocation(){return this.shareLocation;}
}