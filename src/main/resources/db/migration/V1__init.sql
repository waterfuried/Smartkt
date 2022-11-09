DROP TABLE products IF EXISTS CASCADE;
DROP TABLE customers IF EXISTS CASCADE;
DROP TABLE orders IF EXISTS CASCADE;

CREATE TABLE IF NOT EXISTS products (id bigserial PRIMARY KEY, title VARCHAR(255), cost int);
CREATE TABLE IF NOT EXISTS customers (id bigserial PRIMARY KEY, name VARCHAR(255));
CREATE TABLE IF NOT EXISTS orders (id bigserial PRIMARY KEY, product_id int, customer_id int, date_time timestamp NOT NULL, paid timestamp, FOREIGN KEY (product_id) REFERENCES products (id), FOREIGN KEY (customer_id) REFERENCES customers (id));

INSERT INTO products (title, cost) VALUES ('Вода', 20), ('Хлеб', 25), ('Картофель', 30), ('Молоко', 50), ('Яйцо куриное', 55);
INSERT INTO customers (name) VALUES ('Customer1'), ('Customer2'), ('Customer3'), ('Customer4');
INSERT INTO orders (product_id, customer_id, date_time, paid) VALUES (1, 1, '2022-11-06 13:00:00', NULL), (3, 1, '2022-11-06 13:01:00', '2022-11-06 14:00:00'), (4, 2, '2022-11-06 13:05:30', NULL), (5, 2, '2022-11-06 13:10:25', NULL)
