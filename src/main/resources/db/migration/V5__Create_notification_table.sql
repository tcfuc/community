create table notification
(
	id bigint auto_increment primary key,
	notifier varchar (100),
	receiver varchar (100),
	outerid bigint,
	type int,
	gmt_create bigint,
	status int default 0
);