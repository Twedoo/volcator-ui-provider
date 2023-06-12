package com.twedoo.volcator.infrastructure.web;

import com.twedoo.volcator.domain.model.color.*;
import com.twedoo.volcator.infrastructure.helper.PageHelper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/color")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColorClassesResource {


    @GET
    @Path("color-classes")
    public Uni<Response> colorClasses(@QueryParam("page") @DefaultValue("0") int page,
                                              @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Color.findAll(), page, size);
    }

    @GET
    @Path("from-color-classes")
    public Uni<Response> fromColorClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(FromColor.findAll(), page, size);
    }

    @GET
    @Path("gradient-direction-classes")
    public Uni<Response> gradientDirectionClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(GradientDirection.findAll(), page, size);
    }
    @GET
    @Path("to-color-classes")
    public Uni<Response> toColorClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(ToColor.findAll(), page, size);
    }

    @GET
    @Path("via-color-classes")
    public Uni<Response> viaColorClasses(@QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(ViaColor.findAll(), page, size);
    }
}
