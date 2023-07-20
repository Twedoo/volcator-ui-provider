package com.twedoo.volcator.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequestCommand {
    private Document page;
    private String collection;
    private String id;
}
