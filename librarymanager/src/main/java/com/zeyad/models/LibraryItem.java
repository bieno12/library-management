package com.zeyad.models;


public abstract class LibraryItem implements Comparable<LibraryItem> {
    private Integer id;
    private String title;
    public LibraryItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public abstract String getItemDetails();

    @Override
    public int compareTo(LibraryItem arg0) {
        return this.id - arg0.id;
    }

    // Stock Information for Library item

    public class StockInfo {
        private int amount;
        public StockInfo(int amount)
        {
            this.amount = amount;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }
        public LibraryItem getLibraryItem()
        {
            return LibraryItem.this;
        }
        @Override
        public String toString() {
            return String.format("StockInfo(itemId=%d, amount=%d)", LibraryItem.this.id, amount);
        }

        
    }

    
}
