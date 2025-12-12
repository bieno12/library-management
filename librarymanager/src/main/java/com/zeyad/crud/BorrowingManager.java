package com.zeyad.crud;

public interface BorrowingManager {
    public void borrowItem(int clientId, int libraryItemId) throws ItemNotFoundException, LibraryManagementException;

    public void returnItem(int clientId, int libraryItemId) throws ItemNotFoundException, LibraryManagementException;
}
