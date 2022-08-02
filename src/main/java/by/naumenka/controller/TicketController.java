package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import by.naumenka.model.Event;
import by.naumenka.model.Ticket;
import by.naumenka.model.User;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private static final String TEMPLATE = "ticketPage";
    private final BookingFacade bookingFacade;

    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @SneakyThrows
    @PostMapping("/new")
    public Ticket bookTicket(@RequestBody Ticket ticket) {
        return bookingFacade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
    }

    @GetMapping("/byUser")
    public ModelAndView getBookedTicketsByUser(@RequestBody User user,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize,
                                               @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
        if (bookedTickets != null) {
            modelAndView.addObject("ticketModel", bookedTickets);
        } else {
            modelAndView.addObject("ticketModel", "Tickets not found for user = " + user);
        }
        return modelAndView;
    }

    @GetMapping("/byEvent")
    public ModelAndView getBookedTicketsByEvent(@RequestBody Event event,
                                                @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);
        if (bookedTickets != null) {
            modelAndView.addObject("ticketModel", bookedTickets);
        } else {
            modelAndView.addObject("ticketModel", "Tickets not found for event = " + event);
        }
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        boolean isTicketDeleted = bookingFacade.cancelTicket(id);
        if (isTicketDeleted) {
            modelAndView.addObject("ticketModel", "Ticket is delete by id = " + id);
        } else {
            modelAndView.addObject("ticketModel", "Error! Ticket wasn't delete by id = " + id);
        }
        return modelAndView;
    }
}
