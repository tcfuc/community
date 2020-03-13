create table comment
(
	id BIGINT auto_increment,
	parent_id BIGINT,
	type int,
	commentator int,
	content varchar(1024),
	comment_count int default 0,
	gmt_modified BIGINT,
	gmt_create BIGINT,
	like_count BIGINT default 0,
	constraint comment_pk
		primary key (id)
);