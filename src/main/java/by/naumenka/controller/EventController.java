package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import org.springframework.stereotype.Controller;

@Controller
public class EventController {

    private final BookingFacade bookingFacade;

    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }
}
