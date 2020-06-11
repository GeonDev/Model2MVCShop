DROP TABLE images;
DROP TABLE transaction;
DROP TABLE discount;

DROP TABLE product CASCADE constraints;
DROP TABLE users CASCADE constraints;
DROP TABLE coupon CASCADE constraints;


DROP SEQUENCE seq_product_prod_no;
DROP SEQUENCE seq_transaction_tran_no;
DROP SEQUENCE seq_image_no;
DROP SEQUENCE seq_coupon_no;
DROP SEQUENCE seq_discount_no;


CREATE SEQUENCE seq_product_prod_no	 	INCREMENT BY 1 START WITH 1000;
CREATE SEQUENCE seq_transaction_tran_no	INCREMENT BY 1 START WITH 2000;
CREATE SEQUENCE seq_image_no			INCREMENT BY 1 START WITH 3000;
CREATE SEQUENCE seq_coupon_no			INCREMENT BY 1 START WITH 4000;
CREATE SEQUENCE seq_discount_no			INCREMENT BY 1 START WITH 5000;


CREATE TABLE users ( 
	user_id 			VARCHAR2(20)		NOT NULL,
	user_name 			VARCHAR2(50)		NOT NULL,
	password 			VARCHAR2(10)		NOT NULL,
	role 				VARCHAR2(5) 		DEFAULT 'user',
	ssn 				VARCHAR2(13),
	cell_phone			VARCHAR2(14),
	addr 				VARCHAR2(100),
	email 				VARCHAR2(50),
	reg_date 			DATE,
	PRIMARY KEY(user_id)
);


CREATE TABLE product ( 
	prod_no 				NUMBER 			NOT NULL,
	prod_name 				VARCHAR2(100) 	NOT NULL,
	prod_detail 			VARCHAR2(200),
	manufacture_day			VARCHAR2(8),
	price 					NUMBER(10),
	reg_date 				DATE,
	PRIMARY KEY(prod_no)
);


CREATE TABLE images ( 
	image_no				NUMBER 			NOT NULL,
	prod_no 				NUMBER			NOT NULL REFERENCES product(prod_no),
	image_file 				VARCHAR2(100) 	NOT NULL,
	PRIMARY KEY(image_no)
);




CREATE TABLE transaction ( 
	tran_no 				NUMBER 			NOT NULL,
	prod_no 				NUMBER			NOT NULL REFERENCES product(prod_no),
	buyer_id 				VARCHAR2(20)	NOT NULL REFERENCES users(user_id),
	payment_option			VARCHAR2(3),
	receiver_name 			VARCHAR2(20),
	receiver_phone			VARCHAR2(14),
	dlvy_addr 				VARCHAR2(100),
	dlvy_request 			VARCHAR2(100),
	tran_status_code		VARCHAR2(3),
	order_date 				DATE,
	dlvy_date 				DATE,
	PRIMARY KEY(tran_no)
);


CREATE TABLE coupon(
	coupon_no				NUMBER 			NOT NULL,
	coupon_name				VARCHAR2(100) 	NOT NULL,
	discount_ratio			NUMBER		 	NOT NULL,	
	maximum_discount_price	NUMBER,
	minimum_price			NUMBER,
	PRIMARY KEY(coupon_no)
);


CREATE TABLE discount(
	discount_no				NUMBER 			NOT NULL,
	owner_id				VARCHAR2(20) 	NOT NULL REFERENCES users(user_id),
	coupon_no				NUMBER			NOT NULL REFERENCES coupon(coupon_no), 	
	issued_date				DATE,
	expiration_date			DATE,
	PRIMARY KEY(discount_no)
);



INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'admin', 'admin', '1234', 'admin', NULL, NULL, '서울시 서초구', 'admin@mvc.com',to_date('2012/01/14', 'YYYY/MM/DD')); 

INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'manager', 'manager', '1234', 'admin', NULL, NULL, NULL, 'manager@mvc.com', to_date('2012/01/14', 'YYYY/MM/DD'));          

INSERT INTO users 
VALUES ( 'user01', 'SCOTT', '1111', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user02', 'SCOTT', '2222', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user03', 'SCOTT', '3333', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user04', 'SCOTT', '4444', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user05', 'SCOTT', '5555', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user06', 'SCOTT', '6666', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user07', 'SCOTT', '7777', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user08', 'SCOTT', '8888', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user09', 'SCOTT', '9999', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user10', 'SCOTT', '1010', 'user', NULL, NULL, NULL, NULL, sysdate); 

       

insert into product values (seq_product_prod_no.nextval,'소니 A7R3','고화질 미러리스','20190312',3200000, sysdate);
insert into images values(seq_image_no.nextval,1000,'Q4GH4H580A7R3.jpg');
insert into images values(seq_image_no.nextval,1000,'4sxi6wsu.png');
insert into images values(seq_image_no.nextval,1000,'asdfga235sg.jpg');
insert into images values(seq_image_no.nextval,1000,'dyfd6egt55g4.jpg');

insert into product values (seq_product_prod_no.nextval,'소니 RM100','컴팩트 미러리스','20150425',430000, sysdate);
insert into images values(seq_image_no.nextval,1001,'17369039962142.jpg');
insert into images values(seq_image_no.nextval,1001,'wdqwfgqg234.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-pro3','클래식 미러리스','20190317',1350000, sysdate);
insert into images values(seq_image_no.nextval,1002,'2016243376werth321106.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 100f','클래식 컴팩트 카메라','20180228',630000, sysdate);
insert into images values(seq_image_no.nextval,1003,'20181384765947qqw52.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-T3','클래식 미러리스 카메라','20180520',829000, sysdate);
insert into images values(seq_image_no.nextval,1004,'201825733wrth453311.jpg');

insert into product values (seq_product_prod_no.nextval,'소니 RM102','컴팩트 미러리스','20160425',550000, sysdate);
insert into images values(seq_image_no.nextval,1005,'23467fhyg4hh.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-T2','클래식 미러리스 카메라','20140425',350000, sysdate);
insert into images values(seq_image_no.nextval,1006,'2346902GQ3G34.jpg');

insert into product values (seq_product_prod_no.nextval,'즉석카메라','일회용 필름 카메라','20120425',350000, sysdate);
insert into images values(seq_image_no.nextval,1007,'234UYWERH467G.jpg');

insert into product values (seq_product_prod_no.nextval,'캐논 R5','전문가용 미러리스 카메라','20180605',350000, sysdate);
insert into images values(seq_image_no.nextval,1008,'33570_63794_817.jpg');

insert into product values (seq_product_prod_no.nextval,'니콘 D780','전문가용 DSLR 카메라','20200205',350000, sysdate);
insert into images values(seq_image_no.nextval,1009,'82705_53723_2834.jpg');

insert into product values (seq_product_prod_no.nextval,'올림푸스 el10','컴팩트 미러리스 카메라','20191225',350000, sysdate);
insert into images values(seq_image_no.nextval,1010,'997B20355C15B67E10.png');

insert into product values (seq_product_prod_no.nextval,'삼성 NX200','컴팩트 미러리스 카메라','20160125',150000, sysdate);
insert into images values(seq_image_no.nextval,1011,'asdfah34h348i.jpg');

insert into product values (seq_product_prod_no.nextval,'라이카 폴라로이드','컴팩트 미러리스 카메라','20140125',1850000, sysdate);
insert into images values(seq_image_no.nextval,1012,'fqg3g.jpg');

insert into product values (seq_product_prod_no.nextval,'토이카메라','컴팩트 카메라','20140425',50000, sysdate);
insert into images values(seq_image_no.nextval,1013,'G6T34HY.jpg');

insert into product values (seq_product_prod_no.nextval,'캐논 5Ds','전문가용 DSLR 카메라','20140425',9580000, sysdate);
insert into images values(seq_image_no.nextval,1014,'gasgqh.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X100 pro','컴팩트 미러리스 카메라','20140425',50000, sysdate);
insert into images values(seq_image_no.nextval,1015,'gqrghsgqerg.jpg');

insert into product values (seq_product_prod_no.nextval,'올림푸스 펜F','컴팩트 미러리스 카메라','20170425',590000, sysdate);
insert into images values(seq_image_no.nextval,1016,'gwergh.jpg');

insert into product values (seq_product_prod_no.nextval,'종이 카메라','일회용 카메라','20150425',90000, sysdate);
insert into images values(seq_image_no.nextval,1017,'qweh3hA87943.jpg');

insert into product values (seq_product_prod_no.nextval,'올림푸스 EM5','전문가용 미러리스 카메라','20190425',1290000, sysdate);
insert into images values(seq_image_no.nextval,1018,'qwergwg453.jpg');

insert into product values (seq_product_prod_no.nextval,'라이카 C-Lux','컴팩트 카메라','20170521',4570000, sysdate);
insert into images values(seq_image_no.nextval,1019,'unfjkhjk8768.jpg');

insert into product values (seq_product_prod_no.nextval,'캐논 200D','DSLR 카메라','20150521',5570000, sysdate);
insert into images values(seq_image_no.nextval,1020,'u7n46naas5me6d.jpg');

insert into product values (seq_product_prod_no.nextval,'라이카 D-Lux ','컴팩트 카메라','20140521',550000, sysdate);
insert into images values(seq_image_no.nextval,1021,'unnaasdfmedfjhfh6.jpg');

insert into product values (seq_product_prod_no.nextval,'소니 A5100','컴팩트 카메라','20130521',892000, sysdate);
insert into images values(seq_image_no.nextval,1022,'asfeginneasdfasgasfd03.jpg');

insert into product values (seq_product_prod_no.nextval,'캐논 200D 화이트','DSLR 카메라','20180521',132000, sysdate);
insert into images values(seq_image_no.nextval,1023,'asdfasdfefff2345ffa.jpg');

insert into product values (seq_product_prod_no.nextval,'리코 GR','컴팩트 카메라','20170521',9900, sysdate);
insert into images values(seq_image_no.nextval,1024,'ferh476hf335g3.jpg');

insert into product values (seq_product_prod_no.nextval,'오즈모 포켓','컴팩트 카메라','20180521',19900, sysdate);
insert into images values(seq_image_no.nextval,1025,'sdf572g46j2gj7oqht.jpg');

insert into product values (seq_product_prod_no.nextval,'올림푸스 EM1','전문가용 미러리스 카메라','20200325',259900, sysdate);
insert into images values(seq_image_no.nextval,1026,'1fhqw23qwe4qwqw.jpg');
insert into images values(seq_image_no.nextval,1026,'34793w12e5315221980.jpg');
insert into images values(seq_image_no.nextval,1026,'qwevt20qwru000_4.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-T4','전문가용 미러리스 카메라','20200517',859500, sysdate);
insert into images values(seq_image_no.nextval,1027,'gho45t58095jh69g.jpg');
insert into images values(seq_image_no.nextval,1027,'q5we408gh74qrg4.PNG');
insert into images values(seq_image_no.nextval,1027,'q5we408gh74qrg4.PNG');
insert into images values(seq_image_no.nextval,1027,'ret42uer.PNG');
insert into images values(seq_image_no.nextval,1027,'12g75nogwg3.jpg');
insert into images values(seq_image_no.nextval,1027,'234y42uqwt.PNG');

insert into coupon values (seq_coupon_no.nextval,'신규 가입자 쿠폰', 10, 1000,10000);
insert into coupon values (seq_coupon_no.nextval,'이달의 VIP 쿠폰', 10, 50000,5000000);
insert into coupon values (seq_coupon_no.nextval,'이달의 이벤트 쿠폰', 5, 1500, 1000);
insert into coupon values (seq_coupon_no.nextval,'누구나 이벤트 쿠폰', 10, 3000, 0);

commit;
