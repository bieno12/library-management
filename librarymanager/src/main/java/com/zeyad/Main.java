package com.zeyad;

import com.zeyad.app.Application;
import com.zeyad.models.Book;
import com.zeyad.models.Client;
import com.zeyad.models.Magazine;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        try {
            app.getLibrary().addClient(new Client(1,"Ahmed Mohamed","ahmedmohamed@gmail.com"));
            app.getLibrary().addClient(new Client(2, "Sara Ali", "sara.ali@example.com"));
            app.getLibrary().addClient(new Client(3, "Omar Hassan", "omar.hassan@example.com"));
            app.getLibrary().addClient(new Client(4, "Laila Mostafa", "laila.mostafa@example.com"));
            
            app.getLibrary().addLibraryItem(new Book(1, "Kokoro"));
            app.getLibrary().addLibraryItem(new Book(2, "Norwegian Wood"));
            app.getLibrary().addLibraryItem(new Magazine(3, "National Geographic"));
            app.getLibrary().addLibraryItem(new Book(4, "The Catcher in the Rye"));
            app.getLibrary().addLibraryItem(new Magazine(5, "Time"));
            
        } catch (Exception e) {
        }
        app.run();
    }
}