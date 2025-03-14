DROP SCHEMA IF EXISTS `atmdb` ;
CREATE SCHEMA IF NOT EXISTS `atmdb`;
USE `atmdb` ;

CREATE TABLE currencies (
    iso_code VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `atmdb`.`accounts` (
  `id` SERIAL,
  `balance` DECIMAL(15,2) NOT NULL DEFAULT '0.00',
  `currency` VARCHAR(3) NOT NULL,
  `locked` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`),
  CONSTRAINT accounts_fk_currency_iso_code FOREIGN KEY (currency) REFERENCES currencies (iso_code)
	ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS `atmdb`.`cards` (
  `id` SERIAL,
  `number` VARCHAR(16) NOT NULL,
  `pin` CHAR(4) NOT NULL,
  `currency` VARCHAR(3) NOT NULL,
  `account_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `card_number_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_cards_accounts_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_cards_accounts`
    FOREIGN KEY (`account_id`)
    REFERENCES `atmdb`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT cards_fk_currency_iso_code FOREIGN KEY (currency) REFERENCES currencies (iso_code)
	ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS `atmdb`.`currency_rates` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `from_currency` VARCHAR(3) NOT NULL,
  `to_currency` VARCHAR(3) NOT NULL,
  `rate` DECIMAL(10,4) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT currency_rate_from_fk_currency_iso_code FOREIGN KEY (from_currency) REFERENCES currencies (iso_code)
	ON DELETE NO ACTION,
  CONSTRAINT currency_rate_to_fk_currency_iso_code FOREIGN KEY (to_currency) REFERENCES currencies (iso_code)
	ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS `atmdb`.`atms` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `location` VARCHAR(255) NOT NULL,
  `status` ENUM('active', 'out_of_service') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id`))
;

CREATE TABLE IF NOT EXISTS `atmdb`.`transactions` (
  `id` SERIAL,
  `card_id` BIGINT UNSIGNED NOT NULL,
  `transaction_type` ENUM('deposit', 'withdrawal') NOT NULL,
  `amount` DECIMAL(15,2) NULL,
  `timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `atm_id` INT UNSIGNED NOT NULL,
  `currency_rate_id` INT UNSIGNED,
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_cards1_idx` (`card_id` ASC) VISIBLE,
  INDEX `fk_transactions_atm1_idx` (`atm_id` ASC) VISIBLE,
  INDEX `fk_transactions_currency_rate1_idx` (`currency_rate_id` ASC) VISIBLE,
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
    REFERENCES `atmdb`.`currency_rates` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE IF NOT EXISTS `atmdb`.`banknote_types` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `currency` VARCHAR(3) NOT NULL,
  `denomination` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
    CONSTRAINT banknote_types_fk_currency_iso_code FOREIGN KEY (currency) REFERENCES currencies (iso_code)
    ON DELETE NO ACTION)

;

CREATE TABLE IF NOT EXISTS `atmdb`.`atms_have_banknote_types` (
  `atm_id` INT UNSIGNED NOT NULL,
  `banknote_type_id` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`atm_id`, `banknote_type_id`),
  INDEX `fk_atms_have_banknote_types_banknote_types1_idx` (`banknote_type_id` ASC) VISIBLE,
  INDEX `fk_atms_have_banknote_types_atms1_idx` (`atm_id` ASC) VISIBLE,
  CONSTRAINT `fk_atms_have_banknote_types_atms1`
    FOREIGN KEY (`atm_id`)
    REFERENCES `atmdb`.`atms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_atms_have_banknote_types_banknote_types1`
    FOREIGN KEY (`banknote_type_id`)
    REFERENCES `atmdb`.`banknote_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;