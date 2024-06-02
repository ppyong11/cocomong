package com.sw.cocomong.Object;

public class RefObj {
    private String username;
    private String refname;
    private String refnum;

    public RefObj(String refname, String refnum, String username){
        this.refnum=refnum;
        this.refname=refname;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname;
    }

    public String getRefnum() {
        return refnum;
    }

    public void setRefnum(String refnum) {
        this.refnum = refnum;
    }
}
