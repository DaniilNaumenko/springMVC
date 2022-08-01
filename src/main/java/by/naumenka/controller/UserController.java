package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import by.naumenka.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    private final BookingFacade bookingFacade;
    public static final String TEMPLATE = "userPage";

    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/{id}")
    public ModelAndView getUserById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        User userById = bookingFacade.getUserById(id);
        if (userById != null) {
            modelAndView.addObject("userById", userById);
        } else {
            modelAndView.addObject("userById", "user not fount with id = " + id);
        }
        return modelAndView;
    }
}
