## Это репозиторий проекта "Трекер задач"

Проект предназначен для отслеживания состояния задач типов Task, Subtask и Epic и реализует функционал:
* Хранение задач
* Получение списка всех задач
* Удаление всех задач
* Получение задачи по ее идентификатору
* Создание задач
* Обновление статуса задач
* Удаление задач по идентификатору
* Получение списка подзадач типа Subtask для задач типа Epic
* Отображение истории просмотренных задач (повторные просмотры отсутствуют)
* Сохранение (восстановление) истории в файл (из файла)


Приложение написано на Java. Пример кода:
```Java  
public class TaskManager {
    public void updateTask(Task task) {

        switch (task.getStatus()) {
            case NEW -> task.setStatus(Status.IN_PROGRESS);
            case IN_PROGRESS -> task.setStatus(Status.DONE);
            default -> {
            } //reserved for future use
        }
        deleteTopLevelTaskById(task.getId(), task);
        historyManager.add(task);
        put(task);
    }
}
```  
---
Разработчик: Мартынов Иван Александрович (iamartynov@rambler.ru)




