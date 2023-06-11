package com.twedoo.volcator.infrastructure.helper;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheQuery;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public class PageHelper {

    public static  <T> Uni<Response> pageDetailResponse (ReactivePanacheQuery<T> query, int page, int size){
        var count = query.count();
        var hasNext = query.page(Page.of(page, size)).hasNextPage();
        var list = query.page(Page.of(page, size)).list();
        return Uni.combine()
                .all().unis(list, hasNext, count).asTuple()
                .onItem().transform(objects -> {
                    return PageDetail.builder()
                            .items(objects.getItem1())
                            .hasNext(objects.getItem2())
                            .count(objects.getItem3())
                            .build();
                })
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }
}
