package com.sds.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String contents;
    private Boolean isDone;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


}
