create database projdb;
use projdb;

############
# ENTITIES #
############
create table contestant (
	walletAddress varchar(42) not null,
	rewardBalance double,
    check (regexp_like (walletAddress, '^(0x[A-F[:digit:]]{40})$')),
    check (rewardBalance >= 0),
    unique(walletAddress),
    primary key (walletAddress));

create table contest (
	walletAddress varchar(42) not null,
    title varchar(100) not null,
    startDate date not null,
    endDate date not null,
    contestStatus varchar(20) not null,
    sponsorFee double not null,
    requirements varchar(1000) not null,
    check (regexp_like (walletAddress, '^([0xA-F[:digit:]]{40})$')),
    check (contestStatus in ('created','opened','closed','past')),
    check (sponsorFee >= 0),
    check (datediff(endDate,startDate) >= 1),
    unique (walletAddress),
    primary key (walletAddress));
    
create table sponsor (
	walletAddress varchar(42) not null,
    address varchar(200) not null,
    companyName varchar(100) not null,
    email varchar(100) not null,
    check (regexp_like (walletAddress, '^([0xA-F[:digit:]]{40})$')),
    check (regexp_like (email, '.+@.+')),
    primary key (walletAddress));
    
create table judge (
	walletAddress varchar(42) not null,
    rewardBalance double,
    check (regexp_like (walletAddress, '^([0xA-F[:digit:]]{40})$')),
    check (rewardBalance >= 0),
    primary key (walletAddress));
    
create table submission (
	contestantWallet varchar(42) not null,
    contestWallet varchar(42) not null,
	primary key (contestantWallet, contestWallet),
    foreign key (contestantWallet) references contestant (walletAddress),
    foreign key (contestWallet) references contest (walletAddress));
    
create table user (
	walletAddress varchar(42) not null,
    pass varchar(20) not null,
    userRole varchar(20) not null,
    check (userRole in ('root','sponsor','judge','contestant')),
    primary key (walletAddress));

#################
# RELATIONSHIPS #
#################
create table review (
	judgeWallet varchar(42) not null,
	sponsorWallet varchar(42) not null,
    score int not null,
    reviewComment varchar(1000),
    check (score >= 0 and score <= 10),
    unique (sponsorWallet),
    foreign key (judgeWallet) references judge (walletAddress),
    foreign key (sponsorWallet) references sponsor (walletAddress),
    primary key (sponsorWallet));
    
create table submissionGrade (
	contestantWallet varchar(42) not null,
    contestWallet varchar(42) not null,
    judgeWallet varchar(42) not null,
    grade int not null,
    check (grade >= 0 and grade <= 100),
	primary key (contestantWallet, contestWallet, judgeWallet),
    foreign key (contestantWallet) references contestant (walletAddress),
    foreign key (contestWallet) references contest (walletAddress),
    foreign key (judgeWallet) references judge (walletAddress));
    
create table contestJudge (
	contestWallet varchar(42) not null,
	judgeWallet varchar(42) not null,
	foreign key (contestWallet) references contest (walletAddress),
	foreign key (judgeWallet) references judge (walletAddress),
    unique (contestWallet, judgeWallet),
    primary key (contestWallet, judgeWallet));
    
create table contestSponsor (
	contestWallet varchar(42) not null,
    sponsorWallet varchar(42) not null,
    unique (contestWallet),
	foreign key (contestWallet) references contest (walletAddress),
	foreign key (sponsorWallet) references sponsor (walletAddress),
    primary key (contestWallet));
    
#######################
# TRIGGER CONSTRAINTS #
#######################
delimiter $$
create trigger numJudges before insert on contestJudge
	for each row
    begin
		if (select count(judgeWallet) from contestJudge where contestWallet = new.contestWallet) >= 10
        then set new.contestWallet = null;
        end if;
	end$$
delimiter ;
    
