package com.twedoo.volcator.infrastructure.web;

import com.twedoo.volcator.domain.model.outline.Outline;
import com.twedoo.volcator.domain.model.outline.OutlineFocus;
import com.twedoo.volcator.infrastructure.helper.PageHelper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/outline")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OutlineClassesResource {


    @GET
    @Path("outline-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> outlineClasses(@QueryParam("page") @DefaultValue("0") int page,
                                              @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Outline.findAll(), page, size);

    }

    @GET
    @Path("outline-focus-classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> outlineFocusClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(OutlineFocus.findAll(), page, size);

    }

}
