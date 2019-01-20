package com.example.lenovo.mymoviestage2;

public class MyMovie {
    String id;
    String title;
    String vote_avg;
    String poster;
    String overview;
    String release;

    public MyMovie(String id, String title, String vote_avg, String poster, String overview, String release) {
        this.id = id;
        this.title = title;
        this.vote_avg = vote_avg;
        this.poster = poster;
        this.overview = overview;
        this.release = release;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(String vote_avg) {
        this.vote_avg = vote_avg;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}

