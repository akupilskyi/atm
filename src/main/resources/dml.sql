USE atmdb;
INSERT INTO currencies(iso_code, name) VALUES
("EUR", "Euro"),
("USD", "United States dollar"),
("PLN", "Polish złoty")
;

INSERT INTO `atmdb`.`accounts` (`balance`, `currency`, `locked`) VALUES
(1000.00, 'EUR', FALSE),
(1500.50, 'USD', FALSE),
(2000.75, 'PLN', FALSE),
(2500.00, 'EUR', FALSE),
(3000.25, 'USD', FALSE),
(3500.50, 'PLN', FALSE),
(4000.75, 'EUR', FALSE),
(4500.00, 'USD', FALSE),
(5000.25, 'PLN', FALSE),
(5500.50, 'EUR', FALSE);

INSERT INTO `atmdb`.`cards` (`number`, `pin`, `currency`, `account_id`) VALUES
('1234567812345678', '1234', 'EUR', 1),
('2345678923456789', '2345', 'USD', 2),
('3456789034567890', '3456', 'PLN', 3),
('4567890145678901', '4567', 'EUR', 4),
('5678901256789012', '5678', 'USD', 5),
('6789012367890123', '6789', 'PLN', 6),
('7890123478901234', '7890', 'EUR', 7),
('8901234589012345', '8901', 'USD', 8),
('9012345690123456', '9012', 'PLN', 9),
('0123456701234567', '0123', 'EUR', 10),
('1123456711234567', '1123', 'USD', 1),
('2123456721234567', '2123', 'PLN', 2),
('3123456731234567', '3123', 'EUR', 3),
('4123456741234567', '4123', 'USD', 4),
('5123456751234567', '5123', 'PLN', 5);

INSERT INTO `atmdb`.`currency_rates` (`from_currency`, `to_currency`, `rate`) VALUES
('EUR', 'USD', 1.12),
('EUR', 'PLN', 4.50),
('USD', 'EUR', 0.89),
('USD', 'PLN', 4.02),
('PLN', 'EUR', 0.22),
('PLN', 'USD', 0.25);

INSERT INTO `atmdb`.`atms` (`location`, `status`) VALUES
('Berlin, Germany', 'active'),
('New York, USA', 'out_of_service'),
('Warsaw, Poland', 'active'),
('Paris, France', 'active'),
('London, UK', 'active');

INSERT INTO `atmdb`.`transactions` (`card_id`, `transaction_type`, `amount`, `atm_id`, `currency_rate_id`) VALUES
(1, 'deposit', 500.00, 1, 1),
(2, 'withdrawal', 200.00, 2, 2),
(3, 'deposit', 750.00, 3, 3),
(4, 'withdrawal', 100.00, 4, 4),
(5, 'deposit', 1200.00, 5, 5),
(6, 'withdrawal', 300.00, 1, 6),
(7, 'deposit', 450.00, 2, 1),
(8, 'withdrawal', 600.00, 3, 2),
(1, 'deposit', 900.00, 4, 3),
(1, 'withdrawal', 150.00, 5, 4);

INSERT INTO `atmdb`.`banknote_types` (`currency`, `denomination`) VALUES
('EUR', 5),
('EUR', 10),
('EUR', 20),
('EUR', 50),
('USD', 1),
('USD', 5),
('USD', 10),
('USD', 20),
('PLN', 10),
('PLN', 20),
('PLN', 50),
('PLN', 100);

INSERT INTO `atmdb`.`atms_have_banknote_types` (`atm_id`, `banknote_type_id`, `quantity`) VALUES
(1, 1, 100),
(1, 2, 200),
(2, 3, 150),
(2, 4, 100),
(3, 5, 300),
(3, 6, 250),
(4, 7, 200),
(4, 8, 150),
(5, 9, 350),
(5, 10, 400),
(1, 11, 500),
(2, 12, 600);