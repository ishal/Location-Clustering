-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 20, 2014 at 05:26 AM
-- Server version: 5.5.36
-- PHP Version: 5.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `studentlocation`
--

-- --------------------------------------------------------

--
-- Table structure for table `pidcellidsem`
--

CREATE TABLE IF NOT EXISTS `pidcellidsem` (
  `pid` int(11) NOT NULL,
  `cellId` double NOT NULL,
  `Semantics` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pidcellidsem`
--

INSERT INTO `pidcellidsem` (`pid`, `cellId`, `Semantics`) VALUES
(1, 5123.40763, 'home'),
(1, 5188.40763, 'home'),
(1, 5188.40762, 'home'),
(1, 5119.40811, 'work'),
(1, 5119.40332, 'work'),
(2, 5119.40332, 'work'),
(2, 5119.40811, 'work'),
(3, 24127.00111, 'work'),
(3, 24127.00182, 'home'),
(3, 1013.10332, 'home');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
