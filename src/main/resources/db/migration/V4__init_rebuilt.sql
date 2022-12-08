DROP TABLE items IF EXISTS CASCADE;
DROP TABLE item_images IF EXISTS CASCADE;
DROP TABLE item_storage IF EXISTS CASCADE;
drop table item_providers if exists cascade;
drop table item_descriptions if exists cascade;

DROP TABLE orders IF EXISTS CASCADE;
DROP TABLE ordered_items IF EXISTS CASCADE;
DROP TABLE order_statuses IF EXISTS CASCADE;

drop table delivery if exists cascade;
drop table delivery_points if exists cascade;
drop table delivery_lockers if exists cascade;

drop table users if exists cascade;
DROP TABLE roles IF EXISTS CASCADE;

drop table carts if exists cascade;


--
-- 1. структура таблиц БД
--

-- товары
CREATE TABLE IF NOT EXISTS items (
	id bigserial PRIMARY KEY,
	title VARCHAR(255) not null,
	cost int not null,
	provider_id int not null references item_providers (id),
	desc_id int references item_descriptions (id),
	amount int not null,
	-- default задает значение по умолчанию при добавлении новых записей
	-- current_timestamp возвращает текущую временную метку
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- производители/поставщики товаров
create table item_providers (
	id bigserial primary key,
	title varchar(255) not null,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- описания товаров
create table item_descriptions (
	id bigserial primary key,
	short_text varchar(255),
	full_text varchar(255),
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- изображения товаров
create table item_images (
	id bigserial primary key,
	item_id int not null references items (id),
	path varchar(255) not null,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- склад товаров
create table if not exists item_storage (
	id bigserial primary key,
	part_number varchar(100),
	item_id int not null references items (id),
	amount int not null,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- заказанные товары
create table if not exists ordered_items (
	id bigserial primary key,
	order_id int not null,
	item_id int not null,
	amount int not null,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp,
	constraint fk_ords_item_id foreign key (item_id) references storage (item_id),
	constraint fk_ords_order_id foreign key (order_id) references orders (id)
);

-- заказы
create table if not exists orders (
	id bigserial primary key,
	part_number varchar(100),
	customer_id int not null,
	delivery_id int not null,
	status_id int not null references statuses (id),
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp,
	constraint fk_ords_customer_id foreign key (customer_id) references users (id),
	constraint fk_ords_delivery_id foreign key (delivery_id) references delivery (id)
);

-- получение/доставка заказов
create table if not exists delivery (
	id bigserial primary key,
	part_number varchar(100),
	type int not null,
	dest_code int not null,	
	dest_addr varchar(255),
	started_at timestamp default current_timestamp,
	deadline int not null,
	price int not null,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- адреса пунктов выдачи заказов
create table if not exists delivery_points (
	id bigserial primary key,
	location varchar(255) not null
);

-- адреса постаматов
create table if not exists delivery_lockers (
	id bigserial primary key,
	location varchar(255) not null
);

-- состояние заказа: принят, формируется, готов к выдаче, получен
create table if not exists order_statuses (
	id bigserial primary key,
	status varchar(20) not null
);

-- пользователи
CREATE TABLE IF NOT EXISTS users (
	id bigserial primary key,
	username VARCHAR(255) not null,
	password VARCHAR(255) not null,
	email VARCHAR(255),
	address varchar(255),
	phone varchar(20),
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- типы пользователей определяют их права
CREATE TABLE IF NOT EXISTS roles (
	id bigserial PRIMARY KEY,
	designation VARCHAR(30) NOT NULL,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);

-- связи между пользователями и их типами
CREATE TABLE ur_links (
	user_id bigint not null references customers (id),
	role_id bigint not null references roles (id),
	primary key (user_id, role_id)
);

-- хранимые корзины
create table if not exists carts (
	id bigserial primary key,
	order_id int not null references orders (id)
);


--
-- 2. данные таблиц БД
--
INSERT INTO items (title, cost, amount) VALUES
('Вода', 20, 100),
('Хлеб', 25, 100),
('Картофель', 30, 100),
('Молоко', 50, 100),
('Яйцо куриное', 55, 100),
('Мясо', 200, 100),
('Соль', 35, 100),
('Сахар', 70, 100),
('Помидоры', 100, 100),
('Лук', 40, 100),
('Яблоки', 75, 100),
('Апельсины', 90, 100),
('Бананы', 80, 100),
('Виноград', 150, 100),
('Изюм', 300, 100),
('Курага', 300, 100),
('Перец черный молотый', 45, 100),
('Перец черный горошком', 35, 100),
('Лавровый лист', 15, 100),
('Морковь', 25, 100),
('Капуста белокочанная', 35, 100);

insert into order_statuses (name) values
    ('Принят'), ('Формируется'), ('Готов к выдаче'), ('Получен');

insert into roles (name) values
    ('ROLE_SUPER_ADMIN'), ('ROLE_ADMIN'),
    ('ROLE_MAJOR_MANAGER'), ('ROLE_MANAGER'),
    ('ROLE_USER'),
    ('ROLE_GUEST');

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
    (4, 5), (5, 5), (6, 5), (7, 5); -- customers
