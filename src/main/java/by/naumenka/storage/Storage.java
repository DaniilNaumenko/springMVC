package by.naumenka.storage;

import by.naumenka.model.Event;
import by.naumenka.model.Ticket;
import by.naumenka.model.User;
import by.naumenka.model.impl.EventImpl;
import by.naumenka.model.impl.TicketImpl;
import by.naumenka.model.impl.UserImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Storage {

    private final Map<Long, User> users;
    private final Map<Long, Event> events;
    private final Map<Long, Ticket> tickets;
    private String pathToData;

    public Storage() {
        users = new HashMap<>();
        events = new HashMap<>();
        tickets = new HashMap<>();
    }

    private void addingDataToMaps() {
        try (FileReader fileReader = new FileReader(pathToData);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(", ");

                switch (split[0]) {
                    case "User":
                        User user = new UserImpl(split[2], split[3]);
                        user.setId(Long.parseLong(split[1]));

                        users.put(user.getId(), user);
                        break;

                    case "Event":
                        Event event = new EventImpl(split[2], new SimpleDateFormat("dd-MM-yyyy").parse(split[3]));
                        event.setId(Long.parseLong(split[1]));

                        events.put(event.getId(), event);
                        break;

                    case "Ticket":
                        TicketImpl ticket = new TicketImpl(
                                Long.parseLong(split[2]),
                                Long.parseLong(split[3]),
                                Ticket.Category.valueOf(split[4]),
                                Integer.parseInt(split[5]));
                        ticket.setId(Long.parseLong(split[1]));

                        tickets.put(ticket.getId(), ticket);
                        break;

                    default:
                        System.out.println("Check your file, it looks like we have a data problem");
                        break;
                }
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public Map<Long, Event> getEvents() {
        return events;
    }

    public Map<Long, Ticket> getTickets() {
        return tickets;
    }

    public void setPathToData(String pathToData) {
        this.pathToData = pathToData;
    }
}