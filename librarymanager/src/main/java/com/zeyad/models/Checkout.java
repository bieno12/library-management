package com.zeyad.models;

import java.util.Date;

public class Checkout {
    private int clientId;
    private int libraryItemId;
    private Date checkoutDate;
    private Date returnDate;
    public Checkout(int clientId, int libraryItemId) {
        this.clientId = clientId;
        this.libraryItemId = libraryItemId;
        checkoutDate = new Date();

        returnDate = null;
    }
    public Checkout(int clientId, int libraryItemId, Date checkoutDate) {
        this.clientId = clientId;
        this.libraryItemId = libraryItemId;
        this.checkoutDate = checkoutDate;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getLibraryItemId() {
        return libraryItemId;
    }
    public void setLibraryItemId(int libraryItemId) {
        this.libraryItemId = libraryItemId;
    }
    public Date getCheckoutDate() {
        return checkoutDate;
    }
    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    public Date getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void markReturned()
    {
        returnDate = new Date();
    }
    
    public boolean isReturned()
    {
        return returnDate != null;
    }
    @Override
    public String toString() {
        return String.format("Checkout(client=%d, item=%d, checkoutDate=%s, returnDate=%s)"
                , clientId, libraryItemId, checkoutDate, returnDate == null ? "Not yet returned" : returnDate);
    }

    
}
