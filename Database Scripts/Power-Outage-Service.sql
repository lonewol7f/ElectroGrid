-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2022 at 02:30 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `paftest`
--
CREATE DATABASE IF NOT EXISTS `paftest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `paftest`;

-- --------------------------------------------------------

--
-- Table structure for table `outage`
--

CREATE TABLE `outage` (
  `outageID` int(11) NOT NULL,
  `cusID` int(11) NOT NULL,
  `cusName` varchar(30) NOT NULL,
  `outArea` varchar(20) NOT NULL,
  `outDate` varchar(15) NOT NULL,
  `outTime` varchar(8) NOT NULL,
  `outDesc` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `outage`
--

INSERT INTO `outage` (`outageID`, `cusID`, `cusName`, `outArea`, `outDate`, `outTime`, `outDesc`) VALUES
(5, 4, 'Sachintha', 'Colombo', '22/04/2022', '9.00pm', 'outage/breakdown Resolved');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `outage`
--
ALTER TABLE `outage`
  ADD PRIMARY KEY (`outageID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `outage`
--
ALTER TABLE `outage`
  MODIFY `outageID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
