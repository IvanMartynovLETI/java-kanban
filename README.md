## Это репозиторий проекта "Трекер задач"

Проект предназначен для отслеживания состояния задач типов Task, Subtask и Epic и реализует функционал:
* Хранения задач
* Получения списка всех задач
* Удаления всех задач
* Получения задачи по ее идентификатору
* Создания задач
* Обновления статуса задач
* Удаления задач по идентификатору
* Получения списка подзадач типа Subtask для задач типа Epic
* Отображения истории просмотренных задач (повторные просмотры отсутствуют)
* Сохранения (восстановление) истории в файл (из файла)
#
Ключевыми особенностями приложения являются:
* Хранилище задач и истории на отдельном сервере KVServer
* Задачи не пересекаются во времени, кастомное решение гарантирует поиск пересечений задач всех типов за О(1)
* Отсутствуют повторы в истории просмотров, кастомная структура на основе LinkedList гарантирует удаление повторных просмотров задач всех типов за О(1)
#
Для реализации изложенных выше возможностей были созданы:

<details><summary>Эндпойнты пути /tasks/task/:</summary>

* GET /tasks/task/ - получение всех задач.
* GET /tasks/task/?id={id} - получение задачи по ее id.
* POST /tasks/task/ - создание задачи.
* DELETE /tasks/task/?id={id} - удаление задачи по ее id.
* DELETE /tasks/task/ - удаление всех задач.

</details>

<details><summary>Эндпойнты пути /tasks/subtask/:</summary>

* GET /tasks/subtask/ - получение всех подзадач.
* GET /tasks/subtask/?id={id} - получение подзадачи по ее id.
* POST /tasks/subtask/ - создание подзадачи.
* DELETE /tasks/subtask/?id={id} - удаление подзадачи по ее id.
* DELETE /tasks/subtask/ - удаление всех подзадач.

</details>

<details><summary>Эндпойнты пути /tasks/epic/:</summary>

* GET /tasks/epic/ - получение всех эпиков.
* GET /tasks/epic/?id={id} - получение эпика по его id.
* POST /tasks/epic/ - создание эпика.
* DELETE /tasks/epic/?id={id} - удаление эпика по его id.
* DELETE /tasks/epic/ - удаление всех эпиков.

</details>

<details><summary>Дополнительные эндпойнты:</summary>

* GET /tasks/subtask/epic/?id={id} - получение всех подзадач эпика с заданным id.
* GET /tasks/history - получение истории просмотра задач всех типов.
* GET /tasks/ - получение списка приоритетных задач всех типов.

</details>

#
Стек технологий проекта: Java, GSON, JUnit, API
#

Примеры работы с классами приложения можно увидеть в файле java-kanban/test/server/EndpointTest.java.
Приложение написано на Java без использования фреймворков. Пример кода:
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