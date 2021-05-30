package com.mobdeve.ragasam.ragasa_ramos_mp;

public class Contact{
    String id;
    String name;
    String contactNo;
    long defaultMessageId;

    public Contact(String id, String name, String contactNo) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
    }

    public void updateContactName(String name){this.name = name;}

    public void updateContactNo(String contactNo){this.contactNo = contactNo;}

    public void setDefaultMessage(long defaultMessageId) {
        this.defaultMessageId = defaultMessageId;
    }

    public String getContactID(){return this.id;}

    public String getContactName(){return this.name;}

    public String getContactNo(){return this.contactNo;}

    public long getIsDefaultMessageID(){return this.defaultMessageId;}
}