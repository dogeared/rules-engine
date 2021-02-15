package com.afitnerd.rulesengine.model.ghibli;

import java.util.List;

public class Species {
    private String id;
    private String name;
    private String url;
    private List<String> peopleUrls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPeopleUrls() {
        return peopleUrls;
    }

    public void setPeopleUrls(List<String> peopleUrls) {
        this.peopleUrls = peopleUrls;
    }
}
