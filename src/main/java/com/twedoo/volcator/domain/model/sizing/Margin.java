package com.twedoo.volcator.domain.model.sizing;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "margin-classes")
@EqualsAndHashCode(callSuper=false)
public class Margin extends ReactivePanacheMongoEntity {
    private String label;
    private String value;
}
