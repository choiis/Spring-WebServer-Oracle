create user insung identified by 12345678;

grant connect, resource, dba to insung;

CREATE table insung.SM01(
userid varchar2(15) not null,
passwd varchar2(40) not null,
username varchar2(60) not null,
brth varchar2(8) not null,
grade number(1) default 4 not null,
regdate varchar2(8) not null,
email varchar2(100) not null,
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


INSERT INTO insung.SM01 (
  userid, passwd, username, brth, grade, regdate, email, usertype
) VALUES (
  'admin',
  'admin1234',
  '관리자',
  '19900101',
  1,
  '20251129',
  'admin@example.com',
  1
);

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
  menuurl varchar2(150) not null,
  authlevel number(1) default 4 not null,
  reguser varchar2(20) not null,
  regdate varchar2(8) not null,
  moduser varchar2(20) not null,
  moddate varchar2(8) not null
);


alter table insung.MENU
add constraint pk_MENU primary key(menucd);

insert into insung.MENU values('01','code manage','/comm/code/page',1,'admin','20180901','admin','20210725');
insert into insung.MENU values('02','announce','/comm/command/page',2,'admin','20180901','admin','20210725');
insert into insung.MENU values('03','accout manage','/sm01/page',2,'admin','20180901','admin','20210725');
insert into insung.MENU values('04','video board','/sb01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('05','file board','/sf01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('06','vote board','/sv01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('07','restaurant board','/sr01/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('08','my memo','/sm02/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('09','user search','/smi1/page',4,'admin','20180901','admin','20210725');
insert into insung.MENU values('10','chatting room','/comm/chat/page',4,'admin','20210105','admin','20210725');

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

insert into insung.CODE values('01','admin','U001','admin','20180901');
insert into insung.CODE values('02','special','U001','admin','20180901');
insert into insung.CODE values('03','grade','U001','admin','20180901');
insert into insung.CODE values('04','normal','U001','admin','20180901');

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
START WITH 101
  INCREMENT BY 1;


CREATE table insung.SM02(
seq number not null,
userid varchar2(10) not null,
title varchar2(50) not null,
text varchar2(500) not null,
regdate varchar2(20) not null
);

alter table insung.SM02
add constraint pk_SM02 primary key(seq);

alter table insung.SM02
add constraint fk_sm02 foreign key(userid) references insung.SM01(userid) on delete cascade;

CREATE index insung.idx_SM02_1
on insung.SM02(userid, regdate);

CREATE SEQUENCE insung.seq_SB01
START WITH 21
  INCREMENT BY 1;

create table insung.SB01(
  seq number not null,
  title varchar2(50) not null,
  text varchar2(500) not null,
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
START WITH 201
  INCREMENT BY 1;


create table insung.SB02(
  seq number not null,
  seq01 number not null,
  text varchar2(500) not null,
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
START WITH 21
  INCREMENT BY 1;

create table insung.SF01(
  seq number not null,
  title varchar2(50) not null,
  text varchar2(500) not null,
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
START WITH 201
  INCREMENT BY 1;

create table insung.SF02(
  seq number not null,
  seq01 number not null,
  text varchar2(500) not null,
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
START WITH 21
  INCREMENT BY 1;

create table insung.SV01(
  seq number not null,
  title varchar2(50) not null,
  text varchar2(500) not null,
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
  START WITH 201
  INCREMENT BY 1;


create table insung.SV04 (
  seq number not null,
  seq01 number not null,
  text varchar2(500) not null,
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
  START WITH 21
  INCREMENT BY 1;

create table insung.SR01 (
  seq number not null,
  title varchar2(50) not null,
  text varchar2(500) not null,
  userid varchar2(20) not null,
  regdate varchar2(20) not null,
  hit number(4)  default 0 not null,
  markertitle varchar2(100) not null,
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
  START WITH 201
  INCREMENT BY 1;

create table insung.SR03 (
  seq number not null,
  seq01 number not null,
  text varchar2(500) not null,
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


------------------------
----------- data init
------------------------

INSERT INTO insung.SM01 (userid, passwd, username, brth, grade, regdate, email, usertype)
VALUES ('user01', 'pass01', '사용자01', '19900101', 4, '20250101', 'user01@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user02', 'pass02', '사용자02', '19900202', 3, '20250102', 'user02@example.com', 3);
INSERT INTO insung.SM01 VALUES ('user03', 'pass03', '사용자03', '19900303', 2, '20250103', 'user03@example.com', 2);
INSERT INTO insung.SM01 VALUES ('user04', 'pass04', '사용자04', '19900404', 1, '20250104', 'user04@example.com', 1);
INSERT INTO insung.SM01 VALUES ('user05', 'pass05', '사용자05', '19900505', 4, '20250105', 'user05@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user06', 'pass06', '사용자06', '19900606', 4, '20250106', 'user06@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user07', 'pass07', '사용자07', '19900707', 3, '20250107', 'user07@example.com', 3);
INSERT INTO insung.SM01 VALUES ('user08', 'pass08', '사용자08', '19900808', 2, '20250108', 'user08@example.com', 2);
INSERT INTO insung.SM01 VALUES ('user09', 'pass09', '사용자09', '19900909', 1, '20250109', 'user09@example.com', 1);
INSERT INTO insung.SM01 VALUES ('user10', 'pass10', '사용자10', '19901010', 4, '20250110', 'user10@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user11', 'pass11', '사용자11', '19901111', 4, '20250111', 'user11@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user12', 'pass12', '사용자12', '19901212', 3, '20250112', 'user12@example.com', 3);
INSERT INTO insung.SM01 VALUES ('user13', 'pass13', '사용자13', '19910113', 2, '20250113', 'user13@example.com', 2);
INSERT INTO insung.SM01 VALUES ('user14', 'pass14', '사용자14', '19910214', 1, '20250114', 'user14@example.com', 1);
INSERT INTO insung.SM01 VALUES ('user15', 'pass15', '사용자15', '19910315', 4, '20250115', 'user15@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user16', 'pass16', '사용자16', '19910416', 4, '20250116', 'user16@example.com', 4);
INSERT INTO insung.SM01 VALUES ('user17', 'pass17', '사용자17', '19910517', 3, '20250117', 'user17@example.com', 3);
INSERT INTO insung.SM01 VALUES ('user18', 'pass18', '사용자18', '19910618', 2, '20250118', 'user18@example.com', 2);
INSERT INTO insung.SM01 VALUES ('user19', 'pass19', '사용자19', '19910719', 1, '20250119', 'user19@example.com', 1);

COMMIT;

BEGIN
  FOR r IN (SELECT userid FROM insung.SM01) LOOP
    INSERT INTO insung.SMP1 (userid, regdate, photo)
    VALUES (r.userid, '20250201', empty_blob());
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_infocode NUMBER := 1;
BEGIN
  FOR r IN (SELECT userid FROM insung.SM01 ORDER BY userid) LOOP
    INSERT INTO insung.SMI1 (userid, infocode, pfnum, pcnum, pbnum, regdate)
    VALUES (
      r.userid,
      v_infocode,
      LPAD(1000 + v_infocode, 4, '0'),
      '0001',
      '0001',
      '20250201'
    );
    v_infocode := v_infocode + 1;
    IF v_infocode > 99 THEN
      v_infocode := 1;
    END IF;
  END LOOP;
END;
/
COMMIT;

BEGIN
  FOR r IN (SELECT userid FROM insung.SM01 ORDER BY userid) LOOP
    INSERT INTO insung.SME1 (userid, regdate, insertid)
    VALUES (r.userid, '20250201', 'admin');
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_rn   NUMBER := 0;
  v_cnt  NUMBER;
BEGIN
  FOR r IN (SELECT userid FROM insung.SM01 ORDER BY userid) LOOP
    v_rn := v_rn + 1;
    v_cnt := 3 + MOD(v_rn, 5);
    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SM02 (seq, userid, title, text, regdate)
      VALUES (
        insung.seq_SM02.NEXTVAL,
        r.userid,
        'MEMO-' || TO_CHAR(i),
        r.userid || ' 메모 ' || TO_CHAR(i),
        '202503' || LPAD(MOD(i, 28) + 1, 2, '0')
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_userid  VARCHAR2(10);
BEGIN
  FOR i IN 1..20 LOOP
    SELECT userid INTO v_userid
    FROM (
      SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
      FROM insung.SM01
    )
    WHERE rn = MOD(i-1, 20) + 1;

    INSERT INTO insung.SB01 (seq, title, text, userid, regdate, hit, good, videobool)
    VALUES (
      insung.seq_SB01.NEXTVAL,
      'SB01-' || LPAD(i,2,'0'),
      '동영상 게시글 ' || TO_CHAR(i) || ' 내용입니다.',
      v_userid,
      '202504' || LPAD(MOD(i, 28) + 1, 2, '0'),
      5 * i,
      MOD(i, 5),
      CASE WHEN MOD(i, 2) = 0 THEN 1 ELSE 0 END
    );
  END LOOP;
END;
/
COMMIT;

BEGIN
  FOR r IN (SELECT seq, regdate FROM insung.SB01 WHERE videobool = 1) LOOP
    INSERT INTO insung.SBV1 (seq, regdate, videopath)
    VALUES (r.seq, r.regdate, '/video/sb01_' || LPAD(r.seq, 3, '0') || '.mp4');
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt     NUMBER;
  v_userid  VARCHAR2(10);
BEGIN
  FOR r IN (SELECT seq FROM insung.SB01 ORDER BY seq) LOOP
    v_cnt := 5 + MOD(r.seq, 11); -- 5 ~ 15
    IF v_cnt > 15 THEN v_cnt := 15; END IF;

    FOR i IN 1..v_cnt LOOP
      SELECT userid INTO v_userid
      FROM (
        SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
        FROM insung.SM01
      )
      WHERE rn = MOD(i-1, 20) + 1;

      INSERT INTO insung.SB02 (seq, seq01, text, userid, regdate, good, parents)
      VALUES (
        insung.seq_SB02.NEXTVAL,
        r.seq,
        'SB01-' || r.seq || ' 댓글 ' || TO_CHAR(i),
        v_userid,
        '202505' || LPAD(MOD(i, 28) + 1, 2, '0'),
        MOD(i, 3),
        0
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt NUMBER;
BEGIN
  FOR r IN (SELECT seq FROM insung.SB01 ORDER BY seq) LOOP
    v_cnt := 2 + MOD(r.seq, 3); -- 2 ~ 4
    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SBG1 (seq, sessionid, datelog, goodlog, hatelog)
      VALUES (
        r.seq,
        'SBSESS' || TO_CHAR(r.seq) || '_' || TO_CHAR(i),
        '202506' || LPAD(MOD(i, 28) + 1, 2, '0'),
        CASE WHEN MOD(i,2)=1 THEN 'Y' ELSE NULL END,
        CASE WHEN MOD(i,2)=0 THEN 'Y' ELSE NULL END
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_userid VARCHAR2(10);
BEGIN
  FOR i IN 1..20 LOOP
    SELECT userid INTO v_userid
    FROM (
      SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
      FROM insung.SM01
    )
    WHERE rn = MOD(i-1, 20) + 1;

    INSERT INTO insung.SF01 (seq, title, text, userid, regdate, hit, good, filename, ftpfilename)
    VALUES (
      insung.seq_SF01.NEXTVAL,
      'SF01-' || LPAD(i,2,'0'),
      '파일 게시글 ' || TO_CHAR(i) || ' 내용입니다.',
      v_userid,
      '202507' || LPAD(MOD(i, 28) + 1, 2, '0'),
      3 * i,
      MOD(i, 5),
      'file' || TO_CHAR(i) || '.dat',
      'file' || TO_CHAR(i) || '_ftp.dat'
    );
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt     NUMBER;
  v_userid  VARCHAR2(10);
BEGIN
  FOR r IN (SELECT seq FROM insung.SF01 ORDER BY seq) LOOP
    v_cnt := 5 + MOD(r.seq, 11);
    IF v_cnt > 15 THEN v_cnt := 15; END IF;

    FOR i IN 1..v_cnt LOOP
      SELECT userid INTO v_userid
      FROM (
        SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
        FROM insung.SM01
      )
      WHERE rn = MOD(i-1, 20) + 1;

      INSERT INTO insung.SF02 (seq, seq01, text, userid, regdate, good, parents)
      VALUES (
        insung.seq_SF02.NEXTVAL,
        r.seq,
        'SF01-' || r.seq || ' 댓글 ' || TO_CHAR(i),
        v_userid,
        '202508' || LPAD(MOD(i, 28) + 1, 2, '0'),
        MOD(i, 3),
        0
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt NUMBER;
BEGIN
  FOR r IN (SELECT seq FROM insung.SF01 ORDER BY seq) LOOP
    v_cnt := 2 + MOD(r.seq, 3);
    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SFG1 (seq, sessionid, datelog, goodlog, hatelog)
      VALUES (
        r.seq,
        'SFSESS' || TO_CHAR(r.seq) || '_' || TO_CHAR(i),
        '202509' || LPAD(MOD(i, 28) + 1, 2, '0'),
        CASE WHEN MOD(i,2)=1 THEN 'Y' ELSE NULL END,
        CASE WHEN MOD(i,2)=0 THEN 'Y' ELSE NULL END
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_userid VARCHAR2(10);
BEGIN
  FOR i IN 1..20 LOOP
    SELECT userid INTO v_userid
    FROM (
      SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
      FROM insung.SM01
    )
    WHERE rn = MOD(i-1, 20) + 1;

    INSERT INTO insung.SV01 (seq, title, text, userid, regdate, hit, multiselect, good)
    VALUES (
      insung.seq_SV01.NEXTVAL,
      'SV01-' || LPAD(i,2,'0'),
      '투표 ' || TO_CHAR(i) || '의 내용입니다.',
      v_userid,
      '202510' || LPAD(MOD(i, 28) + 1, 2, '0'),
      4 * i,
      CASE WHEN MOD(i,2)=0 THEN 1 ELSE 0 END,
      MOD(i, 5)
    );
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt NUMBER;
BEGIN
  FOR r IN (SELECT seq, title FROM insung.SV01 ORDER BY seq) LOOP
    v_cnt := 3 + MOD(r.seq, 4); -- 3~6
    IF v_cnt > 6 THEN v_cnt := 6; END IF;

    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SV02 (seq, idx, content, userid, regdate)
      VALUES (
        r.seq,
        i,
        r.title || ' 선택지 ' || TO_CHAR(i),
        'admin',
        '202511' || LPAD(MOD(i, 28) + 1, 2, '0')
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

BEGIN
  FOR p IN (SELECT seq FROM insung.SV01 ORDER BY seq) LOOP
    FOR u IN (SELECT userid FROM insung.SM01 ORDER BY userid) LOOP
      INSERT INTO insung.SV03 (seq, idx, userid, regdate)
      VALUES (
        p.seq,
        1,
        u.userid,
        '20251201'
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt     NUMBER;
  v_userid  VARCHAR2(10);
BEGIN
  FOR r IN (SELECT seq FROM insung.SV01 ORDER BY seq) LOOP
    v_cnt := 5 + MOD(r.seq, 11);
    IF v_cnt > 15 THEN v_cnt := 15; END IF;

    FOR i IN 1..v_cnt LOOP
      SELECT userid INTO v_userid
      FROM (
        SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
        FROM insung.SM01
      )
      WHERE rn = MOD(i-1, 20) + 1;

      INSERT INTO insung.SV04 (seq, seq01, text, userid, regdate, good, parents)
      VALUES (
        insung.seq_SV04.NEXTVAL,
        r.seq,
        'SV01-' || r.seq || ' 댓글 ' || TO_CHAR(i),
        v_userid,
        '202512' || LPAD(MOD(i, 28) + 1, 2, '0'),
        MOD(i, 3),
        0
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt NUMBER;
BEGIN
  FOR r IN (SELECT seq FROM insung.SV01 ORDER BY seq) LOOP
    v_cnt := 2 + MOD(r.seq, 3);
    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SVG1 (seq, sessionid, datelog, goodlog, hatelog)
      VALUES (
        r.seq,
        'SVSESS' || TO_CHAR(r.seq) || '_' || TO_CHAR(i),
        '202501' || LPAD(MOD(i, 28) + 1, 2, '0'),
        CASE WHEN MOD(i,2)=1 THEN 'Y' ELSE NULL END,
        CASE WHEN MOD(i,2)=0 THEN 'Y' ELSE NULL END
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_userid VARCHAR2(10);
BEGIN
  FOR i IN 1..20 LOOP
    SELECT userid INTO v_userid
    FROM (
      SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
      FROM insung.SM01
    )
    WHERE rn = MOD(i-1, 20) + 1;

    INSERT INTO insung.SR01 (seq, title, text, userid, regdate, hit, markertitle, mapx, mapy, good)
    VALUES (
      insung.seq_SR01.NEXTVAL,
      'SR01-' || LPAD(i,2,'0'),
      '맛집 ' || TO_CHAR(i) || ' 소개입니다.',
      v_userid,
      '202502' || LPAD(MOD(i, 28) + 1, 2, '0'),
      4 * i,
      'SRMARK-' || LPAD(i,2,'0'),
      120 + i * 0.01,
      35 + i * 0.01,
      MOD(i, 5)
    );
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_seq    insung.SR01.seq%TYPE;
  v_i      NUMBER := 0;
  v_total  NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_total FROM insung.SR01;

  IF v_total = 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'SR01 has no data');
  END IF;

  FOR u IN (SELECT userid FROM insung.SM01 ORDER BY userid) LOOP
    v_i := v_i + 1;

    SELECT seq
      INTO v_seq
      FROM (
        SELECT seq,
               ROW_NUMBER() OVER (ORDER BY seq) AS rn
        FROM insung.SR01
      )
     WHERE rn = MOD(v_i-1, v_total) + 1;

    INSERT INTO insung.SR02 (seq, userid, grade, regdate)
    VALUES (
      v_seq,
      u.userid,
      3 + MOD(v_i, 3),
      '202503' || LPAD(MOD(v_i, 28) + 1, 2, '0')
    );
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt     NUMBER;
  v_userid  VARCHAR2(10);
BEGIN
  FOR r IN (SELECT seq FROM insung.SR01 ORDER BY seq) LOOP
    v_cnt := 5 + MOD(r.seq, 11);
    IF v_cnt > 15 THEN v_cnt := 15; END IF;

    FOR i IN 1..v_cnt LOOP
      SELECT userid INTO v_userid
      FROM (
        SELECT userid, ROW_NUMBER() OVER (ORDER BY userid) rn
        FROM insung.SM01
      )
      WHERE rn = MOD(i-1, 20) + 1;

      INSERT INTO insung.SR03 (seq, seq01, text, userid, regdate, good, parents)
      VALUES (
        insung.seq_SR03.NEXTVAL,
        r.seq,
        'SR01-' || r.seq || ' 댓글 ' || TO_CHAR(i),
        v_userid,
        '202504' || LPAD(MOD(i, 28) + 1, 2, '0'),
        MOD(i, 3),
        0
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt NUMBER;
BEGIN
  FOR r IN (SELECT seq FROM insung.SR01 ORDER BY seq) LOOP
    v_cnt := 1 + MOD(r.seq, 3); -- 1~3
    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SRP1 (seq, idx, regdate, photopath)
      VALUES (
        r.seq,
        i,
        '202505' || LPAD(MOD(i, 28) + 1, 2, '0'),
        '/photo/sr01_' || LPAD(r.seq,3,'0') || '_' || TO_CHAR(i) || '.jpg'
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;

DECLARE
  v_cnt NUMBER;
BEGIN
  FOR r IN (SELECT seq FROM insung.SR01 ORDER BY seq) LOOP
    v_cnt := 2 + MOD(r.seq, 3);
    FOR i IN 1..v_cnt LOOP
      INSERT INTO insung.SRG1 (seq, sessionid, datelog, goodlog, hatelog)
      VALUES (
        r.seq,
        'SRSESS' || TO_CHAR(r.seq) || '_' || TO_CHAR(i),
        '202506' || LPAD(MOD(i, 28) + 1, 2, '0'),
        CASE WHEN MOD(i,2)=1 THEN 'Y' ELSE NULL END,
        CASE WHEN MOD(i,2)=0 THEN 'Y' ELSE NULL END
      );
    END LOOP;
  END LOOP;
END;
/
COMMIT;
