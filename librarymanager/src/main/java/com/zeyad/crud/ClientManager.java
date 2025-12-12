package com.zeyad.crud;

import com.zeyad.models.Client;

public interface ClientManager {


    public <T extends Client> void addClient(T client) throws ItemAlreadyExistsException;
    public Client getClient(int id) throws ItemNotFoundException;
    public <T extends Client> void updateClient(int id, T client) throws ItemNotFoundException;
    public Client removeClient(int id) throws ItemNotFoundException;

}
