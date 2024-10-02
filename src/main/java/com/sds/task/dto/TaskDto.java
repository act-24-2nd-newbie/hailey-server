package com.sds.task.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
    private String contents;
    private Boolean isDone;
    private Integer id;
    private LocalDateTime modifiedDate;
    private LocalDateTime createDate;
}
