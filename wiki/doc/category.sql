drop table if exists `test`;
create table `test`
(
    `id`   bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    `password` varchar (50) comment '密码',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='测试';

insert into `test` (id,name,password) values (1,'测试','password');

drop table if exists `demo`;
create table `demo`
(
    `id`   bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='测试';

insert into `demo` (id,name) values (1,'测试');

#分类
drop table if exists `category`;
create table `category`(
                        `id` bigint not null comment 'id',
                        `parent` bigint not null default 0 comment '父id',
                        `name` varchar(50) comment '名称',
                        `sort` int comment '顺序',
                        primary key (`id`)
)engine = innodb default charset =utf8mb4 comment ='分类';

insert into `category` (id,parent,name,sort)
values (100,000,'前端开发',100);
insert into `category` (id,parent,name,sort)
values (101,100,'Vue',101);
insert into `category` (id,parent,name,sort)
values (102,100,'HTML&CSS',102);
insert into `category` (id,parent,name,sort)
values (200,000,'Java',200);
insert into `category` (id,parent,name,sort)
values (201,200,'基础应用',201);
insert into `category` (id,parent,name,sort)
values (202,200,'框架应用',202);