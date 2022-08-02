package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import by.naumenka.model.Event;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final BookingFacade bookingFacade;
    public static final String TEMPLATE = "eventPage";

    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/{id}")
    public ModelAndView getEventById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        Event eventById = bookingFacade.getEventById(id);
        if (eventById != null) {
            modelAndView.addObject("eventModel", eventById);
        } else {
            modelAndView.addObject("eventModel", "Event not found with id = " + id);
        }
        return modelAndView;
    }

    @GetMapping("/{title}")
    public ModelAndView getEventsByTitle(@PathVariable String title,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Event> eventsByTitle = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        if (eventsByTitle != null) {
            modelAndView.addObject("eventModel", eventsByTitle);
        } else {
            modelAndView.addObject("eventModel", "Event not found with title = " + title);
        }
        return modelAndView;
    }

    @GetMapping("/{day}")
    public ModelAndView getEventsForDay(@PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy") Date day,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Event> eventsByDay = bookingFacade.getEventsForDay(day, pageSize, pageNum);
        if (eventsByDay != null) {
            modelAndView.addObject("eventModel", eventsByDay);
        } else {
            modelAndView.addObject("eventModel", "Event not found by day = " + day);
        }
        return modelAndView;
    }

    @SneakyThrows
    @PostMapping("/new")
    public Event createEvent(@RequestBody Event event) {
        return bookingFacade.createEvent(event);
    }

    @PostMapping("/update/{id}")
    public Event updateEvent(@RequestBody Event event, @PathVariable long id) {
        return bookingFacade.updateEvent(id, event);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteEvent(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        boolean isEventDeleted = bookingFacade.deleteEvent(id);
        if (isEventDeleted) {
            modelAndView.addObject("eventModel", "Event is delete by id = " + id);
        } else {
            modelAndView.addObject("eventModel", "Error! Event wasn't delete by id = " + id);
        }
        return modelAndView;
    }
}
