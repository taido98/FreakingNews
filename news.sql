-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 04, 2019 at 04:23 AM
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

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `idPost` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `idPost`, `idUser`, `content`, `created_at`) VALUES
(1, 1, 1, 'Hay quá', '2019-05-03 00:05:08'),
(2, 1, 2, 'Cũng được', '2019-05-03 00:05:17'),
(3, 2, 1, 'Tuyệt vời', '2019-05-03 00:05:31'),
(4, 3, 1, 'Công nghệ', '2019-05-03 00:05:49'),
(5, 3, 2, 'Đây là công nghệ', '2019-05-03 00:05:59'),
(6, 1, 1, 'Hay', '2019-05-04 00:18:56'),
(7, 2, 1, '123', '2019-05-04 00:33:06'),
(8, 2, 1, '1111', '2019-05-04 00:33:25'),
(9, 2, 1, 'abc', '2019-05-04 00:34:11'),
(10, 2, 1, 'abcd', '2019-05-04 00:34:24'),
(11, 3, 1, 'abc', '2019-05-04 00:34:42'),
(12, 3, 1, 'abcd', '2019-05-04 00:35:00'),
(13, 1, 1, 'aaaa', '2019-05-04 00:35:14'),
(14, 1, 1, 'aaaccc', '2019-05-04 00:35:22'),
(15, 3, 1, 'abg', '2019-05-04 00:35:30'),
(16, 3, 1, 'AA', '2019-05-04 00:36:36'),
(17, 2, 1, 'ASD', '2019-05-04 00:37:25'),
(18, 2, 1, 'AD', '2019-05-04 00:38:13'),
(19, 2, 1, 'A', '2019-05-04 00:39:37'),
(20, 2, 1, 'AS', '2019-05-04 00:40:10'),
(21, 3, 1, 'AS', '2019-05-04 00:40:21'),
(22, 2, 1, 'AS', '2019-05-04 00:40:35'),
(23, 1, 1, 'AS', '2019-05-04 00:40:43'),
(24, 3, 1, 'ABV', '2019-05-04 00:40:50');

-- --------------------------------------------------------

--
-- Table structure for table `news_save`
--

CREATE TABLE `news_save` (
  `id` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `title` text NOT NULL,
  `link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idTopic` int(11) NOT NULL,
  `content` text NOT NULL,
  `url_image` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `idUser`, `idTopic`, `content`, `url_image`, `created_at`, `last_update`) VALUES
(1, 1, 1, 'Đây là chuyên mục Kinh doanh', 'https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.0-9/58595586_2335381520025428_7214766066176622592_n.jpg?_nc_cat=108&_nc_oc=AQmkxqamW6GnIkygV8Hd9CHkvPMyTiTnrTJG_nXym340BAOyvm6cM1Y6w7nmnYGfz3U&_nc_ht=scontent.fsgn2-3.fna&oh=86463bbafdd68f9cda8a50e0da8f7370&oe=5D662368', '2019-05-03 00:03:29', '2019-05-03 00:03:29'),
(2, 2, 2, 'Đây là chuyên mục thể thao', 'https://codiemgicoxuikhong.com/wp-content/uploads/2018/01/3851395811607223840785177179385096254783488n-1-1543141992498321668986.jpg', '2019-05-03 00:04:14', '2019-05-03 00:04:14'),
(3, 2, 3, 'Đây là chuyên mục CÔNG NGHỆ', 'http://hinhanhdephd.com/wp-content/uploads/2016/01/tai-hinh-girl-xinh-lam-avatar-de-thuong-nhat-22.jpg', '2019-05-03 00:04:50', '2019-05-03 00:04:50');

-- --------------------------------------------------------

--
-- Table structure for table `topics`
--

CREATE TABLE `topics` (
  `id` int(11) NOT NULL,
  `topic` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `topics`
--

INSERT INTO `topics` (`id`, `topic`) VALUES
(1, 'Kinh doanh'),
(2, 'Thể thao'),
(3, 'Công nghệ');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` text NOT NULL,
  `name` text NOT NULL,
  `url_avatar` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `url_avatar`) VALUES
(1, 'huongtv.uet@gmail.com', 'Trần Văn Hưởng', 'https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.0-9/58595586_2335381520025428_7214766066176622592_n.jpg?_nc_cat=108&_nc_oc=AQmkxqamW6GnIkygV8Hd9CHkvPMyTiTnrTJG_nXym340BAOyvm6cM1Y6w7nmnYGfz3U&_nc_ht=scontent.fsgn2-3.fna&oh=86463bbafdd68f9cda8a50e0da8f7370&oe=5D662368'),
(2, 'tranhuonghm@gmail.com', 'Hưởng Văn Trần', 'http://sohanews.sohacdn.com/thumb_w/660/2018/8/28/photo1535416480861-15354164808651145995032.png');

-- --------------------------------------------------------

--
-- Table structure for table `votes`
--

CREATE TABLE `votes` (
  `id` int(11) NOT NULL,
  `idPost` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `comments_fk0` (`idPost`),
  ADD KEY `comments_fk1` (`idUser`);

--
-- Indexes for table `news_save`
--
ALTER TABLE `news_save`
  ADD PRIMARY KEY (`id`),
  ADD KEY `news_save_fk0` (`idUser`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `posts_fk0` (`idUser`),
  ADD KEY `posts_fk1` (`idTopic`);

--
-- Indexes for table `topics`
--
ALTER TABLE `topics`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `votes`
--
ALTER TABLE `votes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `votes_fk0` (`idPost`),
  ADD KEY `votes_fk1` (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `news_save`
--
ALTER TABLE `news_save`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `topics`
--
ALTER TABLE `topics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `votes`
--
ALTER TABLE `votes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_fk0` FOREIGN KEY (`idPost`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_fk1` FOREIGN KEY (`idUser`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `news_save`
--
ALTER TABLE `news_save`
  ADD CONSTRAINT `news_save_fk0` FOREIGN KEY (`idUser`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_fk0` FOREIGN KEY (`idUser`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `posts_fk1` FOREIGN KEY (`idTopic`) REFERENCES `topics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `votes`
--
ALTER TABLE `votes`
  ADD CONSTRAINT `votes_fk0` FOREIGN KEY (`idPost`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `votes_fk1` FOREIGN KEY (`idUser`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
