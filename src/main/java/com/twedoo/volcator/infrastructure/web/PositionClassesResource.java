package com.twedoo.volcator.infrastructure.web;

import com.twedoo.volcator.domain.model.position.Float;
import com.twedoo.volcator.domain.model.position.ObjectPosition;
import com.twedoo.volcator.domain.model.position.PlacementTrblPosition;
import com.twedoo.volcator.domain.model.position.Position;
import com.twedoo.volcator.infrastructure.helper.PageHelper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/position")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PositionClassesResource {


    @GET
    @Path("float-classes")
    public Uni<Response> floatClasses(@QueryParam("page") @DefaultValue("0") int page,
                                              @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Float.findAll(), page, size);

    }

    @GET
    @Path("object-position-classes")
    public Uni<Response> objectPositionClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(ObjectPosition.findAll(), page, size);

    }

    @GET
    @Path("placement-trbl-position-classes")
    public Uni<Response> placementTrblPositionClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(PlacementTrblPosition.findAll(), page, size);

    }
    @GET
    @Path("position-classes")
    public Uni<Response> positionClasses(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("5") int size) {
        return PageHelper.pageDetailResponse(Position.findAll(), page, size);

    }
}
