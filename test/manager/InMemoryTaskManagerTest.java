package manager;

import org.junit.Test;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {
        super(new InMemoryTaskManager());
    }

    //Tests for methods from TaskManager interface methods

    @Test
    public void shouldReturnStatusIsInProgressForTask1Updated() {
        super.shouldReturnStatusIsInProgressForTask1Updated();
    }

    @Test
    public void shouldReturnStatusIsNewForEmptyEpic() {
        super.shouldReturnStatusIsNewForEmptyEpic();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoNewSubtasks() {
        super.shouldReturnStatusIsNewForEpicWithTwoNewSubtasks();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks() {
        super.shouldReturnStatusIsNewForEpicWithTwoDoneSubtasks();
    }

    @Test
    public void shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks() {
        super.shouldReturnStatusIsInProgressForEpicWithNewAndDoneSubtasks();
    }

    @Test
    public void shouldReturnStatusIsNewForEpicWithTwoInProgressSubtasks() {
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
        super.shouldReturnTaskBuId();
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOf0() {
        super.shouldThrowNPEWhileReturningTaskByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningTaskByIdOfMinus1() {
        super.shouldThrowNPEWhileReturningTaskByIdOfMinus1();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationSubtaskWithEmptyParameters();
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOf0() {
        super.shouldThrowNPEWhileReturningSubtaskByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningSubtaskByIdOfMinus1() {
        super.shouldThrowNPEWhileReturningSubtaskByIdOfMinus1();
    }

    @Test
    public void shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters() {
        super.shouldThrowNoExceptionWhileCreationEpicWithEmptyParameters();
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOf0() {
        super.shouldThrowNPEWhileReturningEpicByIdOf0();
    }

    @Test
    public void shouldThrowNPEWhileReturningEpicByIdOfMinus1() {
        super.shouldThrowNPEWhileReturningEpicByIdOfMinus1();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasks() {
        super.shouldReturnNonEmptyListOfTasks();
    }

    @Test
    public void shouldReturnEmptyListOfTasks() {
        super.shouldReturnEmptyListOfTasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfSubtasks() {
        super.shouldReturnNonEmptyListOfSubtasks();
    }

    @Test
    public void shouldReturnEmptyListOfSubtasks() {
        super.shouldReturnEmptyListOfSubtasks();
    }

    @Test
    public void shouldReturnNonEmptyListOfEpics() {
        super.shouldReturnNonEmptyListOfEpics();
    }

    @Test
    public void shouldReturnEmptyListOfEpics() {
        super.shouldReturnEmptyListOfEpics();
    }

    @Test
    public void shouldPutTaskToMapOfTasks() {
        super.shouldPutTaskToMapOfTasks();
    }

    @Test
    public void shouldPutSubtaskToMapOfSubtasks() {
        super.shouldPutSubtaskToMapOfSubtasks();
    }

    @Test
    public void shouldPutEpicToMapOfEpics() {
        super.shouldPutEpicToMapOfEpics();
    }

    @Test
    public void shouldDeleteTaskById() {
        super.shouldDeleteTaskById();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0() {
        super.shouldThrowNoExceptionWhileDeletionOfTaskByIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1() {
        super.shouldThrowNoExceptionWhileDeletionOfTaskByIdOfMinus1();
    }

    @Test
    public void shouldDeleteEpicById() {
        super.shouldDeleteEpicById();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0() {
        super.shouldThrowNoExceptionWhileDeletionOfEpicByIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1() {
        super.shouldThrowNoExceptionWhileDeletionOfEpicByIdOfMinus1();
    }

    @Test
    public void shouldDeleteAllTasks() {
        super.shouldDeleteAllTasks();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks() {
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfTasks();
    }

    @Test
    public void shouldDeleteAllSubTasks() {
        super.shouldDeleteAllSubTasks();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks() {
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfSubtasks();
    }

    @Test
    public void shouldDeleteAllEpics() {
        super.shouldDeleteAllEpics();
    }

    @Test
    public void shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics() {
        super.shouldThrowNoExceptionWhileClearingOfEmptyMapOfEpics();
    }

    @Test
    public void shouldReturnNonEmptyListOfTasksFromHistory() {
        super.shouldReturnNonEmptyListOfTasksFromHistory();
    }

    public void shouldReturnEmptyListOfTasksFromHistory() {
        super.shouldReturnEmptyListOfTasksFromHistory();
    }

    //Tests for InMemoryTaskManager-specific methods

    @Test
    public void shouldReturnInstanceOfInMemoryHistoryManager() {
        super.shouldReturnInstanceOfInMemoryHistoryManager();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithOneSubtask() {
        super.shouldReturnListOfSubtasksInEpicWithOneSubtask();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithTwoSubtasks() {
        super.shouldReturnListOfSubtasksInEpicWithTwoSubtasks();
    }

    @Test
    public void shouldReturnListOfSubtasksInEpicWithoutSubtasks() {
        super.shouldReturnListOfSubtasksInEpicWithoutSubtasks();
    }

    @Test
    public void shouldDeleteOneOfTwoSubtasks() {
        super.shouldDeleteOneOfTwoSubtasks();
    }

    @Test
    public void shouldDeleteSingleSubtask() {
        super.shouldDeleteSingleSubtask();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0() {
        super.shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOf0();
    }

    @Test
    public void shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1() {
        super.shouldThrowNoExceptionWhileDeletingOfSubtaskWithIdOfMinus1();
    }
}