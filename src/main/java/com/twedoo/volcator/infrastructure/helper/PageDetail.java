package com.twedoo.volcator.infrastructure.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDetail {
    Object items;
    long count;
    boolean hasNext;
}
