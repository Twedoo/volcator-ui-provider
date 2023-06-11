package com.twedoo.volcator.domain.model.color;


import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "from-color-classes")
@EqualsAndHashCode(callSuper=false)
public class FromColor extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
