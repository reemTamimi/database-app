use projdb;
show tables; 

select * from contestSponsor;

insert into contestSponsor (contestWallet,sponsorWallet) values ('0x000000000000000000000000000000000000111A','0x000000000000000000000000000000000000001C');
insert into contestant (walletAddress,rewardBalance) values ('0x000000000000000000000000000000000000011A',100);
insert into contestant (walletAddress,rewardBalance) values ('0x000000000000000000000000000000000000012A',100);
insert into review (judgeWallet,sponsorWallet,score,reviewComment) 
	values ('0x00000000000000000000000000000000000000AD','0x00000000000000000000000000000000000000CC',10,'Comment12');

# 2. big sponsors; to test, sponsor 1C has 2 contests now
select sponsorWallet from contestSponsor group by sponsorWallet order by count(contestWallet) desc;

# 2. BIG SPONSORS WITH TIES
select a.sponsorWallet from 
(select sponsorWallet,count(contestWallet) as countContest from contestSponsor group by sponsorWallet order by countContest) as a
inner join
(select distinct sponsorWallet,count(contestWallet) as countContest from contestSponsor group by sponsorWallet order by countContest desc limit 1) as b
on a.countContest = b.countContest;

# these next 2 queries are sample inserts to test; disregard
insert into contest (walletAddress, title, startDate, endDate, contestStatus, sponsorFee, requirements)
values ('0x000000000000000000000000000000000000011A','Contest11', '2023-03-19', '2023-04-28', 'opened', 10000, 'Req11');

insert into contestSponsor (contestWallet, sponsorWallet)
values ('0x000000000000000000000000000000000000011A','0x000000000000000000000000000000000000001C');

# 3. top judges;
select judgeWallet, avg(score) as avgscore from review group by judgewallet order by avg(score) desc;

# 3. TOP JUDGES WITH TIES
select a.judgeWallet from
(select judgeWallet,avg(score) as ascore from review group by judgeWallet order by ascore) as a 
inner join
(select distinct judgeWallet, avg(score) as ascore from review group by judgeWallet order by ascore desc limit 1) as b 
on a.ascore = b.ascore;

select distinct judgeWallet, avg(score) as ascore from review group by judgeWallet order by ascore desc limit 1;
select judgeWallet,avg(score) as ascore from review a;

# 4. best contestants; we probably have to do some java logic to display 1 or the tie value
select walletAddress from contestant a 
join (select distinct rewardBalance from contestant order by rewardBalance desc limit 1) b
on a.rewardBalance = b.rewardBalance;

# 6. sleepy contestants
select u.walletAddress from users u where userRole = "contestant" and u.walletAddress not in 
(select s.contestantWallet from submission s);

# 7. busy judges??? I have to actually test it
select j.judgeWallet from contestJudge j where j.contestWallet in 
(select c.walletAddress from contest c where contestStatus = "past");

# 8. tough contests
select s.contestWallet from submission s group by s.contestWallet having count(s.contestantWallet) < 10 and s.contestWallet in 
(select c.walletAddress from contest c where c.contestStatus = "past");

# 9. copy cats
select contestantWallet from
(select contestantWallet, count(contestWallet) as cnt from submission where contestWallet in
(select contestWallet from submission where contestantWallet = '0x000000000000000000000000000000000000002A')
group by contestantWallet)
where cnt in
(select count(contestWallet) from submission where contestantWallet = '0x000000000000000000000000000000000000002A' group by contestantWallet);


# this query worked to fix the timezone issue; idk how lol
set global time_zone = '+3:00';
