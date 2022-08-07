package by.naumenka.controller;

import by.naumenka.facade.BookingFacade;
import by.naumenka.model.User;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final BookingFacade bookingFacade;
    public static final String TEMPLATE = "userPage";

    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        bookingFacade.createUser(user);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable long id) {
        bookingFacade.updateUser(id, user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ModelAndView getUserById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);
        modelAndView.addObject("userModel", bookingFacade.getUserById(id));
        return modelAndView;
    }

    @GetMapping("/getByEmail/{email}")
    public ModelAndView getUserByEmail(@PathVariable String email) {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);
        modelAndView.addObject("userModel", bookingFacade.getUserByEmail(email));
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

    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView(TEMPLATE);

        List<User> allUsers = bookingFacade.getAllUsers();
        modelAndView.addObject("userModel", allUsers);

        return modelAndView;
    }
}
