package com.electrogrid.entity;

import java.util.Date;

public class Consumer {

    private int qid;
    private String subject;
    private Date date;
    private int cus_id;

    public Consumer() {
    }

    public Consumer(int qid, String subject, Date date, int cus_id) {
        this.qid = qid;
        this.subject = subject;
        this.date = date;
        this.cus_id = cus_id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }
}
