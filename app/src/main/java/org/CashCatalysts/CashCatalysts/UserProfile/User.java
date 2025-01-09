package org.CashCatalysts.CashCatalysts.UserProfile;

import java.util.Objects;

/**
 *
 */
public record User(String userID, String username, int rank) {
    /**
     * @param userID   unique identifier of user
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
                "User ID: " + userID + ", " +
                "Username: " + username + ", " +
                "Rank: " + rank + ']';
    }


}
