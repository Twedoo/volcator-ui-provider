package com.twedoo.volcator.infrastructure.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCommand {
    private String collection;
    private String id;
    private String label;
    private String value;
}
