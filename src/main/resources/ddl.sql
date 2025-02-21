DROP SCHEMA IF EXISTS `atmdb` ;
CREATE SCHEMA IF NOT EXISTS `atmdb` DEFAULT CHARACTER SET utf8 ;
USE `atmdb` ;

CREATE TABLE IF NOT EXISTS `atmdb`.`accounts` (
  `id` BIGINT(15) NOT NULL,
  `balance` VARCHAR(45) NOT NULL DEFAULT '0.00',
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `locked` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
  ;

CREATE TABLE IF NOT EXISTS `atmdb`.`cards` (
  `id` BIGINT(15) NOT NULL,
  `card_numer` VARCHAR(16) NOT NULL,
  `pin` CHAR(4) NOT NULL,
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `accounts_id` BIGINT(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `card_numer_UNIQUE` (`card_numer` ASC) VISIBLE,
  INDEX `fk_cards_accounts_idx` (`accounts_id` ASC) VISIBLE,
  CONSTRAINT `fk_cards_accounts`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `atmdb`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ;

CREATE TABLE IF NOT EXISTS `atmdb`.`atm_cash` (
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `denomination` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`currency`, `denomination`),
  INDEX `denomination` (`denomination` ASC) VISIBLE,
  INDEX `quantity` (`quantity` ASC) VISIBLE)
  ;

CREATE TABLE IF NOT EXISTS `atmdb`.`exchenge_rates` (
  `from_currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `to_currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `rate` DECIMAL(10,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`from_currency`, `to_currency`))
;

CREATE TABLE IF NOT EXISTS `atmdb`.`transactions` (
  `id` BIGINT(15) NOT NULL,
  `accounts_id` BIGINT(15) NOT NULL,
  `cards_id` BIGINT(15) NOT NULL,
  `transaction_type` ENUM('withdrawal', 'deposit', 'exchange') NOT NULL,
  `amount` DECIMAL(15,2) UNSIGNED NULL,
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `atm_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_accounts1_idx` (`accounts_id` ASC) VISIBLE,
  INDEX `fk_transactions_cards1_idx` (`cards_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_accounts1`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `atmdb`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_cards1`
    FOREIGN KEY (`cards_id`)
    REFERENCES `atmdb`.`cards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;