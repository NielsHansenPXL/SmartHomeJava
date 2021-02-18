DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET latin1 NOT NULL,
  `password` varchar(64) CHARACTER SET latin1 NOT NULL,
  `role` varchar(45) CHARACTER SET latin1 NOT NULL,
  `enabled` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO users VALUES (1,'admin','$2a$10$.P3jbm1H.4PGCYSDk7K81eMl4BBXTwtI3KBaCdsBq0gqYtLeC8Mt.','ROLE_ADULT',true);
INSERT INTO users VALUES (2,'niels','$2a$10$2HDry6Mkr/143ZVSBRb27OBIR1yeBA8Sh330XzfJPwzZMANCQeMkm','ROLE_MINOR',true);
INSERT INTO users VALUES (3, 'alexander','$2a$10$JG/5w98.IU2Gp9O.caoekuhyKotfR8UCZ3tWdfLmKd5CeHFuUEjAq','ROLE_MINOR',true);