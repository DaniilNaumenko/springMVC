package by.naumenka.dao;

import by.naumenka.model.Event;

import java.util.List;

public interface EventDao {

    Event createEvent(Event event);

    Event readEvent(long id);

    Event updateEvent(long id,Event event);

    Event deleteEvent(long id);

    List<Event> getAllEvents();
}