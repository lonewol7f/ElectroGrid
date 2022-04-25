CREATE DATABASE  IF NOT EXISTS 'connection_request';
USE 'connection_request';

DROP TABLE IF EXISTS 'connection_request'

CREATE TABLE `connection_req` (
  `connReqId` int(11) NOT NULL AUTO_INCREMENT,
  `nicNo` varchar(10) DEFAULT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `address` varchar(50) NOT NULL,
  `tpNo` varchar(10) NOT NULL,
  PRIMARY KEY (`connReqId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;