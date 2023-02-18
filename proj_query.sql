create database projdb;
use projdb;
create table contestant (
	walletAddress varchar(42) not null,
    rewardBalance double,
    unique(walletAddress),
    primary key (walletAddress));

create table contest (
	walletAddress varchar(42) not null,
    title varchar(100) not null,
    startDate date not null,
    endDate date not null,
    status varchar(20) not null,
    sponsorFee double not null,
    requirements varchar(1000) not null,
    unique (walletAddress),
    primary key (walletAddress));
    
create table submission (
	contestantWallet varchar(42) not null,
    contestWallet varchar(42) not null,
	primary key (contestantWallet, contestWallet),
    foreign key (contestantWallet) references contestant (walletAddress),
    foreign key (contestWallet) references contest (walletAddress));
    
create table sponsor (
	walletAddress varchar(42) not null,
    address varchar(200) not null,
    companyName varchar(100) not null,
    email varchar(100) not null,
    primary key (walletAddress));
    
create table judge (
	walletAddress varchar(42) not null,
    rewardBalance double,
    primary key (walletAddress));
    
create table review (
	judgeWallet varchar(42) not null,
	sponsorWallet varchar(42) not null,
    score int not null,
    comment varchar(1000),
    unique (judgeWallet, sponsorWallet),
    foreign key (judgeWallet) references judge (walletAddress),
    foreign key (sponsorWallet) references sponsor (walletAddress),
    primary key (judgeWallet, sponsorWallet));
    
create table grade (
	contestantWallet varchar(42) not null,
    contestWallet varchar(42) not null,
    judgeWallet varchar(42) not null,
    grade int not null,
	primary key (contestantWallet, contestWallet, judgeWallet),
    foreign key (contestantWallet) references contestant (walletAddress),
    foreign key (contestWallet) references contest (walletAddress),
    foreign key (judgeWallet) references judge (walletAddress));
	
