package by.naumenka.integration;

import by.naumenka.config.WebConfiguration;
import by.naumenka.controller.EventController;
import by.naumenka.facade.BookingFacade;
import by.naumenka.model.Event;
import by.naumenka.model.impl.EventImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookingFacade bookingFacade;

    private Event event;

    @Before
    public void setUp() {
        event = new EventImpl("title", new Date(7 - 8 - 2022));
        bookingFacade.createEvent(event);
    }

    @Test
    public void createEventTest() throws Exception {
        mvc.perform(post("/events/new")
                        .content(asJsonString(event))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEventTest() throws Exception {
        Event eventForUpdate = new EventImpl("titleUpdate", new Date(7 - 8 - 2022));

        mvc.perform(post("/events/update/{id}", eventForUpdate.getId())
                        .content(asJsonString(eventForUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getEventByIdTest() throws Exception {
        mvc.perform(get("/events/getById/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("eventPage"))
                .andExpect(model().attributeExists("eventModel"))
                .andExpect(model().attribute("eventModel", bookingFacade.getEventById(event.getId())));
    }

    @Test
    public void getEventsByTitleTest() throws Exception {
        mvc.perform(get("/events/getByTitle/{title}", event.getTitle()))
                .andExpect(status().isOk())
                .andExpect(view().name("eventPage"))
                .andExpect(model().attributeExists("eventModel"))
                .andExpect(model().attribute("eventModel", bookingFacade.getEventsByTitle(event.getTitle(), 10, 1)));
    }

    @Test
    public void getEventsForDayTest() throws Exception {
        String day = "07-08-2022";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(day);

        Event event1 = new EventImpl("title", date);
        bookingFacade.createEvent(event1);

        mvc.perform(get("/events/getByDay/{day}", "07.08.2022"))
                .andExpect(status().isOk())
                .andExpect(view().name("eventPage"))
                .andExpect(model().attributeExists("eventModel"))
                .andExpect(model().attribute("eventModel", bookingFacade.getEventsForDay(date, 10, 1)));
    }

    @Test
    public void deleteEventTest() throws Exception {
        mvc.perform(delete("/events/delete/{id}", event.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAllEventsTest() throws Exception {
        mvc.perform(get("/events/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("eventPage"))
                .andExpect(model().attributeExists("eventModel"))
                .andExpect(model().attribute("eventModel", bookingFacade.getAllEvents()));
    }

    public static String asJsonString(final Event event) {
        try {
            return new ObjectMapper().writeValueAsString(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}