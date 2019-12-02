INSERT INTO User (user_id, username, password, balance, is_logged_in, status)
VALUES (1, 'Apri', '$2a$12$bKYIhhAc1t6zjbQFcf2Wq.CoeRU2HSJ/3Kgxi/Y4yP8gr775zyz0G', 10000.00, false, true),
       (2, 'Wida', '$2a$12$bbl09bhgOwQ6zip19gTOYOjeRzYaqcRdyF2jKY1fXi1aj9N0kl.Rm', 70000.00, true, true),
       (3, 'Yanti', '$2a$12$A.g1eq5QVLSXeW0Okj2S7e2kPs0cMhE7v8Wk6bUZ/3cHpNMYXSQV6', 5000000.00, true, true);

INSERT INTO Product (product_id, product_name, stock, price)
VALUES (1, 'Kaos kaki Wonder Woman', 5, 15000.00),
       (2, 'Baju koko Black Panther', 3, 200000.00),
       (3, 'Celana Hulk', 10, 50000);

INSERT INTO Transaction (transaction_id, transaction_date, user_id, username, product_id, product_name, price, quantity, subtotal, tax, total)
VALUES (1, '2019-12-31', 4, 'Widianto', 1, 'Palu Thor', 17500.00, 1, 15000, 1500, 16500);

INSERT INTO Role (role_id, role_name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_CUSTOMER');

INSERT INTO User_Role (user_id, role_id)
VALUES (1, 2),
       (2, 1),
       (3, 1);