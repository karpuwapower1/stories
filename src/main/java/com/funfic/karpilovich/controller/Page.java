package com.funfic.karpilovich.controller;

public enum Page {

    REGISTRATION("registration", "/registration"), LOGIN("login", "/login"), USERES("users", "/users");

    private final String name;
    private final String path;

    Page(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}