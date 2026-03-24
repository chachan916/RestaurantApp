package com.restaurant.restaurantapp;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String DB_FILE = "users_database.txt";
    private static final Map<String, String> users = new HashMap<>();

    static { loadUsersFromFile(); }

    private static void loadUsersFromFile() {
        File file = new File(DB_FILE);
        if (!file.exists()) {
            users.put("admin", "1234");
            saveAllUsersToFile();
            return;
        }
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] p = line.split(":", 2);
                    if (p.length == 2) users.put(p[0], p[1]);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void saveAllUsersToFile() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(DB_FILE, false))) {
            for (Map.Entry<String, String> e : users.entrySet()) {
                w.write(e.getKey() + ":" + e.getValue());
                w.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static boolean loginUser(String username, String password) {
        String stored = users.get(username);
        return stored != null && stored.equals(password);
    }

    public static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, password);
        saveAllUsersToFile();
        return true;
    }
}