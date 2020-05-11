CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8;
CREATE TABLE `internetshop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(225) NOT NULL,
  `user_login` VARCHAR(225) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_login_UNIQUE` (`user_login` ASC) VISIBLE);

  CREATE TABLE `internetshop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(255) NOT NULL,
  `product_price` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
