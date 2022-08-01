package by.naumenka;

import by.naumenka.exception.EventNotFoundException;
import by.naumenka.exception.TicketNotFoundException;
import by.naumenka.exception.UserNotFoundException;
import by.naumenka.facade.BookingFacade;
import by.naumenka.facade.BookingFacadeImpl;
import by.naumenka.model.Event;
import by.naumenka.model.Ticket;
import by.naumenka.model.User;
import by.naumenka.model.impl.EventImpl;
import by.naumenka.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import by.naumenka.storage.Storage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static by.naumenka.model.Ticket.Category.*;
import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    ApplicationContext context;
    Storage storage;
    BookingFacade bookingFacade;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        storage = context.getBean(Storage.class);
        bookingFacade = context.getBean(BookingFacadeImpl.class);
    }

    @Test
    public void integrationTest() throws UserNotFoundException, EventNotFoundException, TicketNotFoundException {
        User user1 = bookingFacade.createUser(new UserImpl("name1", "email1"));
        User user2 = bookingFacade.createUser(new UserImpl("name2", "email2"));
        User user3 = bookingFacade.createUser(new UserImpl("name3", "email3"));

        Event event1 = bookingFacade.createEvent(new EventImpl("event1", new Date()));
        Event event2 = bookingFacade.createEvent(new EventImpl("event2", new Date()));

        Ticket ticket1 = bookingFacade.bookTicket(user1.getId(), event1.getId(), 1, BAR);
        Ticket ticket2 = bookingFacade.bookTicket(user1.getId(), event1.getId(), 2, PREMIUM);

        Ticket ticket3 = bookingFacade.bookTicket(user2.getId(), event2.getId(), 15, STANDARD);
        Ticket ticket4 = bookingFacade.bookTicket(user2.getId(), event2.getId(), 16, STANDARD);
        Ticket ticket5 = bookingFacade.bookTicket(user2.getId(), event1.getId(), 5, STANDARD);

        Ticket ticket6 = bookingFacade.bookTicket(user3.getId(), event1.getId(), 11, BAR);
        Ticket ticket7 = bookingFacade.bookTicket(user3.getId(), event2.getId(), 3, PREMIUM);
        Ticket ticket8 = bookingFacade.bookTicket(user3.getId(), event2.getId(), 4, PREMIUM);

        List<Ticket> expectedTicketsByUser1 = Arrays.asList(ticket1, ticket2);
        List<Ticket> actualTicketsByUser = bookingFacade.getBookedTickets(user1, 10, 1);

        List<Ticket> expectedTicketsByEvent1 =
                Arrays.asList(ticket1, ticket2, ticket5, ticket6);
        List<Ticket> actualTicketsByEvent = bookingFacade.getBookedTickets(event1, 10, 1);

        assertEquals(expectedTicketsByUser1, actualTicketsByUser);
        assertEquals(expectedTicketsByEvent1, actualTicketsByEvent);
    }

    @Test
    public void integrationTest2() throws UserNotFoundException {
        User user = bookingFacade.createUser(new UserImpl("nameTest", "emailTest"));

        System.out.println(user.getId());

    }
}
