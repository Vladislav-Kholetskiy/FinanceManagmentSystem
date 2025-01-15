package storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import java.io.File;
import java.io.IOException;

public class UserStorage {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String dataFolder = "./users";

    // Создаем папку для пользователей, если ее нет
    public UserStorage() {
        new File(dataFolder).mkdirs();
    }

    // Сохранение данных пользователя
    public void saveUser(User user) throws IOException {
        String fileName = dataFolder + "/" + user.getUsername() + ".json";
        File userFile = new File(fileName);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(userFile, user);
    }

    public User loadUser(String username) throws IOException {
        String fileName = dataFolder + "/" + username + ".json";
        File userFile = new File(fileName);
        if (!userFile.exists()) {
            throw new IOException("Пользователь не найден");
        }
        User user = objectMapper.readValue(userFile, User.class);
        return user;
    }

    public boolean authenticate(String username, String password) throws IOException {
        User user = loadUser(username);
        boolean result = user.getPassword().equals(password);
        return result;
    }

    public boolean userExists(String username) {
        String fileName = "./users/" + username + ".json";
        return new File(fileName).exists();
    }
}
