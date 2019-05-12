-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 12, 2019 at 02:45 PM
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
(29, 1, 1, 'lol@@@1', '2019-05-12 12:58:11'),
(30, 1, 1, '2@@@1', '2019-05-12 13:44:08'),
(31, 7, 1, 'haha@@@1', '2019-05-12 19:44:20');

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
(1, 1, 1, 'test', '[{\"url\": \"http://192.168.0.109/FreakingNews/upload/photo-3-1535416390418891808922.png\"}, {\"url\": \"http://hinhanhdephd.com/wp-content/uploads/2016/01/tai-hinh-girl-xinh-lam-avatar-de-thuong-nhat-22.jpg\"}]', '2019-05-12 01:27:47', '2019-05-12 01:27:47'),
(7, 1, 1, '1', '[{\"url\":\"http://192.168.0.109/FreakingNews/upload/photo-3-1535416390418891808922.png\"}]', '2019-05-12 02:12:25', '2019-05-12 02:12:25'),
(8, 1, 1, 'haha', '[{\"url\":\"http://192.168.0.109/FreakingNews/upload/cho-pug-mat-xe-hinh-anh-cho-mat-xe-cho-mat-xe-de-thuong-1.jpg\"}]', '2019-05-12 12:58:29', '2019-05-12 12:58:29'),
(9, 1, 1, 'haha', '[{\"url\":\"http://192.168.0.109/FreakingNews/upload/photo-3-1535416390418891808922.png\"}]', '2019-05-12 19:46:21', '2019-05-12 19:46:21'),
(10, 1, 1, 'handle', '[{\"url\":\"http://192.168.0.109/FreakingNews/upload/212310618041193431006884986278710988847462n-1507458620893.jpg\"}]', '2019-05-12 20:31:08', '2019-05-12 20:31:08');

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
(7, 1, 1, 0),
(8, 7, 1, 0),
(9, 8, 1, 0),
(10, 9, 1, 1),
(11, 10, 1, -1);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `news_save`
--
ALTER TABLE `news_save`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
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
