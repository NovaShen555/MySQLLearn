# MySQLLearn

### 概念

数据库(DataBase)

用于存储 管理数据，基于操作系统

SQL可以做500w以下的所有操作

### 分类

#### 关系型数据库 SQL

类似excel

- MySQL, Oracle, SqlServer, DB2, SQLlite
- 通过表与表之间，行与列之间的关系进行数据存贮，数据之间存在通用关系

#### 非关系型数据库 NOSQL, not only SQL

{key:value}

- Redis, MongoDB
- 将数据以对象形式存贮，这里不做讨论

### DBMS

DataBaseManageSystem

数据库的管理软件，使得用户可以通过特定的接口去操作数据库

所有程序通过DBMS操作具体的数据

**MySQL就是一个DBMS**

### MySQL简介

是一个RDBMS，属于Oracle公司

体积小 速度快 成本低

### MySQL安装

尽量使用压缩包安装，exe安装时会修改注册表，难以卸载

注意要先安装vc++环境！！！

1. 解压到你所需要的目录下

2. 配置MySQL下的bin目录进入PATH环境变量

3. 在MySQL目录下新建`my.ini`文件，配置以下信息

   ```ini
   [mysqld]
   basedir=C:\Program Files\MySQL
   datadir=C:\Program Files\MySQL\data\
   port=3306
   skip-grant-tables
   ```

4. 使用管理员模式CMD运行以下命令

   ```
   cd dir_of_mysql_bin 如 cd C:\Program Files\MySQL\bin
   mysqld -install 
   mysqld --initialize-insecure --user=mysql
   ```

5. 无报错后启动MySQL，修改密码

   ```
   net start mysql
   mysql -u root -p
   //注意-p的p后面不要加空格
   update mysql.user set authentication_string=password('123456') where user='root' and Host = 'localhost';
   flush privileges;
   ```

6. 注释掉`my.ini`文件中最后一行跳过密码，以后的密码就是123456了

7. 重启mysql即可



**输入`mysql -u root -p`即可登录**

### sqlyog安装

1. 无脑安装，注册
2. 连接数据库
3. 新建一个数据库school，新建一个表student
4. 新建栏目
    - id int 10 主键非空
    - name varchar 50 非空
    - age int 3 非空
5. 右键student表，打开表
6. 尝试添加数据，保存

### 基本操作

**尽量把所有的操作加上`if exists`**

#### 修改密码

```mysql
update mysql.user set authentication_string=password('123456') where user='root' and Host = 'localhost';
```

#### 刷新权限

```mysql
flush privileges;
```

#### 查看所有的数据库

```mysql
show databases;
```

#### 查看所有的表

```mysql
show tables;
```

#### 切换数据库

```mysql
use name_of_database;
```

#### 显示表信息

```mysql
describe name_of_table; 
desc name_of_table;
```

#### 创建数据库

```mysql
create database name_of_database;
```

#### 删除数据库

```mysql
drop database name_of_database;
```

#### 创建一个表

```mysql
CREATE TABLE `student` (
	`id` INT(4) NOT NULL AUTO_INCREMENT COMMENT '学号',
	`name` VARCHAR(30) NOT NULL DEFAULT '匿名' COMMENT '姓名',
	`pwd` VARCHAR(30) NOT NULL DEFAULT '123456' COMMENT '密码',
	`sex` VARCHAR(3) NOT NULL DEFAULT '男' COMMENT '性别',
	`birthday` DATETIME  DEFAULT NULL COMMENT '出生日期',
	`address` VARCHAR(60) DEFAULT NULL COMMENT '家庭住址',
	`email` VARCHAR(30) DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
```

注意的是，mysql中区分关键词与用户输入的方式是反引号  ( **`** ) 具体的值才是单引号

#### 删除一个表

```mysql
DROP TABLE name_of_table;
```



#### **查看创建数据库的语句**

```mysql
show create database name_of_database;
```

#### **查看创建表的语句**

```mysql
show create table name_of_table;
```

#### 修改表内容

##### 修改表名

```mysql
ALTER TABLE old_name RENAME AS new_name;
```

##### 增加表字段

```mysql
ALTER TABLE name_of_table ADD var_name INT(11).....;
```

##### 修改表字段

区别：

- modify用于修改约束，不能rename
- change用于修改名字和约束

结论：change更强大，但对于微小的改动尽量使用modify

###### 修改约束

```mysql
ALTER TABLE name_of_table MODIFY var_name VARCHAR(11);
```

###### 修改名字及约束

```mysql
ALTER TABLE name_of_table CHANGE old_name new_name INT(11);
```

##### 删除表字段

```mysql
ALTER TABLE name_of_table DROP var_name;
```



#### 注释

```mysql
--hahaha
```

#### 多行注释

```mysql
/*
hahahaha
*/
```



### 数据类型

- 数值

    - tinyint 1Byte
    - smallint 2Byte
    - mediumint 3Byte
    - **int 4Byte**
    - bigint 8Byte
    - float 4Byte
    - double 8Byte
    - decimal    Float of Sting

- 字符串

    - char 2^8Byte
    - **varchar 2^16Byte**
    - tinytext 2^8Byte
    - **text 2^16Byte**

- 时间

    - data YYYY-MM-DD
    - time HH:mm:ss
    - **datatime YYYY-MM-DD-HH:mm:ss**
    - **timestamp Millisecond fr 1970.1.1 to now**
    - year

- null

  未知，无值

  **切勿运算null，因为结果为null**



### 字段属性

- unsigned

  无符号整数

  不能为负数，否则报错

- zerofill

  不足的位数用0填充

  如 int(3)-> 6--006

- 自增

  自动在上一个记录上默认加1(可改)

  通常用于设计唯一的主键 index

- 非空

  插入数据时必须有数据，否则报错

  不选时插入无数据默认为null

- 默认

  设置默认的值，不填则为null



### 必选字段 阿里规范

- id 主键
- 'version' 用于乐观锁
- is_delete 用于伪删除
- gmt_create 创建时间
- gmt_update 修改时间

用于表示一个字段存在的意义



### 数据库引擎

- innodb

多使用，安全性高，支持事务，多表多用户操作

表中只有一个*.frm文件，以及上级目录的 ibdata 文件

- myisam

早年使用，节约空间，速度快

有 *.frm(结构) *.myd(数据) *.myi(索引) 文件

|            | innodb | myisam  |
| ---------- | ------ | ------- |
| 事务支持   | N      | Y       |
| 数据行锁定 | N      | Y       |
| 外键约束   | N      | Y       |
| 全文索引   | Y      | N       |
| 表大小     | SMALL  | BIG (2) |

我也不懂啥意思，估计以后会学



### MySQL数据管理

#### 外键

只需了解

将本表下的某个字段约束为另外一个表的字段(引用)，即可将一个表作为一个字段使用

添加外键后，不可删除被引用的表

此为物理外键，属于数据库级别，当关系增多时引用将十分混乱

不建议使用

```mysql
KEY `FK_name` (`name`),
CONSTRAINT `FK_name` FOREIGN KEY (`name`) REFERENCES `foreign_table_name`(`foreign_name`)
```

#### DML语言

需要熟悉

#### 添加

由于主键自增，在添加数据的时候可以省略主键

如果不写目标字段，就**按顺序匹配**字段

可以同时插入多条数据，需要在values后多写几个括号并用逗号隔开即可

```mysql
INSERT INTO `name_of_database`.`name_of_table` (`var`, `var2`) VALUES ('123', '321');
```

#### 修改

```mysql
UPDATE `name_of_table` SET `name1`='var1',`name2`='var2'... WHERE (你需要的条件)
```

where后写约束，意会一下，后面也会讲

#### 删除

- delete

  ```mysql
  DELETE FROM `name_of_table` WHERE (你需要的条件)
  ```

- truncate

  完全清空数据库的**数据**

  ```mysql
  TRUNCATE `name_of_table`
  ```

区别：

- truncate 会重置自增计数器，delete 不会
- 但是innodb如果使用delete删除后重启mysql，也会重置计数器（为最后一条数据），因为其计数器存于内存
- truncate 不影响事务



### DQL查询数据

最重点

- 所有的查询操作都用它
- **数据库中最核心的语句，最重要的语句**
- 使用频率最高

语法

```mysql
SELECT[ALL|DISTINCT|DISTINCTROW|TOP]
{*|talbe.*|[table.]field1[AS alias1][,[table.]field2[AS alias2][,…]]}
FROM tableexpression[,…][IN externaldatabase]
[WHERE…]
[GROUP BY…]
[HAVING…]
[ORDER BY…]
```

执行步骤：

1. 先从from字句一个表或多个表创建工作表
2. 将where条件应用于1）的工作表，保留满足条件的行
3. GroupBy 将2）的结果分成多个组
4. Having 将条件应用于3）组合的条件过滤，只保留符合要求的组。
5. Order By对结果进行排序。



#### 创建测试用表

```mysql
-- 创建年级表
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`(
	`gradeid` INT(11) NOT NULL AUTO_INCREMENT COMMENT '年级编号',
	`gradename` VARCHAR(50) NOT NULL COMMENT '年级名称',
	PRIMARY KEY (`gradeid`)
) ENGINE=INNODB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8;

-- 创建科目表
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`(
	`subjectno`INT(11) NOT NULL AUTO_INCREMENT COMMENT '课程编号',
    `subjectname` VARCHAR(50) DEFAULT NULL COMMENT '课程名称',
    `classhour` INT(4) DEFAULT NULL COMMENT '学时',
    `gradeid` INT(4) DEFAULT NULL COMMENT '年级编号',
    PRIMARY KEY (`subjectno`)
)ENGINE = INNODB AUTO_INCREMENT = 19 DEFAULT CHARSET = utf8;

-- 创建成绩表
DROP TABLE IF EXISTS `result`;
CREATE TABLE `result`(
	`studentno` INT(4) NOT NULL COMMENT '学号',
    `subjectno` INT(4) NOT NULL COMMENT '课程编号',
    `examdate` DATETIME NOT NULL COMMENT '考试日期',
    `studentresult` INT (4) NOT NULL COMMENT '考试成绩',
    KEY `subjectno` (`subjectno`)
)ENGINE = INNODB DEFAULT CHARSET = utf8;


INSERT INTO `grade`(`GradeID`, `GradeName`)
VALUES (1, '大一'),
       (2, '大二'),
       (3, '大三'),
       (4, '大四'),
       (5, '预科班');
       
INSERT INTO `result`(`StudentNo`, `SubjectNo`, `ExamDate`, `StudentResult`)
VALUES (1000, 1, '2013-11-11 16:00:00', 85),
       (1000, 2, '2013-11-12 16:00:00', 70),
       (1000, 3, '2013-11-11 09:00:00', 68),
       (1000, 4, '2013-11-13 16:00:00', 98),
       (1000, 5, '2013-11-14 16:00:00', 58);
       
       
INSERT INTO `subject`(`SubjectNo`, `SubjectName`, `ClassHour`, `GradeID`)
VALUES (1, '高等数学-1', 110, 1),
       (2, '高等数学-2', 110, 2),
       (3, '高等数学-3', 100, 3),
       (4, '高等数学-4', 130, 4),
       (5, 'C语言-1', 110, 1),
       (6, 'C语言-2', 110, 2),
       (7, 'C语言-3', 100, 3),
       (8, 'C语言-4', 130, 4),
       (9, 'Java程序设计-1', 110, 1),
       (10, 'Java程序设计-2', 110, 2),
       (11, 'Java程序设计-3', 100, 3),
       (12, 'Java程序设计-4', 130, 4),
       (13, '数据库结构-1', 110, 1),
       (14, '数据库结构-2', 110, 2),
       (15, '数据库结构-3', 100, 3),
       (16, '数据库结构-4', 130, 4),
       (17, 'C#基础', 130, 1);
       
       
-- 自连接
CREATE TABLE `category` (
  `categoryid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主题id',
  `pid` INT(10) NOT NULL COMMENT '父id',
  `categoryName` VARCHAR(50) NOT NULL COMMENT '主题名字',
  PRIMARY KEY (`categoryid`)
) ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
INSERT INTO `category`(`categoryid`,`pid`,`categoryName`)
VALUES('2','1','信息技术'),
('3','1','软件开发'),
('4','3','数据库'),
('5','1','美术设计'),
('6','3','web开发'),
('7','5','ps技术'),
('8','2','办公信息');
```

#### 基础查询

通用公式

```mysql
SELECT var1 AS 1,var2 AS 2... FROM tablename AS tn WHERE 条件;
```

##### 查询某表中所有信息

```mysql
SELECT * FROM `name_of_table`;
```

##### 查询某表中指定字段

```mysql
SELECT `name1`,`name2` FROM `name_of_table`;
```

##### 查询某表中指定字段并指定别名

```mysql
SELECT `name1` AS `newname1`,`name2` AS `newname2` FROM `name_of_table` AS `not`;
```

其中`AS`可以省略

##### 拼接字符串

```mysql
CONCAT(str1,str2)
```

##### 去重

```mysql
SELECT DISTINCT `name1`,`name2` FROM `name_of_table`;
```

多列使用也是仅去除所有字段都相同的数据

#### WHERE条件句

例如

```mysql
SELECT `studentno`,`studentname` FROM `student` WHERE studentno BETWEEN 1000 AND 1002;
```

##### LIKE语句

- `%`可以指代任意个字符，`_`仅指代单个字符

例如查询所有刘开头的字段

```mysql
SELECT `name1`,`name2` FROM `name_of_table` WHERE `name2` LIKE '刘%';
```

只有完全匹配表达式才会被查出来

##### IN语句

只有在括号内出现过的才会被查出来

```mysql
SELECT `name1`,`name2` FROM `name_of_table` WHERE `address` IN('北京朝阳');
```



#### 联表查询

![SQL JOINS](https://img-blog.csdnimg.cn/20181103160140252.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2h1YW5nX18y,size_16,color_FFFFFF,t_70)

**注意若存在字段名相同的字段一定要指明该字段来自哪个表**

##### INNER JOIN

取交集

##### LEFT JOIN

左表(FROM 的表)的所有数据都列出来

并将右表(JOIN 的表)所符合 WHERE语句的数据一并列出

不符合WHERE语句的右表字段用NULL填充

##### RIGHT JOIN

右表(JOIN 的表)的所有数据都列出来

并将所符合左表(FROM 的表) WHERE语句的数据一并列出

不符合WHERE语句的左表字段用NULL填充



举例子：

```mysql
建表语句：

CREATE TABLE tbl_dep(id INT(11) NOT NULL AUTO_INCREMENT,
deptName VARCHAR(22) DEFAULT NULL,
addr VARCHAR(22) DEFAULT NULL,
PRIMARY KEY(id)
)ENGINE = INNODB DEFAULT CHARSET=utf8;

CREATE TABLE tbl_emp(
`id` INT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(22) DEFAULT NULL,
`deptId` VARCHAR(22) DEFAULT NULL,
PRIMARY KEY (`id`),
#constraint `fk_deptId` foreign key (`deptId`) references tbl_dep(id);
)engine=innodb default charset =utf8;

插入数据


INSERT INTO tbl_dep(deptName,addr) VALUES('hr','111');
INSERT INTO tbl_dep(deptName,addr) VALUES('bd','112');
INSERT INTO tbl_dep(deptName,addr) VALUES('vb','113');
INSERT INTO tbl_dep(deptName,addr) VALUES('sd','114');
INSERT INTO tbl_dep(deptName,addr) VALUES('yy','115');

INSERT INTO tbl_emp(name,deptId) VALUES('k8',1); 
INSERT INTO tbl_emp(name,deptId) VALUES('k6',2); 
INSERT INTO tbl_emp(name,deptId) VALUES('k4',3); 
INSERT INTO tbl_emp(name,deptId) VALUES('k4',11); 


七种join理论。
内连接(两表的共有部分)
SELECT * FROM tbl_dep d INNER JOIN tbl_emp e ON d.id=e.deptId;

左连接（左表的全部，右表不满足补NULL）
SELECT * FROM tbl_dep d LEFT JOIN tbl_emp e ON d.id=e.deptId;

右连接（右表的全部，左表不满足的补NULL）
SELECT * FROM tbl_dep d RIGHT JOIN tbl_emp e ON d.id=e.deptId;

特殊的左连接，（显示为左表的独有的数据）
说明：查询tbl_dep 表中跟tbl_emp 表无关联关系的数据，即tbl_dep 独占，且tbl_emp 表的显示列补NULL；
SELECT * FROM tbl_dep d LEFT JOIN tbl_emp e ON d.id=e.deptId WHERE e.deptId IS NULL;

特殊的右连接（显示为右表的独有的数据 ）
说明：查询tbl_emp 表中跟tbl_dep 表无关联关系的数据，即tbl_emp 独占，且tbl_dep 表的显示列补NULL；
SELECT * FROM tbl_dep d RIGHT JOIN tbl_emp e ON d.id=e.deptId WHERE d.id IS NULL;

全连接（显示全部数据）（mysql 不支持 full outer join）
UNION ：有去重的功能。
SELECT * FROM tbl_dep d LEFT JOIN tbl_emp e ON d.id=e.deptId UNION
SELECT * FROM tbl_dep d RIGHT JOIN tbl_emp e ON d.id=e.deptId;

显示两表的独有的数据
SELECT * FROM tbl_dep d LEFT JOIN tbl_emp e ON d.id=e.deptId WHERE e.deptId IS NULL UNION
SELECT * FROM tbl_dep d RIGHT JOIN tbl_emp e ON d.id=e.deptId WHERE d.id IS NULL;
```

原文链接：https://blog.csdn.net/huang__2/article/details/83688001



#### 自连接

自己的表和自己的表的字段连接

作用是将一张表拆成两张表，明晰字段间的关联关系

核心操作是将一张表同时取两个别名，并select出不同含义的字段

再将这两个别名以字段的关系关联起来并输出

```mysql
SELECT a.`categoryName` AS '父栏目',b.`categoryName` AS '子栏目'
FROM `category` AS a,`category` AS b
WHERE a.`categoryid` = b.`pid`
-- 当别名a的id等于别名b的父id时输出
```



#### 分页和排序

##### 排序

- ASC 升序
- DESC 降序

使用方法

```mysql
ORDER BY name ASC/DESC
```

##### 分页

将数据分成不同的页进行输出

使用方法

```mysql
LIMIT 起始值，数据个数
```



#### 子查询

在where中嵌套一次查询

使用方法

```mysql
WHERE(SELECT * FROM * WHERE *)
```

即，括号内的查询返回一个结果集合，再利用这个结果集合进行归属判断或者其他判断

如

```mysql
SELECT `StudentNo`,`SubjectNo`,`StudentResult`
FROM `result`
WHERE SubjectNo = (
-- 查询所有数据库结构-1 的学生学号
-- 由里即外
      SELECT SubjectNo
      FROM `subject`
      WHERE SubjectName = '数据库结构-1'
)
ORDER BY StudentResult DESC
```



#### 分组查询

将某个字段相同的数据排在一起，相当于将该字段按字典序排列

使用方法

```mysql
GROUP BY name
```



#### 常用函数

[MySQL :: MySQL 8.0 Reference Manual :: 12.1 Built-In Function and Operator Reference](https://dev.mysql.com/doc/refman/8.0/en/built-in-function-reference.html)



### 事务

**要么都成功，要么都失败**

例子，转账

1. A减少钱
2. B增加钱

这两个操作必须同时成功或同时失败



#### 事务原则

**ACID**

- Atomicity 原子性

  整个事务相当于一个原子，必须保证同时成立

- Consistency 一致性

  针对一个事务操作前的状态与操作后的状态一定相同

- Isolation 隔离性

  多个事务同时进行，不会读到其他事务未提交的数据，否则引起**脏读**

- Durability 持久性

  不会存在事务过程中的状态，要么是事务前的状态，要么是事务后的状态，事务提交即不可逆



#### 脏读

一个事务读取了另一个事务未提交的数据

#### 幻读

一个事务**内**读取到了其他事务插入(修改)的数据，导致前后读取数据不一致

#### 不可重复读

一个事务多次读取的数据不一致(不一定是错误)



#### 测试事务

MySQL是默认动开启事务自动提交的

```mysql
SET autocommit = 1;(默认开启)
SET autocommit = 0;(手动关闭)
```

- 事务开始

  ```mysql
  START TRANSACTION;
  ```

- 提交

  ```mysql
  COMMIT;
  ```

- 回滚

  ```mysql
  ROLLBACK (TO SAVEPOINT name_of_point);
  ```

- 事务结束

  ```mysql
  SET autocommit = 1;
  ```

- 保存点，但是不提交

  ```mysql
  SAVEPOINT name_of_point;
  ```

- 删除保存点

  ```mysql
  RELEASE SAVEPOINT name_of_point;
  ```



##### 样例

```mysql
/*
=============课堂测试题目===========================

A在线买一款价格为500元商品,网上银行转账.
A的银行卡余额为2000,然后给商家B支付500.
商家B一开始的银行卡余额为10000

创建数据库shop和创建表account并插入2条数据
*/

CREATE DATABASE `shop`CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `shop`;

CREATE TABLE `account` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(32) NOT NULL,
    `cash` DECIMAL(9,2) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO account (`name`,`cash`)
VALUES('A',2000.00),('B',10000.00)

-- 转账实现
SET autocommit = 0; -- 关闭自动提交
START TRANSACTION;  -- 开始一个事务,标记事务的起始点
UPDATE account SET cash=cash-500 WHERE `name`='A';
UPDATE account SET cash=cash+500 WHERE `name`='B';
COMMIT; -- 提交事务
# rollback;
SET autocommit = 1; -- 恢复自动提交
```



### 索引

> 索引是帮助MySQL高效获取数据的数据结构。
>
> 索引是数据结构。

[联合索引和组合索引的区别](https://blog.csdn.net/m0_45406092/article/details/111994896)

[CodingLabs - MySQL索引背后的数据结构及算法原理](http://blog.codinglabs.org/articles/theory-of-mysql-index.html)

#### 分类

- 主键索引(PRIMARY KEY)

  表中唯一的标识，不可重复，只能有一个，但是可以多列

- 唯一索引(UNIQUE KEY)

  避免重复的列出现，可以有多个，表示该字段是唯一的

- 常规索引(KEY/INDEX)

  默认存在

- 全文索引(FullText)

  快速定位数据

  ```mysql
  /*
  #方法一：创建表时
    　　CREATE TABLE 表名 (
                 字段名1 数据类型 [完整性约束条件…],
                 字段名2 数据类型 [完整性约束条件…],
                 [UNIQUE | FULLTEXT | SPATIAL ]   INDEX | KEY
                 [索引名] (字段名[(长度)] [ASC |DESC])
                 );
  
  
  #方法二：CREATE在已存在的表上创建索引
         CREATE [UNIQUE | FULLTEXT | SPATIAL ] INDEX 索引名
                      ON 表名 (字段名[(长度)] [ASC |DESC]) ;
  
  
  #方法三：ALTER TABLE在已存在的表上创建索引
         ALTER TABLE 表名 ADD [UNIQUE | FULLTEXT | SPATIAL ] INDEX
                              索引名 (字段名[(长度)] [ASC |DESC]) ;
                             
                             
  #删除索引：DROP INDEX 索引名 ON 表名字;
  #删除主键索引: ALTER TABLE 表名 DROP PRIMARY KEY;
  
  
  #显示索引信息: SHOW INDEX FROM student;
  */
  
  /*增加全文索引*/
  ALTER TABLE `school`.`student` ADD FULLTEXT INDEX `studentname` (`StudentName`);
  
  /*EXPLAIN : 分析SQL语句执行性能*/
  EXPLAIN SELECT * FROM student WHERE studentno='1000';
  
  /*使用全文索引*/
  -- 全文搜索通过 MATCH() 函数完成。
  -- 搜索字符串作为 against() 的参数被给定。搜索以忽略字母大小写的方式执行。对于表中的每个记录行，MATCH() 返回一个相关性值。即，在搜索字符串与记录行在 MATCH() 列表中指定的列的文本之间的相似性尺度。
  EXPLAIN SELECT *FROM student WHERE MATCH(studentname) AGAINST('love');
  
  /*
  开始之前，先说一下全文索引的版本、存储引擎、数据类型的支持情况
  
  MySQL 5.6 以前的版本，只有 MyISAM 存储引擎支持全文索引；
  MySQL 5.6 及以后的版本，MyISAM 和 InnoDB 存储引擎均支持全文索引;
  只有字段的数据类型为 char、varchar、text 及其系列才可以建全文索引。
  测试或使用全文索引时，要先看一下自己的 MySQL 版本、存储引擎和数据类型是否支持全文索引。
  */
  ```



#### 联合索引

TODO



#### 索引原则

- 不是越多越好
- 不要对经常变动的数据加索引
- 小数据表不要加索引
- 常用查询不常修改的表可以加索引

