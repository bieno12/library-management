package com.zeyad.crud;

import com.zeyad.models.LibraryItem;

public interface StockManager {
    public LibraryItem.StockInfo getStockInfo(int libraryItemId) throws LibraryManagementException;
    public void restock(int libraryItemId, int amount) throws LibraryManagementException;

}
