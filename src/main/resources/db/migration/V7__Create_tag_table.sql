create table TAG
(
    ID           INT identity (1,1) primary key not null,
    ACCOUNT_ID   VARCHAR(100),
    TAG         VARCHAR(50),
    TAG_TYPE     INT,
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT
);