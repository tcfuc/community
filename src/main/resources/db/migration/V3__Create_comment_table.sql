create table comment
(
	id bigint auto_increment primary key,
	parent_id bigint,
	type int,
	commentator varchar(100),
	content varchar(1024),
	comment_count int default 0,
	gmt_modified bigint,
	gmt_create bigint,
	like_count bigint default 0
);