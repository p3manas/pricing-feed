package com.tigeranalytics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
    int totalPages;
    int currentPage;
    int pages;
    boolean hasPreviousPage;
    boolean hasNextPage;
}
