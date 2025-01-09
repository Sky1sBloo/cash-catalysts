package org.CashCatalysts.CashCatalysts.UserProfile;

public class UserProfileHandler {
    /**
     * Create new user
     *
     * @param userID identifier of user
     * @param username name of user
     * @param rank rank of user; can't be < 1
     * @return a {@code User} object representing the created user
     */
    public User createUser(int userID, String username, int rank){
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("Fill in username.");
        }
        if(rank < 1){
            throw new IllegalArgumentException("Rank can't be negative.");
        }
        return new User(userID, username, rank);
    }

    /**
     * Creates a new user with default rank
     * Note: Generally used for registering new users
     */
    public User createUser(String username){
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("Fill in username.");
        }
        return new User(null, username, 1);
    }
}
