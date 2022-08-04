package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import by.naumenka.model.Event;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/events")
public class EventController {

    private final BookingFacade bookingFacade;
    public static final String TEMPLATE = "eventPage";

    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createEvent(@RequestBody Event event) {
        bookingFacade.createEvent(event);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateEvent(@RequestBody Event event, @PathVariable long id) {
        bookingFacade.updateEvent(id, event);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @GetMapping("/getById/{id}")
    public ModelAndView getEventById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        Event eventById = bookingFacade.getEventById(id);
        if (Objects.nonNull(eventById)) {
            modelAndView.addObject("eventModel", eventById);
        } else {
            modelAndView.addObject("eventModel", "Event not found with id = " + id);
        }
        return modelAndView;
    }

    @GetMapping("/getByTitle/{title}")
    public ModelAndView getEventsByTitle(@PathVariable String title,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Event> eventsByTitle = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        if (Objects.nonNull(eventsByTitle)) {
            modelAndView.addObject("eventModel", eventsByTitle);
        } else {
            modelAndView.addObject("eventModel", "Event not found with title = " + title);
        }
        return modelAndView;
    }

    @GetMapping("/getByDay/{day}")
    public ModelAndView getEventsForDay(@PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy") Date day,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Event> eventsByDay = bookingFacade.getEventsForDay(day, pageSize, pageNum);
        if (Objects.nonNull(eventsByDay)) {
            modelAndView.addObject("eventModel", eventsByDay);
        } else {
            modelAndView.addObject("eventModel", "Event not found by day = " + day);
        }
        return modelAndView;
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

    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Event> allEvents = bookingFacade.getAllEvents();
        modelAndView.addObject("eventModel", allEvents);

        return modelAndView;
    }
}
