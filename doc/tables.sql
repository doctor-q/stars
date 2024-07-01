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