package by.naumenka.dao.impl;

import by.naumenka.dao.TicketDao;
import by.naumenka.model.Ticket;
import by.naumenka.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {

    private Storage storage;

    @Override
    public Ticket createTicket(Ticket ticket) {
        return storage.getTickets().put(ticket.getId(), ticket);
    }

    @Override
    public Ticket readTicket(long id) {
        return storage.getTickets().get(id);
    }

    @Override
    public Ticket updateTicket(long id, Ticket ticket) {
        return storage.getTickets().replace(id, ticket);
    }

    @Override
    public Ticket deleteTicket(long id) {
        return storage.getTickets().remove(id);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(storage.getTickets().values());
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}