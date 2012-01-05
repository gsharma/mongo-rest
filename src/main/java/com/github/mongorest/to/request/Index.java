package com.github.mongorest.to.request;

import java.util.List;

public class Index {
    private String name;
    private List<String> keys;
    private boolean unique;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Index [name=").append(name).append(", keys=").append(keys).append(", unique=").append(unique)
                .append("]");
        return builder.toString();
    }
}
