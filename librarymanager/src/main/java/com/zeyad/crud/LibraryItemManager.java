package com.zeyad.crud;

import com.zeyad.models.LibraryItem;

public interface LibraryItemManager {


    public <T extends LibraryItem> void addLibraryItem(T item) throws ItemAlreadyExistsException;
    public LibraryItem getLibraryItem(int id) throws ItemNotFoundException;
    public <T extends LibraryItem> void updateLibraryItem(int id, T item) throws ItemNotFoundException;
    public LibraryItem removeLibraryItem(int id) throws ItemNotFoundException;

}
