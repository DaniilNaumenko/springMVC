package by.naumenka.service.xml;

import by.naumenka.model.Ticket;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
public class TicketXml {

    private Long userId;
    private Long eventId;
    private Ticket.Category category;
    private int place;

    @XmlAttribute(name = "user")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @XmlAttribute(name = "event")
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @XmlAttribute(name = "category")
    public Ticket.Category getCategory() {
        return category;
    }

    public void setCategory(Ticket.Category category) {
        this.category = category;
    }

    @XmlAttribute(name = "place")
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}