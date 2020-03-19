create table question
(
	id int,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified bigint,
	creator VARCHAR(100),
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);