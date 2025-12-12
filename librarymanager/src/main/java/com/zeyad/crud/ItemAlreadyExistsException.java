package com.zeyad.crud;

public class ItemAlreadyExistsException extends LibraryManagementException{
    public ItemAlreadyExistsException(String message)
    {
        super(message);
    }
}
