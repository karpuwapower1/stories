package com.funfic.karpilovich.service.util;

public enum SortingType {

    RAITING("name"), LAST_UPDATE("id"), AUTHOR("user.firstName"), NONE("none"), NAME("name");

    private String sortingColumn;

    private SortingType(String sortingColumn) {
        this.sortingColumn = sortingColumn;
    }

    public String getSortingColumn() {
        return sortingColumn;
    }
}