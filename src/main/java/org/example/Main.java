
package org.example;
import org.example.Key;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int capacity=10000;
        HashSet<String> hs=new HashSet<>(capacity);
        //int i,count=0;
        while(hs.size()<capacity) {
            hs.add(generateString());
        }
        String uri = Key.key;


        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("urlslugs");
            MongoCollection<Document> collection = database.getCollection("slugs5");

            collection.createIndex(Indexes.ascending("slugs")); //fieldname


            //List<Document> documents = new ArrayList<>(capacity);
            List<Document> Arr = new ArrayList<>(capacity);

            for (String item : hs) {
//              Document document = new Document("field", item);
                Document document = new Document("slug", item)
                        .append("url", "")
                        .append("isUsed", false);
                //documents.add(document);
                Arr.add(document);
            }
            //collection.insertMany(documents);
            collection.insertMany(Arr);

        }





    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        uuid=uuid.replace("-", "").substring(0,6);

        while (uuid.matches("[0-9]+")) {
            uuid = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "").substring(0, 6);
        }

        return uuid;
    }


}
