-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2023 at 11:06 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_donation_inventory_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_distribution`
--

CREATE TABLE `tbl_distribution` (
  `DistributionID` int(11) NOT NULL,
  `DonorID` int(11) DEFAULT NULL,
  `ItemID` int(11) DEFAULT NULL,
  `Recipient` text NOT NULL,
  `DistributionDate` date NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Notes` text DEFAULT NULL,
  `DistributionLocation` text NOT NULL,
  `Status` int(1) NOT NULL,
  `picture` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_distribution`
--

INSERT INTO `tbl_distribution` (`DistributionID`, `DonorID`, `ItemID`, `Recipient`, `DistributionDate`, `Quantity`, `Notes`, `DistributionLocation`, `Status`, `picture`) VALUES
(0, NULL, NULL, 'IBARRA', '2023-01-02', 2, 'N/A', 'BACOLOD CITY', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_donors`
--

CREATE TABLE `tbl_donors` (
  `DonorID` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `Address` text NOT NULL,
  `ContactNumber` text NOT NULL,
  `gender` int(1) NOT NULL,
  `birth` date DEFAULT NULL,
  `love` enum('true','false') NOT NULL DEFAULT 'false',
  `picture` varchar(100) NOT NULL DEFAULT 'http://172.22.69.14/db_donation_inventory_system/pets_picture/pet_logo.png',
  `Email` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_donors`
--

INSERT INTO `tbl_donors` (`DonorID`, `name`, `Address`, `ContactNumber`, `gender`, `birth`, `love`, `picture`, `Email`) VALUES
(1, 'Donor Donor Donor', 'Norway Road, Bacolod City', '09123456789', 1, '2018-07-28', 'false', '/demo_pets/pets_picture/1.jpeg', NULL),
(2, 'Donor', 'Country Roads, Bacolod City', '09455666070', 1, '2018-07-28', 'false', '/demo_pets/pets_picture/2.jpeg', NULL),
(6, 'Donor Donor', 'Silk Road, China Town Square, Bacolod City', '0977766453', 1, '2017-12-07', 'false', '/demo_pets/pets_picture/6.jpeg', NULL),
(7, 'Cardo Dalisay', 'South Homes, Sum-ag, Bacolod City', '09123345678', 1, '1994-05-05', 'false', 'http://192.168.1.103/demo_pets/pets_picture/pet_logo.png', NULL),
(8, 'Example', 'Example', '09123456789', 2, '2023-05-15', 'false', 'http://192.168.1.103/demo_pets/pets_picture/pet_logo.png', 'example@yahoo.com'),
(9, 'Example2', 'Example2', '09454545454', 2, '2023-05-16', 'false', 'http://172.22.69.14/db_donation_inventory_system/pets_picture/pet_logo.png', 'example2@email.com'),
(10, 'Example3', 'Example3', '0945678910', 2, '2023-05-01', 'false', 'http://172.22.69.14/db_donation_inventory_system/pets_picture/pet_logo.png', 'example3@email.com'),
(11, 'DD', 'BC', '09455666074', 1, '2023-05-01', 'false', 'http://172.22.69.14/db_donation_inventory_system/pets_picture/pet_logo.png', 'D@GMAIL.COM');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_inventory`
--

CREATE TABLE `tbl_inventory` (
  `ItemID` int(11) NOT NULL,
  `ItemName` text NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Description` text DEFAULT NULL,
  `picture` varchar(1000) DEFAULT 'http://192.168.254.103/db_donation_inventory_system/pets_picture/pet_logo.png',
  `Expiration` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_inventory`
--

INSERT INTO `tbl_inventory` (`ItemID`, `ItemName`, `Quantity`, `Description`, `picture`, `Expiration`) VALUES
(1, 'Cookies', 300, 'Oreo Cookies', 'http://192.168.254.103/db_donation_inventory_system/pets_picture/pet_logo.png', '2026-05-13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_distribution`
--
ALTER TABLE `tbl_distribution`
  ADD PRIMARY KEY (`DistributionID`),
  ADD KEY `DonorID` (`DonorID`),
  ADD KEY `ItemID` (`ItemID`);

--
-- Indexes for table `tbl_donors`
--
ALTER TABLE `tbl_donors`
  ADD PRIMARY KEY (`DonorID`);

--
-- Indexes for table `tbl_inventory`
--
ALTER TABLE `tbl_inventory`
  ADD PRIMARY KEY (`ItemID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_donors`
--
ALTER TABLE `tbl_donors`
  MODIFY `DonorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tbl_inventory`
--
ALTER TABLE `tbl_inventory`
  MODIFY `ItemID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
