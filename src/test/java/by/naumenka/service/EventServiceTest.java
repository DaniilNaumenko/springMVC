package by.naumenka.service;

import by.naumenka.exception.EventNotFoundException;
import by.naumenka.model.Event;
import by.naumenka.model.impl.EventImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import by.naumenka.storage.Storage;

import java.util.Date;

import static org.junit.Assert.*;

public class EventServiceTest {

    ApplicationContext context;
    EventService eventService;
    Event event;
    Storage storage;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        eventService = context.getBean(EventService.class);
        storage = context.getBean("storage", Storage.class);
        event = Mockito.mock(Event.class);
    }

    @Test
    public void getEventByIdTest() {
        Event eventById = eventService.getById(1);

        assertNotNull(eventById);
        assertEquals(1, eventById.getId());
    }

    @Test
    public void getEventByTitleTest() {
        assertNotNull(eventService.getEventsByTitle("Title1", 1, 1));
    }

    @Test
    public void createEventTest() throws EventNotFoundException  {
        Event event = new EventImpl("Title1", new Date());

        Assert.assertEquals(event, eventService.createEvent(event));
    }

    @Test
    public void updateEventTest() {
        Event updated = new EventImpl("update", new Date());
        updated.setId(1L);

        assertEquals(updated, eventService.updateEvent(1L, updated));
    }

    @Test
    public void deleteEventTest() {
        Assert.assertTrue(eventService.deleteEvent(1));
    }
}