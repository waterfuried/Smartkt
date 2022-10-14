-- 1. Создайте таблицу в базе данных для хранения объектов
--    сущности Product (Long id, String title, int price)
--
--    Hibernate docs -- https://hibernate.org/orm/documentation/5.4/
DROP TABLE products IF EXISTS;
CREATE TABLE IF NOT EXISTS products (id bigserial, title VARCHAR(255), cost int, PRIMARY KEY (id));
-- TODO: список товаров должен быть случайным, но нет времени на разбор используемого диалекта SQL
--DECLARE cnt, rnd AS int
--SET cnt = 5
--FOR i in 1..cnt
--SET rnd = CAST(RAND()*10+1 AS int)
--итд.
INSERT INTO products (title, cost) VALUES ('Вода', 20), ('Хлеб', 25), ('Картофель', 30), ('Молоко', 50), ('Яйцо куриное', 55);
