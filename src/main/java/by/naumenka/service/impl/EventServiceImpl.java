package by.naumenka.service.impl;

import by.naumenka.dao.EventDao;
import by.naumenka.exception.EventNotFoundException;
import by.naumenka.model.Event;
import by.naumenka.service.EventService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final Log LOGGER = LogFactory.getLog(EventServiceImpl.class);

    private final EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event getById(long id) {
        LOGGER.info("getById event " + id);
        return eventDao.readEvent(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        LOGGER.info("getEventsByTitle " + title);
        return eventDao.getAllEvents()
                .stream()
                .filter(event -> event.getTitle().equals(title))
                .sorted(Comparator.comparing(Event::getId))
                .skip(((long) pageSize * pageNum) - pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        LOGGER.info("getEventsForDay " + day);
        int dayOfWeekFromDate = getDayOfWeekFromDate(day);

        return eventDao.getAllEvents()
                .stream()
                .filter(event -> {
                    int dayOfWeekFromDateEvent = getDayOfWeekFromDate(event.getDate());
                    return dayOfWeekFromDateEvent == dayOfWeekFromDate;
                })
                .sorted(Comparator.comparing(Event::getId))
                .skip(((long) pageSize * pageNum) - pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    private int getDayOfWeekFromDate(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public Event createEvent(Event event)  {
        Optional<Long> maxId = eventDao.getAllEvents().stream()
                .max(Comparator.comparing(Event::getId))
                .map(Event::getId);

        if (maxId.isPresent()) {
            event.setId(maxId.get() + 1L);
        } else {
            event.setId(1L);
        }
        LOGGER.info("created event " + event);
        return eventDao.createEvent(event);
    }

    @Override
    public Event updateEvent(long id, Event event) {
        LOGGER.info("updateEvent " + event);
        eventDao.getAllEvents().stream()
                .max(Comparator.comparing(Event::getId))
                .map(Event::getId)
                .ifPresent(event::setId);

        return eventDao.updateEvent(id, event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        LOGGER.info("deleteEvent " + eventId);
        Event event = eventDao.deleteEvent(eventId);
        return event != null;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }
}