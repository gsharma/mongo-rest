package com.github.mongorest.to.request;

public enum WriteConcern {
    NONE(com.mongodb.WriteConcern.NONE), NORMAL(com.mongodb.WriteConcern.NORMAL), SAFE(com.mongodb.WriteConcern.SAFE), FSYNC_SAFE(
            com.mongodb.WriteConcern.FSYNC_SAFE), REPLICAS_SAFE(com.mongodb.WriteConcern.REPLICAS_SAFE);

    private com.mongodb.WriteConcern mongoWriteConcern;

    private WriteConcern(com.mongodb.WriteConcern mongoWriteConcern) {
        this.mongoWriteConcern = mongoWriteConcern;
    }

    public com.mongodb.WriteConcern getMongoWriteConcern() {
        return mongoWriteConcern;
    }

    public static WriteConcern fromMongoWriteConcern(com.mongodb.WriteConcern mongoWriteConcern) {
        WriteConcern found = null;
        for (WriteConcern writeConcern : values()) {
            if (writeConcern.mongoWriteConcern == mongoWriteConcern) {
                found = writeConcern;
                break;
            }
        }
        return found;
    }
}
