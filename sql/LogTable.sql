
CREATE table log_login(
userid varchar2(10) not null,
logintime varchar2(20) not null,
browser varchar2(20) not null,
device varchar2(20) not null,
ip varchar2(30)
)
partition by range (logintime)
(
  partition log_logintime_part1 values less than ('20190101'),
  partition log_logintime_part2 values less than ('20200101'),
  partition log_logintime_part3 values less than ('20210101')
);

CREATE index idx_log_login_1
on log_login(logintime);

CREATE index idx_log_login_2
on log_login(userid);

CREATE table log_error (
erroruri varchar2(30) not null,
errortime varchar2(20) not null,
errormsg varchar2(300) not null
)
partition by range (errortime)
(
  partition log_error_part1 values less than ('20190101'),
  partition log_error_part2 values less than ('20200101'),
  partition log_error_part3 values less than ('20210101')
);

CREATE index idx_log_error_1
on log_error(errortime);

CREATE table log_click_board (
logtime varchar2(20) not null,
sessionid varchar2(10) not null,
boardtype varchar2(5) not null,
boardnumber varchar2(5) not null
);

CREATE index idx_log_click_board_1
on log_click_board(logtime);

CREATE table log_click_goods (
logtime varchar2(20) not null,
sessionid varchar2(10) not null,
seqtype varchar2(5) not null,
seqnumber varchar2(5) not NULL,
likehate varchar2(5) not NULL
);

CREATE index idx_log_click_goods_1
on log_click_goods(logtime);