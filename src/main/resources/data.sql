INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT IGNORE INTO users (id, username, password, first_name, last_name) VALUES (1, 'aliakseikarpilovich@gmail.com', '$2a$10$8RsBxWVIk8Wq879Nz9EQyO8BkRw4f/QZ0.3yP/JNd293Bg9wnrXrK', 'Aliaksei', 'Karpilovich');

INSERT IGNORE INTO users_roles (user_id, roles_id) VALUES (1, 1); 