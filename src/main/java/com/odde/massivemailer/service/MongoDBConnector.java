package com.odde.massivemailer.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.onlinetest.QuestionOption;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

public class MongoDBConnector {
    static MongoDBConnector dbConnector = null;
    MongoDatabase database;

    public static MongoDBConnector instance() {
        if (dbConnector == null) {
            dbConnector = new MongoDBConnector();
        }
        return dbConnector;
    }

    public MongoDBConnector() {
        MongoClient mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("kyouha_unit_test").withCodecRegistry(getCodecRegistry());
    }

    public <T>MongoCollection<T> getMongoCollection(Class<T> klass, String name) {
        return database.getCollection(name, klass);
    }

    private static CodecRegistry getCodecRegistry() {
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(
                new Category.CategoryCodec(),
                new Question.QuestionCodec(),
                new User.UserCodec(),
                new Template.TemplateCodec(),
                new QuestionOption.QuestionOptionCodec());
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        return CodecRegistries.fromRegistries(codecRegistry, defaultCodecRegistry);
    }

    public static void resetAll() {
        instance().database.drop();
    }
}