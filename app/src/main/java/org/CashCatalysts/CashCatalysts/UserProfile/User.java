package org.CashCatalysts.CashCatalysts.UserProfile;

import java.util.Objects;

/**
 *
 */
public record User(Integer id, String username, int rank) {
    /**
     * @param id unique identifier of user
     * @param username username of user
     * @param rank     rank of user
     */
    public User {
    }

    /**
     * @return string representation of user
     */
    @Override
    public String toString() {
        return "User[" +
                "User ID: " + id + ", " +
                "Username: " + username + ", " +
                "Rank: " + rank + ']';
    }


}
