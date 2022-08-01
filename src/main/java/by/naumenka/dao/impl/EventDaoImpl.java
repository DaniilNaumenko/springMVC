package by.naumenka.dao.impl;

import by.naumenka.dao.EventDao;
import by.naumenka.model.Event;
import by.naumenka.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {

    private final Storage storage;

    public EventDaoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Event createEvent(Event event) {
        return storage.getEvents().put(event.getId(), event);
    }

    @Override
    public Event readEvent(long id) {
        return storage.getEvents().get(id);
    }

    @Override
    public Event updateEvent(long id,Event event) {
        return storage.getEvents().replace(id, event);
    }

    @Override
    public Event deleteEvent(long id) {
        return storage.getEvents().remove(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<>(storage.getEvents().values());
    }

}