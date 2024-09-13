create table resources (
    id int unsigned not null auto_increment comment 'ID',
    rs_type tinyint not null comment '资源来源类型',
    rs_mime_type tinyint not null default 0 comment '资源文件类型',
    rs_uri varchar(256) null comment '资源路径',
    rs_store_uri varchar(256) not null comment '资源存储路径',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) default charset utf8mb4 comment '资源表';

create table rs_aweme (
    id int unsigned not null auto_increment comment 'ID',
    rs_id int unsigned not null comment '资源id',
    aweme_id varchar(32) not null comment '短视频id',
    aw_title varchar(256) not null comment '标题',
    aw_create_time bigint unsigned not null comment '抖音创建时间',
    aw_st_admire_count int unsigned null comment '欣赏数',
    aw_st_comment_count int unsigned null comment '评论数',
    aw_st_digg_count int unsigned null comment '点赞数',
    aw_st_collect_count int unsigned null comment '收藏数',
    aw_st_play_count int unsigned null comment '播放数',
    aw_st_share_count int unsigned null comment '分享数',
    aw_v_play_url varchar(256) not null comment '播放地址',
    aw_v_play_uri varchar(128) not null comment '播放id',
    aw_v_play_size int unsigned not null comment '视频大小',
    aw_cover_url varchar(256) not null comment '封面地址',
    aw_au_uid varchar(32) not null comment '作者uid',
    aw_au_nickname varchar(32) not null comment '作者昵称',
    aw_au_sec_uid varchar(128) not null comment '作者加密uid',
    is_top tinyint not null default 0 comment '是否置顶',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) default charset utf8mb4 comment '抖音短视频';

create table rs_author (
   id int unsigned not null auto_increment comment 'ID',
   uid varchar(64) not null comment '平台用户id',
   rs_type tinyint not null comment '资源来源类型',
   nick_name varchar(64) not null comment '昵称',
   description varchar(256) comment '描述',
   avatar_url varchar(256) comment '头像地址',
   primary key (id)
) default charset utf8mb4 comment '资源作者';

create table users (
   id int unsigned not null auto_increment comment 'ID',
   nickname varchar(32) not null comment '昵称',
   email varchar(64) not null comment '邮箱',
   password varchar(64) not null comment '密码MD5',
   child_gender tinyint not null comment '孩子性别',
   child_year int unsigned not null comment '孩子出生年份',
   child_month int unsigned not null comment '孩子出生月份',
   create_time timestamp not null default current_timestamp comment '创建时间',
   update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
   primary key (id),
   unique key uniq_email(email)
) default charset utf8mb4 comment '用户表';

create table verify_code (
    id int unsigned not null auto_increment comment 'ID',
    email varchar(64) not null comment '邮箱',
    verify_code varchar(12) not null comment '验证码',
    expired tinyint not null default 0 comment '是否失效，0-否，1-是',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key (id),
    key idx_email_time(email, create_time)
) default charset utf8mb4 comment '验证码表';

create table rs_collect (
    id int unsigned not null auto_increment comment 'ID',
    user_id int unsigned not null comment '用户id',
    rs_id int unsigned not null comment '资源id',
    collect_status tinyint not null default 0 comment '收藏状态，0-收藏，1-取消收藏',
    collect_time timestamp not null default current_timestamp comment '收藏时间',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_collect(user_id, rs_id)
) default charset utf8mb4 comment '资源收藏表';

create table rs_author_follow (
      id int unsigned not null auto_increment comment 'ID',
      user_id int unsigned not null comment '用户id',
      author_id int unsigned not null comment '资源id',
      follow_status tinyint not null default 0 comment '关注状态，0-关注，1-取消关注',
      follow_time timestamp not null default current_timestamp comment '关注时间',
      create_time timestamp not null default current_timestamp comment '创建时间',
      update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
      primary key (id),
      unique key uniq_collect(user_id, author_id)
) default charset utf8mb4 comment '资源收藏表';

create table rs_history (
    id int unsigned not null auto_increment comment 'ID',
    user_id int unsigned not null comment '用户id',
    rs_id int unsigned not null comment '资源id',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key (id)
) default charset utf8mb4 comment '资源历史表';

create table rs_search_history (
    id int unsigned not null auto_increment comment 'ID',
    user_id int unsigned not null comment '用户id',
    keywords varchar not null comment '关键字',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key (id)
) default charset utf8mb4 comment '资源历史表';

create table file
(
    id    int unsigned auto_increment comment '主键' primary key,
    public tinyint not null default 0 comment '是否公有',
    user_id    int unsigned null comment '用户id',
    file_name  varchar(50)  default ''                not null comment '文件名',
    store_type tinyint not null default 0 comment '存储类型',
    path       varchar(200) default ''                not null comment '文件存储路径',
    is_deleted tinyint      default 0                 not null comment '是否删除',
    created_at datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '文件表' charset = utf8mb4;

create table feedback
(
    id    int unsigned auto_increment comment '主键' primary key,
    user_id    int unsigned not null comment '用户id',
    feedback  text             not null comment '文件名',
    file_ids       varchar(200) null comment '文件ID',
    created_at datetime     default CURRENT_TIMESTAMP not null comment '创建时间'
) comment '反馈表' charset = utf8mb4;