package com.twedoo.volcator.web;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.twedoo.volcator.helper.PageDetail;
import com.twedoo.volcator.helper.Command;
import com.twedoo.volcator.helper.PageHelper;
import com.twedoo.volcator.helper.RequestCommand;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@Path("/command")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandResource {
    private static final String LABEL = "label";
    private static final String VALUE = "value";

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
                         document.append(LABEL, command.getLabel())
                        .append(VALUE, command.getValue())
                 )
                 .onItem().ignore().andContinueWithNull();
    }
    @POST
    @Path("add-list-command/{collectionName}")
    public Uni<Void> addListCommand(List<Command> commands, String collectionName) {
        List<Document> documents = new ArrayList<>();

        for (Command command : commands) {
            Document document = new Document()
                    .append(LABEL, command.getLabel())
                    .append(VALUE, command.getValue());

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
        BasicDBObject newDocument = new BasicDBObject("$set", new BasicDBObject(VALUE, requestCommand.getValue())
                .append(LABEL, requestCommand.getLabel()));


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


    @GET
    @Path("search-command")
    public Uni<Response> fullTextSearch(@QueryParam("collectionName") String collectionName,
                                @QueryParam("pageNumber") @DefaultValue("1") int pageNumber,
                                @QueryParam("pageSize") @DefaultValue("5") int pageSize,
                                @QueryParam("sortField") @DefaultValue("label") String sortField,
                                @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder,
                                @QueryParam("search") @DefaultValue("") String search,
                                @QueryParam("searchAttribute") @DefaultValue("label") String searchAttribute ) {

        var searchText = new BasicDBObject(searchAttribute, new BasicDBObject("$regex", search).append("$options", "i"));
        List<BasicDBObject> basicDBObjectList = List.of(
                new BasicDBObject("$match", searchText)
        );


        var size = getCollection(collectionName).aggregate(basicDBObjectList).collect().asList().map(documents -> documents.size());

        var data =  getCollection(collectionName).aggregate(basicDBObjectList)
                .map(doc -> {
                    Command command = new Command();
                    command.setValue(doc.getString(VALUE));
                    command.setLabel(doc.getString(LABEL));
                    command.setId(doc.getObjectId("_id"));
                    return command;
                }).collect().asList();

        var items = data
                .onItem().transform(commands -> PageHelper.paginate(commands, pageSize, pageNumber, sortField, sortOrder));

        var hasNext = PageHelper.hasNextPage(data,pageSize, pageNumber);

        return Uni.combine()
                .all().unis(size, items, hasNext).asTuple()
                .onItem().transform(objects ->
                        PageDetail.builder()
                                .items(objects.getItem2())
                                .count(objects.getItem1())
                                .hasNext(objects.getItem3())
                                .build()
                )
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);

    }


}
