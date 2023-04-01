package org.example;

import org.example.Key;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 1000;
        int numThreads = 4;
        int batchSize = 100000;

        HashSet<String> hs = new HashSet<>(capacity);
        while (hs.size() < capacity) {
            hs.add(generateString());
        }

        String uri = Key.key;

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("urlslugs");
            MongoCollection<Document> collection = database.getCollection("slugs3");

            collection.createIndex(Indexes.ascending("slugs")); //fieldname

            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            Iterator<String> iterator = hs.iterator();

            while (iterator.hasNext()) {
                List<Document> documents = new ArrayList<>(batchSize);
                for (int i = 0; i < batchSize && iterator.hasNext(); i++) {
                    String item = iterator.next();
                    Document document = new Document("slug", item)
                            .append("url", "")
                            .append("isUsed", false);
                    documents.add(document);
                }
                executor.submit(() -> collection.insertMany(documents));
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "").substring(0, 6);

        return uuid;
    }
}
