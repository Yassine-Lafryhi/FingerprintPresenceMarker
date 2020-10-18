package me.fingerprint.presence.marker.models;

public class Session {
    private int id;
    private String date;
    private String from;
    private String to;
    private String numbers;

    public Session(int id, String date, String from, String to, String numbers) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.numbers = numbers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
