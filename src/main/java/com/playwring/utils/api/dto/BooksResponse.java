package com.playwring.utils.api.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksResponse {

    private String userId;
    private String username;
    private List<Books> books;

}
