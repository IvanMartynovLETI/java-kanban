## Это репозиторий проекта "Трекер задач" ##  

Проект реализован в виде пакета TaskTracker и для задач типов Task, Subtask и Epic обеспечивает:  
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
    public void updateSubtask(Subtask subtask) {

        switch (subtask.getTaskStatus()) {
            case "NEW":
                subtask.taskStatus = "IN_PROGRESS";
                break;
            case "IN_PROGRESS":
                subtask.taskStatus = "DONE";
                break;
            default: //reserved for future use
                break;
        }
    }
}
```
------  
Разработчик: Мартынов Иван Александрович (iamartynov@rambler.ru)


