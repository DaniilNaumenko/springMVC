package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import by.naumenka.model.User;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            modelAndView.addObject("userModel", userById);
        } else {
            modelAndView.addObject("userModel", "user not found with id = " + id);
        }
        return modelAndView;
    }

    @SneakyThrows
    @GetMapping("/{email}")
    public ModelAndView getUserByEmail(@PathVariable String email) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        User userByEmail = bookingFacade.getUserByEmail(email);
        if (userByEmail != null) {
            modelAndView.addObject("userModel", userByEmail);
        } else {
            modelAndView.addObject("userModel", "User not found with email = " + email);
        }
        return modelAndView;
    }

    @GetMapping("/{name}")
    public ModelAndView getUsersByName(@PathVariable String name,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<User> usersByName = bookingFacade.getUsersByName(name, pageSize, pageNum);
        if (usersByName != null) {
            modelAndView.addObject("userModel", usersByName);
        } else {
            modelAndView.addObject("userModel", "Users not found with name = " + name);
        }
        return modelAndView;
    }

    @SneakyThrows
    @PostMapping("/new")
    public User createUser(@RequestBody User user) {
        return bookingFacade.createUser(user);
    }

    @SneakyThrows
    @PostMapping("/update/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        return bookingFacade.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        boolean isUserDelete = bookingFacade.deleteUser(id);
        if (isUserDelete) {
            modelAndView.addObject("userModel", "User is delete by id = " + id);
        } else {
            modelAndView.addObject("userModel", "Error! User wasn't delete by id = " + id);
        }
        return modelAndView;
    }
}
