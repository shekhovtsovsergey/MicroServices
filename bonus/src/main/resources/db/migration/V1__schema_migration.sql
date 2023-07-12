CREATE TABLE bonuses
(
    id          BIGSERIAL PRIMARY KEY,
    client_id   BIGSERIAL,
    amount      DECIMAL,
    expire_date timestamp,
    category    INTEGER,
    is_deleted  BOOLEAN
);

CREATE TABLE settings
(
    id             SERIAL PRIMARY KEY,
    delete_enabled BOOLEAN DEFAULT false
);

CREATE TABLE bonus_details
(
    id       BIGSERIAL PRIMARY KEY,
    bonus_id BIGSERIAL REFERENCES bonuses (id),
    order_id BIGSERIAL,
    amount   BIGSERIAL
);
