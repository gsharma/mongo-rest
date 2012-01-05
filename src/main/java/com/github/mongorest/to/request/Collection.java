package com.github.mongorest.to.request;

public class Collection {
    private String name;
    private WriteConcern writeConcern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Collection [name=").append(name).append(", writeConcern=").append(writeConcern).append("]");
        return builder.toString();
    }
}
