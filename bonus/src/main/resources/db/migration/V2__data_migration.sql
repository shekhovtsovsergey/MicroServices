INSERT INTO bonuses (client_id, amount, expire_date,category,is_deleted)
VALUES (1, 100, '2022-12-31', 1,false),
       (1, 100, '2023-06-30', 1,false),
       (1, 100, '2022-10-31', 1,false),
       (1, 100, '2022-09-30', 1,false);


INSERT INTO Settings (delete_enabled) VALUES (false);