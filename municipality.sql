-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 22, 2019 at 10:00 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `municipality`
--

-- --------------------------------------------------------

--
-- Table structure for table `muni`
--

CREATE TABLE `muni` (
  `id` int(11) NOT NULL,
  `municipality_name` varchar(100) NOT NULL,
  `geography` varchar(100) NOT NULL,
  `population` int(11) NOT NULL,
  `Dean_of_the_Muni` varchar(100) NOT NULL,
  `phone_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `muni`
--

INSERT INTO `muni` (`id`, `municipality_name`, `geography`, `population`, `Dean_of_the_Muni`, `phone_number`) VALUES
(1, 'طرابلس', 'طرابلس الكبرى', 1569000, 'مفتاح بيت المال', 925119043),
(2, 'مصراتة', 'وادي الحياة', 700000, 'حسن عبد الله', 923114669),
(3, 'سبها', 'وادي الشاطئ', 390122, 'محمـد خليفة', 927717522),
(4, 'بنغازي', 'البطنان', 1000700, 'يوسف الشريف', 943233522),
(5, 'نالوت', 'الجبل الغربي', 40600, 'سعيد عامر', 915029080),
(6, 'الكفرة', 'البطنان', 60000, 'أنور الحاج', 913025404),
(7, 'البيضاء', 'الجبل الأخضر', 200000, 'محمد البرعصي', 925123041),
(8, 'الزنتان', 'الجبل الغربي', 340000, 'عبد الكريم يوسف', 924130012),
(9, 'سرت', 'طرابلس الكبرى', 250000, 'عبد الرحمن البوعيشي', 949091223);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `muni`
--
ALTER TABLE `muni`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
