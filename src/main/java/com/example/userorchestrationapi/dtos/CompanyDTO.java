package com.example.userorchestrationapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {
    private String department;
    private String name;
    private String title;
    private AddressDTO address;

    // Getters and Setters

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
