INSERT INTO credentials (broker_id,username,password) VALUES (1111, "test", "test");

INSERT INTO broker (id,last_name,first_name,phone_num,email,address) VALUES (1111,"Doe","John","5555555555","john.doe@test.com","123 Fake St.");

INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11112,"Baird","Enzo","8245922080","Enzo.Baird@test.com","7670 E. Liberty Court",82034);
INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11113,"Acevedo","Thalia","3091476021","Thalia.Acevedo@test.com","8228 Surrey Street",55983);
INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11114,"Krueger","Jose","9183388741","Jose.Krueger@test.com","9990 East Riverview Road",17717);
INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11115,"Galvan","Agustin","7093796099","Agustin.Galvan@test.com","445 Dunbar St.",72200);
INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11116,"Chavez","Erika","2840946032","Erika.Chavez@test.com","49 Race Drive",31076);
INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11117,"Richardson","Jaidyn","4042317514","Jaidyn.Richardson@test.com","662 Airport St.",91786);
INSERT INTO client (id,last_name,first_name,phone_num,email,address,cash) VALUES (11118,"Davenport","Andres","5352385797","Andres.Davenport@test.com","946 Orchard Street",68014);

INSERT INTO relationship (broker_id,client_id) VALUES (1111,11112);
INSERT INTO relationship (broker_id,client_id) VALUES (1111,11113);
INSERT INTO relationship (broker_id,client_id) VALUES (1111,11114);
INSERT INTO relationship (broker_id,client_id) VALUES (1111,11115);
INSERT INTO relationship (broker_id,client_id) VALUES (1111,11116);
INSERT INTO relationship (broker_id,client_id) VALUES (1111,11117);
INSERT INTO relationship (broker_id,client_id) VALUES (1111,11118);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXNNRCAD",173);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXS1",499);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXNNREUR",787);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXNNRGBP",139);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXNNRCHF",53);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXCAD",896);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XCMPNNR",366);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXT25E",947);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11112,"XNDXHKD",134);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11113,"XNDXHKD",365);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11113,"XNDXL3TR",971);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11113,"XNDXL",915);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXHKD",984);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXCHFMH",580);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXT25",308);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXS2",517);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXNNRS3",928);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXNNRCAD",657);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XQC",460);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11114,"XNDXNNRCHF",554);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXS2NNR",839);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXNNRCAD",333);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXNNREUR",624);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXT25E",666);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXS1NNR",195);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXCHF",901);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXEUR",649);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXNNRCHF",1000);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNDXT25",214);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11115,"XNBINNR",498);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXNNREUR",999);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXCHFMH",287);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXNNREUR",959);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXNNRCHF",875);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXNNRGBP",779);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXT25E",904);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXL",853);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXCHF",369);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11116,"XNDXGBPMH",628);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11117,"XNDXGBPMH",485);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11117,"XNDXEUR",965);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11117,"XNBINNR",766);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11117,"XNDXL3TR",534);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11117,"XNDXCHFMH",454);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11117,"XCMPNNR",204);

INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNBINNR",343);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXNNRCAD",158);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXS2",372);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXCHF",935);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXEUR",893);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XCMPNNR",829);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXCAD",427);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNBINNR",400);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXNNREUR",830);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXT25NNR",150);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXT25NNER",260);
INSERT INTO stock_owned (client_id,stock_name,shares) VALUES (11118,"XNDXHKD",137);
