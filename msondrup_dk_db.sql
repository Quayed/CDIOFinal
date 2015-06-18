-- phpMyAdmin SQL Dump
-- version 4.4.7
-- http://www.phpmyadmin.net
--
-- Vært: localhost
-- Genereringstid: 18. 06 2015 kl. 22:58:06
-- Serverversion: 5.6.21
-- PHP-version: 5.6.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `msondrup_dk_db`
--

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `formula`
--

DROP TABLE IF EXISTS `formula`;
CREATE TABLE IF NOT EXISTS `formula` (
  `formula_id` int(11) NOT NULL,
  `formula_name` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `formula`
--

INSERT INTO `formula` (`formula_id`, `formula_name`) VALUES
(1, 'margherita'),
(2, 'prosciutto'),
(3, 'capricciosa');

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `formula_component`
--

DROP TABLE IF EXISTS `formula_component`;
CREATE TABLE IF NOT EXISTS `formula_component` (
  `formula_id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  `nom_netto` double NOT NULL,
  `tolerance` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `formula_component`
--

INSERT INTO `formula_component` (`formula_id`, `material_id`, `nom_netto`, `tolerance`) VALUES
(1, 1, 1, 10),
(1, 2, 0.5, 5),
(1, 5, 0.4, 10),
(2, 1, 1, 10),
(2, 3, 0.7, 7),
(2, 5, 1.2, 6),
(2, 6, 0.5, 4),
(3, 1, 1, 5),
(3, 4, 0.5, 6),
(3, 5, 0.3, 10),
(3, 6, 0.2, 10),
(3, 7, 0.6, 10);

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `material`
--

DROP TABLE IF EXISTS `material`;
CREATE TABLE IF NOT EXISTS `material` (
  `material_id` int(11) NOT NULL,
  `material_name` text NOT NULL,
  `provider` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `material`
--

INSERT INTO `material` (`material_id`, `material_name`, `provider`) VALUES
(1, 'dej', 'Wawelka'),
(2, 'tomat', 'Knoor'),
(3, 'tomat', 'Veaubais'),
(4, 'tomat', 'Franz'),
(5, 'ost', 'Ost og Skinke A/S'),
(6, 'skinke', 'Ost og Skinke A/S'),
(7, 'champignon', 'Igloo Frostvarer');

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `materialbatch`
--

DROP TABLE IF EXISTS `materialbatch`;
CREATE TABLE IF NOT EXISTS `materialbatch` (
  `mb_id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  `quantity` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `materialbatch`
--

INSERT INTO `materialbatch` (`mb_id`, `material_id`, `quantity`) VALUES
(1, 1, 1000),
(2, 2, 300),
(3, 3, 300),
(4, 5, 100),
(5, 5, 100),
(6, 6, 100),
(7, 7, 100);

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `productbatch`
--

DROP TABLE IF EXISTS `productbatch`;
CREATE TABLE IF NOT EXISTS `productbatch` (
  `pb_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `formula_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `productbatch`
--

INSERT INTO `productbatch` (`pb_id`, `status`, `formula_id`) VALUES
(1, 2, 1),
(2, 2, 1),
(3, 2, 2),
(4, 1, 3);

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `productbatch_component`
--

DROP TABLE IF EXISTS `productbatch_component`;
CREATE TABLE IF NOT EXISTS `productbatch_component` (
  `pb_id` int(11) NOT NULL DEFAULT '0',
  `mb_id` int(11) NOT NULL DEFAULT '0',
  `tare` double NOT NULL,
  `netto` double NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `productbatch_component`
--

INSERT INTO `productbatch_component` (`pb_id`, `mb_id`, `tare`, `netto`, `user_id`) VALUES
(1, 1, 0.5, 1.05, 1),
(1, 2, 0.5, 0.53, 1),
(1, 4, 0.5, 0.41, 1),
(2, 1, 0.5, 1.01, 2),
(2, 2, 0.5, 0.55, 2),
(2, 5, 0.5, 0.47, 2),
(3, 1, 0.5, 1.07, 1),
(3, 3, 0.5, 0.76, 2),
(3, 4, 0.5, 1.25, 1),
(3, 6, 0.5, 0.53, 2);

-- --------------------------------------------------------

--
-- Struktur-dump for tabellen `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL,
  `user_name` text NOT NULL,
  `ini` text NOT NULL,
  `cpr` varchar(10) NOT NULL,
  `password` text NOT NULL,
  `role` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Data dump for tabellen `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `ini`, `cpr`, `password`, `role`, `status`) VALUES
(1, 'Angelo A', 'AA', '1211951546', 'RandomPassword1234', 4, 1),
(2, 'Antonella B', 'AB', '1701414234', 'AnotherPassword123', 1, 1),
(3, 'Luigi C', 'LC', '1211851546', 'ThirdPass1234', 1, 0),
(4, 'Gruppe Tretten', 'GT', '2507751345', 'FourthPass1234', 3, 1),
(5, 'Jens Jensen', 'JJ', '3101801434', 'YetAnotherPass123', 2, 1);

--
-- Begrænsninger for dumpede tabeller
--

--
-- Indeks for tabel `formula`
--
ALTER TABLE `formula`
  ADD PRIMARY KEY (`formula_id`);

--
-- Indeks for tabel `formula_component`
--
ALTER TABLE `formula_component`
  ADD PRIMARY KEY (`formula_id`,`material_id`),
  ADD KEY `material_id` (`material_id`);

--
-- Indeks for tabel `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`material_id`);

--
-- Indeks for tabel `materialbatch`
--
ALTER TABLE `materialbatch`
  ADD PRIMARY KEY (`mb_id`),
  ADD KEY `material_id` (`material_id`);

--
-- Indeks for tabel `productbatch`
--
ALTER TABLE `productbatch`
  ADD PRIMARY KEY (`pb_id`),
  ADD KEY `formula_id` (`formula_id`);

--
-- Indeks for tabel `productbatch_component`
--
ALTER TABLE `productbatch_component`
  ADD PRIMARY KEY (`pb_id`,`mb_id`),
  ADD KEY `mb_id` (`mb_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indeks for tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `cpr` (`cpr`);

--
-- Begrænsninger for dumpede tabeller
--

--
-- Begrænsninger for tabel `formula_component`
--
ALTER TABLE `formula_component`
  ADD CONSTRAINT `formula_component_ibfk_1` FOREIGN KEY (`formula_id`) REFERENCES `formula` (`formula_id`),
  ADD CONSTRAINT `formula_component_ibfk_2` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`);

--
-- Begrænsninger for tabel `materialbatch`
--
ALTER TABLE `materialbatch`
  ADD CONSTRAINT `materialbatch_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`);

--
-- Begrænsninger for tabel `productbatch`
--
ALTER TABLE `productbatch`
  ADD CONSTRAINT `productbatch_ibfk_1` FOREIGN KEY (`formula_id`) REFERENCES `formula` (`formula_id`);

--
-- Begrænsninger for tabel `productbatch_component`
--
ALTER TABLE `productbatch_component`
  ADD CONSTRAINT `productbatch_component_ibfk_1` FOREIGN KEY (`pb_id`) REFERENCES `productbatch` (`pb_id`),
  ADD CONSTRAINT `productbatch_component_ibfk_2` FOREIGN KEY (`mb_id`) REFERENCES `materialbatch` (`mb_id`),
  ADD CONSTRAINT `productbatch_component_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
