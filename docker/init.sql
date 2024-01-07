-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: reggie
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address_book`
--
-- 设置存储过程，检查数据库是否存在并退出，下面都是gpt生成的，检查部分
-- DELIMITER //
--
-- CREATE PROCEDURE CheckAndExit()
-- BEGIN
--   -- 检查数据库是否存在
--   IF EXISTS (SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'reggie') THEN
--     -- 数据库存在，输出消息并退出
--     SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Database reggie already exists. Exiting script.';
-- END IF;
-- END //
--
-- DELIMITER ;
--
-- -- 调用存储过程
-- CALL CheckAndExit();

-- 如果没有退出，则继续执行下面的初始化操作

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `reggie`;

-- 切换到 'reggie' 数据库
USE `reggie`;

DROP TABLE IF EXISTS `address_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_book` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '收货人',
  `sex` tinyint NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='地址管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_book`
--

LOCK TABLES `address_book` WRITE;
/*!40000 ALTER TABLE `address_book` DISABLE KEYS */;
INSERT INTO `address_book` VALUES (1726546037324820482,1713534662914818050,'测试',1,'17856796238',NULL,NULL,NULL,NULL,NULL,NULL,'安徽省基尼太美县','家',0,'2023-11-20 18:20:28','2023-11-25 21:33:21',1713534662914818050,1713534662914818050,0,NULL),(1726552718503022593,1713534662914818050,'测试A',1,'17856796238',NULL,NULL,NULL,NULL,NULL,NULL,'吉林省吉林市丰满区滨江东路3999号','学校',1,'2023-11-20 18:47:01','2023-11-25 21:33:22',1713534662914818050,1713534662914818050,0,NULL);
/*!40000 ALTER TABLE `address_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL COMMENT '主键',
  `type` int DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `mendian_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='菜品及套餐分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1397844303408574465,1,'川菜',2,'2021-05-27 09:17:07','2021-06-02 14:27:22',1,1,1),(1397844391040167938,1,'粤菜',3,'2021-05-27 09:17:28','2021-07-09 14:37:13',1,1,1),(1413341197421846529,1,'饮品',11,'2021-07-09 11:36:15','2021-07-09 14:39:15',1,1,1),(1413342269393674242,2,'商务套餐A',5,'2021-07-09 11:40:30','2023-11-21 21:39:32',1,1,1),(1413384954989060097,1,'主食',12,'2021-07-09 14:30:07','2021-07-09 14:39:19',1710274344239407105,1,3),(1710957505986301953,1,'其他',123,'2023-10-08 17:57:12','2023-10-23 19:26:49',1710274344239407105,1710274344239407105,3),(1711697773408706561,2,'儿童套餐',2,'2023-10-10 18:58:46','2023-10-10 18:58:46',1710274344239407105,1,3),(1716416142783471617,2,'测试套餐1',1,'2023-10-23 19:27:53','2023-10-23 19:27:53',1710274344239407105,1710274344239407105,3),(1724777557651042306,2,'测试套餐B',3,'2023-11-15 21:13:09','2023-11-15 21:13:09',1,1,1),(1727313306057392129,1,'小吃',2,'2023-11-22 21:09:19','2023-11-22 21:09:19',1235680076532358987,1235680076532358987,3),(1728369007424843778,1,'小食',1,'2023-11-25 19:04:18','2023-11-25 19:04:18',1,1,2),(1728369045890805761,1,'饮料',2,'2023-11-25 19:04:27','2023-11-25 19:04:27',1,1,2);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '描述信息',
  `status` int NOT NULL DEFAULT '1' COMMENT '0 停售 1 起售',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  `mendian_id` bigint DEFAULT NULL COMMENT '商家id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='菜品管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1397849739276890114,'辣子鸡',1397844303408574465,7800.00,'222222222','f966a38e-0780-40be-bb52-5699d13cb3d9.jpg','来自鲜嫩美味的小鸡，值得一尝',1,0,'2021-05-27 09:38:43','2023-11-25 21:34:48',1,1710274344239407105,0,1),(1397850140982161409,'毛氏红烧肉',1397844303408574465,6800.00,'123412341234','0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg','毛氏红烧肉毛氏红烧肉，确定不来一份？',1,0,'2021-05-27 09:40:19','2021-05-27 09:40:19',1,1,0,1),(1397850392090947585,'组庵鱼翅',1397844303408574465,4800.00,'123412341234','740c79ce-af29-41b8-b78d-5f49c96e38c4.jpg','组庵鱼翅，看图足以表明好吃程度',1,0,'2021-05-27 09:41:19','2021-05-27 09:41:19',1,1,0,1),(1397850851245600769,'霸王别姬',1397844303408574465,12800.00,'123412341234','057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg','还有什么比霸王别姬更美味的呢？',1,0,'2021-05-27 09:43:08','2021-05-27 09:43:08',1,1,0,1),(1397851099502260226,'全家福',1397844303408574465,11800.00,'23412341234','a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg','别光吃肉啦，来份全家福吧，让你长寿又美味',1,0,'2021-05-27 09:44:08','2021-05-27 09:44:08',1,1,0,1),(1397852391150759938,'辣子鸡丁',1397844303408574465,8800.00,'2346812468','ef2b73f2-75d1-4d3a-beea-22da0e1421bd.jpg','辣子鸡丁，辣子鸡丁，永远的魂',1,0,'2021-05-27 09:49:16','2023-11-14 19:12:45',1,1,0,1),(1397853183287013378,'麻辣兔头',1397844303408574465,19800.00,'123456787654321','2a2e9d66-b41d-4645-87bd-95f2cfeed218.jpg','麻辣兔头的详细制作，麻辣鲜香，色泽红润，回味悠长',1,0,'2021-05-27 09:52:24','2023-11-14 19:12:45',1,1,0,1),(1397853709101740034,'蒜泥白肉',1397844303408574465,9800.00,'1234321234321','d2f61d70-ac85-4529-9b74-6d9a2255c6d7.jpg','多么的有食欲啊',1,0,'2021-05-27 09:54:30','2023-11-14 19:12:45',1,1,0,1),(1397853890262118402,'鱼香肉丝',1397844303408574465,3800.00,'1234212321234','8dcfda14-5712-4d28-82f7-ae905b3c2308.jpg','鱼香肉丝简直就是我们童年回忆的一道经典菜，上学的时候点个鱼香肉丝盖饭坐在宿舍床上看着肥皂剧，绝了！现在完美复刻一下上学的时候感觉',1,0,'2021-05-27 09:55:13','2023-11-14 19:12:45',1,1,0,1),(1397854652581064706,'麻辣水煮鱼',1397844303408574465,14800.00,'2345312·345321','1fdbfbf3-1d86-4b29-a3fc-46345852f2f8.jpg','鱼片是买的切好的鱼片，放几个虾，增加味道',1,0,'2021-05-27 09:58:15','2021-05-27 09:58:15',1,1,0,1),(1397854865672679425,'鱼香炒鸡蛋',1397844303408574465,2000.00,'23456431·23456','0f252364-a561-4e8d-8065-9a6797a6b1d3.jpg','鱼香菜也是川味的特色。里面没有鱼却鱼香味',1,0,'2021-05-27 09:59:06','2021-05-27 09:59:06',1,1,0,1),(1397860242057375745,'脆皮烧鹅',1397844391040167938,12800.00,'123456786543213456','e476f679-5c15-436b-87fa-8c4e9644bf33.jpeg','“广东烤鸭美而香，却胜烧鹅说古冈（今新会），燕瘦环肥各佳妙，君休偏重便宜坊”，可见烧鹅与烧鸭在粤菜之中已早负盛名。作为广州最普遍和最受欢迎的烧烤肉食，以它的“色泽金红，皮脆肉嫩，味香可口”的特色，在省城各大街小巷的烧卤店随处可见。',1,0,'2021-05-27 10:20:27','2021-05-27 10:20:27',1,1,0,1),(1397860578738352129,'白切鸡',1397844391040167938,6600.00,'12345678654','9ec6fc2d-50d2-422e-b954-de87dcd04198.jpeg','白切鸡是一道色香味俱全的特色传统名肴，又叫白斩鸡，是粤菜系鸡肴中的一种，始于清代的民间。白切鸡通常选用细骨农家鸡与沙姜、蒜茸等食材，慢火煮浸白切鸡皮爽肉滑，清淡鲜美。著名的泮溪酒家白切鸡，曾获商业部优质产品金鼎奖。湛江白切鸡更是驰名粤港澳。粤菜厨坛中，鸡的菜式有200余款之多，而最为人常食不厌的正是白切鸡，深受食家青睐。',1,0,'2021-05-27 10:21:48','2021-05-27 10:21:48',1,1,0,1),(1397860792492666881,'烤乳猪',1397844391040167938,38800.00,'213456432123456','2e96a7e3-affb-438e-b7c3-e1430df425c9.jpeg','广式烧乳猪主料是小乳猪，辅料是蒜，调料是五香粉、芝麻酱、八角粉等，本菜品主要通过将食材放入炭火中烧烤而成。烤乳猪是广州最著名的特色菜，并且是“满汉全席”中的主打菜肴之一。烤乳猪也是许多年来广东人祭祖的祭品之一，是家家都少不了的应节之物，用乳猪祭完先人后，亲戚们再聚餐食用。',1,0,'2021-05-27 10:22:39','2021-05-27 10:22:39',1,1,0,1),(1397860963880316929,'脆皮乳鸽',1397844391040167938,10800.00,'1234563212345','3fabb83a-1c09-4fd9-892b-4ef7457daafa.jpeg','“脆皮乳鸽”是广东菜中的一道传统名菜，属于粤菜系，具有皮脆肉嫩、色泽红亮、鲜香味美的特点，常吃可使身体强健，清肺顺气。随着菜品制作工艺的不断发展，逐渐形成了熟炸法、生炸法和烤制法三种制作方法。无论那种制作方法，都是在鸽子经过一系列的加工，挂脆皮水后再加工而成，正宗的“脆皮乳鸽皮脆肉嫩、色泽红亮、鲜香味美、香气馥郁。这三种方法的制作过程都不算复杂，但想达到理想的效果并不容易。',1,0,'2021-05-27 10:23:19','2021-05-27 10:23:19',1,1,0,1),(1397861683434139649,'清蒸河鲜海鲜',1397844391040167938,38800.00,'1234567876543213456','1405081e-f545-42e1-86a2-f7559ae2e276.jpeg','新鲜的海鲜，清蒸是最好的处理方式。鲜，体会为什么叫海鲜。清蒸是广州最经典的烹饪手法，过去岭南地区由于峻山大岭阻隔，交通不便，经济发展起步慢，自家打的鱼放在锅里煮了就吃，没有太多的讲究，但却发现这清淡的煮法能使鱼的鲜甜跃然舌尖。',1,0,'2021-05-27 10:26:11','2021-05-27 10:26:11',1,1,0,1),(1397862477831122945,'上汤焗龙虾',1397844391040167938,108800.00,'1234567865432','5b8d2da3-3744-4bb3-acdc-329056b8259d.jpeg','上汤焗龙虾是一道色香味俱全的传统名菜，属于粤菜系。此菜以龙虾为主料，配以高汤制成的一道海鲜美食。本品肉质洁白细嫩，味道鲜美，蛋白质含量高，脂肪含量低，营养丰富。是色香味俱全的传统名菜。',1,0,'2021-05-27 10:29:20','2021-05-27 10:29:20',1,1,0,1),(1413342036832100354,'北冰洋',1413341197421846529,500.00,'','c99e0aab-3cb7-4eaa-80fd-f47d4ffea694.png','',1,0,'2021-07-09 11:39:35','2021-07-09 15:12:18',1,1,0,1),(1413384757047271425,'王老吉',1413341197421846529,500.00,'','00874a5e-0df2-446b-8f69-a30eb7d88ee8.png','',1,0,'2021-07-09 14:29:20','2023-10-10 10:40:08',1,1,0,1),(1413385247889891330,'米饭',1413384954989060097,200.00,'','ee04a05a-1230-46b6-8ad5-1a95b140fff3.png','',1,0,'2021-07-09 14:31:17','2023-11-22 21:14:06',1,1235680076532358987,0,3),(1711563689663619074,'pikaqu',1413384954989060097,1200.00,'','a8f45015-5612-4f94-b413-83800a0bc6a9.png','qs',1,0,'2023-10-10 10:05:58','2023-10-10 16:50:01',1,1,0,3),(1711578298290151425,'bebr',1397844391040167938,11100.00,'','e7c779a8-425d-4598-b2ef-159d87856fd9.png','e11',1,0,'2023-10-10 16:48:35','2023-10-11 10:33:57',1,1,0,3),(1711698232420753410,'鸡你太忙',1710957505986301953,9900.00,'','09487755-031f-4591-a677-d959b7de52c4.png','还好',1,0,'2023-10-10 19:00:35','2023-10-11 10:33:55',1,1,0,3),(1716397731902869505,'坤坤',1397844303408574465,100.00,'','6ed9e717-76b9-4218-87bb-cfe80e1cdba2.jpg','',1,0,'2023-10-23 18:14:43','2023-11-15 20:40:57',1,1,0,3),(1716411268263993346,'炸坤腿',1413384954989060097,200.00,'','f3da33b8-d036-4cf3-9d4a-de80947fe28b.png','好吃',0,0,'2023-10-23 19:08:30','2023-10-26 17:39:19',1710274344239407105,1,0,3),(1716448757934702593,'dw',1413384954989060097,100.00,'','90a22564-55c0-475e-bce1-110d88d46988.png','',1,0,'2023-10-23 21:37:29','2023-10-23 21:37:29',1710274344239407105,1,0,3),(1727318213615345665,'煎饼',1413384954989060097,100.00,'','b7b3fe3b-143e-4729-a521-fbd8b0b8364c.png','超级好吃',1,0,'2023-11-22 21:28:49','2023-11-22 21:28:49',1235680076532358987,1235680076532358987,0,3),(1728369207430230018,'坤柳',1728369007424843778,2200.00,'','8756a1df-c7c3-4db9-8ae6-e5830aab1d3e.jpg','好吃的坤六',1,0,'2023-11-25 19:05:05','2023-11-25 19:05:05',1,1,0,2),(1728369917962104833,'坤柳1',1728369007424843778,2200.00,'','8756a1df-c7c3-4db9-8ae6-e5830aab1d3e.jpg','好吃的坤六',1,0,'2023-11-25 19:07:55','2023-11-25 19:07:55',1,1,0,2);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_flavor`
--

DROP TABLE IF EXISTS `dish_flavor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish_flavor` (
  `id` bigint NOT NULL COMMENT '主键',
  `dish_id` bigint NOT NULL COMMENT '菜品',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='菜品口味关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_flavor`
--

LOCK TABLES `dish_flavor` WRITE;
/*!40000 ALTER TABLE `dish_flavor` DISABLE KEYS */;
INSERT INTO `dish_flavor` VALUES (1397849417888346113,1397849417854791681,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:37:27','2021-05-27 09:37:27',1,1,0,NULL),(1397849739297861633,1397849739276890114,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:38:43','2023-11-18 20:19:20',1,1,0,NULL),(1397849739323027458,1397849739276890114,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:38:43','2023-11-18 20:19:20',1,1,0,NULL),(1397849936421761025,1397849936404983809,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:39:30','2021-05-27 09:39:30',1,1,0,NULL),(1397849936438538241,1397849936404983809,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:39:30','2021-05-27 09:39:30',1,1,0,NULL),(1397850141015715841,1397850140982161409,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:40:19','2021-05-27 09:40:19',1,1,0,NULL),(1397850141040881665,1397850140982161409,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:40:19','2021-05-27 09:40:19',1,1,0,NULL),(1397850392120307713,1397850392090947585,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:41:19','2021-05-27 09:41:19',1,1,0,NULL),(1397850392137084929,1397850392090947585,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:41:19','2021-05-27 09:41:19',1,1,0,NULL),(1397850630734262274,1397850630700707841,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:42:16','2021-05-27 09:42:16',1,1,0,NULL),(1397850630755233794,1397850630700707841,'辣度','[\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:42:16','2021-05-27 09:42:16',1,1,0,NULL),(1397850851274960898,1397850851245600769,'忌口','[\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:43:08','2021-05-27 09:43:08',1,1,0,NULL),(1397850851283349505,1397850851245600769,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:43:08','2021-05-27 09:43:08',1,1,0,NULL),(1397851099523231745,1397851099502260226,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:44:08','2021-05-27 09:44:08',1,1,0,NULL),(1397851099527426050,1397851099502260226,'辣度','[\"不辣\",\"微辣\",\"中辣\"]','2021-05-27 09:44:08','2021-05-27 09:44:08',1,1,0,NULL),(1397851370483658754,1397851370462687234,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-27 09:45:12','2023-10-19 19:06:03',1,1,0,NULL),(1397851370483658755,1397851370462687234,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:45:12','2023-10-19 19:06:03',1,1,0,NULL),(1397851370483658756,1397851370462687234,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:45:12','2023-10-19 19:06:03',1,1,0,NULL),(1397851668283437058,1397851668262465537,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-27 09:46:23','2023-10-23 18:28:03',1,1,0,NULL),(1397852391180120065,1397852391150759938,'忌口','[\"不要葱\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:49:16','2021-05-27 09:49:16',1,1,0,NULL),(1397852391196897281,1397852391150759938,'辣度','[\"不辣\",\"微辣\",\"重辣\"]','2021-05-27 09:49:16','2021-05-27 09:49:16',1,1,0,NULL),(1397853183307984898,1397853183287013378,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:52:24','2021-05-27 09:52:24',1,1,0,NULL),(1397853423486414850,1397853423461249026,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:53:22','2021-05-27 09:53:22',1,1,0,NULL),(1397853709126905857,1397853709101740034,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:54:30','2021-05-27 09:54:30',1,1,0,NULL),(1397853890283089922,1397853890262118402,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:55:13','2021-05-27 09:55:13',1,1,0,NULL),(1397854133632413697,1397854133603053569,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-27 09:56:11','2021-05-27 09:56:11',1,1,0,NULL),(1397854652623007745,1397854652581064706,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 09:58:15','2021-05-27 09:58:15',1,1,0,NULL),(1397854652635590658,1397854652581064706,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:58:15','2021-05-27 09:58:15',1,1,0,NULL),(1397854865735593986,1397854865672679425,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 09:59:06','2021-05-27 09:59:06',1,1,0,NULL),(1397855742303186946,1397855742273826817,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:02:35','2021-05-27 10:02:35',1,1,0,NULL),(1397855906497605633,1397855906468245506,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:03:14','2021-05-27 10:03:14',1,1,0,NULL),(1397856190573621250,1397856190540066818,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:04:21','2021-05-27 10:04:21',1,1,0,NULL),(1397859056709316609,1397859056684150785,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:15:45','2021-05-27 10:15:45',1,1,0,NULL),(1397859277837217794,1397859277812051969,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:16:37','2021-05-27 10:16:37',1,1,0,NULL),(1397859487502086146,1397859487476920321,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:17:27','2021-05-27 10:17:27',1,1,0,NULL),(1397859757061615618,1397859757036449794,'甜味','[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]','2021-05-27 10:18:32','2021-05-27 10:18:32',1,1,0,NULL),(1397860242086735874,1397860242057375745,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:20:27','2021-05-27 10:20:27',1,1,0,NULL),(1397860963918065665,1397860963880316929,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:23:19','2021-05-27 10:23:19',1,1,0,NULL),(1397861135754506242,1397861135733534722,'甜味','[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]','2021-05-27 10:24:00','2021-05-27 10:24:00',1,1,0,NULL),(1397861370035744769,1397861370010578945,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-27 10:24:56','2021-05-27 10:24:56',1,1,0,NULL),(1397861683459305474,1397861683434139649,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:26:11','2021-05-27 10:26:11',1,1,0,NULL),(1397861898467717121,1397861898438356993,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:27:02','2021-05-27 10:27:02',1,1,0,NULL),(1397862198054268929,1397862198033297410,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-27 10:28:14','2021-05-27 10:28:14',1,1,0,NULL),(1397862477835317250,1397862477831122945,'辣度','[\"不辣\",\"微辣\",\"中辣\"]','2021-05-27 10:29:20','2021-05-27 10:29:20',1,1,0,NULL),(1398089545865015297,1398089545676271617,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-28 01:31:38','2021-05-28 01:31:38',1,1,0,NULL),(1398089782323097601,1398089782285348866,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:32:34','2021-05-28 01:32:34',1,1,0,NULL),(1398090003262255106,1398090003228700673,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:33:27','2021-05-28 01:33:27',1,1,0,NULL),(1398090264554811394,1398090264517062657,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:34:29','2021-05-28 01:34:29',1,1,0,NULL),(1398090455399837698,1398090455324340225,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:35:14','2021-05-28 01:35:14',1,1,0,NULL),(1398090685449023490,1398090685419663362,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2021-05-28 01:36:09','2021-05-28 01:36:09',1,1,0,NULL),(1398090825358422017,1398090825329061889,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:36:43','2021-05-28 01:36:43',1,1,0,NULL),(1398091007051476993,1398091007017922561,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:37:26','2021-05-28 01:37:26',1,1,0,NULL),(1398091296164851713,1398091296131297281,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:38:35','2021-05-28 01:38:35',1,1,0,NULL),(1398091546531246081,1398091546480914433,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2021-05-28 01:39:35','2021-05-28 01:39:35',1,1,0,NULL),(1398091729809747969,1398091729788776450,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:40:18','2021-05-28 01:40:18',1,1,0,NULL),(1398091889499484161,1398091889449152513,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:40:56','2021-05-28 01:40:56',1,1,0,NULL),(1398092095179763713,1398092095142014978,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:41:45','2021-05-28 01:41:45',1,1,0,NULL),(1398092283877306370,1398092283847946241,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:42:30','2021-05-28 01:42:30',1,1,0,NULL),(1398094018939236354,1398094018893099009,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:49:24','2021-05-28 01:49:24',1,1,0,NULL),(1398094391494094850,1398094391456346113,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-05-28 01:50:53','2021-05-28 01:50:53',1,1,0,NULL),(1399574026165727233,1399305325713600514,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2021-06-01 03:50:25','2021-06-01 03:50:25',1399309715396669441,1399309715396669441,0,NULL),(1413389540592263169,1413384757047271425,'温度','[\"常温\",\"冷藏\"]','2021-07-12 09:09:16','2021-07-12 09:09:16',1,1,0,NULL),(1413389684020682754,1413342036832100354,'温度','[\"常温\",\"冷藏\"]','2021-07-09 15:12:18','2021-07-09 15:12:18',1,1,0,NULL),(1711361387682029569,1711361387543617537,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-10-09 20:42:05','2023-10-09 20:42:05',1,1,0,NULL),(1711361387682029570,1711361387543617537,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2023-10-09 20:42:05','2023-10-09 20:42:05',1,1,0,NULL),(1711361565742817282,1711361565738622978,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-10-09 20:42:47','2023-10-09 20:42:47',1,1,0,NULL),(1711562785535197186,1711562785497448449,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-10-10 10:02:22','2023-10-10 10:02:22',1,1,0,NULL),(1711563689705562113,1711563689663619074,'温度','[\"热饮\",\"常温\",\"去冰\"]','2023-10-10 10:05:58','2023-10-10 16:50:01',1,1,0,NULL),(1711563689705562114,1711563689663619074,'甜味','[\"无糖\",\"少糖\",\"半糖\"]','2023-10-10 10:05:58','2023-10-10 10:05:58',1,1,0,NULL),(1711578298315317249,1711578298290151425,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-10-10 16:48:35','2023-10-10 16:51:40',1,1,0,NULL),(1711665011142299650,1711578298290151425,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2023-10-10 16:48:35','2023-10-10 16:51:40',1,1,0,NULL),(1711698232445919233,1711698232420753410,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-10-10 19:00:35','2023-10-10 19:00:35',1,1,0,NULL),(1716397731940618242,1716397731902869505,'甜味','[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]','2023-10-23 18:14:43','2023-11-15 20:40:57',1,1,0,NULL),(1716411268301742081,1716411268263993346,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2023-10-23 19:08:30','2023-10-23 21:33:11',1710274344239407105,1710274344239407105,0,NULL),(1716448758274441218,1716448757934702593,'温度','[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]','2023-10-23 21:37:29','2023-10-23 21:37:29',1710274344239407105,1710274344239407105,0,NULL),(1727318213615345666,1727318213615345665,'忌口','[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]','2023-11-22 21:28:49','2023-11-22 21:28:49',1235680076532358987,1235680076532358987,0,NULL),(1728369207430230019,1728369207430230018,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-11-25 19:05:05','2023-11-25 19:05:05',1,1,0,NULL),(1728369917962104834,1728369917962104833,'辣度','[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]','2023-11-25 19:07:55','2023-11-25 19:07:55',1,1,0,NULL);
/*!40000 ALTER TABLE `dish_flavor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `root` int DEFAULT NULL,
  `mendian_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='员工信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'管理员','admin','e10adc3949ba59abbe56e057f20f883e','13812312312','1','110101199001010047',1,'2021-05-06 17:20:07','2023-11-25 19:14:22',1,1,3,1),(1235680076532358987,'ikun','cs','e10adc3949ba59abbe56e057f20f883e','17856796239','1','341623200402286765',1,'2023-10-23 19:14:53','2023-10-23 19:14:53',1,1,1,3),(1710274344239407105,'顶真','cxkbalq','e10adc3949ba59abbe56e057f20f883e','17856786238','1','341623200402288397',1,'2023-10-06 20:42:34','2023-11-25 21:33:42',1,1,2,1728359519716331522),(1716412872941838337,'dwa','daw','e10adc3949ba59abbe56e057f20f883e','17856796239','1','341623200402288325',1,'2023-10-23 19:14:53','2023-11-25 21:34:40',1,1710274344239407105,3,1),(1727998542915215361,'门店1员工1','mendian','e10adc3949ba59abbe56e057f20f883e','14567654345','1','341623299878873324',1,'2023-11-24 18:32:12','2023-11-24 18:32:12',1,1,2,1728667285676793858),(1728368730521088002,'md2yg1','md2yg1','e10adc3949ba59abbe56e057f20f883e','16756767676','1','341323455656656656',1,'2023-11-25 19:03:12','2023-11-25 19:03:12',1,1,2,4),(1728379104481951745,'md3yg1','md3yg1','e10adc3949ba59abbe56e057f20f883e','16787876786','1','454567855656569878',1,'2023-11-25 19:44:25','2023-11-25 19:44:25',1235680076532358987,1235680076532358987,2,3),(1728379360829423617,'md3yg2','md3yg2','e10adc3949ba59abbe56e057f20f883e','15656565656','1','345454545454547',1,'2023-11-25 19:45:26','2023-11-25 19:45:26',1235680076532358987,1235680076532358987,3,3);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mendian`
--

DROP TABLE IF EXISTS `mendian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mendian` (
  `id` bigint NOT NULL,
  `image` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL,
  `stastus` int DEFAULT NULL,
  `todaymony` decimal(10,2) DEFAULT NULL,
  `allmony` decimal(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `update_user` bigint DEFAULT NULL,
  `superson_id` bigint DEFAULT NULL,
  `jianjie` varchar(200) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mendian_pk2` (`id`),
  UNIQUE KEY `mendian_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mendian`
--

LOCK TABLES `mendian` WRITE;
/*!40000 ALTER TABLE `mendian` DISABLE KEYS */;
INSERT INTO `mendian` VALUES (1,'fe93d514-c401-4ac1-84e3-694bf2c585d4.png','测试门店1','测试地址1',1,254.00,1142.00,'2023-10-29 15:48:42','2023-11-26 16:03:11',1,1,1,'品味生活，尽在我们店。美食、潮流、家居，让您尽享精致。感谢您的光临！'),(2,'fe93d514-c401-4ac1-84e3-694bf2c585d4.png','测试门店2','测试地址2',1,22.00,910.00,'2023-10-30 16:17:43','2023-11-26 15:07:56',1,1,1,'品味生活，尽在我们店。美食、潮流、家居，让您尽享精致。感谢您的光临！'),(3,'2a38f7ba-4742-49e5-b89e-8b9b5d53da95.png','测试门店3','测试地址3',1,286.00,1174.00,'2023-11-21 20:09:52','2023-11-25 22:03:46',1235680076532358987,1235680076532358987,1,'本餐厅是坤坤点餐的独立的品牌，定位“大众 化的美食外送餐饮”，旨为顾客打造专业美食'),(4,'2a38f7ba-4742-49e5-b89e-8b9b5d53da95.png','测试门店4','测试地址4',1,22.00,910.00,'2023-11-21 20:09:52','2023-11-26 16:29:25',1,1,1728368730521088002,'本餐厅是坤坤点餐的独立的品牌，定位“大众 化的美食外送餐饮”，旨为顾客打造专业美食'),(1728359519716331522,'27d36d2a-95e6-4e8f-8357-45f3066a6f92.png','测试门店5','吉林省吉林市丰满区滨江东路3999号',1,0.00,0.00,'2023-11-25 18:26:35','2023-11-26 16:16:21',1,1,1728368730521088002,'测试'),(1728360191090184194,'4f617d16-428b-46b9-9942-e4f32c7e6433.png','测试门店6','吉林省吉林市丰满区滨江东路3999号',1,0.00,0.00,'2023-11-25 18:29:16','2023-11-25 18:29:16',1235680076532358987,1235680076532358987,1235680076532358987,'测试'),(1728667285676793858,'4c073d83-248d-4674-b638-aa680c38d305.jpg','xsa','吉林省吉林市丰满区滨江东路3999号',1,0.00,0.00,'2023-11-26 14:49:33','2023-11-26 16:40:07',1,1,1727998542915215361,'哈哈哈哈哈哈哈');
/*!40000 ALTER TABLE `mendian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `mendian_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='订单明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1727996919568539650,'全家福','a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg',1727996919505625089,1397851099502260226,NULL,'不要蒜,中辣',1,118.00,1),(1728653488446664705,'坤坤','6ed9e717-76b9-4218-87bb-cfe80e1cdba2.jpg',1728653488379555841,1716397731902869505,NULL,'多糖',1,1.00,1),(1728654549605576706,'坤坤','6ed9e717-76b9-4218-87bb-cfe80e1cdba2.jpg',1728654549605576705,1716397731902869505,NULL,'多糖',1,1.00,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NOT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `mendian_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1727996919505625089,'1727996919505625089',2,1713534662914818050,1726552718503022593,'2023-11-24 18:25:45','2023-11-24 18:25:45',1,118.00,'','17856796238','吉林省吉林市丰满区滨江东路3999号','17856796238','测试A',1),(1728653488379555841,'1728653488379555841',2,1713534662914818050,1726552718503022593,'2023-11-26 13:54:43','2023-11-26 13:54:43',1,1.00,'','17856796238','吉林省吉林市丰满区滨江东路3999号','17856796238','测试A',1),(1728654549605576705,'1728654549605576705',2,1713534662914818050,1726552718503022593,'2023-11-26 13:58:56','2023-11-26 13:58:56',1,1.00,'','17856796238','吉林省吉林市丰满区滨江东路3999号','17856796238','测试A',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setmeal`
--

DROP TABLE IF EXISTS `setmeal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setmeal` (
  `id` bigint NOT NULL COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  `mendian_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='套餐';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setmeal`
--

LOCK TABLES `setmeal` WRITE;
/*!40000 ALTER TABLE `setmeal` DISABLE KEYS */;
INSERT INTO `setmeal` VALUES (1727322809347772418,1413342269393674242,'门店1套餐A1',33300.00,1,'','便宜好吃！！！！！！！！！！','4bd197b1-5ba0-46ea-a495-0ae7d681b710.png','2023-11-22 21:47:05','2023-11-22 21:51:14',1,1,0,1),(1727323475642310658,1724777557651042306,'门店1套餐B2',2200.00,1,'','大青蛙大大','1452f3de-dda1-4be3-860e-4fd8422c0ed3.png','2023-11-22 21:49:43','2023-11-22 21:50:09',1,1,0,1),(1727639067813396482,1711697773408706561,'门店3套餐A',2200.00,1,'','如如r','3bbdb305-9770-49b0-a1fe-9c10315cc129.png','2023-11-23 18:43:46','2023-11-23 18:43:46',1235680076532358987,1235680076532358987,0,3);
/*!40000 ALTER TABLE `setmeal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setmeal_dish`
--

DROP TABLE IF EXISTS `setmeal_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setmeal_dish` (
  `id` bigint NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int NOT NULL COMMENT '份数',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  `employee_id` bigint DEFAULT NULL,
  `image` varchar(80) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='套餐菜品关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setmeal_dish`
--

LOCK TABLES `setmeal_dish` WRITE;
/*!40000 ALTER TABLE `setmeal_dish` DISABLE KEYS */;
INSERT INTO `setmeal_dish` VALUES (1727323563399733249,'1727322809347772418','1716397731902869505','坤坤',100.00,1,0,'2023-11-22 21:50:04','2023-11-22 21:50:04',1,1,0,NULL,'6ed9e717-76b9-4218-87bb-cfe80e1cdba2.jpg'),(1727323563399733250,'1727322809347772418','1397850851245600769','霸王别姬',12800.00,1,0,'2023-11-22 21:50:04','2023-11-22 21:50:04',1,1,0,NULL,'057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg'),(1727323563399733251,'1727322809347772418','1397851099502260226','全家福',11800.00,1,0,'2023-11-22 21:50:04','2023-11-22 21:50:04',1,1,0,NULL,'a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg'),(1727323582882275329,'1727323475642310658','1711578298290151425','bebr',11100.00,1,0,'2023-11-22 21:50:09','2023-11-22 21:50:09',1,1,0,NULL,'e7c779a8-425d-4598-b2ef-159d87856fd9.png'),(1727323582882275330,'1727323475642310658','1413342036832100354','北冰洋',500.00,1,0,'2023-11-22 21:50:09','2023-11-22 21:50:09',1,1,0,NULL,'c99e0aab-3cb7-4eaa-80fd-f47d4ffea694.png'),(1727323582882275331,'1727323475642310658','1716397731902869505','坤坤',100.00,1,0,'2023-11-22 21:50:09','2023-11-22 21:50:09',1,1,0,NULL,'6ed9e717-76b9-4218-87bb-cfe80e1cdba2.jpg'),(1727639067859533825,'1727639067813396482','1727318213615345665','煎饼',100.00,1,0,'2023-11-23 18:43:46','2023-11-23 18:43:46',1235680076532358987,1235680076532358987,0,NULL,'b7b3fe3b-143e-4729-a521-fbd8b0b8364c.png'),(1727639067859533826,'1727639067813396482','1413385247889891330','米饭',200.00,1,0,'2023-11-23 18:43:46','2023-11-23 18:43:46',1235680076532358987,1235680076532358987,0,NULL,'ee04a05a-1230-46b6-8ad5-1a95b140fff3.png');
/*!40000 ALTER TABLE `setmeal_dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '名称',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='购物车';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart`
--

LOCK TABLES `shopping_cart` WRITE;
/*!40000 ALTER TABLE `shopping_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '头像',
  `status` int DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
  `password` varchar(20) COLLATE utf8mb3_bin DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1713534662914818050,NULL,'17856796238',NULL,NULL,NULL,1,NULL,1),(1713539287764103170,NULL,'17856786238',NULL,NULL,NULL,1,NULL,1710274344239407105);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-26 19:33:12
