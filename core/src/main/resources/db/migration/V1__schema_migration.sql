CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            title VARCHAR(255) UNIQUE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255),
                          category_id BIGINT REFERENCES categories(id),
                          price NUMERIC(8, 2),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        total_price NUMERIC(8, 2) NOT NULL,
                        address VARCHAR(255),
                        phone VARCHAR(255),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,
                             product_id BIGINT NOT NULL REFERENCES products(id),
                             order_id BIGINT NOT NULL REFERENCES orders(id),
                             quantity INT NOT NULL,
                             price_per_product NUMERIC(8, 2) NOT NULL,
                             price NUMERIC(8, 2) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pictures (
                          id BIGSERIAL PRIMARY KEY,
                          content_type VARCHAR(255) NOT NULL,
                          storage_file_name VARCHAR(256) NOT NULL UNIQUE,
                          product_id BIGINT NOT NULL,
                          FOREIGN KEY (product_id) REFERENCES products(id)
);

ALTER TABLE pictures ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id);
