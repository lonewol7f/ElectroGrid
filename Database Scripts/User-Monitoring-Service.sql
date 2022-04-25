CREATE DATABASE  IF NOT EXISTS `meterreadings` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `meterreadings`;

DROP TABLE IF EXISTS `meterreadings`;
CREATE TABLE `meterreadings` (
  `meterID` int(11) NOT NULL AUTO_INCREMENT,
  `meterCode` varchar(10) CHARACTER SET latin1 NOT NULL,
  `houseownerName` varchar(30) CHARACTER SET latin1 NOT NULL,
  `singleUnitPrice` decimal(5,2) NOT NULL,
  `unitsAmount` int(5) NOT NULL,
  `totalPrice` decimal(9,2) NOT NULL,
  PRIMARY KEY (`meterID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `meterreadings` WRITE;
/*!40000 ALTER TABLE `meterreadings` DISABLE KEYS */;
INSERT INTO `meterreadings` VALUES ('501','Epun','34.50','345');
/*!40000 ALTER TABLE `meterreadings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;




