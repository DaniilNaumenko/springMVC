package by.naumenka.dao;

import by.naumenka.model.Ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TicketDaoTest {

    ApplicationContext applicationContext;
    TicketDao ticketDao;
    Ticket ticket;

    @Before
    public void setup() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ticketDao = applicationContext.getBean("ticketDao", TicketDao.class);

        ticket = Mockito.mock(Ticket.class);
        ticketDao.createTicket(ticket);
    }

    @Test
    public void createTicketTest() {
        Assert.assertEquals(ticket, ticketDao.readTicket(ticket.getId()));
    }

    @Test
    public void readTicketTest() {
        Assert.assertEquals(ticket, ticketDao.readTicket(ticket.getId()));
    }

    @Test
    public void updateTicketTest() {
        Assert.assertEquals(ticket, ticketDao.updateTicket(ticket.getId(), ticket));
    }

    @Test
    public void deleteTicketTest() {
        Assert.assertEquals(ticket, ticketDao.deleteTicket(ticket.getId()));
    }

    @Test
    public void getAllTicketsTest() {
        Assert.assertNotNull(ticketDao.getAllTickets());
    }
}
