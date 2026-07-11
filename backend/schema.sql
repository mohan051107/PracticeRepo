CREATE TABLE users(

    id SERIAL PRIMARY KEY,

    full_name VARCHAR(100) NOT NULL,

    email VARCHAR(150) UNIQUE NOT NULL,

    password VARCHAR(255) NOT NULL

);

CREATE TABLE tasks(

    id SERIAL PRIMARY KEY,

    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,

    title VARCHAR(150) NOT NULL,

    description TEXT,

    priority VARCHAR(20),

    status VARCHAR(20),

    due_date DATE

);