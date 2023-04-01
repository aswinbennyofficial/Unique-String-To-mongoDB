
package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int capacity=1000000;
        HashSet<String> hs=new HashSet<>(capacity);
        //int i,count=0;
        while(hs.size()<capacity) {
            hs.add(generateString());
        }
        String uri = "mongodb+srv://willywonka69:cPmIlk57we3nTxXG@cluster1.pkdkagd.mongodb.net/?retryWrites=true&w=majority";
        //String uri = "mongodb+srv://willywonka987:willyhateshelicopter@cluster1.pkdkagd.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("urlslugs");
            MongoCollection<Document> collection = database.getCollection("slugs");

            collection.createIndex(Indexes.ascending("slugs")); //fieldname


            List<Document> documents = new ArrayList<>(capacity);

            for (String item : hs) {
//                Document document = new Document("field", item);
//

                Document document = new Document("slug", item)
                        .append("url", "")
                        .append("isUsed", false);
                documents.add(document);
            }
            collection.insertMany(documents);

        }

/*
{
url:{""},
slug:variable,
isUsed:false

}

 */



    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        uuid=uuid.replace("-", "").substring(0,6);

        return uuid;
    }


}
