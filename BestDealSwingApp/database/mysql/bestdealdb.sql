-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2014 at 07:46 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bestdealdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`name`) VALUES
('Cameras and Optics'),
('Car'),
('Electronics'),
('Hardware'),
('Health and Beauty'),
('Software');

-- --------------------------------------------------------

--
-- Table structure for table `deal`
--

CREATE TABLE IF NOT EXISTS `deal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `desc` varchar(150) NOT NULL,
  `price` float NOT NULL,
  `category` varchar(50) NOT NULL,
  `startDate` datetime NOT NULL,
  `duration` int(11) NOT NULL DEFAULT '300000',
  `owner` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `startDate` (`startDate`),
  KEY `owner` (`owner`),
  KEY `category` (`category`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=58 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`firstName`, `lastName`, `login`, `pwd`) VALUES
('', '', '', ''),
('Mohamed', 'Wertani', 'mohamed', 'mohamed'),
('Seifeddine', 'Dridi', 'seif', 'seif');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `deal`
--
ALTER TABLE `deal`
  ADD CONSTRAINT `deal_ibfk_2` FOREIGN KEY (`owner`) REFERENCES `user` (`login`),
  ADD CONSTRAINT `deal_ibfk_3` FOREIGN KEY (`owner`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `deal_ibfk_4` FOREIGN KEY (`category`) REFERENCES `category` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
