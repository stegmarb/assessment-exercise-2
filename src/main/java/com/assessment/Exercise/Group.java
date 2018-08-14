package com.assessment.Exercise;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
class Group<T> {
    private String groupId;
    @Singular
    private List<? extends T> things;
}
