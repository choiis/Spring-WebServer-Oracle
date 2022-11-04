create user insung identified by 12345678;

grant connect, resource, dba to insung;

CREATE table insung.SM01(
userid varchar2(10) not null,
passwd varchar2(30) not null,
username varchar2(20) not null,
brth varchar2(8) not null,
grade number(1) default 4 not null,
regdate varchar2(8) not null,
email varchar2(30) not null,
usertype number(1) default 4 not null
);

alter table insung.SM01
add constraint pk_SM01 primary key(userid);

CREATE index insung.idx_SM01_1
on insung.SM01(username);

CREATE index insung.idx_SM01_2
on insung.SM01(brth);

CREATE index insung.idx_SM01_3
on insung.SM01(regdate);

CREATE TABLE insung.SMP1(
userid varchar2(10) not null,
regdate varchar2(8) not null,
photo blob not null
);

ALTER TABLE insung.SMP1
add constraint pk_SMP1 primary key(userid);

alter table insung.SMP1
add constraint fk_SMP1 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE table insung.SMI1 (
userid varchar2(10) not null,
infocode number(2) not null,
pfnum varchar2(5) not null,
pcnum varchar2(5) not null,
pbnum varchar2(5) not null,
regdate varchar2(8) not null
);

ALTER TABLE insung.SMI1
add constraint pk_SMI1 primary key(userid,infocode);

alter table insung.SMI1
add constraint fk_SMI1 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SMI1_1
on insung.SMI1(pbnum,pcnum,pfnum);

CREATE table insung.SME1 (
userid varchar2(10) not null,
regdate varchar2(8) not NULL,
insertid varchar2(10) not null 
);

ALTER TABLE insung.SME1
add constraint pk_SME1 primary key(userid);

alter table insung.SME1
add constraint fk_SME1 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE table insung.MENU (
  menucd varchar2(2) not null,
  menunm varchar2(50) not null,
  menuurl varchar2(50) not null,
  authlevel number(1) default 4 not null,
  reguser varchar2(20) not null,
  regdate varchar2(8) not null,
  moduser varchar2(20) not null,
  moddate varchar2(8) not null
);


alter table insung.MENU
add constraint pk_MENU primary key(menucd);

insert into insung.MENU values('01','코드관리','/comm/code/page',1,'admin','20180901','admin','20210725');
insert into insung.MENU values('02','공지사항','/comm/command/page',2,'admin','20180901','admin','20210725');
insert into insung.MENU values('03','회원관리','/sm01/page',2,'admin','20180901','admin','20210725');
insert into insung.MENU values('04','노래 동영상','/sb01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('05','파일 게시판','/sf01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('06','투표 게시판','/sv01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('07','맛집 추천','/sr01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('08','나의 메모장','/sm02/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('09','유저 검색','/smi1/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('10','채팅방','/comm/chat/page',4,'admin','20210105','admin','20210725');

CREATE table insung.CODE_GRP (
codegrp varchar2(5) not null,
codegrpnm varchar2(60) not null,
username varchar2(20) not null,
regdate varchar2(8) not null
);

alter table insung.CODE_GRP
add constraint pk_CODE_GRP primary key(codegrp);

CREATE table insung.CODE (
codecd varchar2(10) not null,
codenm varchar2(40) not null,
codegrp varchar2(5) not null,
username varchar2(20) not null,
regdate varchar2(8) not null
);

alter table insung.CODE
add constraint pk_CODE primary key(codegrp,codecd);

alter table insung.CODE
add constraint fk_code foreign key(codegrp) references insung.CODE_GRP(codegrp) on delete cascade;


insert into insung.CODE_GRP values('U001','유저등급코드','admin','20180901');
insert into insung.CODE_GRP values('P001','판매상태코드','admin','20180901');
insert into insung.CODE_GRP values('P002','판매상품코드','admin','20180901');

insert into insung.CODE values('01','관리자','U001','admin','20180901');
insert into insung.CODE values('02','특별회원','U001','admin','20180901');
insert into insung.CODE values('03','우수회원','U001','admin','20180901');
insert into insung.CODE values('04','사용자','U001','admin','20180901');

insert into insung.CODE values('01','판매대기','P001','admin','20180901');
insert into insung.CODE values('02','판매신청','P001','admin','20180901');
insert into insung.CODE values('03','판매완료','P001','admin','20180901');

insert into insung.CODE values('01','음반','P002','admin','20180901');
insert into insung.CODE values('02','악보','P002','admin','20180901');
insert into insung.CODE values('03','악기','P002','admin','20180901');
insert into insung.CODE values('04','티켓','P002','admin','20180901');
insert into insung.CODE values('05','파일','P002','admin','20180901');
insert into insung.CODE values('06','기타','P002','admin','20180901');

CREATE SEQUENCE insung.seq_SM02
START WITH 1 INCREMENT BY 1 ;


CREATE table insung.SM02(
seq number not null,
userid varchar2(10) not null,
title varchar2(20) not null,
text varchar2(200) not null,
regdate varchar2(20) not null
);

alter table insung.SM02
add constraint pk_SM02 primary key(seq);

alter table insung.SM02
add constraint fk_sm02 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SM02_1
on insung.SM02(userid, regdate);

CREATE SEQUENCE insung.seq_SB01
START WITH 1 INCREMENT BY 1 ;

create table insung.SB01(
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null ,
  good number(4) default 0 not null ,
  videobool number default 0 not null
);

alter table insung.SB01
add constraint pk_SB01 primary key(seq);

alter table insung.SB01
add constraint fk_sb01 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SB01_1
on insung.SB01(title, regdate desc);

CREATE index insung.idx_SB01_2
on insung.SB01(userid, regdate desc);

create table insung.SBV1(
  seq number not null,
  regdate varchar2(8) not null,
  videopath varchar2(40) not null
);

alter table insung.SBV1
add constraint pk_SBV1 primary key(seq);

alter table insung.SBV1
add constraint fk_SBV1 foreign key(seq) references insung.SB01(seq) on delete cascade;

CREATE SEQUENCE insung.seq_SB02
START WITH 1 INCREMENT BY 1 ;

create table insung.SB02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table insung.SB02
add constraint pk_SB02 primary key(seq);

alter table insung.SB02
add constraint fk_sb02 foreign key(seq01) references insung.SB01(seq) on delete cascade;

CREATE index insung.idx_SB02_1
on insung.SB02(seq01, parents, seq);

create table insung.SBG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table insung.SBG1
add constraint pk_SBG1 primary key(seq,sessionid);

alter table insung.SBG1
add constraint fk_sbg1 foreign key(seq) references insung.SB01(seq) on delete cascade;

CREATE SEQUENCE insung.seq_SF01
START WITH 1 INCREMENT BY 1 ;

create table insung.SF01(
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

alter table insung.SF01
add constraint pk_SF01 primary key(seq);

alter table insung.SF01
add constraint fk_sf01 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SF01_1
on insung.SF01(title , regdate desc);

CREATE index insung.idx_SF01_2
on insung.SF01(userid , regdate desc);

CREATE SEQUENCE insung.seq_SF02
START WITH 1 INCREMENT BY 1 ;

create table insung.SF02(
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table insung.SF02
add constraint pk_SF02 primary key(seq);

alter table insung.SF02
add constraint fk_sf02 foreign key(seq01) references insung.SF01(seq) on delete cascade;

CREATE index insung.idx_SF02_1
on insung.SF02(seq01, parents, seq);

create table insung.SFG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table insung.SFG1
add constraint pk_SFG1 primary key(seq,sessionid);

alter table insung.SFG1
add constraint fk_sfg1 foreign key(seq) references insung.SF01(seq) on delete cascade;

create table insung.SFD1(
  seq number not null,
  userid varchar2(20) not null,
  downuserid varchar2(20) not null,
  regdate varchar2(20) not null
);

alter table insung.SFD1
add constraint pk_SFD1 primary key(seq, userid, downuserid);

alter table insung.SFD1
add constraint fk_sfd1 foreign key(seq) references insung.SF01(seq) on delete cascade;

CREATE index insung.idx_SFD1_1
on insung.SFD1(downuserid);

CREATE SEQUENCE insung.seq_SV01
START WITH 1 INCREMENT BY 1 ;

create table insung.SV01(
  seq number not null,
  title varchar2(20) not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null,
  multiselect number  default 0 not null,
  good number(4) default 0 not null
);

alter table insung.SV01
add constraint pk_SV01 primary key(seq);

alter table insung.SV01
add constraint fk_sv01 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SV01_1
on insung.SV01(title , regdate desc);

CREATE index insung.idx_SV01_2
on insung.SV01(userid , regdate desc);

create table insung.SV02(
  seq number not null,
  idx number not null,
  content varchar2(40) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null
);

alter table insung.SV02
add constraint pk_SV02 primary key(seq, idx);

alter table insung.SV02
add constraint fk_sv02 foreign key(seq) references insung.SV01(seq) on delete cascade;

create table insung.SV03(
  seq number not null,
  idx number not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null
);

alter table insung.SV03
add constraint pk_SV03 primary key(seq, idx, userid);

alter table insung.SV03
add constraint fk_sv03 foreign key(seq, idx) references insung.SV02(seq, idx) on delete cascade;

CREATE SEQUENCE insung.seq_SV04
START WITH 1 INCREMENT BY 1 ;

create table insung.SV04 (
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table insung.SV04
add constraint SV04 primary key(seq);

alter table insung.SV04
add constraint fk_SV04 foreign key(seq01) references insung.SV01(seq) on delete cascade;

CREATE index insung.idx_SV04_1
on insung.SV04(seq01, parents, seq);

create table insung.SVG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table insung.SVG1
add constraint pk_SVG1 primary key(seq,sessionid);

alter table insung.SVG1
add constraint fk_svg1 foreign key(seq) references insung.SV01(seq) on delete cascade;

CREATE SEQUENCE insung.seq_SR01
START WITH 1 INCREMENT BY 1;

create table insung.SR01 (
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

alter table insung.SR01
add constraint pk_SR01 primary key(seq);

alter table insung.SR01
add constraint fk_sr01 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SR01_1
on insung.SR01(title , regdate desc);

CREATE index insung.idx_SR01_2
on insung.SR01(userid , regdate desc);

create table insung.SR02 (
  seq number not null,
  userid varchar2(20) not null,
  grade number(1) not null,
  regdate varchar2(20) not null
);

alter table insung.SR02
add constraint pk_SR02 primary key(seq, userid);

alter table insung.SR02
add constraint fk_sr02 foreign key(seq) references insung.SR01(seq) on delete cascade;

CREATE SEQUENCE insung.seq_SR03
START WITH 1 INCREMENT BY 1 ;

create table insung.SR03 (
  seq number not null,
  seq01 number not null,
  text varchar2(200) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  good number(4) default 0 not null,
  parents number default 0
);

alter table insung.SR03
add constraint SR03 primary key(seq);

alter table insung.SR03
add constraint fk_SR03 foreign key(seq01) references insung.SR01(seq) on delete cascade;

CREATE index insung.idx_SR03_1
on insung.SR03(seq01, parents, seq);

CREATE TABLE insung.SRP1 (
  seq number not null,
  idx number not null,
  regdate varchar2(8) not null,
  photopath varchar2(40) not null
);

alter table insung.SRP1
add constraint pk_SRP1 primary key(seq,idx);

alter table insung.SRP1
add constraint fk_SRP1 foreign key(seq) references insung.SR01(seq) on delete cascade;

create table insung.SRG1(
  seq number not null,
  sessionid varchar2(20) not null,
  datelog varchar2(20) not null,
  goodlog varchar2(3),
  hatelog varchar2(3)
);

alter table insung.SRG1
add constraint pk_SRG1 primary key(seq,sessionid);

alter table insung.SRG1
add constraint fk_sg1 foreign key(seq) references insung.SR01(seq) on delete cascade;