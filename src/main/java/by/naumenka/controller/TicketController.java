package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {

    private final BookingFacade bookingFacade;

    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }
}
