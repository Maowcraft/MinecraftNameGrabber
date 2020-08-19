package maowcraft.minecraftnamegrabber;

import com.google.gson.Gson;
import maowcraft.minecraftnamegrabber.json.MojangAPI;
import okhttp3.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Main {
    private static final String UUID_API_URL = "https://api.mojang.com/profiles/minecraft";
    private static final String USERNAME_API_URL = "https://api.mojang.com/user/profiles/%1$s/names";

    public static void main(String[] args) {
        if (args.length == 1) {
            Gson gson = new Gson();

            try {
                MojangAPI.UUID[] uuids = gson.fromJson(post(UUID_API_URL, "[\"" + args[0] + "\"]"), MojangAPI.UUID[].class);
                if (uuids.length > 0) {
                    MojangAPI.UUID uuid = uuids[0];
                    MojangAPI.Username[] usernameHistory = gson.fromJson(get(String.format(USERNAME_API_URL, uuid.getId())), MojangAPI.Username[].class);

                    System.out.println("---------");
                    for (MojangAPI.Username username : usernameHistory) {

                        if (username.getChangedToAt() != 0) {
                            System.out.println("Username: " + username.getName());
                            System.out.println("Changed At: " + LocalDateTime
                                    .ofInstant(Instant.ofEpochMilli(username.getChangedToAt()), ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("E, d MMM yyyy - hh:mm:ss a")));
                        } else {
                            System.out.println("Username on Account Creation: " + username.getName());
                        }
                        System.out.println("---------");
                    }
                } else {
                    System.out.println("Could not find username " + args[0] + ".");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage: java -jar <program> <player username>");
        }
    }

    private static String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static String post(String url, String json) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }
}
