package com.twedoo.volcator.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.twedoo.volcator.helper.ApplicationRequestCommand;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.ConfigProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// TODO QRT : exeception handler error
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
    @Path("add-page/{refSpaceApplication}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addPageOfApplication(ApplicationRequestCommand applicationRequestCommand, @PathParam("refSpaceApplication") String refSpaceApplication) {
        return getCollection(refSpaceApplication)
                .insertOne(applicationRequestCommand.getPage())
                .onItem().transform(InsertOneResult::getInsertedId)
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @GET
    @Path("recent/{prefix}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recentApplicationsByPrefix(@PathParam("prefix") String prefix) {
        List<JsonNode> collectionsWithPrefix = new ArrayList<>();

        List<String> allCollectionNames = mongoClient.getDatabase(switchVersion()).listCollectionNames().collect().asList().await().indefinitely();
        var index = 0;

        for (String collectionName : allCollectionNames) {
            if (collectionName.startsWith(prefix)) {
                index++;
                // Get the first document from the collection
                Uni<Document> firstDocumentUni = getCollection(collectionName).find().collect().first();

                // Wait for the result and get the first document from the Uni
                Document firstDocument = firstDocumentUni.await().indefinitely();

                if (firstDocument != null) {
                    // Get the "formConfig" object from the document
                    Document formConfig = firstDocument.get("formConfig", Document.class);

                    if (formConfig != null) {
                        // Get the values of "applicationName" and "applicationId" from "formConfig"
                        String applicationName = formConfig.getString("applicationName");
                        String applicationId = formConfig.getString("applicationId");

                        // Create a JSON object to store the values
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode jsonObject = mapper.createObjectNode();
                        jsonObject.put("id", index);
                        jsonObject.put("title", applicationId);
                        jsonObject.put("applicationName", applicationName);
                        jsonObject.put("applicationId", applicationId);
                        jsonObject.put("pageId", String.valueOf(firstDocument.get("_id")));
                        jsonObject.put("active", true);

                        // Add the JSON object to the response
                        collectionsWithPrefix.add(jsonObject);

                    }
                }
            }
        }

        // Prepare the JSON response
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("applications", mapper.valueToTree(collectionsWithPrefix));

        return Response.ok(responseJson).build();
    }

    @GET
    @Path("check/{refSpaceApplication}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkApplicationExist(@PathParam("refSpaceApplication") String refSpaceApplication) {
        boolean applicationExists = getCollection(refSpaceApplication)
                .find()
                .collect().asList()
                .map(documents -> !documents.isEmpty())
                .await().indefinitely();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("exists", applicationExists);
        return Response.ok(responseJson).build();
    }

    /**
     * @param refSpaceApplication
     * @return
     */
    @GET
    @Path("pages/{refSpaceApplication}")
    public Uni<Response> getPagesOfApplication(@PathParam("refSpaceApplication") String refSpaceApplication) {
        return getCollection(refSpaceApplication)
                .find()
                .collect().asList()
                .onItem().transform(documents -> Response.ok(documents).build());
    }

    /**
     * @param refSpaceApplication
     * @param pageId
     * @return
     */
    @GET
    @Path("page/{refSpaceApplication}/{pageId}")
    public Uni<Response> getOnePageApplication(@PathParam("refSpaceApplication") String refSpaceApplication, @PathParam("pageId") String pageId) {
        return getCollection(refSpaceApplication)
                .find(eq("_id", new ObjectId(pageId)))
                .collect().asList()
                .onItem().transform(documents -> Response.ok(documents).build());
    }

    /**
     * @param applicationRequestCommand
     * @return
     */
    @PUT
    @Path("edit/")
    public Uni<Void> editPageApplication(ApplicationRequestCommand applicationRequestCommand) {
        return getCollection(applicationRequestCommand.getApplication())
                .updateOne(eq("_id", new ObjectId(applicationRequestCommand.getId())), new Document("$set", applicationRequestCommand.getPage()))
                .onItem().ignore().andContinueWithNull();
    }

    /**
     * @param applicationRequestCommand
     * @return
     */
    @DELETE
    @Path("delete/")
    public Uni<DeleteResult> deletePageApplication(ApplicationRequestCommand applicationRequestCommand) {
        return getCollection(applicationRequestCommand.getApplication()).deleteOne(eq("_id", new ObjectId(applicationRequestCommand.getId())));
    }

    /**
     * @param collection
     * @return
     */
    private ReactiveMongoCollection<Document> getCollection(String collection) {
        return mongoClient.getDatabase(switchVersion()).getCollection(collection);
    }
}
