package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import manager.HttpTaskManager;
import manager.Status;
import org.junit.jupiter.api.*;
import server.adapter.*;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EndpointTest {
    private static Gson gson;
    private static KVServer kvServer;
    private static HttpTaskServer httpTaskServer;
    private static HttpClient client;
    private static URL urlForHttpTaskServer;
    private static URL urlForTasksOperation;
    private static URL urlForSubtasksOperation;
    private static URL urlForEpicsOperation;
    private static URL urlForSubtasksFromEpicsGetting;
    private static URL urlForHistoryGetting;
    private static URL urlForPrioritizedTasksGetting;

    @BeforeAll
    public static void beforeAll() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();

        try {

            urlForHttpTaskServer = new URL ("http://localhost:8081");
            urlForTasksOperation = new URL ("http://localhost:8081/tasks/task/");
            urlForSubtasksOperation = new URL ("http://localhost:8081/tasks/subtask/");
            urlForEpicsOperation = new URL ("http://localhost:8081/tasks/epic/");
            urlForSubtasksFromEpicsGetting = new URL ("http://localhost:8081/tasks/subtask/epic/");
            urlForHistoryGetting = new URL("http://localhost:8081/tasks/history");
            urlForPrioritizedTasksGetting = new URL("http://localhost:8081/tasks/");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void beforeEach() {

        try {

            kvServer = new KVServer();
            kvServer.start();
            httpTaskServer = new HttpTaskServer();
            httpTaskServer.start();
            client = HttpClient.newHttpClient();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void afterEach() {
        httpTaskServer.stop();
        kvServer.stop();
    }

    @Test
    public void shouldReturnCode404WhileRequestingNonExistingTask () {

        System.out.println("shouldReturnCode404WhileRequestingNonExistingTask test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation +"?id=1")).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(404, response.statusCode(), "Incorrect response code, should be 404");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400ForIncorrectRequestForTask () {

        System.out.println("shouldReturnCode400ForIncorrectRequestForTask test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation +"?id=a")).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, response.statusCode(), "Incorrect response code, should be 400");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode204WhilePollingEmptyMapOfTasks () {

        System.out.println("shouldReturnCode204WhilePollingEmptyMapOfTasks test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation.toString())).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(204, response.statusCode(), "Incorrect response code, should be 204");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode201WhileCreateNewTask() {

        System.out.println("shouldReturnCode201WhileCreateNewTask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task1),
                StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();

        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, response.statusCode(), "Incorrect response code, should be 201");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnTask() {

        System.out.println("shouldReturnTask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        HttpRequest taskCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.
                toString())).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation + "?id=1")).
                build();

        HttpResponse<String> creationResponse;
        HttpResponse<String> taskGetResponse;

        try{
            creationResponse = client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, creationResponse.statusCode(), "Incorrect response code, should be 201");

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, taskGetResponse.statusCode(), "Incorrect response code, should be 201");

            Task restoredTask = gson.fromJson(taskGetResponse.body(), Task.class);

            assertEquals(task1, restoredTask, "An error occurred while task restoring.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteTask() {

        System.out.println("shouldDeleteTask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        HttpRequest taskCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest taskDeleteRequest = HttpRequest.newBuilder().DELETE().
                uri(URI.create(urlForTasksOperation + "?id=1")).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForTasksOperation + "?id=1")).build();

        HttpResponse<String> creationResponse;
        HttpResponse<String> deleteResponse;
        HttpResponse<String> taskGetResponse;

        try{
            creationResponse = client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, creationResponse.statusCode(), "Incorrect response code, should be 201");

            deleteResponse = client.send(taskDeleteRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, deleteResponse.statusCode(), "Incorrect response code, should be 200");

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(404, taskGetResponse.statusCode(), "Incorrect response code, should be 404");

        } catch (IOException | InterruptedException | NullPointerException e) {
            System.out.println("An error occurred during connection to server or response body is empty.");
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnListOfTasks() {

        System.out.println("shouldReturnListOfTasks test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        Task task2 = taskManager.createTask("task2", "2d task");
        task2.setStartDateTime("01.08.2023 12:30");
        task2.setDuration(15L);

        HttpRequest task1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest task2CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task2), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation.toString())).
                build();

        HttpResponse<String> taskGetResponse;

        try{
            client.send(task1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task2CreationRequest, HttpResponse.BodyHandlers.ofString());

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, taskGetResponse.statusCode(), "Incorrect response code, should be 200");

            Type taskType = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> listOfTasks = gson.fromJson(taskGetResponse.body(), taskType);

            assertTrue((listOfTasks.size() == 2) && (listOfTasks.contains(task1))
                    && (listOfTasks.contains(task2)), "An error occurred while list of tasks getting.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteAllTasksPreviouslyCreated() {

        System.out.println("shouldDeleteAllTasksPreviouslyCreated test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        Task task2 = taskManager.createTask("task2", "2d task");
        task2.setStartDateTime("01.08.2023 12:30");
        task2.setDuration(15L);

        HttpRequest taskCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest taskDeleteRequest = HttpRequest.newBuilder().DELETE().
                uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest tasksGetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForTasksOperation.toString())).build();

        HttpResponse<String> creationResponse;
        HttpResponse<String> deleteResponse;
        HttpResponse<String> taskGetResponse;

        try{
            creationResponse = client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, creationResponse.statusCode(), "Incorrect response code, should be 201");

            deleteResponse = client.send(taskDeleteRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, deleteResponse.statusCode(), "Incorrect response code, should be 200");

            int statusCode = 0;

            try {
                taskGetResponse = client.send(tasksGetRequest, HttpResponse.BodyHandlers.ofString());
                statusCode = taskGetResponse.statusCode();
            } catch (NullPointerException e) {
                System.out.println("Response is empty");
            }

            assertEquals(204, statusCode, "An error occurred while tasks deletion.");


        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUpdateTask() {

        System.out.println("shouldUpdateTask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        HttpRequest taskCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation + "?id=1")).
                build();

        HttpResponse<String> taskGetResponse;

        try{
            client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());

            client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());


            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, taskGetResponse.statusCode(), "Incorrect response code, should be 200");

            Task restoredTask = gson.fromJson(taskGetResponse.body(), Task.class);

            Assertions.assertEquals(restoredTask.getStatus(), Status.IN_PROGRESS,
                    "An error occurred while task restoring.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode404WhileRequestingNonExistingEpic () {

        System.out.println("shouldReturnCode404WhileRequestingNonExistingEpic test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForEpicsOperation +"?id=1")).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(404, response.statusCode(), "Incorrect response code, should be 404");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400ForIncorrectRequestForEpic () {

        System.out.println("shouldReturnCode400ForIncorrectRequestForEpic test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForEpicsOperation +"?id=a")).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, response.statusCode(), "Incorrect response code, should be 400");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode204WhilePollingEmptyMapOfEpics () {

        System.out.println("shouldReturnCode204WhilePollingEmptyMapOfEpics test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(204, response.statusCode(), "Incorrect response code, should be 204");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode201WhileCreateNewEpic() {

        System.out.println("shouldCreateNewEpic test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1),
                StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();

        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, response.statusCode(), "Incorrect response code, should be 201");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnEpic() throws NullPointerException {

        System.out.println("shouldReturnEpic test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");


        HttpRequest epic1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();

        HttpRequest epic1GetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForEpicsOperation + "?id=1")).
                build();

        HttpResponse<String> taskGetResponse;

        try {
            client.send(epic1CreationRequest, HttpResponse.BodyHandlers.ofString());

            taskGetResponse = client.send(epic1GetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, taskGetResponse.statusCode(), "Incorrect response code, should be 200");

            Epic restoredEpic = gson.fromJson(taskGetResponse.body(), Epic.class);

            assertEquals(epic1, restoredEpic, "An error occurred while epic restoring.");
        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteEpic() {

        System.out.println("shouldDeleteEpic test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        HttpRequest taskCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest taskDeleteRequest = HttpRequest.newBuilder().DELETE().
                uri(URI.create(urlForEpicsOperation + "?id=1")).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForEpicsOperation + "?id=1")).build();

        HttpResponse<String> creationResponse;
        HttpResponse<String> deleteResponse;
        HttpResponse<String> taskGetResponse;

        try{
            creationResponse = client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, creationResponse.statusCode(), "Incorrect response code, should be 201");

            deleteResponse = client.send(taskDeleteRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, deleteResponse.statusCode(), "Incorrect response code, should be 200");

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(404, taskGetResponse.statusCode(), "Incorrect response code, should be 404");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnListOfEpics() {

        System.out.println("shouldReturnListOfEpics test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");
        Epic epic2 = taskManager.createEpic("epic2", "2d epic");

        HttpRequest task1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest task2CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic2), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForEpicsOperation.toString())).
                build();

        HttpResponse<String> taskGetResponse;

        try{
            client.send(task1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task2CreationRequest, HttpResponse.BodyHandlers.ofString());

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, taskGetResponse.statusCode(), "Incorrect response code, should be 200");

            Type epicType = new TypeToken<ArrayList<Epic>>(){}.getType();
            List<Epic> listOfEpics = gson.fromJson(taskGetResponse.body(), epicType);

            assertTrue((listOfEpics.size() == 2) && (listOfEpics.contains(epic1))
                    && (listOfEpics.contains(epic2)), "An error occurred while list of tasks getting.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteAllEpicsPreviouslyCreated() {

        System.out.println("shouldDeleteAllEpicsPreviouslyCreated test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);
        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        HttpRequest taskCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest taskDeleteRequest = HttpRequest.newBuilder().DELETE().
                uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest tasksGetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForEpicsOperation.toString())).build();

        HttpResponse<String> creationResponse;
        HttpResponse<String> deleteResponse;
        HttpResponse<String> taskGetResponse;

        try{
            creationResponse = client.send(taskCreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, creationResponse.statusCode(), "Incorrect response code, should be 201");

            deleteResponse = client.send(taskDeleteRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, deleteResponse.statusCode(), "Incorrect response code, should be 200");

            int statusCode;

            try {

                taskGetResponse = client.send(tasksGetRequest, HttpResponse.BodyHandlers.ofString());

                statusCode = taskGetResponse.statusCode();

                assertEquals(204, statusCode, "An error occurred while epics deletion.");

            } catch (NullPointerException e) {
                System.out.println("Response is empty");
            }

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUpdateEpic() {

        System.out.println("shouldUpdateEpic test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epicCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();

        HttpRequest subtask1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1))).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpRequest epic1GetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForEpicsOperation + "?id=1")).
                build();

        HttpResponse<String> epic1GetResponse;


        try{
            client.send(epicCreationRequest, HttpResponse.BodyHandlers.ofString());

            client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());

            client.send(epicCreationRequest, HttpResponse.BodyHandlers.ofString());

            epic1GetResponse = client.send(epic1GetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, epic1GetResponse.statusCode(), "Incorrect response code, should be 200");

            Epic restoredEpic = gson.fromJson(epic1GetResponse.body(), Epic.class);

            assertEquals(restoredEpic.getDuraTion(), Duration.ofMinutes(15L), "An error occurred while epic" +
                    " restoring.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode404WhileRequestingNonExistingSubtask () {

        System.out.println("shouldReturnCode404WhileRequestingNonExistingSubtask test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForSubtasksOperation +"?id=1")).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(404, response.statusCode(), "Incorrect response code, should be 404");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400ForIncorrectRequestForSubtask () {

        System.out.println("shouldReturnCode400ForIncorrectRequestForSubtask test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForSubtasksOperation +"?id=a")).build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, response.statusCode(), "Incorrect response code, should be 400");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode204WhilePollingEmptyMapOfSubtasks () {

        System.out.println("shouldReturnCode204WhilePollingEmptyMapOfSubtasks test");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(urlForSubtasksOperation.toString())).
                build();
        HttpResponse<String> response;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(204, response.statusCode(), "Incorrect response code, should be 200");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode201WhileCreateNewSubtask() {

        System.out.println("shouldReturnCode201WhileCreateNewSubtask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epicCreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();

        HttpRequest subtask1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpResponse<String> response;

        try{
            response = client.send(epicCreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, response.statusCode(), "Incorrect response code, should be 201");

            response = client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, response.statusCode(), "Incorrect response code, should be 201");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnSubtask() {

        System.out.println("shouldReturnSubtask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epic1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpRequest subtask1GetRequest = HttpRequest.newBuilder().GET().uri(URI.
                create(urlForSubtasksOperation +"?id=2")).build();

        HttpResponse<String> subtask1GetResponse;

        try{
            client.send(epic1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreateRequest, HttpResponse.BodyHandlers.ofString());

            subtask1GetResponse = client.send(subtask1GetRequest, HttpResponse.BodyHandlers.ofString());

            Subtask restoredSubtask = gson.fromJson(subtask1GetResponse.body(), Subtask.class);

            assertEquals(subtask1, restoredSubtask, "An error occurred while subtask restoring.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteSubtask() {

        System.out.println("shouldDeleteSubtask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epic1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();
        HttpRequest subtask1DeleteRequest = HttpRequest.newBuilder().DELETE().
                uri(URI.create(urlForSubtasksOperation + "?id=2")).build();
        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForSubtasksOperation + "?id=2")).build();

        HttpResponse<String> deleteResponse;
        HttpResponse<String> taskGetResponse;

        try{
            client.send(epic1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreateRequest, HttpResponse.BodyHandlers.ofString());

            deleteResponse = client.send(subtask1DeleteRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, deleteResponse.statusCode(), "Incorrect response code, should be 200");

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(404, taskGetResponse.statusCode(), "Incorrect response code, should be 404");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnListOfSubtasks() {

        System.out.println("shouldReturnListOfSubtasks test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        subtask2.setStartDateTime("01.08.2023 13:30");
        subtask2.setDuration(15L);

        HttpRequest epic1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();
        HttpRequest subtask2CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask2), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpRequest taskGetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpResponse<String> taskGetResponse;

        try{
            client.send(epic1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask2CreateRequest, HttpResponse.BodyHandlers.ofString());

            taskGetResponse = client.send(taskGetRequest, HttpResponse.BodyHandlers.ofString());

            Type SubtaskType = new TypeToken<ArrayList<Subtask>>(){}.getType();

            List<Subtask> listOfSubtasks = gson.fromJson(taskGetResponse.body(), SubtaskType);

            assertTrue((listOfSubtasks.size() == 2) && (listOfSubtasks.contains(subtask1))
                    && (listOfSubtasks.contains(subtask2)), "An error occurred while" +
                    " list of subtasks getting.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteAllSubtasksPreviouslyCreated() {

        System.out.println("shouldDeleteAllSubtasksPreviouslyCreated test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        subtask2.setStartDateTime("01.08.2023 13:30");
        subtask2.setDuration(15L);

        HttpRequest epic1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();
        HttpRequest subtask2CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask2), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpRequest subtasksDeleteRequest = HttpRequest.newBuilder().DELETE().
                uri(URI.create(urlForSubtasksOperation.toString())).build();

        HttpRequest tasksGetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForSubtasksOperation.toString())).build();

        HttpResponse<String> deleteResponse;
        HttpResponse<String> taskGetResponse;

        try{

            client.send(epic1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask2CreateRequest, HttpResponse.BodyHandlers.ofString());

            deleteResponse = client.send(subtasksDeleteRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, deleteResponse.statusCode(), "Incorrect response code, should be 200");

            taskGetResponse = client.send(tasksGetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(204,taskGetResponse.statusCode(), "Incorrect deletion of all tasks");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUpdateSubtask() {

        System.out.println("shouldUpdateSubtask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epic1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpRequest subtask1GetRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForSubtasksOperation + "?id=2")).build();

        HttpResponse<String> subtask1GetResponse;

        try{
            client.send(epic1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());

            subtask1GetResponse = client.send(subtask1GetRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, subtask1GetResponse.statusCode(),
                    "Incorrect response code, should be 200");

            Subtask restoredSubtask = gson.fromJson(subtask1GetResponse.body(), Subtask.class);

            assertEquals(restoredSubtask.getStatus(), Status.IN_PROGRESS, "An error occurred while subtask" +
                    " restoring.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400WhileAttemptingIncorrectUpdateOfSubtask() {

        System.out.println("shouldReturnCode400WhileAttemptingIncorrectUpdateOfSubtask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epic1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();
        HttpRequest incorrectUpdateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                        ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).
                uri(URI.create(urlForSubtasksOperation + "?id=6")).build();

        HttpResponse<String> subtask1GetResponse;

        try{
            client.send(epic1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());

            subtask1GetResponse = client.send(incorrectUpdateRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, subtask1GetResponse.statusCode(),
                    "Incorrect response code, should be 400");


        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400WhileAttemptingGetSubtaskWithIncorrectParameterOfRequest() {

        System.out.println("shouldReturnCode400WhileAttemptingIncorrectUpdateOfSubtask test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        HttpRequest epic1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();
        HttpRequest incorrectUpdateRequest = HttpRequest.newBuilder().GET()
                .uri(URI.create(urlForSubtasksOperation + "?iD=6")).build();

        HttpResponse<String> subtask1GetResponse;

        try{
            client.send(epic1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreationRequest, HttpResponse.BodyHandlers.ofString());

            subtask1GetResponse = client.send(incorrectUpdateRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, subtask1GetResponse.statusCode(),
                    "Incorrect response code, should be 400");


        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode204WhileGettingResponseToEmptyListOfPrioritizedTasks() {

        System.out.println("shouldReturnCode204WhileGettingResponseToEmptyListOfPrioritizedTasks test");

        HttpRequest request = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForPrioritizedTasksGetting.toString())).build();

        HttpResponse<String> response;

        try{

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(204, response.statusCode(),
                    "Incorrect response code, should be 204");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400WhileGettingIncorrectResponseToPrioritizedTasks() {

        System.out.println("shouldReturnCode400WhileGettingIncorrectResponseToPrioritizedTasks test");

        HttpRequest request = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForPrioritizedTasksGetting + "?id")).build();

        HttpResponse<String> response;

        try {

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, response.statusCode(),
                    "Incorrect response code, should be 400");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnListOfPrioritizedTasks () {

        System.out.println("shouldReturnListOfPrioritizedTasks test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        Task task2 = taskManager.createTask("task2", "2d task");
        task2.setStartDateTime("01.08.2023 12:30");
        task2.setDuration(15L);

        HttpRequest task1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest task2CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task2), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest task1GetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation + "?id=1")).
                build();
        HttpRequest task2GetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation + "?id=2")).
                build();

        HttpRequest prioritizedRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForPrioritizedTasksGetting.toString())).build();

        HttpResponse<String> taskGetResponse;

        try{
            client.send(task1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task2CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task1GetRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task2GetRequest, HttpResponse.BodyHandlers.ofString());

            taskGetResponse = client.send(prioritizedRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, taskGetResponse.statusCode(), "Incorrect response code, should be 200");

            Type taskType = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> listOfTasks = gson.fromJson(taskGetResponse.body(), taskType);

            assertTrue((listOfTasks.size() == 2) && (listOfTasks.contains(task1))
                    && (listOfTasks.contains(task2)), "An error occurred while list of tasks getting.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode204WhileAttemptingToGetEmptyHistory() {

        System.out.println("shouldReturnCode204WhileAttemptingToGetEmptyHistory test");

        HttpRequest historyRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation.toString())).
                build();

        HttpResponse<String> taskGetResponse;

        try{

            taskGetResponse = client.send(historyRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(204, taskGetResponse.statusCode(), "Incorrect response code, should be 204");


        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCode400WhileAttemptingToDeleteHistory() {

        System.out.println("shouldReturnCode400WhileAttemptingToDeleteHistory test");

        HttpRequest historyRequest = HttpRequest.newBuilder().DELETE().uri(URI.create(urlForHistoryGetting.toString())).
                build();

        HttpResponse<String> taskGetResponse;

        try{

            taskGetResponse = client.send(historyRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(400, taskGetResponse.statusCode(), "Incorrect response code, should be 400");


        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnNonEmptyHistory() {

        System.out.println("shouldReturnNonEmptyHistory test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Task task1 = taskManager.createTask("task1", "1st task");
        task1.setStartDateTime("01.08.2023 12:00");
        task1.setDuration(15L);

        Task task2 = taskManager.createTask("task2", "2d task");
        task2.setStartDateTime("01.08.2023 12:30");
        task2.setDuration(15L);

        HttpRequest task1CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task1), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest task2CreationRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(task2), StandardCharsets.UTF_8)).uri(URI.create(urlForTasksOperation.toString())).build();
        HttpRequest task1GetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation + "?id=1")).
                build();
        HttpRequest task2GetRequest = HttpRequest.newBuilder().GET().uri(URI.create(urlForTasksOperation + "?id=2")).
                build();

        HttpRequest historyRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForHistoryGetting.toString())).build();

        HttpResponse<String> historyResponse;

        try{
            client.send(task1CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task2CreationRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task1GetRequest, HttpResponse.BodyHandlers.ofString());
            client.send(task2GetRequest, HttpResponse.BodyHandlers.ofString());

            historyResponse = client.send(historyRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, historyResponse.statusCode(), "Incorrect response code, should be 200");

            Type taskType = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> history = gson.fromJson(historyResponse.body(), taskType);

            assertTrue((history.size() == 2) && (history.contains(task1))
                    && (history.contains(task2)), "An error occurred while list of tasks getting.");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnListOfSubtasksFromEpic() {

        System.out.println("shouldReturnListOfSubtasksFromEpic test");

        HttpTaskManager taskManager = new HttpTaskManager(urlForHttpTaskServer);

        Epic epic1 = taskManager.createEpic("epic1", "1st epic");

        Subtask subtask1 = taskManager.createSubtask("subtask1", "1st subtask", epic1);
        subtask1.setStartDateTime("01.08.2023 13:00");
        subtask1.setDuration(15L);

        Subtask subtask2 = taskManager.createSubtask("subtask2", "2d subtask", epic1);
        subtask2.setStartDateTime("01.08.2023 13:30");
        subtask2.setDuration(15L);

        HttpRequest epic1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(gson.
                toJson(epic1), StandardCharsets.UTF_8)).uri(URI.create(urlForEpicsOperation.toString())).build();
        HttpRequest subtask1CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask1), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();
        HttpRequest subtask2CreateRequest = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.
                ofString(gson.toJson(subtask2), StandardCharsets.UTF_8)).uri(URI.create(urlForSubtasksOperation.
                toString())).build();

        HttpRequest listOfSubtasksRequest = HttpRequest.newBuilder().GET().
                uri(URI.create(urlForSubtasksFromEpicsGetting + "?id=1")).build();

        HttpResponse<String> listOfSubtasksFromEpicResponse;

        try{

            client.send(epic1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask1CreateRequest, HttpResponse.BodyHandlers.ofString());
            client.send(subtask2CreateRequest, HttpResponse.BodyHandlers.ofString());

            listOfSubtasksFromEpicResponse = client.send(listOfSubtasksRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, listOfSubtasksFromEpicResponse.statusCode(),
                    "Incorrect response code, should be 200");

            Type SubtaskType = new TypeToken<ArrayList<Subtask>>(){}.getType();
            List<Subtask> listOfSubtasks = gson.fromJson(listOfSubtasksFromEpicResponse.body(), SubtaskType);

            assertTrue((listOfSubtasks.size() == 2) && (listOfSubtasks.contains(subtask1)
                    && (listOfSubtasks.contains(subtask2))), "Incorrect obtaining list of subtasks from epic");

        } catch (IOException | InterruptedException | NullPointerException e) {
            assertEquals(1, 0,
                    "An error occurred during connection to server or response body is empty.");
            e.printStackTrace();
        }
    }
}