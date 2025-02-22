INSERT INTO `atmdb`.`accounts` (`balance`, `currency`, `locked`)
VALUES (1500.50, 'USD', 0),
       (2300.75, 'EUR', 0),
       (3200.00, 'PLN', 0),
       (1100.25, 'USD', 0),
       (5000.00, 'EUR', 0),
       (750.50, 'PLN', 0),
       (2100.00, 'USD', 0),
       (4500.80, 'EUR', 0),
       (2750.30, 'PLN', 0),
       (1250.00, 'USD', 0);

INSERT INTO `atmdb`.`cards` (`number`, `pin`, `currency`, `account_id`)
VALUES ('1234567812345678', '1111', 'USD', 1),
       ('2345678923456789', '2222', 'USD', 1),
       ('3456789034567890', '3333', 'EUR', 2),
       ('4567890145678901', '4444', 'EUR', 2),
       ('5678901256789012', '5555', 'PLN', 3),
       ('6789012367890123', '6666', 'USD', 4),
       ('7890123478901234', '7777', 'EUR', 5),
       ('8901234589012345', '8888', 'PLN', 6),
       ('9012345690123456', '9999', 'USD', 7),
       ('0123456701234567', '0000', 'EUR', 8),
       ('1111222233334444', '1234', 'EUR', 8),
       ('2222333344445555', '4321', 'PLN', 9),
       ('3333444455556666', '5678', 'USD', 10),
       ('4444555566667777', '8765', 'USD', 10);

INSERT INTO `atmdb`.`atms` (`location`, `status`)
VALUES ('New York, Times Square', 'active'),
       ('Berlin, Alexanderplatz', 'active'),
       ('Warsaw, Old Town', 'active');

INSERT INTO `atmdb`.`atm_cash` (`currency`, `denomination`, `quality`, `atm_id`)
VALUES ('USD', 100, 50, 1),
       ('USD', 50, 100, 1),
       ('EUR', 100, 30, 2),
       ('EUR', 50, 60, 2),
       ('PLN', 200, 40, 3),
       ('PLN', 100, 70, 3);

INSERT INTO `atmdb`.`currency_rate` (`code`, `rate`)
VALUES ('EUR', 1.08), -- 1 EUR = 1.08 USD
       ('USD', 1.00), -- 1 USD = 1.00 USD
       ('PLN', 0.25); -- 1 PLN = 0.25 USD

INSERT INTO `atmdb`.`transactions` (`account_id`, `card_id`, `transaction_type`, `amount`, `atm_id`,
                                    `currency_rate_id`)
VALUES (1, 1, 'withdrawal', 200.00, 1, 2),
       (2, 3, 'deposit', 150.00, 2, 1),
       (3, 5, 'withdrawal', 300.00, 3, 3),
       (4, 6, 'withdrawal', 50.00, 1, 2),
       (5, 7, 'deposit', 500.00, 2, 1),
       (6, 8, 'withdrawal', 100.00, 3, 3),
       (7, 9, 'deposit', 1200.00, 1, 2),
       (8, 10, 'withdrawal', 75.00, 2, 1),
       (9, 12, 'exchange', 400.00, 3, 3),
       (10, 13, 'deposit', 600.00, 1, 2);