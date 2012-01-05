package com.github.mongorest.security;

import org.springframework.beans.factory.annotation.Required;

import com.github.mongorest.util.Utils.EncodingUtils;
import com.github.mongorest.util.Utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoBase64CryptDe implements CryptDe {
    private Mongo mongo;

    @Override
    public boolean validate(String user, String password) {
        boolean isValid = false;
        if (!StringUtils.isNullOrEmpty(user) && !StringUtils.isNullOrEmpty(password)) {
            DB db = mongo.getDB("credentials");
            DBCollection collection = db.getCollection("data_service");
            DBObject credentials = new BasicDBObject();
            credentials.put("user", user.toLowerCase());
            credentials.put("password", EncodingUtils.encodeBase64(password));
            if (collection.findOne(credentials) != null) {
                isValid = true;
            }
        }
        return isValid;
    }

    @Required
    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }
}
