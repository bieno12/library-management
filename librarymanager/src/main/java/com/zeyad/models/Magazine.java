package com.zeyad.models;

public class Magazine extends LibraryItem {

    public Magazine(Integer id, String title) {
        super(id, title);
    }

    @Override
    public String getItemDetails() {
        StringBuilder builder = new StringBuilder("Magazine(title=");

        builder.append("\"" + getTitle() + "\", id=");
        builder.append(getId()).append(")");
        return builder.toString();

    }
    

}
