package org.jealvarez.todoapp.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Task {

    private final long id;
    private final String name;
    private final String description;

}
