package com.mycompany.teamproject9.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
    private int boardId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
}
