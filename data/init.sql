-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema wishme
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `wishme` ;

-- -----------------------------------------------------
-- Schema wishme
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `wishme` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `wishme` ;

-- -----------------------------------------------------
-- Table `wishme`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wishme`.`asset` (
  `asset_seq` BIGINT NOT NULL AUTO_INCREMENT,
  `asset_img` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`asset_seq`))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `wishme`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wishme`.`user` (
  `user_seq` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `user_nickname` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_seq`))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `wishme`.`myletter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wishme`.`myletter` (
  `my_letter_seq` BIGINT NOT NULL AUTO_INCREMENT,
  `to_user` BIGINT NOT NULL,
  `asset_seq` BIGINT NOT NULL,
  `create_at` DATETIME NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `from_user` BIGINT NULL DEFAULT NULL,
  `is_public` TINYINT(1) NOT NULL DEFAULT '0',
  `nickname` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`my_letter_seq`),
  INDEX `fk_myletter_user_idx` (`to_user` ASC) VISIBLE,
  INDEX `fk_myletter_asset1_idx` (`asset_seq` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `wishme`.`reply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wishme`.`reply` (
  `reply_seq` BIGINT NOT NULL AUTO_INCREMENT,
  `my_letter_seq` BIGINT NOT NULL,
  `create_at` DATETIME NOT NULL,
  `content` TEXT NOT NULL,
  `from_user` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`reply_seq`),
  INDEX `fk_reply_myletter1_idx` (`my_letter_seq` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `wishme`.`school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wishme`.`school` (
  `school_seq` INT NOT NULL AUTO_INCREMENT,
  `region` VARCHAR(255) NOT NULL,
  `school_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`school_seq`))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `wishme`.`schoolletter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wishme`.`schoolletter` (
  `school_letter_seq` BIGINT NOT NULL AUTO_INCREMENT,
  `asset_seq` BIGINT NOT NULL,
  `school_seq` INT NOT NULL,
  `create_at` DATETIME NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `nickname` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`school_letter_seq`),
  INDEX `fk_schoolletter_asset1_idx` (`asset_seq` ASC) VISIBLE,
  INDEX `fk_schoolletter_school1_idx` (`school_seq` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
