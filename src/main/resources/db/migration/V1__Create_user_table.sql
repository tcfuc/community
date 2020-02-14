
create table USER
(
    ID           INT identity(1,1) primary key not null ,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    AVATAR_URL   VARCHAR(100)
);