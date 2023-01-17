## Это репозиторий проекта "Трекер задач" ##  

Проект реализован в виде пакета taskTracker и для задач типов Task, Subtask и Epic обеспечивает:  
* Хранение  
* Получение списка всех задач  
* Удаление всех задач  
* Получение задачи по ее идентификатору  
* Создание
* Обновление статуса
* Удаление по идентификатору  
* Получение списка подзадач типа Subtask для задач типа Epic


Приложение написано на Java. Пример кода:  
```Java  
public class TaskManager {
    public void updateTask(Task task) {

        switch (task.getTaskStatus()) {
            case "NEW" -> task.taskStatus = "IN_PROGRESS";
            case "IN_PROGRESS" -> task.taskStatus = "DONE";
            default -> {
            } //reserved for future use
        }

        deleteTaskById(task.getTaskId());
        putTask(task);
    }
}
```
---  
Разработчик: Мартынов Иван Александрович (iamartynov@rambler.ru)


