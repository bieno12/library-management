package com.zeyad.crud;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.zeyad.models.LibraryItem;
import com.zeyad.models.LibraryItem.StockInfo;
import com.zeyad.models.Checkout;
import com.zeyad.models.Client;

public class Library implements LibraryItemManager, ClientManager, BorrowingManager, StockManager {
    private Map<Integer, LibraryItem> items;
    private Map<Integer, Client> clients;

    private List<Checkout> checkouts;
    private List<LibraryItem.StockInfo> itemsStockInfos;
    public Library() {
        items = new TreeMap<>();
        clients = new TreeMap<>();
        checkouts = new ArrayList<>();
        itemsStockInfos = new ArrayList<>();
    }   

    public void displayLibraryItems()
    {
        for (LibraryItem item : items.values())
            System.out.printf("%s\n", item.getItemDetails());
    }
    public void displayClients()
    {
        for(Client client : clients.values())
            System.out.println(client);
    }
    @Override
    public <T extends LibraryItem> void addLibraryItem(T item) throws ItemAlreadyExistsException {
        if(items.containsKey(item.getId()))
            throw new ItemAlreadyExistsException("Item with id:"+item.getId() +" already exists");
        items.put(item.getId(), item);
        itemsStockInfos.add(item.new StockInfo(1));
    }

    @Override
    public LibraryItem getLibraryItem(int id) throws ItemNotFoundException {
        if(!items.containsKey(id))
            throw new ItemNotFoundException("Library Item with id:"+id+" doesn't exist");
        return items.get(id);
    }
    
    @Override
    public <T extends LibraryItem> void updateLibraryItem(int id, T item) throws ItemNotFoundException {
        if(!items.containsKey(id))
            throw new ItemNotFoundException("Library Item with id:"+id+" doesn't exist");
        items.put(id, item);

    }

    @Override
    public LibraryItem removeLibraryItem(int id) throws ItemNotFoundException {
        if(!items.containsKey(id))
            throw new ItemNotFoundException("Library Item with id:"+id+" doesn't exist");
        itemsStockInfos.removeIf(info -> info.getLibraryItem().getId() == id);
        return items.remove(id);
    }

    //#region ClientManager
    @Override
    public <T extends Client> void addClient(T client) throws ItemAlreadyExistsException {
        if(clients.containsKey(client.getId()))
            throw new ItemAlreadyExistsException("Client with id:"+client.getId()+" already exists");
        clients.put(client.getId(), client);
    }

    @Override
    public Client getClient(int id) throws ItemNotFoundException {
        if(!clients.containsKey(id))
            throw new ItemNotFoundException("Client with id:"+id+" doesn't exist");
        return clients.get(id);
    }

    @Override
    public <T extends Client> void updateClient(int id, T client) throws ItemNotFoundException {
        if(!clients.containsKey(id))
            throw new ItemNotFoundException("Client with id:"+id+" doesn't exist");
        clients.put(client.getId(), client);
    }

    @Override
    public Client removeClient(int id) throws ItemNotFoundException {
        if(!clients.containsKey(id))
            throw new ItemNotFoundException("Client with id:"+id+" doesn't exist");
        return clients.remove(id);
    }
    //#endregion

    //#region BorrowingManager
    
    @Override
    public void borrowItem(int clientId, int libraryItemId) throws LibraryManagementException {
        if(!clients.containsKey(clientId))
            throw new ItemNotFoundException("Client with id:"+clientId+" doesn't exist");

        if(!clients.containsKey(libraryItemId))
            throw new ItemNotFoundException("Item with id:"+libraryItemId+" doesn't exist");

        Optional<Checkout> alreadyBorrowedQery = checkouts.stream()
            .filter(c -> c.getClientId() == clientId
                    && c.getLibraryItemId() == libraryItemId
                    && c.getReturnDate() == null)
            .findAny();
        if(alreadyBorrowedQery.isPresent())
            throw new LibraryManagementException(
                String.format("Client %d, already borrowed item %d", clientId, libraryItemId));
        
        LibraryItem.StockInfo info = itemsStockInfos.stream().filter(s -> s.getLibraryItem().getId() == libraryItemId)
                    .findAny().get();
        if(info.getAmount() == 0)
            throw new LibraryManagementException(String.format("Item %d is out of stock", libraryItemId));
        info.setAmount(info.getAmount() - 1);
        checkouts.add(new Checkout(clientId, libraryItemId));

    }

    @Override
    public void returnItem(int clientId, int libraryItemId) throws LibraryManagementException {
        if(!clients.containsKey(clientId))
            throw new ItemNotFoundException("Client with id:"+clientId+" doesn't exist");

        if(!clients.containsKey(libraryItemId))
            throw new ItemNotFoundException("Item with id:"+libraryItemId+" doesn't exist");
        Optional<Checkout> alreadyBorrowedQery = checkouts.stream()
            .filter(c -> c.getClientId() == clientId
                    && c.getLibraryItemId() == libraryItemId
                    && c.getReturnDate() == null)
            .findAny();

        if(alreadyBorrowedQery.isEmpty())
            throw new LibraryManagementException(
                String.format("Client %d, didn't borrow item %d lately", clientId, libraryItemId));
        LibraryItem.StockInfo info = itemsStockInfos.stream()
                .filter(s -> s.getLibraryItem().getId() == libraryItemId)
                .findAny().get();
        info.setAmount(info.getAmount() + 1);
        alreadyBorrowedQery.get().markReturned();
    }

    //#endregion

    //#region StockManager

    @Override
    public void restock(int libraryItemId, int amount) throws LibraryManagementException {

        LibraryItem.StockInfo stockInfo = itemsStockInfos.stream().filter(info -> info.getLibraryItem().getId() == libraryItemId)
                .findAny()
                .orElseThrow(() -> new ItemNotFoundException("Library Item with id:"+libraryItemId+" doesn't exist"));
        stockInfo.setAmount(amount);
    }

    @Override
    public StockInfo getStockInfo(int libraryItemId) throws LibraryManagementException {
        return itemsStockInfos.stream().filter(info -> info.getLibraryItem().getId() == libraryItemId)
                .findAny()
                .orElseThrow((
                    () -> new ItemNotFoundException("Item with id:"+libraryItemId+" doesn't exist"))
                );
    }

    public void displayStockInfo()
    {
        for(StockInfo stockInfo : itemsStockInfos)
        {
            System.out.println(stockInfo);
        }
    }
    public void displayCheckouts()
    {
        for(Checkout checkout : checkouts)
            System.out.println(checkout);
    }
    //#endregion

}
