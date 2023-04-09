package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {
    private final String uri;
    private String authKey = null;
    private final HttpClient client;

    public KVTaskClient(java.net.URL url) {
        this.uri = url.toString();
        URI authUri = URI.create(url + "/register");
        this.client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(authUri).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.authKey = response.body();
        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("An error occurred during connection to server.");
        }
    }

    public void put(String key, String json) {
        URI uriToSave = URI.create(uri + "/save/" + key + "?API_TOKEN=" + authKey);
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json,
                StandardCharsets.UTF_8)).uri(uriToSave).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();

            if(responseCode != 200) {
                System.out.println("An error occurred while saving data on server.");
            }

        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("An error occurred during connection to server or response body is empty.");
        }
    }

    public String load(String key) {
        URI uriToLoad = URI.create(uri + "/load/" + key + "?API_TOKEN=" + authKey);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uriToLoad).build();
        HttpResponse<String> response;
        String responseStr = null;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();

            if(responseCode != 200) {
                System.out.println("An error occurred while reading data from server.");
            }

            responseStr = response.body();

        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("An error occurred during connection to server or response body is empty.");
        }

        return responseStr;
    }
}
