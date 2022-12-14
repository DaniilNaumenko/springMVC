package by.naumenka.service.impl;

import by.naumenka.dao.TicketDao;
import by.naumenka.model.Event;
import by.naumenka.model.Ticket;
import by.naumenka.model.User;
import by.naumenka.model.impl.TicketImpl;
import by.naumenka.service.TicketService;
import by.naumenka.service.xml.TicketXml;
import by.naumenka.service.xml.XmlToObjectConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;
    private final XmlToObjectConverter converter;

    public TicketServiceImpl(TicketDao ticketDao, XmlToObjectConverter converter) {
        this.ticketDao = ticketDao;
        this.converter = converter;
    }

    @PostConstruct
    public void init(){
        preloadTickets();
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Optional<Long> maxId = ticketDao.getAllTickets().stream()
                .max(Comparator.comparing(Ticket::getId))
                .map(Ticket::getId);
        Ticket ticket = new TicketImpl(eventId, userId, category, place);
        if (maxId.isPresent()) {
            ticket.setId(maxId.get() + 1L);
        } else {
            ticket.setId(1L);
        }

        return ticketDao.createTicket(ticket);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.info("getBookedTickets by user " + user);
        return ticketDao.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .sorted(Comparator.comparing(Ticket::getId))
                .skip(((long) pageSize * pageNum) - pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("getBookedTickets by event " + event);
        return ticketDao.getAllTickets()
                .stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .sorted(Comparator.comparing(Ticket::getId))
                .skip(((long) pageSize * pageNum) - pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        log.info("cancelTicket " + ticketId);
        Ticket ticket = ticketDao.deleteTicket(ticketId);
        return ticket != null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketDao.getAllTickets();
    }

    @SneakyThrows
    @Override
    public void preloadTickets() {
        List<TicketXml> ticketsXml = converter.unmarshallXML();

        log.info("tickets loaded from xml: " + ticketsXml.toString());
        System.out.println(ticketsXml);

        ticketsXml.forEach(ticket -> bookTicket(
                ticket.getUserId(),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getCategory()));
    }
}