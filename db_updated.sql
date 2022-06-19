-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 02, 2022 alle 16:14
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 7.3.31
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;
/*!40101 SET NAMES utf8mb4 */
;
--
-- Database: `catering`
--
CREATE DATABASE IF NOT EXISTS `catering` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `catering`;
-- --------------------------------------------------------
--
-- Table structure for table `Events`
--
DROP TABLE IF EXISTS `Events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `Events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL,
  `organizer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `Events`
--
LOCK TABLES `Events` WRITE;
/*!40000 ALTER TABLE `Events` DISABLE KEYS */
;
INSERT INTO `Events`
VALUES (
    1,
    'Convegno Agile Community',
    '2022-09-25',
    '2022-09-25',
    100,
    2
  ),
  (
    2,
    'Compleanno di Manuela',
    '2022-08-13',
    '2022-08-13',
    25,
    2
  ),
  (
    3,
    'Fiera del Sedano Rapa',
    '2022-10-02',
    '2022-10-04',
    400,
    1
  );
/*!40000 ALTER TABLE `Events` ENABLE KEYS */
;
UNLOCK TABLES;
-- --------------------------------------------------------
--
-- Table structure for table `MenuFeatures`
--
DROP TABLE IF EXISTS `MenuFeatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `MenuFeatures` (
  `menu_id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `value` tinyint(1) DEFAULT '0'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `MenuFeatures`
--
LOCK TABLES `MenuFeatures` WRITE;
/*!40000 ALTER TABLE `MenuFeatures` DISABLE KEYS */
;
INSERT INTO `MenuFeatures`
VALUES (80, 'Richiede cuoco', 0),
  (80, 'Buffet', 0),
  (80, 'Richiede cucina', 0),
  (80, 'Finger food', 0),
  (80, 'Piatti caldi', 0),
  (82, 'Richiede cuoco', 0),
  (82, 'Buffet', 0),
  (82, 'Richiede cucina', 0),
  (82, 'Finger food', 0),
  (82, 'Piatti caldi', 0),
  (86, 'Richiede cuoco', 0),
  (86, 'Buffet', 0),
  (86, 'Richiede cucina', 0),
  (86, 'Finger food', 0),
  (86, 'Piatti caldi', 0);
/*!40000 ALTER TABLE `MenuFeatures` ENABLE KEYS */
;
UNLOCK TABLES;
-- --------------------------------------------------------
--
-- Table structure for table `MenuItems`
--
DROP TABLE IF EXISTS `MenuItems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `MenuItems` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `description` tinytext,
  `procedure_id` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 121 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `MenuItems`
--
LOCK TABLES `MenuItems` WRITE;
/*!40000 ALTER TABLE `MenuItems` DISABLE KEYS */
;
INSERT INTO `MenuItems`
VALUES (96, 80, 0, 'Croissant vuoti', 9, 0),
  (97, 80, 0, 'Croissant alla marmellata', 9, 1),
  (98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
  (
    99,
    80,
    0,
    'Panini al latte con prosciutto crudo',
    12,
    4
  ),
  (
    100,
    80,
    0,
    'Panini al latte con prosciutto cotto',
    12,
    5
  ),
  (
    101,
    80,
    0,
    'Panini al latte con formaggio spalmabile alle erbe',
    12,
    6
  ),
  (102, 80, 0, 'Girelle all\'uvetta mignon', 11, 3),
  (103, 82, 0, 'Biscotti', 13, 1),
  (104, 82, 0, 'Lingue di gatto', 14, 2),
  (105, 82, 0, 'Bigné alla crema', 15, 3),
  (106, 82, 0, 'Bigné al caffè', 15, 4),
  (107, 82, 0, 'Pizzette', 16, 5),
  (
    108,
    82,
    0,
    'Croissant al prosciutto crudo mignon',
    9,
    6
  ),
  (
    109,
    82,
    0,
    'Tramezzini tonno e carciofini mignon',
    17,
    7
  ),
  (112, 86, 41, 'Vitello tonnato', 1, 0),
  (113, 86, 41, 'Carpaccio di spada', 2, 1),
  (114, 86, 41, 'Alici marinate', 3, 2),
  (115, 86, 42, 'Penne alla messinese', 5, 0),
  (116, 86, 42, 'Risotto alla zucca', 20, 1),
  (117, 86, 43, 'Salmone al forno', 8, 0),
  (118, 86, 44, 'Sorbetto al limone', 18, 0),
  (119, 86, 44, 'Torta Saint Honoré', 19, 1);
/*!40000 ALTER TABLE `MenuItems` ENABLE KEYS */
;
UNLOCK TABLES;
-- --------------------------------------------------------
--
-- Table structure for table `MenuSections`
--
DROP TABLE IF EXISTS `MenuSections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `MenuSections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `name` tinytext,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 47 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `MenuSections`
--
LOCK TABLES `MenuSections` WRITE;
/*!40000 ALTER TABLE `MenuSections` DISABLE KEYS */
;
INSERT INTO `MenuSections`
VALUES (41, 86, 'Antipasti', 0),
  (42, 86, 'Primi', 1),
  (43, 86, 'Secondi', 2),
  (44, 86, 'Dessert', 3),
  (45, 87, 'Antipasti', 0);
/*!40000 ALTER TABLE `MenuSections` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `Menus`
--
DROP TABLE IF EXISTS `Menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `Menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` tinytext,
  `owner_id` int(11) DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 89 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `Menus`
--
LOCK TABLES `Menus` WRITE;
/*!40000 ALTER TABLE `Menus` DISABLE KEYS */
;
INSERT INTO `Menus`
VALUES (80, 'Coffee break mattutino', 2, 1),
  (82, 'Coffee break pomeridiano', 2, 1),
  (86, 'Cena di compleanno pesce', 3, 1);
/*!40000 ALTER TABLE `Menus` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `Procedures`
--
DROP TABLE IF EXISTS `Procedures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `Procedures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` tinytext,
  `isRecipe` boolean NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 21 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `Procedures`
--
LOCK TABLES `Procedures` WRITE;
/*!40000 ALTER TABLE `Procedures` DISABLE KEYS */
;
INSERT INTO `Procedures`
VALUES (1, 'Vitello tonnato', true),
  (2, 'Carpaccio di spada', true),
  (3, 'Alici marinate', true),
  (4, 'Insalata di riso', true),
  (5, 'Penne al sugo di baccalà', true),
  (6, 'Pappa al pomodoro', true),
  (7, 'Hamburger con bacon e cipolla caramellata', true),
  (8, 'Salmone al forno', true),
  (9, 'Croissant', true),
  (10, 'Pane al cioccolato', true),
  (11, 'Girelle all\'uvetta', true),
  (12, 'Panini al latte', true),
  (13, 'Biscotti di pasta frolla', true),
  (14, 'Lingue di gatto', true),
  (15, 'Bigné farciti', true),
  (16, 'Pizzette', true),
  (17, 'Tramezzini', true),
  (18, 'Sorbetto al limone', true),
  (19, 'Torta Saint Honoré', true),
  (20, 'Risotto alla zucca', true);
/*!40000 ALTER TABLE `Procedures` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `Roles`
--
DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `Roles` (
  `id` char(1) NOT NULL,
  `role` varchar(128) NOT NULL DEFAULT 'servizio',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `Roles`
--
LOCK TABLES `Roles` WRITE;
/*!40000 ALTER TABLE `Roles` DISABLE KEYS */
;
INSERT INTO `Roles`
VALUES ('c', 'cuoco'),
  ('h', 'chef'),
  ('o', 'organizzatore'),
  ('s', 'servizio');
/*!40000 ALTER TABLE `Roles` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `Services`
--
DROP TABLE IF EXISTS `Services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `Services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `proposed_menu_id` int(11) NOT NULL DEFAULT '0',
  `approved_menu_id` int(11) DEFAULT '0',
  `service_date` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 9 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `Services`
--
LOCK TABLES `Services` WRITE;
/*!40000 ALTER TABLE `Services` DISABLE KEYS */
;
INSERT INTO `Services`
VALUES (
    1,
    2,
    'Cena',
    86,
    0,
    '2022-08-13',
    '20:00:00',
    '23:30:00',
    25
  ),
  (
    2,
    1,
    'Coffee break mattino',
    0,
    80,
    '2022-09-25',
    '10:30:00',
    '11:30:00',
    100
  ),
  (
    3,
    1,
    'Colazione di lavoro',
    0,
    0,
    '2022-09-25',
    '13:00:00',
    '14:00:00',
    80
  ),
  (
    4,
    1,
    'Coffee break pomeriggio',
    0,
    82,
    '2022-09-25',
    '16:00:00',
    '16:30:00',
    100
  ),
  (
    5,
    1,
    'Cena sociale',
    0,
    0,
    '2022-09-25',
    '20:00:00',
    '22:30:00',
    40
  ),
  (
    6,
    3,
    'Pranzo giorno 1',
    0,
    0,
    '2022-10-02',
    '12:00:00',
    '15:00:00',
    200
  ),
  (
    7,
    3,
    'Pranzo giorno 2',
    0,
    0,
    '2022-10-03',
    '12:00:00',
    '15:00:00',
    300
  ),
  (
    8,
    3,
    'Pranzo giorno 3',
    0,
    0,
    '2022-10-04',
    '12:00:00',
    '15:00:00',
    400
  );
/*!40000 ALTER TABLE `Services` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `UserRoles`
--
DROP TABLE IF EXISTS `UserRoles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `UserRoles` (
  `user_id` int(11) NOT NULL,
  `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `UserRoles`
--
LOCK TABLES `UserRoles` WRITE;
/*!40000 ALTER TABLE `UserRoles` DISABLE KEYS */
;
INSERT INTO `UserRoles`
VALUES (1, 'o'),
  (2, 'o'),
  (2, 'h'),
  (3, 'h'),
  (4, 'h'),
  (4, 'c'),
  (5, 'c'),
  (6, 'c'),
  (7, 'c'),
  (8, 's'),
  (9, 's'),
  (10, 's'),
  (7, 's');
/*!40000 ALTER TABLE `UserRoles` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `Users`
--
DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!40101 SET character_set_client = utf8 */
;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 11 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `Users`
--
LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */
;
INSERT INTO `Users`
VALUES (1, 'Carlin'),
  (2, 'Lidia'),
  (3, 'Tony'),
  (4, 'Marinella'),
  (5, 'Guido'),
  (6, 'Antonietta'),
  (7, 'Paola'),
  (8, 'Silvia'),
  (9, 'Marco'),
  (10, 'Piergiorgio');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */
;
UNLOCK TABLES;
-- --------------------------------------------------------
--
-- Struttura della tabella `Shifts`
--
CREATE TABLE `Shifts` (
  `date` date NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
--
-- Dumping data for table `Shift`
--
LOCK TABLES `Shifts` WRITE;
/*!40000 ALTER TABLE `Shifts` DISABLE KEYS */
;
INSERT INTO `Shifts`
VALUES ('2022-08-13', '20:00:00', '22:00:00', 1),
  -- Cena (Compleanno di Manuela)
  ('2022-08-13', '22:00:00', '23:30:00', 2),
  -- Cena (Compleanno di Manuela)
  ('2022-09-25', '10:30:00', '11:30:00', 3),
  -- Coffee break mattino (Convegno Agile Community) 
  ('2022-09-25', '13:00:00', '14:00:00', 4),
  -- Colazione di Lavoro (Convegno Agile Community)
  ('2022-09-25', '16:00:00', '16:30:00', 5),
  -- Coffee break pomeriggio (Convegno Agile Community)
  ('2022-09-25', '20:00:00', '22:30:00', 6),
  -- Cena sociale (Convegno Agile Community) 
  ('2022-10-02', '12:00:00', '13:00:00', 7),
  -- Pranzo giorno 1 (Fiera del Sedano Rapa)
  ('2022-10-02', '13:00:00', '15:00:00', 8),
  -- Pranzo giorno 1 (Fiera del Sedano Rapa)
  ('2022-10-03', '16:00:00', '18:00:00', 9),
  -- Pranzo giorno 2 (Fiera del Sedano Rapa)
  ('2022-10-03', '17:00:00', '19:00:00', 10),
  -- Pranzo giorno 2 (Fiera del Sedano Rapa)
  ('2022-10-04', '18:00:00', '20:00:00', 11),
  -- Pranzo giorno 3 (Fiera del Sedano Rapa)
  ('2022-10-04', '18:00:00', '20:00:00', 12);
-- Pranzo giorno 3 (Fiera del Sedano Rapa)
/*!40000 ALTER TABLE `Shifts` ENABLE KEYS */
;
UNLOCK TABLES;
-- --------------------------------------------------------
--
-- Struttura della tabella `UserShifts`
--
CREATE TABLE `UserShifts` (
  `id_shift` int(11) NOT NULL,
  `id_cook` int(11) NOT NULL,
  `availableTime` bigint NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
--
-- Dumping data for table `Shift`
--
LOCK TABLES `UserShifts` WRITE;
/*!40000 ALTER TABLE `Shifts` DISABLE KEYS */
;
INSERT INTO `UserShifts`
VALUES (3, 4, 120),
  -- Marinella
  (1, 5, 120),
  -- Guido
  (4, 6, 120),
  -- Antonietta
  (5, 7, 120),
  -- Paola
  (9, 7, 120),
  -- Paola
  (10, 7, 120);
-- Paola
/*!40000 ALTER TABLE `Shifts` ENABLE KEYS */
;
UNLOCK TABLES;
-- --------------------------------------------------------
--
-- Struttura della tabella `SummarySheets`
--
CREATE TABLE `SummarySheets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_service` int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
-- --------------------------------------------------------
--
-- Struttura della tabella `Assignments`
--
CREATE TABLE `Assignments` (
  `completed` tinyint(1) NOT NULL,
  `quantity` int NOT NULL,
  `estimatedTime` time NOT NULL,
  `position` int NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_continuation` int(11) NOT NULL,
  `id_shift` int(11) NOT NULL,
  `id_cook` int(11) NOT NULL,
  `id_procedure` int(11) NOT NULL,
  `id_summary_sheet` int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;