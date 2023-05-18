insert into categories (title) values ('Wine'), ('Vodka'), ('Whiskey');
insert into products (title, price, category_id) values
                                                     ('Cabernet Sauvignon', 1200.00, 1),
                                                     ('Merlot', 1000.00, 1),
                                                     ('Chardonnay', 1100.00, 1),
                                                     ('Pinot Noir', 1300.00, 1),
                                                     ('Sauvignon Blanc', 1050.00, 1),
                                                     ('Shiraz', 1400.00, 1),
                                                     ('Zinfandel', 1250.00, 1),
                                                     ('Riesling', 1150.00, 1),
                                                     ('Malbec', 1350.00, 1),
                                                     ('Vodka Zubrowka', 400.00, 2),
                                                     ('Vodka Russian Standard', 450.00, 2),
                                                     ('Vodka Stolichnaya', 500.00, 2),
                                                     ('Vodka Grey Goose', 550.00, 2),
                                                     ('Vodka Beluga', 600.00, 2),
                                                     ('Vodka Absolut', 350.00, 2),
                                                     ('Jack Daniels', 700.00, 3),
                                                     ('Johnnie Walker', 800.00, 3),
                                                     ('Chivas Regal', 900.00, 3),
                                                     ('Jameson', 750.00, 3),
                                                     ('Bushmills', 650.00, 3),
                                                     ('Ballantine’s', 600.00, 3);

INSERT INTO pictures (content_type, storage_file_name, product_id) VALUES ('image/jpeg', '2a35319c-bc9e-454f-9b29-e2beccf608e0', 1);