package com.kyle.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "author")
public class Author implements Serializable {
    /*(
    aid                  int not null,
    aname                varchar(255),
    aphone               varchar(255),
    aemail               varchar(255),
    astatus              int,
    apic                 varchar(255),
    awallet              decimal,

);*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;
    private String aname;
    private String aphone;
    private String aemail;
    private Integer astatus;
    private String apic;
    private BigDecimal awallet;
    private String apassword;
    private String msgcode;

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    public String getApassword() {
        return apassword;
    }

    public void setApassword(String apassword) {
        this.apassword = apassword;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public String getAemail() {
        return aemail;
    }

    public void setAemail(String aemail) {
        this.aemail = aemail;
    }

    public Integer getAstatus() {
        return astatus;
    }

    public void setAstatus(Integer astatus) {
        this.astatus = astatus;
    }

    public String getApic() {
        return apic;
    }

    public void setApic(String apic) {
        this.apic = apic;
    }

    public BigDecimal getAwallet() {
        return awallet;
    }

    public void setAwallet(BigDecimal awallet) {
        this.awallet = awallet;
    }
}
