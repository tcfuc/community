alter table question drop id;
alter table question add id int identity (1,1) primary key;