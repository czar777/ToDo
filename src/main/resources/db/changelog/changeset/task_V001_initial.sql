CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    status      SMALLINT    DEFAULT 1,
    priority    SMALLINT    DEFAULT 1
);

CREATE INDEX index_task_name ON tasks (name);