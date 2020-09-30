CREATE DATABASE  IF NOT EXISTS `blackjack`;
USE `blackjack`;

DROP TABLE IF EXISTS `player`;

CREATE TABLE `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_name` varchar(15),
  `chips` int DEFAULT 10000,
  `rounds_played` int DEFAULT 0,
  `right_moves` int DEFAULT 0,
  `wrong_moves` int DEFAULT 0,
  `remaining_hints` int DEFAULT 5,
  PRIMARY KEY (`id`)
);

