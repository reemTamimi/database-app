use projdb;

############
# ENTITIES #
############
insert into contestant(walletAddress, rewardBalance)
	values ('0x000000000000000000000000000000000000001A',0),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();
    
insert into contest(walletAddress, title, startDate, endDate, contestStatus, sponsorFee, requirements)
	values ('0x000000000000000000000000000000000000001B', 'Contest1', '02-19-2023', '02-28-2023', 'opened', 1000, 'Req1'),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();
    
insert into sponsor(walletAddress, address, companyName, email)
	values ('0x000000000000000000000000000000000000001C', '1 Main St.', 'Main St. Co.', 'mainco@mainco.com'),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();
    
insert into judge(walletAddress, rewardBalance)
	values ('0x000000000000000000000000000000000000001D', 100),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();

insert into submission(contestantWallet, contestWallet)
	values ('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000001B'),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();

#################
# RELATIONSHIPS #
#################
insert into review(judgeWallet, sponsorWallet, score, reviewComment)
	values ('0x000000000000000000000000000000000000001D','0x000000000000000000000000000000000000001C', 7, 'Comment1'),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();
    
insert into submissionGrade(contestantWallet, contestWallet, judgeWallet, grade)
	values ('0x000000000000000000000000000000000000001A','0x000000000000000000000000000000000000001B','0x000000000000000000000000000000000000001D', 80),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();
    
insert into contestJudge(contestWallet, judgeWallet)
	values ('0x000000000000000000000000000000000000001B','0x000000000000000000000000000000000000001D'),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();
    
insert into contestSponsor(contestWallet, sponsorWallet)
	values ('0x000000000000000000000000000000000000001B','0x000000000000000000000000000000000000001C'),
			(),
            (),
            (),
            (),
            (),
            (),
            (),
            (),
            ();