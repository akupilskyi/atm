INSERT INTO accounts (id, balance, currency, locked) VALUES
(1, '1500.00', 'USD', 0),
(2, '2350.75', 'EUR', 0),
(3, '5000.00', 'PLN', 0),
(4, '120.50', 'USD', 0),
(5, '780.00', 'EUR', 0),
(6, '923.25', 'PLN', 0),
(7, '3120.00', 'USD', 0),
(8, '670.00', 'EUR', 0),
(9, '2540.50', 'PLN', 0),
(10, '400.00', 'USD', 0);

INSERT INTO cards (id, card_numer, pin, currency, accounts_id) VALUES
(1, '1111222233334444', '1234', 'USD', 1),
(2, '1111999922223333', '5678', 'USD', 1),
(3, '5555666677778888', '4321', 'EUR', 2),
(4, '5555000011112222', '1111', 'EUR', 2),
(5, '9999000011112222', '2222', 'PLN', 3),
(6, '9999444455556666', '3333', 'PLN', 3),
(7, '3333444455556666', '4444', 'USD', 4),
(8, '3333000088887777', '5555', 'USD', 4),
(9, '1234567812345678', '6666', 'PLN', 5),
(10, '8765432187654321', '7777', 'USD', 6);

INSERT INTO atm_cash (currency, denomination, quantity) VALUES
('USD', 100, 10),
('USD', 50, 20),
('USD', 20, 30),
('USD', 10, 40),
('EUR', 100, 5),
('EUR', 50, 15),
('EUR', 20, 25),
('EUR', 10, 35),
('PLN', 200, 10),
('PLN', 100, 20);

INSERT INTO exchenge_rates (from_currency, to_currency, rate) VALUES
('USD', 'EUR', 0.92),
('USD', 'PLN', 4.10),
('EUR', 'USD', 1.09),
('EUR', 'PLN', 4.45),
('PLN', 'USD', 0.24),
('PLN', 'EUR', 0.22);

INSERT INTO transactions (id, accounts_id, cards_id, transaction_type, amount, currency, timestamp, atm_id) VALUES
(1, 1, 1, 'withdrawal', 200.00, 'USD', NOW(), 1),
(2, 2, 3, 'withdrawal', 100.00, 'EUR', NOW(), 1),
(3, 3, 5, 'withdrawal', 500.00, 'PLN', NOW(), 2),
(4, 4, 7, 'withdrawal', 50.00, 'USD', NOW(), 3),
(5, 5, 9, 'withdrawal', 250.00, 'EUR', NOW(), 1),
(6, 6, 10, 'withdrawal', 300.00, 'PLN', NOW(), 2),
(7, 7, 1, 'withdrawal', 150.00, 'USD', NOW(), 3),
(8, 8, 4, 'withdrawal', 90.00, 'EUR', NOW(), 1),
(9, 9, 6, 'withdrawal', 400.00, 'PLN', NOW(), 2),
(10, 10, 2, 'exchange', 100.00, 'EUR', NOW(), 1);








