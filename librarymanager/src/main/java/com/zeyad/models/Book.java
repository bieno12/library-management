package com.zeyad.models;

public class Book extends LibraryItem {

    public Book(Integer id, String title) {
        super(id, title);
    }

    @Override
    public String getItemDetails() {
        StringBuilder builder = new StringBuilder("Book(title=");

        builder.append("\"" + getTitle() + "\", id=");
        builder.append(getId()).append(")");
        return builder.toString();

    }
    
}
