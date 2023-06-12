package com.twedoo.volcator.infrastructure.web;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.twedoo.volcator.infrastructure.helper.Command;
import com.twedoo.volcator.infrastructure.helper.RequestCommand;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@Path("/command")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandResource {

    @Inject
    ReactiveMongoClient mongoClient;

    @ConfigProperty( name= "quarkus.mongodb.database")
    String databaseName;

    @POST
    @Path("add-command")
    public Uni<Void> addCommand(RequestCommand command) {
        Document document = new Document();
         return getCollection(command.getCollection())
                 .insertOne(
                         document.append("label", command.getLabel())
                        .append("value", command.getValue())
                 )
                 .onItem().ignore().andContinueWithNull();
    }
    @POST
    @Path("add-list-command/{collectionName}")
    public Uni<Void> addListCommand(List<Command> commands, String collectionName) {
        List<Document> documents = new ArrayList<>();

        for (Command command : commands) {
            Document document = new Document()
                    .append("label", command.getLabel())
                    .append("value", command.getValue());

            documents.add(document);
        }

        return getCollection(collectionName)
                .insertMany(
                        documents
                ).onItem().ignore().andContinueWithNull();
    }
    @PUT
    @Path("edit-command")
    public Uni<UpdateResult> editCommand(RequestCommand requestCommand) {

        BasicDBObject query = new BasicDBObject("_id", new ObjectId(requestCommand.getId()));
        BasicDBObject newDocument = new BasicDBObject("$set", new BasicDBObject("value", requestCommand.getValue())
                .append("label", requestCommand.getLabel()));


        return getCollection(requestCommand.getCollection())
                .updateOne(query, newDocument);
    }

    @DELETE
    @Path("delete-command")
    public Uni<DeleteResult> deleteCommand(RequestCommand requestCommand) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(requestCommand.getId()));
        return getCollection(requestCommand.getCollection())
                .deleteOne(query);
    }

    private ReactiveMongoCollection<Document> getCollection(String collection){
        return mongoClient.getDatabase(databaseName).getCollection(collection);
    }

}
