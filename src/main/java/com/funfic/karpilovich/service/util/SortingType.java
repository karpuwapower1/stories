package com.funfic.karpilovich.service.util;

public enum SortingType {

    RAITING("id"), LAST_UPDATE("updated"), AUTHOR("user.firstName"), NONE("none"), NAME("name"), DATE_POSTED("created");

    private String sortingColumn;

    private SortingType(String sortingColumn) {
        this.sortingColumn = sortingColumn;
    }

    public String getSortingColumn() {
        return sortingColumn;
    }
}