CREATE table SM01(
userid varchar2(10) not null,
passwd varchar2(10) not null,
adminyn varchar2(5) not null,
username varchar2(20) not null,
brth varchar2(8) not null,
grade varchar2(2) not null,
regdate varchar2(8) not null,
phone varchar2(15) not null,
email varchar2(30) not null,
video blob
);

alter table SM01
add constraint pk_SM01 primary key(userid);

CREATE table SL01(
userid varchar2(10) not null,
logintime varchar2(20) not null,
browser varchar2(20) not null,
url varchar2(30) 
);

alter table SL01
add constraint pk_SL01 primary key(userid,logintime);

CREATE SEQUENCE seq_SM02
START WITH 1 INCREMENT BY 1 ;


CREATE table SM02(
seq number not null,
userid varchar2(10) not null,
title varchar2(20) not null,
text varchar2(200) not null,
regdate varchar2(20) not null
);
alter table SM02
add constraint pk_SM02 primary key(seq);

CREATE index idx_SM02_1
on SM02(userid, regdate);

CREATE SEQUENCE seq_SB01 
START WITH 1 INCREMENT BY 1 ;

create table SB01(
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null ,
  good number(4) default 0 not null ,
  video blob
);

alter table SB01
add constraint pk_SB01 primary key(seq,title)

CREATE index idx_SB01_1
on SB01(seq, regdate);

CREATE index idx_SB01_2
on SB01(title);

CREATE index idx_SB01_3
on SB01(userid);



CREATE SEQUENCE seq_SB02 
START WITH 1 INCREMENT BY 1 ;

create table SB02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null
);

alter table SB02
add constraint pk_SB02 primary key(seq, seq01)

CREATE index idx_SB02_1
on SB02(seq01, regdate);


CREATE SEQUENCE seq_SF01 
START WITH 1 INCREMENT BY 1 ;

create table SF01(
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null ,
  good number(4) default 0 not null ,
  filename varchar2(100) not null,
  fileblob blob
);

alter table SF01
add constraint pk_SF01 primary key(seq,title)

CREATE index idx_SF01_1
on SF01(seq, regdate);

CREATE index idx_SF01_2
on SF01(title);

CREATE index idx_SF01_3
on SF01(userid);


CREATE SEQUENCE seq_SF02 
START WITH 1 INCREMENT BY 1 ;

create table SF02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null
);

alter table SF02
add constraint pk_SF02 primary key(seq, seq01)

CREATE index idx_SF02_1
on SF02(seq01, regdate);