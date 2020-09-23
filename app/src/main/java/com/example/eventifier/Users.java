package com.example.eventifier;




public class Users {

    private Long sap;
    private String name, email, password, cfnPass, stream;

    public Users() {
    }


    public Long getSap() {
        return sap;
    }

    public void setSap(Long sap) {
        this.sap = sap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCfnPass() {
        return cfnPass;
    }

    public void setCfnPass(String cfnPass) {
        this.cfnPass = cfnPass;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}