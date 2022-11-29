DROP TABLE products IF EXISTS CASCADE;
DROP TABLE customers IF EXISTS CASCADE;
DROP TABLE orders IF EXISTS CASCADE;
DROP TABLE roles IF EXISTS CASCADE;

CREATE TABLE IF NOT EXISTS products (
    id bigserial PRIMARY KEY,
    title VARCHAR(255) not null,
    cost int);

CREATE TABLE IF NOT EXISTS customers (
    id bigserial primary key,
    username VARCHAR(255) not null,
    password VARCHAR(255) not null,
    email VARCHAR(255));

CREATE TABLE IF NOT EXISTS orders (
    id bigserial PRIMARY KEY,
    product_id int,
    customer_id int,
	-- default задает значение по умолчанию при добавлении новых записей
	-- current_timestamp возвращает текущую временную метку
    date_time timestamp default current_timestamp,
    paid timestamp,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id));

CREATE TABLE IF NOT EXISTS roles (
	id bigserial PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	created_at TIMESTAMP DEFAULT current_timestamp,
	updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE ur_links (
    user_id bigint not null references customers (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

INSERT INTO products (title, cost) VALUES
('Вода', 20),
('Хлеб', 25),
('Картофель', 30),
('Молоко', 50),
('Яйцо куриное', 55),
('Мясо', 200),
('Соль', 35),
('Сахар', 70),
('Помидоры', 100),
('Лук', 40),
('Яблоки', 75),
('Апельсины', 90),
('Бананы', 80),
('Виноград', 150),
('Изюм', 300),
('Курага', 300),
('Перец черный молотый', 45),
('Перец черный горошком', 35),
('Лавровый лист', 15),
('Морковь', 25),
('Капуста белокочанная', 35);

insert into roles (name) values
    ('ROLE_SUPERADMIN'), ('ROLE_ADMIN'), ('ROLE_SUPERMANAGER'), ('ROLE_MANAGER'), ('ROLE_USER');

INSERT INTO customers (username, password, email) VALUES
	('SuperAdmin', '$2a$12$6aBoqIqMDZnmnjablEflCeD4EYCZ/dF6hQ74ZEqZKcxIDqPXFq52y', 'too_sad@email.com'), -- 1357908642
	('JustAdmin', '$2a$12$ZalCI46/hEaKLY2c9erk/O1xE.t20KUPIOJAN09lPM7UfrUI4m7mC', 'too_too@email.com'), -- PowerOverwhelming
	('Manager1', '$2a$12$NM.QRaB6R5hPuDEEOI4SNOqX096xt.mb102myCIodE9CJzZwtZJ8i', 'manny@email.com'), -- manNY7Z1I3E0O
	('Customer1', '$2a$12$FzbrC9w24jSNVW9q087sUenfPrYr6Au5CVuKZpghvU.LksNLdIo9S', 'mail1@email.com'), -- qwerasdmnbv
	('Customer2', ' $2a$12$j8XV0hQ9JtR3Zo16wR9ItuL3Qe5596sd0AbpF8esDcsFucBH86geG', 'mail2@email.com'), -- qazwsxedcrfv
	('Customer3', ' $2a$12$buaH2jzItOtA8Idu6Y5EC.dgH3Yw2lsJcngtp9S2u3T1QH2PagWHe', 'mail3@email.com'), -- 5463782910
	('Customer4', '$2a$12$S3xKNkbAtfACn4Wq2KD6Refdb74ZPLVrGeKokudpVVX7b8wfoL1e2', 'mail4@email.com'); -- 0293817465

insert into ur_links (user_id, role_id) values
    (1, 1), (2, 2), (3, 4), -- admins & managers
    (1, 5), (2, 5), (3, 5), (4, 5); -- customers

INSERT INTO orders (product_id, customer_id, date_time, paid) VALUES
(1, 1, '2022-11-06 13:00:00', NULL),
(3, 1, '2022-11-06 13:01:00', '2022-11-06 14:00:00'),
(4, 2, '2022-11-06 13:05:30', NULL),
(5, 2, '2022-11-06 13:10:25', NULL);