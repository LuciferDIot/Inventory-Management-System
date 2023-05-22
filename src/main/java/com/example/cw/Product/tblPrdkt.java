package com.example.cw.Product;

public class tblPrdkt {
    private String clmid;
    private String Qty;
    private String clmname;
    private String clmnCtgry;
    private String clmDes;

    public tblPrdkt(String clmid, String qty, String clmname, String clmnCtgry, String clmDes) {
        this.clmid = clmid;
        Qty = qty;
        this.clmname = clmname;
        this.clmnCtgry = clmnCtgry;
        this.clmDes = clmDes;
    }



    public String getClmid() {
        return clmid;
    }

    public void setClmid(String clmid) {
        this.clmid = clmid;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getClmname() {
        return clmname;
    }

    public void setClmname(String clmname) {
        this.clmname = clmname;
    }

    public String getClmnCtgry() {
        return clmnCtgry;
    }

    public void setClmnCtgry(String clmnCtgry) {
        this.clmnCtgry = clmnCtgry;
    }

    public String getClmDes() {
        return clmDes;
    }

    public void setClmDes(String clmDes) {
        this.clmDes = clmDes;
    }
}
