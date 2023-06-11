package com.twedoo.volcator.domain.model.border;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "border-ring-classes")
@EqualsAndHashCode(callSuper=false)
public class BorderRing extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
