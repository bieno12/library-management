package com.zeyad.app;

import java.util.Scanner;

import com.zeyad.crud.ItemAlreadyExistsException;
import com.zeyad.crud.ItemNotFoundException;
import com.zeyad.crud.Library;
import com.zeyad.crud.LibraryManagementException;
import com.zeyad.models.Book;
import com.zeyad.models.Client;
import com.zeyad.models.LibraryItem;
import com.zeyad.models.Magazine;

public class Application {

    private Library library;
    private boolean running;
    private Scanner sc;
    public Application()
    {
        library = new Library();
        sc = new Scanner(System.in);
    }

    private void printUsageMessage()
    {
        System.out.println("Usage:");
        System.out.println("list_library_items");
        System.out.println("add_book <ID> <Title>");
        System.out.println("add_magazine <ID> <Title>");
        System.out.println("get_library_item <ID>");
        System.out.println("update_book <ID> [--name <Name>]");
        System.out.println("update_magazine <ID> [--name <Name>]");
        System.out.println("delete_library_item <ID>");
        System.out.println("<------->");
        System.out.println("list_clients");
        System.out.println("add_client <ID> <Email> <Name>");
        System.out.println("get_client <ID>");
        System.out.println("update_client <ID> [--email <Email>] [--name <Name>]");
        System.out.println("delete_client <ID> ");
        System.out.println("<------->");
        System.out.println("display_stock");
        System.out.println("borrow_item <clientId> <itemId>");
        System.out.println("return_item <clientId> <itemId>");
        System.out.println("restock <ItemId> <Amount>");
        System.out.println("display_checkouts");


        System.out.println("help");
    }
    public void run()
    {
        running = true;

        printUsageMessage();
        while (running) {
            System.out.print("->");
            String line = sc.nextLine();
            String[] args = new ArgumentTokenizer(line).split();
            if(args.length == 0)
                continue;

            try {
                handleCommand(args[0], args);
            } catch (BadCommandUsageException | LibraryManagementException e) {
                System.err.println(args[0]+": "+e.getMessage());
            }
        }
    }

    private void handleCommand(String command, String[] args) throws BadCommandUsageException, LibraryManagementException {
        switch (command) {
            case "list_library_items":
                listLibraryItems(args);
                break;
                
            case "add_book":
                addBook(args);
                break;
            case "add_magazine":
                addMagazine(args);
                break;
                
            case "get_library_item":
                getLibraryItem(args);
                break;
                
            case "update_book":
                updateBook(args);
                break;
                
            case "update_magazine":
                updateMagazine(args);
                break;
                
            case "delete_library_item":
                listLibraryItems(args);
                break;
                
            case "list_clients":
                listClients(args);
                break;
                
            case "add_client":
                addClient(args);
                break;
                
            case "get_client":
                getClient(args);
                break;
                
            case "update_client":
                updateClient(args);
                break;
                
            case "delete_client":
                deleteClient(args);
                break;
            case "restock":
                restock(args);
                break;
            case "display_stock":
                displayStock(args);
                break;
            case "borrow_item":
                borrowItem(args);
                break;
            case "return_item":
                returnItem(args);
                break;
            case "display_checkouts":
                library.displayCheckouts();
                break;
            case "help":
                printUsageMessage();
                break;
            default:
                System.err.println("invalid Command");
                break;
        }
    }

    



    private void returnItem(String[] args) throws BadCommandUsageException, LibraryManagementException {
        if(args.length != 3)
            throw new BadCommandUsageException("Illegal number of arguments");
        int clientId;
        try {
            clientId = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad clientId");
        }
        int libraryItemId;
        try {
            libraryItemId = Integer.parseInt(args[2]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad itemId");
        }

        library.returnItem(clientId, libraryItemId);
    }

    private void borrowItem(String[] args) throws BadCommandUsageException, LibraryManagementException {
        if(args.length != 3)
            throw new BadCommandUsageException("Illegal number of arguments");
        int clientId;
        try {
            clientId = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad clientId");
        }
        int libraryItemId;
        try {
            libraryItemId = Integer.parseInt(args[2]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad itemId");
        }

        library.borrowItem(clientId, libraryItemId);
    }

    private void displayStock(String[] args) {
        library.displayStockInfo();
    }

    private void restock(String[] args) throws BadCommandUsageException, LibraryManagementException {
        if(args.length != 3)
            throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
        }
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad amount");
        }
        library.restock(id, amount);
    }

    private void deleteClient(String[] args) throws BadCommandUsageException, ItemNotFoundException {
        if(args.length != 2)
            throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
        }
        library.removeClient(id);
    }

    private void updateClient(String[] args) throws BadCommandUsageException, ItemNotFoundException {
        if(args.length < 2 || args.length %2 != 0 || args.length > 6)
            throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
        }
        String name = null, email = null;
        for(int argIndex = 2; argIndex < args.length; argIndex++)
        {
            if(args[argIndex].equals( "--name"))
            {
                if(!isValidName(args[argIndex+1]))
                    throw new BadCommandUsageException("bad name");
                name = args[argIndex+1];
            }
            else if (args[argIndex].equals("--email"))
            {
                if(!isValidEmail(args[argIndex+1]))
                    throw new BadCommandUsageException("bad email");
                email = args[argIndex+1];   
            }
        }
        Client client = library.getClient(id);
        client.setName(name != null ? name : client.getName());
        client.setEmail(email != null ? email : client.getEmail());
        library.updateClient(id, client);

    }


    private void addClient(String[] args) throws BadCommandUsageException, ItemAlreadyExistsException {
        if(args.length != 4)
            throw new BadCommandUsageException("Illegal number of arguments");

        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
            
        }

        String name = args[2];
        if(!isValidName(name))
            throw new BadCommandUsageException("Bad name");
        
        String email = args[3];
        if(!isValidEmail(email))
            throw new BadCommandUsageException("Bad email");

        library.addClient(new Client(id, name, email));
    }

    private void listClients(String[] args) throws BadCommandUsageException {
        if(args.length != 1)
            throw new BadCommandUsageException("Illegal number of arguments");
        library.displayClients();
    }

    private void getClient(String[] args) throws BadCommandUsageException, ItemNotFoundException {
        if(args.length != 2)
            throw new BadCommandUsageException("Illegal number of arguments");

        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
            
        }
        System.out.println(library.getClient(id));
    }

    private void updateMagazine(String[] args) throws BadCommandUsageException, ItemNotFoundException {
        if (args.length != 3)
            throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
            
        }

        String name = args[2];
        if(!isValidName(name))
            throw new BadCommandUsageException("Bad name");

        LibraryItem item = library.getLibraryItem(id);
        if(!(item instanceof Magazine))
            throw new BadCommandUsageException("Library Item with id:"+id+" is not a Magazine");
        Magazine book = (Magazine)item;
        book.setTitle(name);
        library.updateLibraryItem(id, book);   
    }
    private void updateBook(String[] args) throws BadCommandUsageException, ItemNotFoundException {
        if (args.length != 3)
            throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
            
        }

        String name = args[2];
        if(!isValidName(name))
            throw new BadCommandUsageException("Bad name");

        LibraryItem item = library.getLibraryItem(id);
        if(!(item instanceof Book))
            throw new BadCommandUsageException("Library Item with id:"+id+" is not a Book");
        Book book = (Book)item;
        book.setTitle(name);
        library.updateLibraryItem(id, book);
    }

    private void getLibraryItem(String[] args) throws BadCommandUsageException, ItemNotFoundException {
        if (args.length != 2)
            throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
        }
        System.out.println(library.getLibraryItem(id));
    }

    private void addMagazine(String[] args) throws BadCommandUsageException, ItemAlreadyExistsException {
        if (args.length != 3)
                throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
            
        }

        String name = args[2];
        if(!isValidName(name))
            throw new BadCommandUsageException("Bad name");
        library.addLibraryItem(new Magazine(id, name));
    }

    private void addBook(String[] args) throws BadCommandUsageException, ItemAlreadyExistsException {
        if (args.length != 3)
                throw new BadCommandUsageException("Illegal number of arguments");
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new BadCommandUsageException("Bad Id");
            
        }

        String name = args[2];
        if(!isValidName(name))
            throw new BadCommandUsageException("Bad name");
        library.addLibraryItem(new Book(id, name));
    }

    private void listLibraryItems(String[] args) throws BadCommandUsageException {
        if(args.length != 1)
            throw new BadCommandUsageException("Illegal number of arguments");
        library.displayLibraryItems();
    }

    private boolean isValidName(String name)
    {
        name = name.strip();
        return name.length() > 1 && name.chars().allMatch(c -> Character.isAlphabetic(c) || c==' ');
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public Library getLibrary() {
        return library;
    }

}

class BadCommandUsageException extends Exception {
    public BadCommandUsageException(String message)
    {
        super(message);
    }
}