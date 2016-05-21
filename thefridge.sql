-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2016 a las 16:28:10
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 7.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


GRANT USAGE ON *.* TO 'fridge_user'@'localhost' IDENTIFIED BY PASSWORD '*9A8A83C4DD718B8D4F4622E2837189E2E42D6496';

GRANT ALL PRIVILEGES ON `fridge`.* TO 'fridge_user'@'localhost' WITH GRANT OPTION;

--
-- Base de datos: `thefridge`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingrediente`
--

CREATE TABLE `ingrediente` (
  `id` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(65) NOT NULL,
  `categoria` enum('Aceites','Bebidas','Carnes','Cereales','Elaborados','Especias','Fruta','Frutos secos','Hierbas aromáticas','Hortalizas','Huevo','Legumbres','Lácteos','Marisco','Pasta','Pescado','Salsa','Setas','Verduras') NOT NULL,
  `rareza` enum('Usual','Poco usual','Raro','Exótico') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nevera`
--

CREATE TABLE `nevera` (
  `usuario` varchar(65) NOT NULL,
  `id_ingrediente` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion`
--

CREATE TABLE `publicacion` (
  `id` int(11) UNSIGNED NOT NULL,
  `usuario` varchar(65) NOT NULL,
  `id_receta` int(11) UNSIGNED NOT NULL,
  `mensaje` varchar(400) NOT NULL,
  `fecha` date NOT NULL,
  `privada` tinyint(1) NOT NULL DEFAULT '0',
  `img` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta`
--

CREATE TABLE `receta` (
  `id` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(65) NOT NULL,
  `dificultad` enum('1','2','3','4','5') NOT NULL,
  `duracion` int(11) NOT NULL,
  `personas` int(11) NOT NULL,
  `img` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta_ingredientes`
--

CREATE TABLE `receta_ingredientes` (
  `id_receta` int(11) UNSIGNED NOT NULL,
  `id_ingrediente` int(11) UNSIGNED NOT NULL,
  `descripcion` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta_pasos`
--

CREATE TABLE `receta_pasos` (
  `id_receta` int(11) UNSIGNED NOT NULL,
  `paso` int(11) NOT NULL,
  `descripcion` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `nombre` varchar(65) NOT NULL,
  `password` varchar(65) NOT NULL,
  `img` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ingrediente`
--
ALTER TABLE `ingrediente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `nevera`
--
ALTER TABLE `nevera`
  ADD PRIMARY KEY (`usuario`,`id_ingrediente`);

--
-- Indices de la tabla `publicacion`
--
ALTER TABLE `publicacion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_receta` (`id_receta`),
  ADD KEY `usuario` (`usuario`),
  ADD KEY `id_receta_2` (`id_receta`);

--
-- Indices de la tabla `receta`
--
ALTER TABLE `receta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `receta_ingredientes`
--
ALTER TABLE `receta_ingredientes`
  ADD PRIMARY KEY (`id_receta`,`id_ingrediente`),
  ADD KEY `id_ingrediente` (`id_ingrediente`);

--
-- Indices de la tabla `receta_pasos`
--
ALTER TABLE `receta_pasos`
  ADD PRIMARY KEY (`id_receta`,`paso`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ingrediente`
--
ALTER TABLE `ingrediente`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `publicacion`
--
ALTER TABLE `publicacion`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `receta`
--
ALTER TABLE `receta`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `nevera`
--
ALTER TABLE `nevera`
  ADD CONSTRAINT `nevera_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `publicacion`
--
ALTER TABLE `publicacion`
  ADD CONSTRAINT `publicacion_ibfk_1` FOREIGN KEY (`id_receta`) REFERENCES `receta` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `publicacion_ibfk_2` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `receta_ingredientes`
--
ALTER TABLE `receta_ingredientes`
  ADD CONSTRAINT `receta_ingredientes_ibfk_1` FOREIGN KEY (`id_receta`) REFERENCES `receta` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `receta_ingredientes_ibfk_2` FOREIGN KEY (`id_ingrediente`) REFERENCES `ingrediente` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `receta_pasos`
--
ALTER TABLE `receta_pasos`
  ADD CONSTRAINT `receta_pasos_ibfk_1` FOREIGN KEY (`id_receta`) REFERENCES `receta` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
