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


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `catering`
--
CREATE DATABASE IF NOT EXISTS `catering` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `catering`;

-- --------------------------------------------------------

--
-- Struttura della tabella `assignment`
--

CREATE TABLE `assignment` (
  `completed` tinyint(1) NOT NULL,
  `quantity` text NOT NULL,
  `estimatedTime` time NOT NULL,
  `id_as` int(11) NOT NULL,
  `id_as_continuation` int(11) NOT NULL,
  `id_shift` int(11) NOT NULL,
  `id_cook` int(11) NOT NULL,
  `id_Procedure` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL,
  `organizer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `events`
--

INSERT INTO `events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`) VALUES
(1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2),
(2, 'Compleanno di Manuela', '2020-08-13', '2020-08-13', 25, 2),
(3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `menufeatures`
--

CREATE TABLE `menufeatures` (
  `menu_id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `value` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menufeatures`
--

INSERT INTO `menufeatures` (`menu_id`, `name`, `value`) VALUES
(80, 'Richiede cuoco', 0),
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
(86, 'Piatti caldi', 0),
(89, 'Richiede cuoco', 1),
(89, 'Buffet', 1),
(89, 'Richiede cucina', 1),
(89, 'Finger food', 1),
(89, 'Piatti caldi', 1),
(90, 'Richiede cuoco', 1),
(90, 'Buffet', 1),
(90, 'Richiede cucina', 1),
(90, 'Finger food', 1),
(90, 'Piatti caldi', 1),
(91, 'Richiede cuoco', 1),
(91, 'Buffet', 1),
(91, 'Richiede cucina', 1),
(91, 'Finger food', 1),
(91, 'Piatti caldi', 1),
(92, 'Richiede cuoco', 1),
(92, 'Buffet', 1),
(92, 'Richiede cucina', 1),
(92, 'Finger food', 1),
(92, 'Piatti caldi', 1),
(93, 'Richiede cuoco', 1),
(93, 'Buffet', 1),
(93, 'Richiede cucina', 1),
(93, 'Finger food', 1),
(93, 'Piatti caldi', 1),
(94, 'Richiede cuoco', 1),
(94, 'Buffet', 1),
(94, 'Richiede cucina', 1),
(94, 'Finger food', 1),
(94, 'Piatti caldi', 1),
(95, 'Richiede cuoco', 1),
(95, 'Buffet', 1),
(95, 'Richiede cucina', 1),
(95, 'Finger food', 1),
(95, 'Piatti caldi', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `menuitems`
--

CREATE TABLE `menuitems` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `description` tinytext DEFAULT NULL,
  `recipe_id` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menuitems`
--

INSERT INTO `menuitems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`) VALUES
(96, 80, 0, 'Croissant vuoti', 9, 0),
(97, 80, 0, 'Croissant alla marmellata', 9, 1),
(98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
(99, 80, 0, 'Panini al latte con prosciutto crudo', 12, 4),
(100, 80, 0, 'Panini al latte con prosciutto cotto', 12, 5),
(101, 80, 0, 'Panini al latte con formaggio spalmabile alle erbe', 12, 6),
(102, 80, 0, 'Girelle all\'uvetta mignon', 11, 3),
(103, 82, 0, 'Biscotti', 13, 1),
(104, 82, 0, 'Lingue di gatto', 14, 2),
(105, 82, 0, 'Bigné alla crema', 15, 3),
(106, 82, 0, 'Bigné al caffè', 15, 4),
(107, 82, 0, 'Pizzette', 16, 5),
(108, 82, 0, 'Croissant al prosciutto crudo mignon', 9, 6),
(109, 82, 0, 'Tramezzini tonno e carciofini mignon', 17, 7),
(112, 86, 41, 'Vitello tonnato', 1, 0),
(113, 86, 41, 'Carpaccio di spada', 2, 1),
(114, 86, 41, 'Alici marinate', 3, 2),
(115, 86, 42, 'Penne alla messinese', 5, 0),
(116, 86, 42, 'Risotto alla zucca', 20, 1),
(117, 86, 43, 'Salmone al forno', 8, 0),
(118, 86, 44, 'Sorbetto al limone', 18, 0),
(119, 86, 44, 'Torta Saint Honoré', 19, 1),
(121, 89, 47, 'Vitello tonnato', 1, 0),
(122, 89, 47, 'Carpaccio di spada', 2, 1),
(123, 89, 47, 'Alici marinate', 3, 2),
(124, 89, 49, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(125, 89, 49, 'Salmone al forno', 8, 1),
(126, 89, 0, 'Insalata di riso', 4, 0),
(127, 89, 0, 'Penne al sugo di baccalà', 5, 1),
(128, 90, 50, 'Vitello tonnato', 1, 0),
(129, 90, 50, 'Carpaccio di spada', 2, 1),
(130, 90, 50, 'Alici marinate', 3, 2),
(131, 90, 52, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(132, 90, 52, 'Salmone al forno', 8, 1),
(133, 90, 0, 'Insalata di riso', 4, 0),
(134, 90, 0, 'Penne al sugo di baccalà', 5, 1),
(135, 91, 53, 'Vitello tonnato', 1, 0),
(136, 91, 53, 'Carpaccio di spada', 2, 1),
(137, 91, 53, 'Alici marinate', 3, 2),
(138, 91, 55, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(139, 91, 55, 'Salmone al forno', 8, 1),
(140, 91, 0, 'Insalata di riso', 4, 0),
(141, 91, 0, 'Penne al sugo di baccalà', 5, 1),
(142, 92, 56, 'Vitello tonnato', 1, 0),
(143, 92, 56, 'Carpaccio di spada', 2, 1),
(144, 92, 56, 'Alici marinate', 3, 2),
(145, 92, 58, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(146, 92, 58, 'Salmone al forno', 8, 1),
(147, 92, 0, 'Insalata di riso', 4, 0),
(148, 92, 0, 'Penne al sugo di baccalà', 5, 1),
(149, 93, 59, 'Vitello tonnato', 1, 0),
(150, 93, 59, 'Carpaccio di spada', 2, 1),
(151, 93, 59, 'Alici marinate', 3, 2),
(152, 93, 61, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(153, 93, 61, 'Salmone al forno', 8, 1),
(154, 93, 0, 'Insalata di riso', 4, 0),
(155, 93, 0, 'Penne al sugo di baccalà', 5, 1),
(156, 94, 62, 'Vitello tonnato', 1, 0),
(157, 94, 62, 'Carpaccio di spada', 2, 1),
(158, 94, 62, 'Alici marinate', 3, 2),
(159, 94, 64, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(160, 94, 64, 'Salmone al forno', 8, 1),
(161, 94, 0, 'Insalata di riso', 4, 0),
(162, 94, 0, 'Penne al sugo di baccalà', 5, 1),
(163, 95, 65, 'Vitello tonnato', 1, 0),
(164, 95, 65, 'Carpaccio di spada', 2, 1),
(165, 95, 65, 'Alici marinate', 3, 2),
(166, 95, 67, 'Hamburger con bacon e cipolla caramellata', 7, 0),
(167, 95, 67, 'Salmone al forno', 8, 1),
(168, 95, 0, 'Insalata di riso', 4, 0),
(169, 95, 0, 'Penne al sugo di baccalà', 5, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `menus`
--

CREATE TABLE `menus` (
  `id` int(11) NOT NULL,
  `title` tinytext DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `published` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menus`
--

INSERT INTO `menus` (`id`, `title`, `owner_id`, `published`) VALUES
(80, 'Coffee break mattutino', 2, 1),
(82, 'Coffee break pomeridiano', 2, 1),
(86, 'Cena di compleanno pesce', 3, 1),
(89, 'Titolo Nuovo', 2, 1),
(90, 'Titolo Nuovo', 2, 1),
(91, 'Titolo Nuovo', 2, 1),
(92, 'Titolo Nuovo', 2, 1),
(93, 'Titolo Nuovo', 2, 1),
(94, 'Titolo Nuovo', 2, 1),
(95, 'Titolo Nuovo', 2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `menusections`
--

CREATE TABLE `menusections` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `name` tinytext DEFAULT NULL,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `menusections`
--

INSERT INTO `menusections` (`id`, `menu_id`, `name`, `position`) VALUES
(41, 86, 'Antipasti', 0),
(42, 86, 'Primi', 1),
(43, 86, 'Secondi', 2),
(44, 86, 'Dessert', 3),
(45, 87, 'Antipasti', 0),
(47, 89, 'Antipasti', 0),
(48, 89, 'Primi', 1),
(49, 89, 'Secondi', 2),
(50, 90, 'Antipasti', 0),
(51, 90, 'Primi', 1),
(52, 90, 'Secondi', 2),
(53, 91, 'Antipasti', 0),
(54, 91, 'Primi', 1),
(55, 91, 'Secondi', 2),
(56, 92, 'Antipasti', 0),
(57, 92, 'Primi', 1),
(58, 92, 'Secondi', 2),
(59, 93, 'Antipasti', 0),
(60, 93, 'Primi', 1),
(61, 93, 'Secondi', 2),
(62, 94, 'Antipasti', 0),
(63, 94, 'Primi', 1),
(64, 94, 'Secondi', 2),
(65, 95, 'Antipasti', 0),
(66, 95, 'Primi', 1),
(67, 95, 'Secondi', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `recipes`
--

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL,
  `name` tinytext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `recipes`
--

INSERT INTO `recipes` (`id`, `name`) VALUES
(1, 'Vitello tonnato'),
(2, 'Carpaccio di spada'),
(3, 'Alici marinate'),
(4, 'Insalata di riso'),
(5, 'Penne al sugo di baccalà'),
(6, 'Pappa al pomodoro'),
(7, 'Hamburger con bacon e cipolla caramellata'),
(8, 'Salmone al forno'),
(9, 'Croissant'),
(10, 'Pane al cioccolato'),
(11, 'Girelle all\'uvetta'),
(12, 'Panini al latte'),
(13, 'Biscotti di pasta frolla'),
(14, 'Lingue di gatto'),
(15, 'Bigné farciti'),
(16, 'Pizzette'),
(17, 'Tramezzini'),
(18, 'Sorbetto al limone'),
(19, 'Torta Saint Honoré'),
(20, 'Risotto alla zucca');

-- --------------------------------------------------------

--
-- Struttura della tabella `roles`
--

CREATE TABLE `roles` (
  `id` char(1) NOT NULL,
  `role` varchar(128) NOT NULL DEFAULT 'servizio'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
('c', 'cuoco'),
('h', 'chef'),
('o', 'organizzatore'),
('s', 'servizio');

-- --------------------------------------------------------

--
-- Struttura della tabella `services`
--

CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `proposed_menu_id` int(11) NOT NULL DEFAULT 0,
  `approved_menu_id` int(11) DEFAULT 0,
  `service_date` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `services`
--

INSERT INTO `services` (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`) VALUES
(1, 2, 'Cena', 86, 0, '2020-08-13', '20:00:00', '23:30:00', 25),
(2, 1, 'Coffee break mattino', 0, 80, '2020-09-25', '10:30:00', '11:30:00', 100),
(3, 1, 'Colazione di lavoro', 0, 0, '2020-09-25', '13:00:00', '14:00:00', 80),
(4, 1, 'Coffee break pomeriggio', 0, 82, '2020-09-25', '16:00:00', '16:30:00', 100),
(5, 1, 'Cena sociale', 0, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
(6, 3, 'Pranzo giorno 1', 0, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
(7, 3, 'Pranzo giorno 2', 0, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
(8, 3, 'Pranzo giorno 3', 0, 0, '2020-10-04', '12:00:00', '15:00:00', 400);

-- --------------------------------------------------------

--
-- Struttura della tabella `shift`
--

CREATE TABLE `shift` (
  `date` date NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `id_shift` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `summarysheet`
--

CREATE TABLE `summarysheet` (
  `id_summary_sheet` int(11) NOT NULL,
  `id_service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `userroles`
--

CREATE TABLE `userroles` (
  `user_id` int(11) NOT NULL,
  `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `userroles`
--

INSERT INTO `userroles` (`user_id`, `role_id`) VALUES
(1, 'o'),
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

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(128) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`id`, `username`) VALUES
(1, 'Carlin'),
(2, 'Lidia'),
(3, 'Tony'),
(4, 'Marinella'),
(5, 'Guido'),
(6, 'Antonietta'),
(7, 'Paola'),
(8, 'Silvia'),
(9, 'Marco'),
(10, 'Piergiorgio');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `assignment`
--
ALTER TABLE `assignment`
  ADD PRIMARY KEY (`id_as`);

--
-- Indici per le tabelle `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `menuitems`
--
ALTER TABLE `menuitems`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `menusections`
--
ALTER TABLE `menusections`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `shift`
--
ALTER TABLE `shift`
  ADD PRIMARY KEY (`id_shift`);

--
-- Indici per le tabelle `summarysheet`
--
ALTER TABLE `summarysheet`
  ADD PRIMARY KEY (`id_summary_sheet`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `assignment`
--
ALTER TABLE `assignment`
  MODIFY `id_as` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `menuitems`
--
ALTER TABLE `menuitems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=170;

--
-- AUTO_INCREMENT per la tabella `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT per la tabella `menusections`
--
ALTER TABLE `menusections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT per la tabella `recipes`
--
ALTER TABLE `recipes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT per la tabella `services`
--
ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `shift`
--
ALTER TABLE `shift`
  MODIFY `id_shift` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `summarysheet`
--
ALTER TABLE `summarysheet`
  MODIFY `id_summary_sheet` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;