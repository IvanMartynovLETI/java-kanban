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
* Отображение последних просмотренных задач (последние 10 задач любого типа)


Приложение написано на Java. Пример кода:
```Java  
public class TaskManager {
    public <T extends Task> void update(T t) {
        if (t instanceof Epic) {
            updateEpic((Epic) t);
        } else if (t instanceof Subtask) {
            updateSubtask((Subtask) t, getEpicById(((Subtask) t).getUpperEpicId()));
        } else {
            updateTask(t);
        }
    }
}
```  
---
Разработчик: Мартынов Иван Александрович (iamartynov@rambler.ru)




