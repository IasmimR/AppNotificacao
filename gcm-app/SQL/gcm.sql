-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           5.6.16 - MySQL Community Server (GPL)
-- OS do Servidor:               Win32
-- HeidiSQL Versão:              8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Copiando estrutura do banco de dados para gcm
CREATE DATABASE IF NOT EXISTS `gcm` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `gcm`;


-- Copiando estrutura para tabela gcm.gcm_groups
CREATE TABLE IF NOT EXISTS `gcm_groups` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'Id do grupo.',
  `group_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tabela destinada a receber todos os grupos cadastrados no sistema.';

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela gcm.gcm_users
CREATE TABLE IF NOT EXISTS `gcm_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gcm_regid` text,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela gcm.user_per_group
CREATE TABLE IF NOT EXISTS `user_per_group` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `gcm_user_id` int(5) NOT NULL,
  `gcm_group_id` int(5) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_GCM_GROUP_ID` (`gcm_group_id`),
  KEY `FK_GCM_USER_ID` (`gcm_user_id`),
  CONSTRAINT `FK_GCM_GROUP_ID` FOREIGN KEY (`gcm_group_id`) REFERENCES `gcm_groups` (`id`),
  CONSTRAINT `FK_GCM_USER_ID` FOREIGN KEY (`gcm_user_id`) REFERENCES `gcm_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Relacionamento da tabela gcm_users, com a tabeal gcm_groups.';

-- Exportação de dados foi desmarcado.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
