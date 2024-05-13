package com.sw.cocomong.dto;

public class LoginDto {
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
    public LoginDto(String name, String pw){
        this.name=name;
        this.pw=pw;
    }
}
