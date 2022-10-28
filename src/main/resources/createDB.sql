-- 1. Создайте таблицу в базе данных для хранения объектов
--    сущности Product (Long id, String title, int price)
-- 2. В базе данных необходимо реализовать возможность хранить информацию
--    - о покупателях (id, имя)
--    - и товарах (id, название, стоимость) -- см. п.1
--
-- Hibernate docs -- https://hibernate.org/orm/documentation/5.4/
--
DROP TABLE products IF EXISTS;
DROP TABLE customers IF EXISTS;
DROP TABLE orders IF EXISTS;

CREATE TABLE IF NOT EXISTS products (id bigserial, title VARCHAR(255), cost int, PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS customers (id bigserial, name VARCHAR(255), PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS orders (id bigserial, product int, customer int, date_time timestamp, PRIMARY KEY (id));

--ALTER TABLE orders
--    ADD CONSTRAINT ordersFK0 FOREIGN KEY (customer) REFERENCES customers

-- TODO: список товаров должен быть случайным, но нужно разбираться в используемом диалекте SQL
--DECLARE cnt, rnd AS int
--SET cnt = 5
--FOR i in 1..cnt
--SET rnd = CAST(RAND()*10+1 AS int)
--итд.
INSERT INTO products (title, cost) VALUES ('Вода', 20), ('Хлеб', 25), ('Картофель', 30), ('Молоко', 50), ('Яйцо куриное', 55);
INSERT INTO customers (name) VALUES ('Customer1'), ('Customer2'), ('Customer3'), ('Customer4');
