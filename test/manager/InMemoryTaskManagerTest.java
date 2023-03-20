package manager;

import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {
        super(new InMemoryTaskManager());
    }

    //Tests for methods from TaskManager interface methods

    @Test
    public void shouldReturnStatusIsInProgressForTask1Updated() {
        super.beforeEach();
        super.shouldReturnStatusIsInProgressForTask1Updated();
    }

    @Test
    public void shouldReturnStatusIsNewForEmptyEpic() {
        super.beforeEach();
        super.shouldReturnStatusIsNewForEmptyEpic();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoNewSubtasks() {
        super.beforeEach();
        super.shouldReturnStatusIsNewForEpicWithTwoNewSubtasks();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks() {
        super.beforeEach();
        super.shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks();
    }

    @Test
    public void shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks() {
        super.beforeEach();
        super.shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoInProgressSubtasks() {
        super.beforeEach();
        super.shouldReturnStatusIsNewForEpicWithTwoInProgressSubtasks();
    }

    @Test
    public void shouldBeUpperEpicForSubtask() {
        super.shouldBeUpperEpicForSubtask();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationTaskWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationTaskWithEmptyParameters();
    }

    @Test
    public void shouldReturnTaskBuId() {
        super.beforeEach();
        super.shouldReturnTaskBuId();
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOf0() {
        super.beforeEach();
        super.shouldThrowNPEWhileReturningTaskByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOfMinus1() {
        super.beforeEach();
        super.shouldThrowNPEWhileReturningTaskByIdOfMinus1();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters();
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOf0() {
        super.beforeEach();
        super.shouldThrowNPEWhileReturningSubtaskByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOfMinus1() {
        super.beforeEach();
        super.shouldThrowNPEWhileReturningSubtaskByIdOfMinus1();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters();
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOf0() {
        super.beforeEach();
        super.shouldThrowNPEWhileReturningEpicByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOfMinus1() {
        super.beforeEach();
        super.shouldThrowNPEWhileReturningEpicByIdOfMinus1();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        super.beforeEach();
        super.shouldReturnNonEmptyListOfTasks();
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        super.beforeEach();
        super.shouldReturnEmptyListOfTasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfSubtasks() {
        super.beforeEach();
        super.shouldReturnNonEmptyListOfSubtasks();
    }

    @Test
    public void shouldReturnEmptyListOfSubtasks() {
        super.beforeEach();
        super.shouldReturnEmptyListOfSubtasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfEpics() {
        super.beforeEach();
        super.shouldReturnNonEmptyListOfEpics();
    }

    @Test
    public void shouldReturnEmptyListOfEpics() {
        super.beforeEach();
        super.shouldReturnEmptyListOfEpics();
    }

    @Test
    public void shouldPutTaskToMapOfTasks() {
        super.beforeEach();
        super.shouldPutTaskToMapOfTasks();
    }

    @Test
    public void shouldPutSubtaskToMapOfSubtasks() {
        super.beforeEach();
        super.shouldPutSubtaskToMapOfSubtasks();
    }

    @Test
    public void shouldPutEpicToMapOfEpics() {
        super.beforeEach();
        super.shouldPutEpicToMapOfEpics();
    }

    @Test
    public void shouldDeleteTaskById() {
        super.beforeEach();
        super.shouldDeleteTaskById();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1();
    }

    @Test
    public void shouldDeleteEpicById() {
        super.beforeEach();
        super.shouldDeleteEpicById();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1();
    }

    @Test
    public void shouldDeleteAllTasks() {
        super.beforeEach();
        super.shouldDeleteAllTasks();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks();
    }

    @Test
    public void shouldDeleteAllSubTasks() {
        super.beforeEach();
        super.shouldDeleteAllSubTasks();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks();
    }

    @Test
    public void shouldDeleteAllEpics() {
        super.beforeEach();
        super.shouldDeleteAllEpics();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasksFromHistory() {
        super.beforeEach();
        super.shouldReturnNonEmptyListOfTasksFromHistory();
    }

    public void shouldReturnEmptyListOfTasksFromHistory() {
        super.beforeEach();
        super.shouldReturnEmptyListOfTasksFromHistory();
    }

    //Tests for InMemoryTaskManager-specific methods

    @Test
    public void shouldReturnInstanceOfInMemoryHistoryManager() {
        super.beforeEach();
        super.shouldReturnInstanceOfInMemoryHistoryManager();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithOneSubtask() {
        super.beforeEach();
        super.shouldReturnListOfSubtasksInEpicWithOneSubtask();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithTwoSubtasks() {
        super.beforeEach();
        super.shouldReturnListOfSubtasksInEpicWithTwoSubtasks();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithoutSubtasks() {
        super.beforeEach();
        super.shouldReturnListOfSubtasksInEpicWithoutSubtasks();
    }

    @Test
    public void shouldDeleteOneOfTwoSubtasks() {
        super.beforeEach();
        super.shouldDeleteOneOfTwoSubtasks();
    }

    @Test
    public void shouldDeleteSingleSubtask() {
        super.beforeEach();
        super.shouldDeleteSingleSubtask();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1() {
        super.beforeEach();
        super.shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1();
    }
}