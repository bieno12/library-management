package com.zeyad.crud;

public class ItemNotFoundException extends LibraryManagementException{
    public ItemNotFoundException(String message)
    {
        super(message);
    }
}
