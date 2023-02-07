package org.lab1;

import jakarta.persistence.*;

@Entity
public class BuddyInfo {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String phoneNo;

    public BuddyInfo(){
        name = "";
        phoneNo = "";
    }

    public BuddyInfo(String name, String phoneNo){
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nPhone No.: " + phoneNo;
    }

}
