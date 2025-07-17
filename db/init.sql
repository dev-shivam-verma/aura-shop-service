-- SCHEMA


CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY,
    phone_number        VARCHAR(15) NOT NULL UNIQUE,
    full_name           VARCHAR(150),
    address             TEXT,
    is_profile_complete BOOLEAN   DEFAULT FALSE,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE authorities
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);


CREATE TABLE user_authorities
(
    user_id      INT NOT NULL,
    authority_id INT NOT NULL,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (authority_id) REFERENCES authorities (id) ON DELETE CASCADE
);



-- DATA
INSERT INTO authorities (name, description) VALUES
('ROLE_USER', 'Standard user with basic access'),
('ROLE_ADMIN', 'Administrator with full access'),
('ROLE_SELLER', 'Moderator with limited administrative privileges');

