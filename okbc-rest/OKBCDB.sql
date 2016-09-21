-- phpMyAdmin SQL Dump
-- version 4.6.3
-- https://www.phpmyadmin.net/
--
-- Host: mysqlhost.uni-koblenz.de
-- Erstellungszeit: 21. Sep 2016 um 14:20
-- Server-Version: 10.0.26-MariaDB
-- PHP-Version: 7.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `OKBCDB`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Authors`
--

CREATE TABLE `Authors` (
  `authorid` int(11) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `refid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Authors`
--

INSERT INTO `Authors` (`authorid`, `author`, `refid`) VALUES
(1, 'John Doe', 30),
(2, 'Maria Gill', 31),
(3, 'John Doe', 32),
(4, 'Laura Clark', 33),
(5, 'Ahmed Sheikh', 34),
(6, 'John Doe', 35),
(7, 'Laura Clark', 36),
(8, 'Jeremy White', 37),
(9, 'John Doe', 38),
(10, 'Laura Clark', 39),
(11, 'Jeremy White', 40),
(12, 'Ahmed Sheikh', 41),
(13, 'Jeremy White', 42),
(14, 'Ahmed Sheikh', 43),
(15, 'Laura Clark', 44),
(16, 'Ahmed Sheikh', 45),
(17, 'Laura Clark', 46),
(18, 'Laura Clark', 47),
(19, 'Ahmed Sheikh', 49),
(20, '', 57),
(21, '', 58),
(22, '', 59),
(23, '', 60),
(24, '', 61),
(25, '', 62),
(26, '', 63),
(27, '', 64),
(28, '', 65),
(29, '', 66);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Categories`
--

CREATE TABLE `Categories` (
  `ctid` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `eventid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Categories`
--

INSERT INTO `Categories` (`ctid`, `category`, `eventid`) VALUES
(1, 'tennis tournament', 23892541),
(2, 'Men\'s Singles', 23892541),
(3, 'suicide bombing', 23365300),
(4, 'mass murder', 23365300),
(5, 'terrorist attack', 23365300),
(6, 'flood', 24287229),
(7, 'train wreck', 22673778),
(8, 'head-on collision', 22673778),
(9, 'referendum', 21812812),
(10, 'terrorist attack', 25007917),
(11, 'suicide bombing', 25007917),
(12, 'accident', 23936619),
(13, 'plane crash', 23936619);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Claims`
--

CREATE TABLE `Claims` (
  `claimid` int(11) NOT NULL,
  `snaktype` enum('value','novalue','missingvalue') DEFAULT NULL,
  `cvalue` varchar(255) DEFAULT NULL,
  `ranking` enum('deprecated','normal','preferred') DEFAULT NULL,
  `multiclaimtype` varchar(31) DEFAULT '',
  `statementid` int(11) NOT NULL,
  `userid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Claims`
--

INSERT INTO `Claims` (`claimid`, `snaktype`, `cvalue`, `ranking`, `multiclaimtype`, `statementid`, `userid`) VALUES
(1, 'value', 'Novak Djokovic', NULL, '', 1, NULL),
(2, 'value', 'Andy Murray', NULL, '', 1, NULL),
(3, 'value', '30', NULL, '', 2, NULL),
(4, 'value', '30', NULL, '', 2, NULL),
(5, 'value', '20', NULL, '', 2, NULL),
(6, 'value', '16', NULL, '', 3, NULL),
(7, 'value', '14', NULL, '', 3, NULL),
(8, 'value', '16', NULL, '', 3, NULL),
(9, 'value', '2016-02-11', NULL, '', 4, NULL),
(10, 'value', '2016-02-11', NULL, '', 4, NULL),
(11, 'value', '2016-02-12', NULL, '', 4, NULL),
(12, 'value', '2016-02-10', NULL, '', 4, NULL),
(13, 'value', '33551983', NULL, '', 5, NULL),
(14, 'value', '33551983', NULL, '', 5, NULL),
(15, 'value', '33551983', NULL, '', 5, NULL),
(16, 'value', '46500001', NULL, '', 7, NULL),
(17, 'value', '46500001', NULL, '', 7, NULL),
(18, 'value', '147', 'deprecated', '', 8, NULL),
(19, 'value', '150', 'deprecated', '', 8, NULL),
(20, 'value', '120', 'deprecated', '', 8, NULL),
(21, 'value', '147', 'deprecated', '', 8, NULL),
(22, 'value', 'Øygarden', NULL, '', 9, NULL),
(23, 'value', 'Fjell', NULL, '', 9, NULL),
(24, 'value', 'Fjell', NULL, '', 9, NULL),
(25, 'value', '342', NULL, 'conflict', 10, 1),
(26, 'value', '342', NULL, 'conflict', 10, 1),
(27, 'value', '342', NULL, 'conflict', 10, 1),
(37, 'value', '230', 'preferred', 'collision', 8, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Events`
--

CREATE TABLE `Events` (
  `eventid` int(11) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `lastedited` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Events`
--

INSERT INTO `Events` (`eventid`, `label`, `lastedited`) VALUES
(99999, 'test event', '2016-09-19 00:00:00'),
(21812812, 'United Kingdom European Union membership referendum', '2016-07-19 01:02:03'),
(22673778, 'Bad Aibling rail crash', '2016-07-19 01:02:03'),
(23365300, '2016 Brussels bombings', '2016-07-19 01:02:03'),
(23892541, '2016 French Open', '2016-07-19 01:02:03'),
(23936619, '2016 Turøy helicopter crash', '2016-07-19 01:02:03'),
(24287229, '2016 European Floods', '2016-07-19 01:02:03'),
(25007917, '2016 Istanbul airport attack', '2016-07-19 01:02:03');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Qualifiers`
--

CREATE TABLE `Qualifiers` (
  `qualifierid` int(11) NOT NULL,
  `propertyid` int(11) NOT NULL,
  `datatype` enum('quantity','item','property','url','time','globe coordinate','string') DEFAULT NULL,
  `qvalue` varchar(255) NOT NULL,
  `claimid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Refs`
--

CREATE TABLE `Refs` (
  `refid` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `publicationdate` date DEFAULT NULL,
  `retrievaldate` date DEFAULT NULL,
  `articletype` varchar(255) DEFAULT NULL,
  `trustrating` float DEFAULT NULL,
  `neutralityrating` float DEFAULT NULL,
  `claimid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Refs`
--

INSERT INTO `Refs` (`refid`, `url`, `title`, `publicationdate`, `retrievaldate`, `articletype`, `trustrating`, `neutralityrating`, `claimid`) VALUES
(1, 'http://bbc.com', 'Djokovic win 2016 French open', '2016-06-06', '2016-06-07', NULL, 0.8, 0, 61),
(2, 'http://fox.com', 'Murray beats Djokovic', '2016-06-06', '2016-06-08', NULL, 0.1, 0, 62),
(3, 'http://bbc.com', 'Brussels bombing response', '2016-03-25', '2016-04-07', NULL, 0.8, 0, 63),
(4, 'http://nytimes.com', 'Brussels attack investigation', '2016-03-24', '2016-04-04', NULL, 0.9, 0, 64),
(5, 'http://al-jezeera.com', 'Attack in Brussels', '2016-03-26', '2016-04-08', NULL, 0.3, 0, 65),
(6, 'http://dw.com', 'Deadly flood in Europe', '2016-06-02', '2016-06-02', NULL, 0.9, 0.2, 66),
(7, 'http://dw.com', 'Flood due to heavy rain', '2016-06-02', '2016-06-04', NULL, 0.9, 0.2, 67),
(8, 'http://bbc.com', 'Flood in central Europe', '2016-06-06', '2016-06-07', NULL, 0.6, 0.7, 68),
(9, 'http://www.dw.com/en/death-toll-goes-up-in-bad-aibling-train-crash/a-19043637', 'Death Toll goes up in Bad Aibling Crash', '2016-02-11', '2016-02-12', NULL, 0.9, 0.4, 69),
(10, 'http://www.dw.com/en/prosecutor-identifies-human-error-as-cause-of-bad-aibling-train-disaster/a-19051663', 'Prosecutor identifies human error as cause of Bad Aibling train disaster', '2016-02-13', '2016-02-14', NULL, 0.9, 0.4, 70),
(11, 'http://bbc.com', 'Train Crash in Germany', '2016-02-12', '2016-02-13', NULL, 0.6, 0.7, 71),
(12, 'http://fox.com', 'Train Crash in Germany', '2016-02-10', '2016-02-13', NULL, 0.4, 0.7, 72),
(13, 'http://bbc.com', 'Britain leaves EU', '2016-06-24', '2016-06-24', NULL, 0.9, 0, 73),
(14, 'http://al-jezeera.com', 'UK out of EU', '2016-06-24', '2016-06-24', NULL, 0.4, 0, 74),
(15, 'http://nytimes.com', 'Britishers decides to leave EU', '2016-06-24', '2016-06-24', NULL, 0.8, 0, 75),
(16, 'http://al-jezeera.com', 'Britishers decides to leave EU', '2016-06-22', '2016-06-23', NULL, 0.4, 0, 76),
(17, 'http://nytimes.com', 'Britishers decides to leave EU', '2016-06-24', '2016-06-24', NULL, 0.9, 0, 77),
(18, 'http://bbc.com', 'Several dead in Istanbul airport attack', '2016-06-29', '2016-06-29', NULL, 0.8, 0, 78),
(19, 'http://fox.com', 'Attack on Istanbul airport', '2016-06-28', '2016-06-29', NULL, 0.2, 0, 79),
(20, 'http://al-jezeera.com', 'Sucide bombing in Istanbul', '2016-06-28', '2016-06-28', NULL, 0.7, 0, 80),
(21, 'http://dw.com', 'Many injured in Istanbul attack', '2016-06-28', '2016-06-29', NULL, 0.9, 0, 81),
(22, 'http://nytimes.com', '2016 Turøy helicopter crash', '2016-04-29', '2016-04-29', NULL, 0.9, 0, 82),
(23, 'http://fox.com', '2016 Turøy helicopter crash', '2016-04-29', '2016-04-29', NULL, 0.6, 0, 83),
(24, 'http://al-jezeera.com', '2016 Turøy helicopter crash', '2016-04-29', '2016-04-29', NULL, 0.6, 0, 84),
(25, 'example.org', NULL, '2016-09-19', '2016-09-19', NULL, 0, 0, 85),
(26, 'example.org', NULL, '2016-09-19', '2016-09-19', NULL, 0.81, 0.69, 86),
(27, 'omsegdr', NULL, '1062-12-01', '1345-10-01', NULL, 0.1, 0.3, 87),
(28, 'omsegdr', NULL, '1062-12-01', '1345-10-01', NULL, 0.1, 0.3, 88),
(29, 'omsegdr', NULL, '1062-12-01', '1345-10-01', NULL, 0.1, 0.3, 89),
(30, 'http://bbc.com', 'Djokovic win 2016 French open', '2016-06-06', '2016-06-07', NULL, 0.8, 0, 1),
(31, 'http://fox.com', 'Murray beats Djokovic', '2016-06-06', '2016-06-08', NULL, 0.1, 0, 2),
(32, 'http://bbc.com', 'Brussels bombing response', '2016-03-25', '2016-04-07', NULL, 0.8, 0, 3),
(33, 'http://nytimes.com', 'Brussels attack investigation', '2016-03-24', '2016-04-04', NULL, 0.9, 0, 4),
(34, 'http://al-jezeera.com', 'Attack in Brussels', '2016-03-26', '2016-04-08', NULL, 0.3, 0, 5),
(35, 'http://dw.com', 'Deadly flood in Europe', '2016-06-02', '2016-06-02', NULL, 0.9, 0.2, 6),
(36, 'http://dw.com', 'Flood due to heavy rain', '2016-06-02', '2016-06-04', NULL, 0.9, 0.2, 7),
(37, 'http://bbc.com', 'Flood in central Europe', '2016-06-06', '2016-06-07', NULL, 0.6, 0.7, 8),
(38, 'http://www.dw.com/en/death-toll-goes-up-in-bad-aibling-train-crash/a-19043637', 'Death Toll goes up in Bad Aibling Crash', '2016-02-11', '2016-02-12', NULL, 0.9, 0.4, 9),
(39, 'http://www.dw.com/en/prosecutor-identifies-human-error-as-cause-of-bad-aibling-train-disaster/a-19051663', 'Prosecutor identifies human error as cause of Bad Aibling train disaster', '2016-02-13', '2016-02-14', NULL, 0.9, 0.4, 10),
(40, 'http://bbc.com', 'Train Crash in Germany', '2016-02-12', '2016-02-13', NULL, 0.6, 0.7, 11),
(41, 'http://fox.com', 'Train Crash in Germany', '2016-02-10', '2016-02-13', NULL, 0.4, 0.7, 12),
(42, 'http://bbc.com', 'Britain leaves EU', '2016-06-24', '2016-06-24', NULL, 0.9, 0, 13),
(43, 'http://al-jezeera.com', 'UK out of EU', '2016-06-24', '2016-06-24', NULL, 0.4, 0, 14),
(44, 'http://nytimes.com', 'Britishers decides to leave EU', '2016-06-24', '2016-06-24', NULL, 0.8, 0, 15),
(45, 'http://al-jezeera.com', 'Britishers decides to leave EU', '2016-06-22', '2016-06-23', NULL, 0.4, 0, 16),
(46, 'http://nytimes.com', 'Britishers decides to leave EU', '2016-06-24', '2016-06-24', NULL, 0.9, 0, 17),
(47, 'http://bbc.com', 'Several dead in Istanbul airport attack', '2016-06-29', '2016-06-29', NULL, 0.8, 0, 18),
(48, 'http://fox.com', 'Attack on Istanbul airport', '2016-06-28', '2016-06-29', NULL, 0.2, 0, 19),
(49, 'http://al-jezeera.com', 'Sucide bombing in Istanbul', '2016-06-28', '2016-06-28', NULL, 0.7, 0, 20),
(50, 'http://dw.com', 'Many injured in Istanbul attack', '2016-06-28', '2016-06-29', NULL, 0.9, 0, 21),
(51, 'http://nytimes.com', '2016 Turøy helicopter crash', '2016-04-29', '2016-04-29', NULL, 0.9, 0, 22),
(52, 'http://fox.com', '2016 Turøy helicopter crash', '2016-04-29', '2016-04-29', NULL, 0.6, 0, 23),
(53, 'http://al-jezeera.com', '2016 Turøy helicopter crash', '2016-04-29', '2016-04-29', NULL, 0.6, 0, 24),
(54, 'omsegdr', NULL, '1062-12-01', '1345-10-01', NULL, 0.1, 0.3, 25),
(55, 'omsegdr', NULL, '1062-12-01', '1345-10-01', NULL, 0.1, 0.3, 26),
(56, 'omsegdr', NULL, '1062-12-01', '1345-10-01', NULL, 0.1, 0.3, 27),
(57, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 28),
(58, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 29),
(59, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 30),
(60, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 31),
(61, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 32),
(62, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-07-29', '2016-09-19', NULL, 1, 1, 33),
(63, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 34),
(64, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 35),
(65, 'http://www.bbc.com/news/world-europe-36658187', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 36),
(66, 'Demo video for the Open Knowledge Base project and research practical at the University Koblenz. The video shows the process of claim curation and the results of the ranking process.', NULL, '2016-06-29', '2016-09-19', NULL, 1, 1, 37);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Statements`
--

CREATE TABLE `Statements` (
  `statementid` int(11) NOT NULL,
  `propertyid` int(11) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `datatype` varchar(255) NOT NULL,
  `eventid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Statements`
--

INSERT INTO `Statements` (`statementid`, `propertyid`, `label`, `datatype`, `eventid`) VALUES
(1, 1346, 'winner', 'item', 23892541),
(2, 1339, 'number of injured', 'quantity', 23365300),
(3, 1120, 'number of deaths', 'quantity', 24287229),
(4, 1339, 'start time', 'time', 22673778),
(5, 1697, 'total valid votes', 'quantity', 21812812),
(6, 1867, 'eligible voters', 'quantity', 21812812),
(7, 1867, 'eligible voters', 'quantity', 21812812),
(8, 1339, 'number of injured', 'quantity', 25007917),
(9, 131, 'located in the administrative territorial entity', 'item', 23936619),
(10, 123, 'test property', 'NULL', 99999);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Users`
--

CREATE TABLE `Users` (
  `userid` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `reputation` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Users`
--

INSERT INTO `Users` (`userid`, `username`, `reputation`) VALUES
(1, 'test user', 0.0196078),
(2, 'Faility', 0.023);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserVotes`
--

CREATE TABLE `UserVotes` (
  `userid` int(11) DEFAULT NULL,
  `claimid` int(11) DEFAULT NULL,
  `preferred` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `Authors`
--
ALTER TABLE `Authors`
  ADD PRIMARY KEY (`authorid`),
  ADD UNIQUE KEY `authorid` (`authorid`),
  ADD KEY `refid` (`refid`);

--
-- Indizes für die Tabelle `Categories`
--
ALTER TABLE `Categories`
  ADD PRIMARY KEY (`ctid`),
  ADD UNIQUE KEY `ctid` (`ctid`),
  ADD KEY `eventid` (`eventid`);

--
-- Indizes für die Tabelle `Claims`
--
ALTER TABLE `Claims`
  ADD PRIMARY KEY (`claimid`),
  ADD UNIQUE KEY `claimid` (`claimid`),
  ADD KEY `statementid` (`statementid`),
  ADD KEY `userid` (`userid`);

--
-- Indizes für die Tabelle `Events`
--
ALTER TABLE `Events`
  ADD PRIMARY KEY (`eventid`),
  ADD UNIQUE KEY `eventid` (`eventid`);

--
-- Indizes für die Tabelle `Qualifiers`
--
ALTER TABLE `Qualifiers`
  ADD PRIMARY KEY (`qualifierid`),
  ADD UNIQUE KEY `qualifierid` (`qualifierid`),
  ADD KEY `claimid` (`claimid`);

--
-- Indizes für die Tabelle `Refs`
--
ALTER TABLE `Refs`
  ADD PRIMARY KEY (`refid`),
  ADD UNIQUE KEY `refid` (`refid`),
  ADD KEY `claimid` (`claimid`);

--
-- Indizes für die Tabelle `Statements`
--
ALTER TABLE `Statements`
  ADD PRIMARY KEY (`statementid`),
  ADD UNIQUE KEY `statementid` (`statementid`),
  ADD KEY `eventid` (`eventid`);

--
-- Indizes für die Tabelle `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `userid` (`userid`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indizes für die Tabelle `UserVotes`
--
ALTER TABLE `UserVotes`
  ADD KEY `userid` (`userid`),
  ADD KEY `claimid` (`claimid`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `Authors`
--
ALTER TABLE `Authors`
  MODIFY `authorid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT für Tabelle `Categories`
--
ALTER TABLE `Categories`
  MODIFY `ctid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT für Tabelle `Claims`
--
ALTER TABLE `Claims`
  MODIFY `claimid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT für Tabelle `Qualifiers`
--
ALTER TABLE `Qualifiers`
  MODIFY `qualifierid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `Refs`
--
ALTER TABLE `Refs`
  MODIFY `refid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;
--
-- AUTO_INCREMENT für Tabelle `Statements`
--
ALTER TABLE `Statements`
  MODIFY `statementid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT für Tabelle `Users`
--
ALTER TABLE `Users`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `Authors`
--
ALTER TABLE `Authors`
  ADD CONSTRAINT `Authors_ibfk_1` FOREIGN KEY (`refid`) REFERENCES `Refs` (`refid`);

--
-- Constraints der Tabelle `Categories`
--
ALTER TABLE `Categories`
  ADD CONSTRAINT `Categories_ibfk_1` FOREIGN KEY (`eventid`) REFERENCES `Events` (`eventid`);

--
-- Constraints der Tabelle `Claims`
--
ALTER TABLE `Claims`
  ADD CONSTRAINT `Claims_ibfk_1` FOREIGN KEY (`statementid`) REFERENCES `Statements` (`statementid`),
  ADD CONSTRAINT `Claims_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `Users` (`userid`);

--
-- Constraints der Tabelle `Qualifiers`
--
ALTER TABLE `Qualifiers`
  ADD CONSTRAINT `Qualifiers_ibfk_1` FOREIGN KEY (`claimid`) REFERENCES `Claims` (`claimid`);

--
-- Constraints der Tabelle `Refs`
--
ALTER TABLE `Refs`
  ADD CONSTRAINT `Refs_ibfk_1` FOREIGN KEY (`claimid`) REFERENCES `Claims` (`claimid`);

--
-- Constraints der Tabelle `Statements`
--
ALTER TABLE `Statements`
  ADD CONSTRAINT `Statements_ibfk_1` FOREIGN KEY (`eventid`) REFERENCES `Events` (`eventid`);

--
-- Constraints der Tabelle `UserVotes`
--
ALTER TABLE `UserVotes`
  ADD CONSTRAINT `UserVotes_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `Users` (`userid`),
  ADD CONSTRAINT `UserVotes_ibfk_2` FOREIGN KEY (`claimid`) REFERENCES `Claims` (`claimid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
