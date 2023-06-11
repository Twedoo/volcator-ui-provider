package com.twedoo.volcator.domain.model.outline;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "outline-classes")
@EqualsAndHashCode(callSuper=false)
public class Outline extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
