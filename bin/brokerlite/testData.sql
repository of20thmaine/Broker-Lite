delete from people;
delete from broker;
delete from credentials;
delete from client;
delete from stock;

insert into people values ('12345', 'Smith', 'John');
insert into people values ('45623', 'Watson', 'Jeff');
insert into people values ('63453', 'Holmes', 'Sherlock');
insert into people values ('34958', 'Shakur', 'Tupac');
insert into people values ('12356', 'Trump', 'Donald');
insert into people values ('23498', 'Clinton', 'Hilary');
insert into people values ('10235', 'Pann', 'Sam');
insert into people values ('45862', 'Doe', 'John');
insert into people values ('09876', 'Temple', 'Shirley');
insert into people values ('32340', 'Einstein', 'Albert');

insert into broker values ('12345','Smith','John','7532 Rockledge St.','6041157544');
insert into broker values ('45623','Watson','Jeff','169 Miller Drive','6219384428');
insert into broker values ('63453','Holmes','Sherlock','51 San Carlos Dr.','5182176089');

insert into client values ('34958','Shakur','Tupac','7482 Bowman St.','5115001990');
insert into client values ('12356','Trump','Donald','28 Purple Finch St.','3534100936');
insert into client values ('23498','Clinton','Hilary','368 E. Gainsway Dr.','6346872927');
insert into client values ('10235','Pann','Sam','7895 Homestead Circle','6831431545');
insert into client values ('45862','Doe','John','44 Livingston Street','1073683623');
insert into client values ('09876','Temple','Shirley','9412 Wood Street','1803015594');
insert into client values ('32340','Einstein','Albert','856 East Piper Ave.','1209792161');

insert into credentials values ('12345','Playboyize','Gm3G6wZpz4A*~vW6TzY2');
insert into credentials values ('45623','Gigastrength','Rnc5i6RSxCDm2=QN8R*e');
insert into credentials values ('63453','Deadlyinx','yDPrL3zJ3kgKRcM0P*b1');
insert into credentials values ('34958','Techpill','rz-lt6Jgf9e5pry5%TbT');
insert into credentials values ('12356','Methshot','iH-UCpht3MYmO1iuUPsM');
insert into credentials values ('23498','Methnerd','WJs$CHkcgJqziMC1sk0n');
insert into credentials values ('10235','TreeEater','l@Nj8lzyJ4kVmoXT12~t');
insert into credentials values ('45862','PackManBrainlure','tAR+rCMY?CCuoY56qaaz');
insert into credentials values ('09876','Carnalpleasure','CeQQ&&ifZUDQqTbEzM3x');
insert into credentials values ('32340','Sharpcharm','RYj+VRmJnlMyXRB2IQ9%');

insert into stock values ('34958','APPL','5434.32','5455.23','Up');
insert into stock values ('34958','Dell','1434.32','955.23','Down');
insert into stock values ('34958','SAS','9434.32','9498.23','Up');
insert into stock values ('12356','APPL','5434.32','5455.23','Up');
insert into stock values ('12356','Dell','1434.32','955.23','Down');
insert into stock values ('12356','SAS','9434.32','9498.23','Up');
insert into stock values ('23498','APPL','5434.32','5455.23','Up');
insert into stock values ('10235','APPL','5434.32','5455.23','Up');
insert into stock values ('45862','APPL','5434.32','5455.23','Up');
insert into stock values ('45862','Dell','1434.32','955.23','Down');
insert into stock values ('09876','APPL','5434.32','5455.23','Up');
insert into stock values ('32340','APPL','5434.32','5455.23','Up');
insert into stock values ('32340','Dell','1434.32','955.23','Down');
insert into stock values ('32340','SAS','9434.32','9498.23','Up');
insert into stock values ('32340','ORCL','3434.32','2455.23','Down');
