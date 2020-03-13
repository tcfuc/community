alter table QUESTION drop column ID;
alter table QUESTION add ID int IDENTITY (1,1) PRIMARY KEY;