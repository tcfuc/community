create table user
(
    id           int identity (1,1) primary key not null,
    account_id   varchar(100),
    name         varchar(50),
    token        char(36),
    gmt_create   bigint,
    gmt_modified bigint,
    avatar_url   varchar(100),
    bio          varchar(256)
);