package JavaDriver.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.Doc;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.ExplainVerbosity;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

public class App 
{
    public static void findAll(MongoCollection<Document> collection){
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            System.out.println("> " +cursor.next() );
        }
    }

    public static void findAllWithProjection(MongoCollection<Document> collection){
        FindIterable<Document> iterDoc =
        collection.find().projection(Projections.fields(Projections.include("restaurant_id", "nome", "localidade", "gastronomia"), Projections.excludeId()));
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void findAllWithProjection2(MongoCollection<Document> collection){
        FindIterable<Document> iterDoc =
        collection.find().projection(Projections.fields(Projections.include("restaurant_id", "nome", "localidade", "address.zipcode"), Projections.excludeId()));
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void findAllbigscore(MongoCollection<Document> collection){
        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("grades.score", new BasicDBObject("$gt", 85));
        FindIterable<Document> iterDoc = collection.find(gtQuery);
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void findAllsmallLat(MongoCollection<Document> collection){
        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("address.coord.0", new BasicDBObject("$lt", -95.7));
        FindIterable<Document> iterDoc = collection.find(gtQuery);
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void insertNewDoc(MongoCollection<Document> collection){
        Document doc1 = new Document("name", "NEWDOC").append("qty", 5);
        collection.insertOne(doc1);
        System.out.println("Inserted a document (cant display id of it cuz the driver is buggy)");
    }

    public static void updateDoc(MongoCollection<Document> collection){
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("name", "NEWDOC"));
        BasicDBObject searchQuery = new BasicDBObject().append("newProperty", "property");

        collection.updateOne(searchQuery, newDocument);
        System.out.println("Updated new doc");
    }

    public static void removeDoc(MongoCollection<Document> collection){
        collection.deleteOne(Filters.eq("name", "NEWDOC"));
        System.out.println("Document deleted successfully...");
    }

    public static void createIndex(MongoCollection<Document> collection){
        // Index localidade
        String resultCreateIndex0 = collection.createIndex(Indexes.ascending("localidade"));
        System.out.println(String.format("Index created: %s", resultCreateIndex0));
        
        // Index gastronomia
        String resultCreateIndex1 = collection.createIndex(Indexes.ascending("gastronomia"));
        System.out.println(String.format("Index created: %s", resultCreateIndex1));
        
        // Texto para o nome
        String resultCreateIndex2 = collection.createIndex(Indexes.text("nome"));
        System.out.println(String.format("Index created: %s", resultCreateIndex2));
    }

    public static int countLocalidade(MongoCollection<Document> collection){
        List<Document> l = new ArrayList<Document>();

        AggregateIterable<Document> col = collection.aggregate(
            Arrays.asList(
                Aggregates.group("$localidade")
            )
        );
        Integer result = 1;
        Iterator it = col.iterator();
        while (it.hasNext()) {
            result++;
            it.next();
        }
        return result;
    }

    public static Map<String, Integer> countRestByLocalidade(MongoCollection<Document> collection){
        Map<String, Integer> localidades = new HashMap();
        AggregateIterable<Document> col = collection.aggregate(
            Arrays.asList(
                Aggregates.group("$localidade", Accumulators.sum("count", 1))
            )
        );
        Iterator it = col.iterator();

        while (it.hasNext()) {
            String[] iter = it.next().toString().replace("Document{{_id=", "").split(", ");
            Integer count = Integer.parseInt(iter[1].replace("count=", "").replace("}}", ""));
            localidades.put(iter[0], count);
        }
        return localidades;
    }

    public static List<String> getRestWithNameCloserTo(String name, MongoCollection<Document> collection){
        List<String> result = new ArrayList<>();
        AggregateIterable<Document> col = collection.aggregate(
            Arrays.asList(
                Aggregates.match(Filters.regex("nome", name))
            )
        );
        Iterator it = col.iterator();
        while (it.hasNext()) {
            String[] iter = it.next().toString().split("nome=");
            String[] iter2= iter[1].split(", restaurant_id=");
            result.add(iter2[0]);
        }
        return result;
    }

    public static void main(String[] args) {
        // Connect to server
        String uri = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("cbd");

            // Ping DB
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.print("\n\nActions:\n");
                System.out.println("Connected successfully to server.");
            } catch (com.mongodb.MongoCommandException me) {
                System.out.print("\n\nActions:\n");
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
            
            // Select restaurants Collection
            MongoCollection<Document> collection = database.getCollection("restaurants");

            // Insert Document
            insertNewDoc(collection);

            // Update inserted Document
            updateDoc(collection);

            // Remove inserted Document
            removeDoc(collection);

            // Create Indexes
            createIndex(collection);

            // Questions from 2.2

            // 1. Liste todos os documentos da coleção.
            System.out.println("\n1. Liste todos os documentos da coleção.\n"); 
            findAll(collection);

            // 2. Apresente os campos restaurant_id, nome, localidade e gastronomia para todos os documentos da coleção. 
            System.out.println("\n2. Apresente os campos restaurant_id, nome, localidade e gastronomia para todos os documentos da coleção.\n");
            findAllWithProjection(collection);

            // 3. Apresente os campos restaurant_id, nome, localidade e código postal (zipcode), mas exclua o campo _id de todos os documentos da coleção.
            System.out.println("\n3. Apresente os campos restaurant_id, nome, localidade e código postal (zipcode), mas exclua o campo _id de todos os documentos da coleção.\n"); 
            findAllWithProjection2(collection);

            // 6. Liste todos os restaurantes que tenham pelo menos um score superior a 85.
            System.out.println("\n6. Liste todos os restaurantes que tenham pelo menos um score superior a 85.\n"); 
            findAllbigscore(collection);

            // 8. Indique os restaurantes com latitude inferior a -95,7. 
            System.out.println("\n8. Indique os restaurantes com latitude inferior a -95,7.\n");
            findAllsmallLat(collection);

            // public int countLocalidades() 
            System.out.println("\npublic int countLocalidades()\n");
            System.out.println("Numero de localidades distintas: " + countLocalidade(collection));

            // Map<String, Integer> countRestByLocalidade()  
            // System.out.println("\nMap<String, Integer> countRestByLocalidade()\n");
            Map<String, Integer> resultMap = countRestByLocalidade(collection);
            System.out.println("\nNumero de restaurantes por localidade:");
            for (String string : resultMap.keySet()) {
                System.out.println("-> " + string + " - " + resultMap.get(string));
            }

            // List<String> getRestWithNameCloserTo(String name) 
            // System.out.println("\nMap<String, Integer> countRestByLocalidade()\n");
            String name = "Park";
            List<String> result = getRestWithNameCloserTo(name, collection);
            System.out.println("\nNome de restaurantes contendo 'Park' no nome:");
            for (String rest : result) {
                System.out.println("-> " + rest);
            }
        }
    }
}
