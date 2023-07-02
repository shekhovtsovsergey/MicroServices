CREATE TABLE bonus (
                       id BIGSERIAL PRIMARY KEY,
                       client_id BIGINT,
                       amount INTEGER,
                       expire_date TIMESTAMP
);

