/*
Navicat MySQL Data Transfer

Source Server         : demo
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : user_db_security

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2022-01-03 19:44:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端标\r\n识',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) DEFAULT NULL COMMENT '客户端权限',
  `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '授权码模式',
  `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '跳转uri，当授权码申请成功后会跳转到此地址，并在后边带上code参数（授权码）',
  `authorities` varchar(255) DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '令牌有效期',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '刷新令牌token',
  `additional_information` longtext COMMENT '额外信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `archived` tinyint(4) DEFAULT NULL COMMENT '存档',
  `trusted` tinyint(4) DEFAULT NULL COMMENT '授信',
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接入客户端信息';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('c1', 'res1', '$2a$10$5VB1tTz2esgCz5kNM9z0..EPGDQSIMXpiK9n.K3KZzdWv.Q.PBF1W', 'ROLE_ADMIN,ROLE_USER,ROLE_API,all', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com', null, '7200', '259200', null, '2022-01-03 19:28:45', '0', '0', 'false');
INSERT INTO `oauth_client_details` VALUES ('c2', 'res2', '$2a$10$6p00zw1Yv1E8/DEDrRgWYuAUhm2ICnE6GewtNV27zXK7IsnZfXtnK', 'ROLE_API,all', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com', null, '31536000', '2592000', null, '2022-01-03 19:28:49', '0', '0', 'false');

-- ----------------------------
-- Table structure for `oauth_code`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) DEFAULT NULL COMMENT '授权码',
  `authentication` blob COMMENT '认证',
  KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='授权码表';

-- ----------------------------
-- Records of oauth_code
-- ----------------------------
INSERT INTO `oauth_code` VALUES ('2022-01-03 19:34:31', 'Vin3C3', 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A65787000000002770400000002737200426F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E617574686F726974792E53696D706C654772616E746564417574686F7269747900000000000001FE0200014C0004726F6C657400124C6A6176612F6C616E672F537472696E673B787074000270317371007E000D74000270337871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B726564697265637455726971007E000E4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0016787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000E4C001172657175657374506172616D657465727371007E00144C000573636F706571007E001678707400026331737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E00147870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000067708000000080000000474000D726573706F6E73655F74797065740004636F646574000C72656469726563745F757269740014687474703A2F2F7777772E62616964752E636F6D740009636C69656E745F696471007E001974000573636F7065740003616C6C78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000171007E002478017371007E0028770C000000103F40000000000000787371007E001C3F400000000000007708000000100000000078740014687474703A2F2F7777772E62616964752E636F6D707371007E0028770C000000103F4000000000000174000472657331787371007E0028770C000000103F4000000000000171007E001F787372004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000001FE0200024C000B63726564656E7469616C7371007E00054C00097072696E636970616C71007E00057871007E0003017371007E00077371007E000B0000000277040000000271007E000F71007E00117871007E0033737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000001FE0200024C000D72656D6F74654164647265737371007E000E4C000973657373696F6E496471007E000E787074000F303A303A303A303A303A303A303A31740020413737344433453138303845393936303739424543303343364346324638363170737200326F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E7573657264657461696C732E5573657200000000000001FE0200075A00116163636F756E744E6F6E457870697265645A00106163636F756E744E6F6E4C6F636B65645A001563726564656E7469616C734E6F6E457870697265645A0007656E61626C65644C000B617574686F72697469657371007E00164C000870617373776F726471007E000E4C0008757365726E616D6571007E000E7870010101017371007E0025737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200466F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E7573657264657461696C732E5573657224417574686F72697479436F6D70617261746F7200000000000001FE020000787077040000000271007E000F71007E001178707400957B2266756C6C6E616D65223A22E5BCA0E4B889222C226964223A2231222C226D6F62696C65223A223136343837353436353738222C2270617373776F7264223A2224326124313024447A736F49727A495562375261576D4C50312E47444F3955672E483377554268556C6F42354C4C43537962624742426F7775694375222C22757365726E616D65223A227A68616E6773616E227D);

-- ----------------------------
-- Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '权限标识符',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `url` varchar(128) DEFAULT NULL COMMENT '请求地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', 'p1', '测试资源\r\n1', '/r/r1');
INSERT INTO `t_permission` VALUES ('2', 'p3', '测试资源2', '/r/r2');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(32) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` char(1) NOT NULL COMMENT '角色状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员', null, null, null, '');

-- ----------------------------
-- Table structure for `t_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(32) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1');
INSERT INTO `t_role_permission` VALUES ('1', '2');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `fullname` varchar(255) NOT NULL COMMENT '用户姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'zhangsan', '$2a$10$DzsoIrzIUb7RaWmLP1.GDO9Ug.H3wUBhUloB5LLCSybbGBBowuiCu', '张三', '16487546578');
INSERT INTO `t_user` VALUES ('2', 'lisi', '$2a$10$knThlWcTk4OaQguzoTjKR.HJvLXu/RssqY3gZ3o6aMgmawtJUGKYS', '李四', '18654795647');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', null, null);
