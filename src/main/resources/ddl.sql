DROP SCHEMA IF EXISTS `atmdb` ;
CREATE SCHEMA IF NOT EXISTS `atmdb` DEFAULT CHARACTER SET utf8 ;
USE `atmdb` ;

CREATE TABLE IF NOT EXISTS `atmdb`.`accounts` (
  `id` BIGINT(15) NOT NULL,
  `balance` DECIMAL(15,2) NOT NULL DEFAULT '0.00',
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `locked` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
;

CREATE TABLE IF NOT EXISTS `atmdb`.`cards` (
  `id` BIGINT(15) NOT NULL,
  `number` VARCHAR(16) NOT NULL,
  `pin` CHAR(4) NOT NULL,
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `account_id` BIGINT(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `card_numer_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_cards_accounts_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_cards_accounts`
    FOREIGN KEY (`account_id`)
    REFERENCES `atmdb`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE IF NOT EXISTS `atmdb`.`atms` (
  `id` INT NOT NULL,
  `location` VARCHAR(255) NOT NULL,
  `status` ENUM('active', 'out_of_service') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id`))
;

CREATE TABLE IF NOT EXISTS `atmdb`.`atm_cash` (
  `id` INT NOT NULL,
  `currency` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `denomination` INT NOT NULL,
  `quality` INT NOT NULL,
  `atm_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `denomination` (`currency` ASC) VISIBLE,
  INDEX `quantity` (`denomination` ASC) VISIBLE,
  INDEX `fk_atm_cash_atm1_idx` (`atm_id` ASC) VISIBLE,
  CONSTRAINT `fk_atm_cash_atm1`
    FOREIGN KEY (`atm_id`)
    REFERENCES `atmdb`.`atms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE IF NOT EXISTS `atmdb`.`currency_rate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` ENUM('EUR', 'USD', 'PLN') NOT NULL,
  `rate` DECIMAL(10,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
;

CREATE TABLE IF NOT EXISTS `atmdb`.`transactions` (
  `id` BIGINT(15) NOT NULL,
  `account_id` BIGINT(15) NOT NULL,
  `card_id` BIGINT(15) NOT NULL,
  `transaction_type` ENUM('withdrawal', 'deposit', 'exchange') NOT NULL,
  `amount` DECIMAL(15,2) UNSIGNED NULL,
  `timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `atm_id` INT NOT NULL,
  `currency_rate_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_accounts1_idx` (`account_id` ASC) VISIBLE,
  INDEX `fk_transactions_cards1_idx` (`card_id` ASC) VISIBLE,
  INDEX `fk_transactions_atm1_idx` (`atm_id` ASC) VISIBLE,
  INDEX `fk_transactions_currency_rate1_idx` (`currency_rate_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_accounts1`
    FOREIGN KEY (`account_id`)
    REFERENCES `atmdb`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_cards1`
    FOREIGN KEY (`card_id`)
    REFERENCES `atmdb`.`cards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_atms1`
    FOREIGN KEY (`atm_id`)
    REFERENCES `atmdb`.`atms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_currency_rate1`
    FOREIGN KEY (`currency_rate_id`)
    REFERENCES `atmdb`.`currency_rate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;