CREATE table SM01(
userid varchar2(10) not null,
passwd varchar2(30) not null,
username varchar2(20) not null,
brth varchar2(8) not null,
grade number(1) default 4 not null,
regdate varchar2(8) not null,
email varchar2(30) not null,
usertype number(1) default 4 not null
);

alter table SM01
add constraint pk_SM01 primary key(userid);

CREATE index idx_SM01_1
on SM01(username);

CREATE index idx_SM01_2
on SM01(brth);

CREATE TABLE SMP1(
userid varchar2(10) not null,
regdate varchar2(8) not null,
photo blob not null
);

ALTER TABLE SMP1
add constraint pk_SMP1 primary key(userid);

alter table SMP1
add constraint fk_SMP1 foreign key(userid) references SM01(userid) on delete cascade;

CREATE table SMI1 (
userid varchar2(10) not null,
infocode number(2) not null,
pfnum varchar2(5) not null,
pcnum varchar2(5) not null,
pbnum varchar2(5) not null,
regdate varchar2(8) not null
);

ALTER TABLE SMI1
add constraint pk_SMI1 primary key(userid,infocode);

alter table SMI1
add constraint fk_SMI1 foreign key(userid) references SM01(userid) on delete cascade;

CREATE index idx_SMI1_1
on SMI1(pbnum,pcnum,pfnum);

CREATE table SME1 (
userid varchar2(10) not null,
regdate varchar2(8) not NULL,
insertid varchar2(10) not null 
);

ALTER TABLE SME1
add constraint pk_SME1 primary key(userid);

alter table SME1
add constraint fk_SME1 foreign key(userid) references SM01(userid) on delete cascade;

CREATE table MENU (
  menucd varchar2(2) not null,
  menunm varchar2(20) not null,
  menuurl varchar2(20) not null,
  authlevel number(1) default 4 not null,
  reguser varchar2(20) not null,
  regdate varchar2(8) not null,
  moduser varchar2(20) not null,
  moddate varchar2(8) not null
);


alter table MENU
add constraint pk_MENU primary key(menucd);

insert into MENU values('01','코드관리','/codepage',1,'admin','20180901','admin','20180901');
insert into MENU values('02','공지사항','/commandpage',2,'admin','20180901','admin','20180901');
insert into MENU values('03','회원관리','/sm01page',2,'admin','20180901','admin','20180901');
insert into MENU values('04','노래 동영상','/sb01page',4,'admin','20180901','admin','20180901');
insert into MENU values('05','파일 게시판','/sf01page',4,'admin','20180901','admin','20180901');
insert into MENU values('06','투표 게시판','/sv01page',4,'admin','20180901','admin','20180901');
insert into MENU values('07','맛집 추천','/sr01page',4,'admin','20180901','admin','20180901');
insert into MENU values('08','나의 메모장','/sm02page',4,'admin','20180901','admin','20180901');
insert into MENU values('09','유저 검색','/smi1Page',4,'admin','20180901','admin','20180901');
insert into MENU values('10','채팅방','/chatting',4,'admin','20210105','admin','20210105');

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


insert into CODE_GRP values('U001','유저등급코드','admin','20180901');
insert into CODE_GRP values('P001','판매상태코드','admin','20180901');
insert into CODE_GRP values('P002','판매상품코드','admin','20180901');

insert into CODE values('01','관리자','U001','admin','20180901');
insert into CODE values('02','특별회원','U001','admin','20180901');
insert into CODE values('03','우수회원','U001','admin','20180901');
insert into CODE values('04','사용자','U001','admin','20180901');

insert into CODE values('01','판매대기','P001','admin','20180901');
insert into CODE values('02','판매신청','P001','admin','20180901');
insert into CODE values('03','판매완료','P001','admin','20180901');

insert into CODE values('01','음반','P002','admin','20180901');
insert into CODE values('02','악보','P002','admin','20180901');
insert into CODE values('03','악기','P002','admin','20180901');
insert into CODE values('04','티켓','P002','admin','20180901');
insert into CODE values('05','파일','P002','admin','20180901');
insert into CODE values('06','기타','P002','admin','20180901');

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
  videobool number default 0 not null
);

alter table SB01
add constraint pk_SB01 primary key(seq);

alter table SB01
add constraint fk_sb01 foreign key(userid) references SM01(userid) on delete cascade;

CREATE index idx_SB01_1
on SB01(title, regdate desc);

CREATE index idx_SB01_2
on SB01(userid, regdate desc);

create table SBV1(
  seq number not null,
  regdate varchar2(8) not null,
  videopath varchar2(40) not null
);

alter table SBV1
add constraint pk_SBV1 primary key(seq);

alter table SBV1
add constraint fk_SBV1 foreign key(seq) references SB01(seq) on delete cascade;

CREATE SEQUENCE seq_SB02
START WITH 1 INCREMENT BY 1 ;

create table SB02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table SB02
add constraint pk_SB02 primary key(seq);

alter table SB02
add constraint fk_sb02 foreign key(seq01) references SB01(seq) on delete cascade;

CREATE index idx_SB02_1
on SB02(seq01, parents, seq);

create table SBG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table SBG1
add constraint pk_SBG1 primary key(seq,sessionid);

alter table SBG1
add constraint fk_sbg1 foreign key(seq) references SB01(seq) on delete cascade;

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
  ftpfilename varchar2(100) not null
);

alter table SF01
add constraint pk_SF01 primary key(seq);

alter table SF01
add constraint fk_sf01 foreign key(userid) references SM01(userid) on delete cascade;

CREATE index idx_SF01_1
on SF01(title , regdate desc);

CREATE index idx_SF01_2
on SF01(userid , regdate desc);

CREATE SEQUENCE seq_SF02
START WITH 1 INCREMENT BY 1 ;

create table SF02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table SF02
add constraint pk_SF02 primary key(seq);

alter table SF02
add constraint fk_sf02 foreign key(seq01) references SF01(seq) on delete cascade;

CREATE index idx_SF02_1
on SF02(seq01, parents, seq);

create table SFG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table SFG1
add constraint pk_SFG1 primary key(seq,sessionid);

alter table SFG1
add constraint fk_sfg1 foreign key(seq) references SF01(seq) on delete cascade;

CREATE SEQUENCE seq_SV01
START WITH 1 INCREMENT BY 1 ;

create table SV01(
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null,
  multiselect number  default 0 not null,
  good number(4) default 0 not null
);

alter table SV01
add constraint pk_SV01 primary key(seq);

alter table SV01
add constraint fk_sv01 foreign key(userid) references SM01(userid) on delete cascade;

CREATE index idx_SV01_1
on SV01(title , regdate desc);

CREATE index idx_SV01_2
on SV01(userid , regdate desc);

create table SV02(
  seq number not null,
  idx number not null,
  content varchar2(40) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null
);

alter table SV02
add constraint pk_SV02 primary key(seq, idx);

alter table SV02
add constraint fk_sv02 foreign key(seq) references SV01(seq) on delete cascade;

create table SV03(
  seq number not null,
  idx number not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null
);

alter table SV03
add constraint pk_SV03 primary key(seq, idx, userid);

alter table SV03
add constraint fk_sv03 foreign key(seq, idx) references SV02(seq, idx) on delete cascade;

CREATE SEQUENCE seq_SV04
START WITH 1 INCREMENT BY 1 ;

create table SV04 (
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table SV04
add constraint SV04 primary key(seq);

alter table SV04
add constraint fk_SV04 foreign key(seq01) references SV01(seq) on delete cascade;

CREATE index idx_SV04_1
on SV04(seq01, parents, seq);

create table SVG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table SVG1
add constraint pk_SVG1 primary key(seq,sessionid);

alter table SVG1
add constraint fk_svg1 foreign key(seq) references SV01(seq) on delete cascade;

CREATE SEQUENCE seq_SR01
START WITH 1 INCREMENT BY 1;

create table SR01 (
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null,
  markertitle varchar2(40) not null,
  mapx number  not NULL,
  mapy number  not NULL,
  good number(4) default 0 not null
);

alter table SR01
add constraint pk_SR01 primary key(seq);

alter table SR01
add constraint fk_sr01 foreign key(userid) references SM01(userid) on delete cascade;

CREATE index idx_SR01_1
on SR01(title , regdate desc);

CREATE index idx_SR01_2
on SR01(userid , regdate desc);

create table SR02 (
  seq number not null,
  userid varchar2(20) not null,
  grade number(1) not null,
  regdate varchar2(20) not null
);

alter table SR02
add constraint pk_SR02 primary key(seq, userid);

alter table SR02
add constraint fk_sr02 foreign key(seq) references SR01(seq) on delete cascade;

CREATE SEQUENCE seq_SR03
START WITH 1 INCREMENT BY 1 ;

create table SR03 (
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table SR03
add constraint SR03 primary key(seq);

alter table SR03
add constraint fk_SR03 foreign key(seq01) references SR01(seq) on delete cascade;

CREATE index idx_SR03_1
on SR03(seq01, parents, seq);

CREATE TABLE SRP1 (
  seq number not null,
  idx number not null,
  regdate varchar2(8) not null,
  photopath varchar2(40) not null
);

alter table SRP1
add constraint pk_SRP1 primary key(seq,idx);

alter table SRP1
add constraint fk_SRP1 foreign key(seq) references SR01(seq) on delete cascade;

create table SRG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table SRG1
add constraint pk_SRG1 primary key(seq,sessionid);

alter table SRG1
add constraint fk_sg1 foreign key(seq) references SR01(seq) on delete cascade;
