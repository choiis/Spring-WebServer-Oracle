CREATE table SM01(
userid varchar2(10) not null,
passwd varchar2(30) not null,
adminyn varchar2(5) not null,
username varchar2(20) not null,
brth varchar2(8) not null,
grade varchar2(2) not null,
regdate varchar2(8) not null,
phone varchar2(15) not null,
email varchar2(30) not null,
video blob,
usertype varchar2(5)
);

alter table SM01
add constraint pk_SM01 primary key(userid);

CREATE table MENU (
  menucd varchar2(2) not null,
  menunm varchar2(20) not null,
  menuurl varchar2(20) not null,
  authlevel varchar2(2) not null,
  reguser varchar2(20) not null,
  regdate varchar2(8) not null,
  moduser varchar2(20) not null,
  moddate varchar2(8) not null
);


alter table MENU
add constraint pk_MENU primary key(menucd);

insert into MENU values('01','코드관리','/common/code.do','01','admin','20180901','admin','20180901')
insert into MENU values('02','공지사항','/common/command.do','02','admin','20180901','admin','20180901')
insert into MENU values('03','회원관리','/sm01.do','02','admin','20180901','admin','20180901')
insert into MENU values('04','노래 동영상','/common/sb01.do','04','admin','20180901','admin','20180901')
insert into MENU values('05','파일 게시판','/common/sf01.do','04','admin','20180901','admin','20180901')
insert into MENU values('06','상품 거래','/common/sp01.do','04','admin','20180901','admin','20180901')
insert into MENU values('07','나의 메모장','/common/sm02.do','04','admin','20180901','admin','20180901')

CREATE table CODE_GRP (
codegrp varchar2(5) not null,
codegrpnm varchar2(20) not null,
username varchar2(20) not null,
regdate varchar2(8) not null
);

alter table CODE_GRP
add constraint pk_CODE_GRP primary key(codegrp);

CREATE table CODE (
codecd varchar2(10) not null,
codenm varchar2(20) not null,
codegrp varchar2(5) not null,
username varchar2(20) not null,
regdate varchar2(8) not null
);

alter table CODE
add constraint pk_CODE primary key(codegrp,codecd);

alter table CODE
add constraint fk_code foreign key(codegrp) references CODE_GRP(codegrp) on delete cascade;


insert into CODE_GRP values('U001','유저등급코드','admin','20180901')
insert into CODE_GRP values('P001','판매상태코드','admin','20180901')
insert into CODE_GRP values('P002','판매상품코드','admin','20180901')

insert into CODE values('01','관리자','U001','admin','20180901')
insert into CODE values('02','특별회원','U001','admin','20180901')
insert into CODE values('03','우수회원','U001','admin','20180901')
insert into CODE values('04','사용자','U001','admin','20180901')

insert into CODE values('01','판매대기','P001','admin','20180901')
insert into CODE values('02','판매신청','P001','admin','20180901')
insert into CODE values('03','판매완료','P001','admin','20180901')

insert into CODE values('01','음반','P002','admin','20180901')
insert into CODE values('02','악보','P002','admin','20180901')
insert into CODE values('03','악기','P002','admin','20180901')
insert into CODE values('04','티켓','P002','admin','20180901')
insert into CODE values('05','파일','P002','admin','20180901')
insert into CODE values('06','기타','P002','admin','20180901')

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

alter table SM02
add constraint fk_sm02 foreign key(userid) references SM01(userid) on delete cascade;

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
add constraint pk_SB01 primary key(seq)

alter table SB01
add constraint fk_sb01 foreign key(userid) references SM01(userid) on delete cascade;

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

alter table SB02
add constraint fk_sb02 foreign key(seq01) references SB01(seq) on delete cascade;

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
add constraint pk_SF01 primary key(seq)

alter table SF01
add constraint fk_sf01 foreign key(userid) references SM01(userid) on delete cascade;

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

alter table SF02
add constraint fk_sf02 foreign key(seq01) references SF01(seq) on delete cascade;

CREATE index idx_SF02_1
on SF02(seq01, regdate);

create table SG01(
  btype varchar2(10) not null,
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3)  ,
  hatelog varchar2(3) 
);

alter table SG01
add constraint pk_SG01 primary key(btype,seq,sessionid)

CREATE SEQUENCE seq_SP01 
START WITH 1 INCREMENT BY 1 ;

create table SP01(
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  state varchar2(5) not null,
  ptype varchar2(5) not null,
  hit number(4)  default 0 not null ,
  good number(4) default 0 not null ,
  explain blob
);

alter table SP01
add constraint pk_SP01 primary key(seq)

alter table SP01
add constraint fk_sp01 foreign key(userid) references SM01(userid) on delete cascade;

CREATE index idx_SP01_1
on SP01(seq, regdate);

CREATE index idx_SP01_2
on SP01(title);

CREATE index idx_SP01_3
on SP01(userid);

CREATE SEQUENCE seq_SP02 
START WITH 1 INCREMENT BY 1 ;

create table SP02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null
);

alter table SP02
add constraint pk_SP02 primary key(seq, seq01)

alter table SP02
add constraint fk_sp02 foreign key(seq01) references SP01(seq) on delete cascade;

CREATE index idx_SP02_1
on SB02(seq01, regdate);

create table SP03(
  registerid varchar2(20) not null,
  seq number not null,
  regdate varchar2(20) not null
);

alter table SP03
add constraint pk_SP03 primary key(registerid, seq)

alter table SP03
add constraint fk_sp03 foreign key(seq) references SP01(seq) on delete cascade;
