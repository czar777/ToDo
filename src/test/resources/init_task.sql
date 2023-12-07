INSERT INTO tasks (name, description, created_at, updated_at, status, priority)
VALUES ('Task1', 'This is a task #1', '2023-12-07 15:09:24.00 +00:00', '2023-12-07 15:09:24.00 +00:00', 1, 1),
       ('Task2', 'This is a task #2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2),
       ('Task3', 'This is a task #3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
       ('Task4', 'This is a task #4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
       ('Task5', 'This is a task #5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);