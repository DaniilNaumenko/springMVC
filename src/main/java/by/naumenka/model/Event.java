package by.naumenka.model;

import by.naumenka.model.impl.EventImpl;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
/**
 * Created by maksym_govorischev.
 */
@JsonDeserialize(as = EventImpl.class)
public interface Event {
    /**
     * Event id. UNIQUE.
     *
     * @return Event Id
     */
    long getId();

    void setId(long id);

    String getTitle();

    void setTitle(String title);

    Date getDate();

    void setDate(Date date);
}