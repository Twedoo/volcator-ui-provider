package com.twedoo.volcator.web;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.twedoo.volcator.helper.ApplicationRequestCommand;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.ConfigProvider;

import static com.mongodb.client.model.Filters.eq;

@Path("/{version}/volcator/application")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VolcatorApplicationsCommandResource {

    @Inject
    ReactiveMongoClient mongoClient;

    @PathParam("version")
    private String version;

    /**
     * @return databaseName
     */
    public String switchVersion() {
        String databaseName = null;
        if (version.equals("v1")) {
            databaseName = ConfigProvider.getConfig().getValue("volcator.v1.database.application", String.class);
        } else if (version.equals("v2")) {
            databaseName = ConfigProvider.getConfig().getValue("volcator.v2.database.application", String.class);
        }
        return databaseName;
    }

    /**
     * @param applicationRequestCommand
     * @param refSpaceApplication
     * @return
     */
    @POST
    @Path("add/{refSpaceApplication}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addStructureApplication(ApplicationRequestCommand applicationRequestCommand, @PathParam("refSpaceApplication") String refSpaceApplication) {
        return getCollection(refSpaceApplication)
                .insertOne(applicationRequestCommand.getPage())
                .onItem().transform(InsertOneResult::getInsertedId)
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }

    /**
     * @param applicationRequestCommand
     * @return
     */
    @PUT
    @Path("edit/")
    public Uni<Void> editStructureApplication(ApplicationRequestCommand applicationRequestCommand) {
        return getCollection(applicationRequestCommand.getCollection())
                .updateOne(eq("_id", new ObjectId(applicationRequestCommand.getId())), new Document("$set", applicationRequestCommand.getPage()))
                .onItem().ignore().andContinueWithNull();
    }

//    /**
//     * @param applicationRequestCommand
//     * @return
//     */
//    @DELETE
//    @Path("delete/")
//    public Uni<DeleteResult> deleteStructureApplication(ApplicationRequestCommand applicationRequestCommand) {
//        return (Uni<DeleteResult>) getCollection(applicationRequestCommand.getCollection())
//                .deleteOne(eq("_id", new ObjectId(applicationRequestCommand.getId())))
//                .onItem().transform(deleteResult -> {
//                    if (deleteResult.getDeletedCount() > 0) {
//                        return Response.ok().build();
//                    } else {
//                        return Response.status(Response.Status.NOT_FOUND).build();
//                    }
//                });
//    }
    
    @DELETE
    @Path("delete/")
    public Uni<DeleteResult> deleteStructureApplication(ApplicationRequestCommand applicationRequestCommand) {
        return getCollection(applicationRequestCommand.getCollection()).deleteOne(eq("_id", new ObjectId(applicationRequestCommand.getId())));
    }

    /**
     * @param collection
     * @return
     */
    private ReactiveMongoCollection<Document> getCollection(String collection) {
        return mongoClient.getDatabase(switchVersion()).getCollection(collection);
    }
}
