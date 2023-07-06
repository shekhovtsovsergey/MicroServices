CREATE TABLE bonuses (
                       id BIGSERIAL PRIMARY KEY,
                       client_id BIGINT,
                       amount INTEGER,
                       expire_date TIMESTAMP
);

