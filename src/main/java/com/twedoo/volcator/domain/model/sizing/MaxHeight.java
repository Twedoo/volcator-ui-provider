package com.twedoo.volcator.domain.model.sizing;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "max-height-classes")
@EqualsAndHashCode(callSuper=false)
public class MaxHeight extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
