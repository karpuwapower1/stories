package com.funfic.karpilovich.controller.constant;

public enum LinkRel {

    AUTHOR("author"), MAIN_PAGE("main_page"), POPULAR("popular"), UPDATE("update"), ADD_BOOK("add_book"),
    LOGIN("login"), LOGOUT("logout"), REGISTER("register"), ACTIVATE("activation"), DELETE("delete"), GENRE("genre"),
    TAG("tag"), BOOK("book");

    private String name;

    LinkRel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}