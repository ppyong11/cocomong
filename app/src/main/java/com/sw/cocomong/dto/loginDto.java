package com.sw.cocomong.dto;

public class loginDto {
    private String name;
    private String pw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
    public loginDto(String name, String pw){
        this.name=name;
        this.pw=pw;
    }
}
