create table comment
(
	id bigint auto_increment,
	parent_id bigint,
	type int,
	commentator int,
	content varchar(1024),
	comment_count int default 0,
	gmt_modified bigint,
	gmt_create bigint,
	like_count bigint default 0,
	constraint comment_pk
		primary key (id)
);