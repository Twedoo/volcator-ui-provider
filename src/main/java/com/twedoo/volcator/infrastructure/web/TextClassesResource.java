package com.twedoo.volcator.infrastructure.web;

import com.twedoo.volcator.domain.model.text.*;
import com.twedoo.volcator.infrastructure.helper.PageHelper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/text")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TextClassesResource {


    @GET
    @Path("decoration-classes")
    public Uni<Response> decorationClasses(@QueryParam("page") @DefaultValue("0") int page,
                                              @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Decoration.findAll(), page, size);
    }

    @GET
    @Path("font-classes")
    public Uni<Response> fontClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Font.findAll(), page, size);
    }

    @GET
    @Path("list-classes")
    public Uni<Response> listClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(List.findAll(), page, size);
    }
    @GET
    @Path("size-classes")
    public Uni<Response> sizeClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Size.findAll(), page, size);
    }
    @GET
    @Path("space-classes")
    public Uni<Response> spaceClasses(@QueryParam("page") @DefaultValue("0") int page,
                                         @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Space.findAll(), page, size);
    }

    @GET
    @Path("style-classes")
    public Uni<Response> styleClasses(@QueryParam("page") @DefaultValue("0") int page,
                                          @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Style.findAll(), page, size);
    }
}
