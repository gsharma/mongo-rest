package com.mulesoft.mongo.to.request;

public class Document {
    private String json;
    private String name;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Document [json=").append(json).append("]");
        return builder.toString();
    }
}
