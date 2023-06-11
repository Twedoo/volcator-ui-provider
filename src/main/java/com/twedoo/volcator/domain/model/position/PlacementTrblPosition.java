package com.twedoo.volcator.domain.model.position;

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
@MongoEntity(collection = "placement-trbl-position-classes")
public class PlacementTrblPosition extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
