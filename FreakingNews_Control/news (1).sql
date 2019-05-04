-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 03, 2019 at 01:27 PM
-- Server version: 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `news`
--

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `idPost`, `idUser`, `content`, `created_at`) VALUES
(1, 1, 1, 'Hay quá', '2019-05-03 00:05:08'),
(2, 1, 2, 'Cũng được', '2019-05-03 00:05:17'),
(3, 2, 1, 'Tuyệt vời', '2019-05-03 00:05:31'),
(4, 3, 1, 'Công nghệ', '2019-05-03 00:05:49'),
(5, 3, 2, 'Đây là công nghệ', '2019-05-03 00:05:59');

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `idUser`, `idTopic`, `content`, `url_image`, `created_at`, `last_update`) VALUES
(1, 1, 1, 'Đây là chuyên mục Kinh doanh', 'https://saobiz.net/wp-content/uploads/2017/04/co-gai-khien-hon-mot-trieu-trai-tim-loan-nhip-la-ai-co-gai-khien-hon-1-trieu-trai-tim-loan-nhip-la-ai6-1477015010-width500height625_01.jpg', '2019-05-03 00:03:29', '2019-05-03 00:03:29'),
(2, 2, 2, 'Đây là chuyên mục thể thao', 'https://codiemgicoxuikhong.com/wp-content/uploads/2018/01/3851395811607223840785177179385096254783488n-1-1543141992498321668986.jpg', '2019-05-03 00:04:14', '2019-05-03 00:04:14'),
(3, 2, 3, 'Đây là chuyên mục CÔNG NGHỆ', 'http://hinhanhdephd.com/wp-content/uploads/2016/01/tai-hinh-girl-xinh-lam-avatar-de-thuong-nhat-22.jpg', '2019-05-03 00:04:50', '2019-05-03 00:04:50');

--
-- Dumping data for table `topics`
--

INSERT INTO `topics` (`id`, `topic`) VALUES
(1, 'Kinh doanh'),
(2, 'Thể thao'),
(3, 'Công nghệ');

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `url_avatar`) VALUES
(1, '\'huongtv.uet@gmail.com\'', '\'Trần Văn Hưởng\'', '\'https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.0-9/58595586_2335381520025428_7214766066176622592_n.jpg?_nc_cat=108&_nc_oc=AQmkxqamW6GnIkygV8Hd9CHkvPMyTiTnrTJG_nXym340BAOyvm6cM1Y6w7nmnYGfz3U&_nc_ht=scontent.fsgn2-3.fna&oh=86463bbafdd68f9cda8a50e0da8f7370&oe=5D662368\''),
(2, '\'tranhuonghm@gmail.com\'', '\'Hưởng Văn Trần\'', '\'http://sohanews.sohacdn.com/thumb_w/660/2018/8/28/photo1535416480861-15354164808651145995032.png\'');

--
-- Dumping data for table `votes`
--

INSERT INTO `votes` (`id`, `idPost`, `idUser`, `status`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 1),
(3, 2, 1, -1),
(4, 2, 2, -1),
(5, 3, 1, 1),
(6, 3, 2, -1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
