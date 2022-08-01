package by.naumenka.service;

import by.naumenka.exception.TicketNotFoundException;
import by.naumenka.model.Event;
import by.naumenka.model.Ticket;
import by.naumenka.model.User;
import by.naumenka.model.impl.TicketImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import by.naumenka.storage.Storage;

public class TicketServiceTest {

    ApplicationContext context;
    TicketService ticketService;
    Ticket ticket;
    Storage storage;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ticketService = context.getBean(TicketService.class);
        storage = context.getBean("storage", Storage.class);
        ticket = Mockito.mock(Ticket.class);
    }

    @Test
    public void bookTicketTest() throws TicketNotFoundException {
        Ticket ticket = new TicketImpl(1, 1, Ticket.Category.BAR, 1);
        ticket.setId(7);

        Assert.assertEquals(ticket, ticketService.bookTicket(1, 1, 1, Ticket.Category.BAR));
    }

    @Test
    public void getBookedTicketsByUserTest(){
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(1L);
        Assert.assertEquals(2, ticketService.getBookedTickets(user, 10, 1).size());
    }

    @Test
    public void getBookedTicketsByEventTest(){
        Event event = Mockito.mock(Event.class);
        Mockito.when(event.getId()).thenReturn(1L);
        Assert.assertEquals(2, ticketService.getBookedTickets(event, 10, 1).size());
    }


    @Test
    public void cancelTicketTest() {
        Assert.assertTrue(ticketService.cancelTicket(1));
    }
}