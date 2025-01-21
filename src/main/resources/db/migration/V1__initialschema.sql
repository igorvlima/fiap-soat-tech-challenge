CREATE SEQUENCE customer_id_seq START 1;

CREATE TABLE customer (
                          id BIGINT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
                          cpf VARCHAR(255) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          active BOOLEAN NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP
);

CREATE SEQUENCE product_id_seq START 1;

CREATE TABLE product (
                         id BIGINT DEFAULT nextval('product_id_seq') PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price NUMERIC(19, 2) NOT NULL,
                         description TEXT,
                         category VARCHAR(255) NOT NULL,
                         active BOOLEAN NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP
);

CREATE SEQUENCE product_image_id_seq START 1;

CREATE TABLE product_image (
                               id BIGINT DEFAULT nextval('product_image_id_seq') PRIMARY KEY,
                               product_id BIGINT NOT NULL,
                               url VARCHAR(255) NOT NULL,
                               created_at TIMESTAMP,
                               updated_at TIMESTAMP,
                               CONSTRAINT fk_product_image FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE SEQUENCE order_id_seq START 1;

CREATE TABLE "order" (
                         id BIGINT DEFAULT nextval('order_id_seq') PRIMARY KEY,
                         customer_id BIGINT,
                         total NUMERIC(19, 2) NOT NULL,
                         status VARCHAR(255) NOT NULL,
                         waiting_time_in_minutes BIGINT,
                         created_at TIMESTAMP NOT NULL
);

CREATE SEQUENCE order_item_id_seq START 1;

CREATE TABLE order_item (
                            id BIGINT DEFAULT nextval('order_item_id_seq') PRIMARY KEY,
                            order_id BIGINT NOT NULL,
                            product_id BIGINT NOT NULL,
                            quantity INTEGER,
                            created_at TIMESTAMP NOT NULL,
                            CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES "order" (id),
                            CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id)
);