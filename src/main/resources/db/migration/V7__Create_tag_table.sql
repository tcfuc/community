create table tag
(
    id           int identity (1,1) primary key not null,
    account_id   varchar(100),
    tag         varchar(50),
    tag_type     int,
    gmt_create   bigint,
    gmt_modified bigint
);