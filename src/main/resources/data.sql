INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_USER');

INSERT IGNORE INTO users (id, username, password, first_name, last_name, enabled) VALUES (1, 'aliakseikarpilovich@gmail.com', '$2a$10$8RsBxWVIk8Wq879Nz9EQyO8BkRw4f/QZ0.3yP/JNd293Bg9wnrXrK', 'Aliaksei', 'Karpilovich', true);
INSERT IGNORE INTO users (id, username, password, first_name, last_name, enabled) VALUES (2, '1@gmail.com', '$2a$10$8RsBxWVIk8Wq879Nz9EQyO8BkRw4f/QZ0.3yP/JNd293Bg9wnrXrK', 'Aliaksei', 'Karpilovich', true);


INSERT IGNORE INTO users_roles (users_id, roles_id) VALUES (1, 2); 
INSERT IGNORE INTO users_roles (users_id, roles_id) VALUES (2, 1);

INSERT IGNORE INTO genres (id, name) VALUES (1, "Детектив"); 
INSERT IGNORE INTO genres (id, name) VALUES (2, "Наука"); 
INSERT IGNORE INTO genres (id, name) VALUES (3, "Фентези"); 
INSERT IGNORE INTO genres (id, name) VALUES (4, "Бред");


INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (1, 1); 
INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (1, 2); 
INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (1, 3); 
INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (1, 4); 

INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (2, 1); 
INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (3, 2); 
INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (2, 3); 
INSERT IGNORE INTO books_genres (book_id, genre_id) VALUES (3, 4); 

INSERT IGNORE INTO books (id, name, description, user_id) VALUES (1, 'Think as a mathematic', 'Book about mathematic', 2);
INSERT IGNORE INTO books (id, name, description, user_id) VALUES (2, 'English grammar in use', 'Good book for encrease yout english level', 2);
INSERT IGNORE INTO books (id, name, description, user_id) VALUES (3, 'Effective java', 'Must have', 2);

INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (1, 'First chapter', 'Повседневная практика показывает, что рамки и место обучения 
кадров способствует подготовки и реализации модели развития. Идейные соображения высшего порядка,', 1, 1);
INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (2, 'Second chapter', 'Разнообразный и богатый опыт постоянный количественный 
рост и сфера нашей активности требуют от нас анализа соответствующий условий активизации. Разнообразный и богатый опыт постоянное 
информационно-пропагандистское обеспечение нашей деятельности способствует подготовки и реализации направлений прогрессивного развития. 
Товарищи! постоянное информационно-пропагандистское обеспечение нашей деятельности представляи форм развития.', 2, 1);
INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (3, 'Third chapter', 'Разнообразный и богатый опыт постоянный количественный 
рост и сфера нашей активности требуют от нас анализа соответствующий условий активизации.  развития.', 3, 1);

INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (4, 'Fourth chapter', 'Разнообразный и богатый опыт постоянный количественный 
рост и сфера нашей активности требуют от нас анализа соответствующий условий активизации. Разнообразный и богатый опыт постоянное развития.', 4, 1);


INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (5, 'First chapter', 'Lorem t a, tellus. ', 1, 2);

INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (6, 'Second chapter', 'Lorem ipsum dolor sit amet, 
consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, 
ger tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, 
enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. ', 2, 2);

INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (7, 'Third chapter', 'Lorem ipsum dolor sit amet, 
consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, 
nabus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, 
enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. ', 3, 2);


INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (8, 'First chapter', 'Lorem ipsum dolor sit amet, 
consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, 
nasunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, 
enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. ', 1, 3);

INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (9, 'Second chapter', 'Lorem ipsum dolor sit amet, 
consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, 
ndunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, 
enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. ', 3, 3);

INSERT IGNORE INTO chapters (id, name, text, number, book_id) VALUES (10, 'Third chapter', 'Lorem ipsum dolor sit amet, 
consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, 
nt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, 
enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. ', 2, 3);
