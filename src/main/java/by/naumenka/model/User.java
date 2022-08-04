package by.naumenka.model;

import by.naumenka.model.impl.UserImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by maksym_govorischev on 14/03/14.
 */
@JsonDeserialize(as = UserImpl.class)
public interface User {
    /**
     * User Id. UNIQUE.
     *
     * @return User Id.
     */
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);

    /**
     * User email. UNIQUE.
     *
     * @return User email.
     */
    String getEmail();

    void setEmail(String email);
}