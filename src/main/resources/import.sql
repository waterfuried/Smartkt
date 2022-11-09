-- 1. Создайте таблицу в базе данных для хранения объектов
--    сущности Product (Long id, String title, int price)
-- 2. В базе данных необходимо реализовать возможность хранить информацию
--    - о покупателях (id, имя)
--    - и товарах (id, название, стоимость) -- см. п.1
--
-- Hibernate docs -- https://hibernate.org/orm/documentation/5.4/
-- H2 Databases -- http://h2database.com/html/main.html
--
-- при наличии связей между таблицами их нужно удалять каскадом
DROP TABLE products IF EXISTS CASCADE;
DROP TABLE customers IF EXISTS CASCADE;
DROP TABLE orders IF EXISTS CASCADE;

-- при переносах строк нарушается чтение определения структуры таблиц БД из этого файла
CREATE TABLE IF NOT EXISTS products (id bigserial PRIMARY KEY, title VARCHAR(255), cost int);
CREATE TABLE IF NOT EXISTS customers (id bigserial PRIMARY KEY, name VARCHAR(255));
CREATE TABLE IF NOT EXISTS orders (id bigserial PRIMARY KEY, product_id int, customer_id int, date_time timestamp NOT NULL, paid timestamp, FOREIGN KEY (product_id) REFERENCES products (id), FOREIGN KEY (customer_id) REFERENCES customers (id));

--ALTER TABLE orders
--    ADD CONSTRAINT ordersFK0 FOREIGN KEY (customer) REFERENCES customers

-- список товаров лучше бы был случайным (для одного из начальных заданий),
-- но для реализации этой случаности нужно разбираться в используемом диалекте SQL;
-- с задания №7 в таблице товаров должны быть тестовые данные - 20 записей,
-- а потому в случайности уже нет нужды
--DECLARE cnt, rnd AS int
--SET cnt = 5
--FOR i in 1..cnt
--SET rnd = CAST(RAND()*10+1 AS int)
--итд.
INSERT INTO products (title, cost) VALUES ('Вода', 20), ('Хлеб', 25), ('Картофель', 30), ('Молоко', 50), ('Яйцо куриное', 55);
INSERT INTO customers (name) VALUES ('Customer1'), ('Customer2'), ('Customer3'), ('Customer4');
INSERT INTO orders (product_id, customer_id, date_time, paid) VALUES (1, 1, '2022-11-06 13:00:00', NULL), (3, 1, '2022-11-06 13:01:00', '2022-11-06 14:00:00'), (4, 2, '2022-11-06 13:05:30', NULL), (5, 2, '2022-11-06 13:10:25', NULL)
