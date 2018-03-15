package com.kayani.brighthr.login.model;

/**
 * Repository of user data
 *
 */
    class UserRepository {

    // Sample uses a simple string[] for now, would really be a combination of a local SQLite database and a web service
    boolean loginUser(String email, String password) throws Exception {
        String[] DUMMY_CREDENTIALS = new String[]{
                "foo@example.com:hello", "bar@example.com:world"};

        // Simulate network access with delay
        Thread.sleep(2000);

        boolean match = false;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(email)) {
                match = pieces[1].equals(password);
                break;
            }
        }
        return match;
    }

}
