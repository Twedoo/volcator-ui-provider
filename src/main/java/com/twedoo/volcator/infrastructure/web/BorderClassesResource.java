package com.twedoo.volcator.infrastructure.web;


import com.twedoo.volcator.domain.model.border.Border;
import com.twedoo.volcator.domain.model.border.BorderColor;
import com.twedoo.volcator.domain.model.border.BorderFocus;
import com.twedoo.volcator.domain.model.border.BorderRing;
import com.twedoo.volcator.infrastructure.helper.PageHelper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



@Path("/border")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BorderClassesResource {


    @GET
    @Path("border-classes")
    public Uni<Response> borderClasses(@QueryParam("page") @DefaultValue("0") int page,
                                              @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Border.findAll(), page, size);

    }

    @GET
    @Path("border-color-classes")
    public Uni<Response> borderColorClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(BorderColor.findAll(), page, size);

    }

    @GET
    @Path("border-focus-color-classes")
    public Uni<Response> borderFocusColorClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(BorderFocus.findAll(), page, size);

    }
    @GET
    @Path("border-ring-classes")
    public Uni<Response> borderRingClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(BorderRing.findAll(), page, size);

    }
}
