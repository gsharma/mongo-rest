package com.mulesoft.mongo.to.response;

public class Document {
    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Document [json=").append(json).append("]");
        return builder.toString();
    }
}
