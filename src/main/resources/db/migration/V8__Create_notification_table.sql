create table notification
(
	id bigint auto_increment,
	notifier varchar (100),
	receiver varchar (100),
	outerid bigint,
	type int,
	gmt_create bigint,
	status int default 0,
	constraint notification_pk
		primary key (id)
);