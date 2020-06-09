
DROP TABLE discount;
DROP TABLE transaction;
DROP TABLE images;
DROP TABLE product;
DROP TABLE users;
DROP TABLE coupon;


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
insert into images values(seq_image_no.nextval,1000,'c964463492c2825a843ef.png');
insert into images values(seq_image_no.nextval,1000,'Q4GH4H580A7R3.jpg');
insert into images values(seq_image_no.nextval,1000,'asdfga235sg.jpg');
insert into images values(seq_image_no.nextval,1000,'dyfd6egt55g4.jpg');

insert into product values (seq_product_prod_no.nextval,'소니 RM100','컴팩트 미러리스','20150425',430000, sysdate);
insert into images values(seq_image_no.nextval,1001,'17369039962142.jpg');
insert into images values(seq_image_no.nextval,1001,'wdqwfgqg234.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-pro3','클래식 미러리스','20190317',1350000, sysdate);
insert into images values(seq_image_no.nextval,1002,'2016243376werth321106.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 100f','클래식 컴팩트 카메라','20180228',1350000, sysdate);
insert into images values(seq_image_no.nextval,1003,'20181384765947qqw52.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-T3','클래식 미러리스 카메라','20180520',829000, sysdate);
insert into images values(seq_image_no.nextval,1004,'201825733wrth453312.jpg');

insert into product values (seq_product_prod_no.nextval,'소니 RM102','컴팩트 미러리스','20160425',550000, sysdate);
insert into images values(seq_image_no.nextval,1005,'23467fhyg4hh.jpg');

insert into product values (seq_product_prod_no.nextval,'후지 X-T2','클래식 미러리스 카메라','20140425',350000, sysdate);
insert into images values(seq_image_no.nextval,1005,'2346902GQ3G34.jpg');


insert into coupon values (seq_coupon_no.nextval,'신규 가입자 쿠폰', 10, 1000,1000);
insert into coupon values (seq_coupon_no.nextval,'이달의 VIP 쿠폰', 10, 5000,5000);
insert into coupon values (seq_coupon_no.nextval,'이달의 이벤트 쿠폰', 5, 1500, 1000);
insert into coupon values (seq_coupon_no.nextval,'누구나 이벤트 쿠폰', 10, 3000, 0);

commit;
