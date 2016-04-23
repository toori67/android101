package com.lizzardry.temporary.dao.beans;

public class Contact {
    private String name;
    private String company;
    private String department;
    private String position;
    private String phone;
    private String address;

    public Contact() {

    }

    public Contact(String name, String company, String department, String position, String phone, String address) {
        this.name = name;
        this.company = company;
        this.department = department;
        this.position = position;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocument() {
        return (name+company+department+position+phone+address).toLowerCase();
    }

    public String getMatchedString(String criterions) {
        String s = "";
        String[] criterionArrays = criterions.split("\\s");
        for (String criterion : criterionArrays) {
            s += matched(criterion);
        }
        return s;
    }

    private String matched(String criterion) {
        String s = "";
        if (name.toLowerCase().contains(criterion)) {
            s += "name : " + name + "\n";
        }
        if (company.toLowerCase().contains(criterion)) {
            s += "company : " + company + "\n";
        }
        if (department.toLowerCase().contains(criterion)) {
            s += "department : " + department + "\n";
        }
        if (position.toLowerCase().contains(criterion)) {
            s += "position : " + position + "\n";
        }
        if (phone.toLowerCase().contains(criterion)) {
            s += "phone : " + phone + "\n";
        }
        if (address.toLowerCase().contains(criterion)) {
            s += "address : " + address + "\n";
        }
        return s;
    }
}
