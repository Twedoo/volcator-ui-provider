package com.twedoo.volcator.domain.model.sizing;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "min-width-classes")
public class MinWidth extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
