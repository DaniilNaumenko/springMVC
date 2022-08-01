package by.naumenka.service.impl;

import by.naumenka.dao.EventDao;
import by.naumenka.exception.EventNotFoundException;
import by.naumenka.model.Event;
import by.naumenka.service.EventService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
    public Event createEvent(Event event) throws EventNotFoundException {
        LOGGER.info("createEvent " + event);
        Long maxId = eventDao.getAllEvents().stream()
                .max(Comparator.comparing(Event::getId))
                .map(Event::getId)
                .orElseThrow(() -> new EventNotFoundException("There are no events in storage"));

        event.setId(maxId + 1);
        eventDao.createEvent(event);
        return eventDao.readEvent(event.getId());
    }

    @Override
    public Event updateEvent(long id, Event event) {
        LOGGER.info("updateEvent " + event);
        eventDao.updateEvent(id, event);
        return eventDao.readEvent(id);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        LOGGER.info("deleteEvent " + eventId);
        Event event = eventDao.deleteEvent(eventId);
        return event != null;
    }
}