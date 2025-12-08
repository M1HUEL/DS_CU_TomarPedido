package com.itson.persistencia.util;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conn {

    private static Conn instance;
    private final MongoClient client;
    private final MongoDatabase database;

    private Conn() {
        String uri = "mongodb://localhost:27017";

        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri)).serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

        client = MongoClients.create(settings);

        database = client.getDatabase("restaurante_db");
    }

    public static synchronized Conn getInstance() {
        if (instance == null) {
            instance = new Conn();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
