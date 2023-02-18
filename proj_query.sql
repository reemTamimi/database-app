create database projdb;
use projdb;
create table contestant (
	walletAddress varchar(40) not null,
    rewardBalance double,
    unique(walletAddress),
    primary key (walletAddress));

create table contest (
	walletAddress varchar(40) not null,
    title varchar(100) not null,
    startDate date not null,
    endDate date not null,
    status varchar(20) not null,
    sponsorFee double not null,
    requirements varchar(1000) not null,
    unique (walletAddress),
    primary key (walletAddress));
    
create table submission (
	contestantWallet varchar(40) not null,
    contestWallet varchar(40) not null,
	primary key (contestantWallet, contestWallet),
    foreign key (contestantWallet) references contestant (walletAddress),
    foreign key (contestWallet) references contest (walletAddress));
    
