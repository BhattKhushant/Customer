package com.example.customer;

public class Ticket {
    String Source,Destination,Pay,Cond,date,Rout,Ticket_No,Time;

    public Ticket() {
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setTicket_No(String ticket_No) {
        Ticket_No = ticket_No;
    }

    public void setSource(String source) {
        Source = source;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setPay(String pay) {
        Pay = pay;
    }

    public void setCond(String cond) {
        Cond = cond;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRout(String rout) {
        Rout = rout;
    }

    public String getTime() {
        return Time;
    }

    public String getDate() {
        return date;
    }

    public String getTicket_No() {
        return Ticket_No;
    }

    public String getSource() {
        return Source;
    }

    public String getDestination() {
        return Destination;
    }

    public String getPay() {
        return Pay;
    }

    public String getCond() {
        return Cond;
    }

    public String getRout() {
        return Rout;
    }
}
