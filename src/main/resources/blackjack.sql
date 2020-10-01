CREATE DATABASE  IF NOT EXISTS `motherdatabase`;
USE `motherdatabase`;

DROP TABLE IF EXISTS `mother`;

CREATE TABLE `mother` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45),
  `age` int,
  `address` varchar(45),
  PRIMARY KEY (`id`)
);

