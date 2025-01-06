package com.playwring.utils.api.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Books {

    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String publish_date;
    private String publisher;
    private int pages;
    private String description;
    private String website;

    @Override
    public String toString() {
        return "\n {" +
               "\n isbn: " + isbn  +
               ", \n title: " + title +
               ", \n subTitle: " + subTitle +
               ", \n author: " + author +
               ", \n publish_date: " + publish_date +
               ", \n publisher: " + publisher +
               ", \n pages: " + pages +
               ", \n description: " + description +
               ", \n website: " + website +
               "\n }";
    }
}
