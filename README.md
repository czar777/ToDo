# ToDo Service

CRUD-приложение для ведения дел, заметок, задач

* TaskController:
  * для создания, получения, обновления и удаления задач
  * получение списка транзакций, превысивших лимит
    
# Использованные технологии

* Spring Boot – как основной фрэймворк
* PostgreSQL – как основная реляционная база данных
* testcontainers – для изолированного тестирования с базой данных
* Liquibase – для ведения миграций схемы БД
* Gradle – как система сборки приложения
* Docker - для контейниризации Postgres

# База данных

* Liquibase сам накатывает нужные миграции на голый PostgreSql при старте приложения
* В тестах используется testcontainers, в котором тоже запускается отдельный инстанс
  postgres
* В коде продемонстрирована работа с JPA (Hibernate)
  
## Swagger-UI: http://localhost:8080/swagger-ui/index.html

# Код

* Трёхслойная
  архитектура – [Controller](src/main/java/com/testtask/todo/controller), [Service](src/main/java/com/testtask/todo/service), [Repository](src/main/java/com/testtask/todo/repository)

## Actions
### Task

    URL                                           HTTP Method        Operation
    
    /tasks/                                       POST               Создать задачу
    /tasks/{id}                                   GET                Получить задачу по Id
    /tasks/name                                   GET                Получить задачу по названию
    /tasks/{id}                                   PUT                Обновиь задачу
    /tasks/{id}                                   DELETE             Удалить задачу
    /tasks/page/{offset}/limit/{limit}            GET                Получить все задачи
    
# Тесты
[Перейти к тестам](src/test/java/com/testtask/todo/)

* SpringBootTest
* MockMvc
* Testcontainers
* JUnit5
