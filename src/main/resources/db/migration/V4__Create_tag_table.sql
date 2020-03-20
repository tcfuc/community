create table tag
    (
        id           bigint auto_increment primary key,
        account_id   varchar(100),
        tag           varchar(50),
        tag_type     int,
        gmt_create   bigint,
        gmt_modified bigint
    );