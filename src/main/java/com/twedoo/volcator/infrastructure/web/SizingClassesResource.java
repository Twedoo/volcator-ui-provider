package com.twedoo.volcator.infrastructure.web;

import com.twedoo.volcator.domain.model.sizing.*;
import com.twedoo.volcator.infrastructure.helper.PageHelper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/sizing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SizingClassesResource {


    @GET
    @Path("height-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> heightClasses(@QueryParam("page") @DefaultValue("0") int page,
                                              @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Height.findAll(), page, size);
    }

    @GET
    @Path("margin-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> marginClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Margin.findAll(), page, size);
    }

    @GET
    @Path("max-height-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> maxHeightClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(MaxHeight.findAll(), page, size);
    }
    @GET
    @Path("max-width-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> maxWidthClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(MaxWidth.findAll(), page, size);
    }
    @GET
    @Path("min-height-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> minHeightClasses(@QueryParam("page") @DefaultValue("0") int page,
                                         @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(MinHeight.findAll(), page, size);
    }

    @GET
    @Path("min-width-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> minWidthClasses(@QueryParam("page") @DefaultValue("0") int page,
                                          @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(MinWidth.findAll(), page, size);
    }
    @GET
    @Path("padding-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> paddingClasses(@QueryParam("page") @DefaultValue("0") int page,
                                         @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Padding.findAll(), page, size);
    }
    @GET
    @Path("width-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> widthClasses(@QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Width.findAll(), page, size);
    }
}
