package com.mulesoft.mongo.to.response;

public class Document {
    private String json;
    private String locationUri;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getLocationUri() {
        return locationUri;
    }

    public void setLocationUri(String locationUri) {
        this.locationUri = locationUri;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Document [json=").append(json).append(", locationUri=").append(locationUri).append("]");
        return builder.toString();
    }
}
