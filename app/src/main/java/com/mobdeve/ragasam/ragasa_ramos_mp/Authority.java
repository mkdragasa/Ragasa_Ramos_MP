package com.mobdeve.ragasam.ragasa_ramos_mp;


public class Authority {
    String id;
    String name;
    String contactNo;
    boolean isDefault;

    public Authority(String id, String name, String contactNo, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.isDefault = isDefault;
    }

    public void updateNumber(String contactNo){this.contactNo = contactNo;}

    public void updateIsDefault(Boolean isDefault){this.isDefault = isDefault;}

    public String getID(){return this.id;}

    public String getName(){return this.name;}

    public String getContactNo(){return this.contactNo;}

    public boolean getIsDefault(){return this.isDefault;}
}
