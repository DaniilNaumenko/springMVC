package by.naumenka.service;

import by.naumenka.exception.TicketNotFoundException;
import  by.naumenka.model.Event;
import  by.naumenka.model.Ticket;
import  by.naumenka.model.User;

import java.util.List;

public interface TicketService {

    Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean cancelTicket(long ticketId);

    List<Ticket> getAllTickets();
}