import java.util.Objects;

/**
 * @param userID unique identifier of user
 * @param username username of user
 * @param rank rank of user
 */
public record User(String userID, String username, int rank) {

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
