/*
Navicat MySQL Data Transfer

Source Server         : testing
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : mailingdb

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2010-10-11 14:34:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mails`
-- ----------------------------
DROP TABLE IF EXISTS `mails`;
CREATE TABLE `mails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `Subject` text NOT NULL,
  `Content` longtext NOT NULL,
  `IsImediateMessage` bit(1) NOT NULL,
  `CreationDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of mails
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` text NOT NULL,
  `email` text NOT NULL,
  `lastMailSendedDate` datetime DEFAULT NULL,
  `mailingDelai` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

ALTER TABLE `mails` ADD CONSTRAINT fk_mailhaveuser FOREIGN KEY (UserId) REFERENCES users(id);
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO users VALUES ('1', 'toto', 'toto@hotmail.com', null, '0');
INSERT INTO users VALUES ('2', 'mami', 'mami@hotmail.com', null, '1');
INSERT INTO users VALUES ('3', 'alain', 'alain@hotmail.com', null, '2');
INSERT INTO users VALUES ('4', 'eric', 'eric@hotmail.com', null, '2');
INSERT INTO users VALUES ('5', 'steph', 'steph@hotmail.com', null, '1');
INSERT INTO users VALUES ('6', 'john', 'john@hotmail.com', null, '0');
INSERT INTO users VALUES ('7', 'tati', 'tati@hotmail.com', null, '0');

