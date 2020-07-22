package com.funfic.karpilovich.controller.constant;

public enum LinkRel {

    AUTHOR("author"), MAIN_PAGE("main_page"), POPULAR("popular"), LAST_UPDATE("update"), ADD_BOOK("add_book"),
    LOGIN("login"), LOGOUT("logout"), REGISTER("register"), ACTIVATE("activation"), GENRE("genre"),
    TAG("tag");

    private String name;

    LinkRel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}