/*
CSERE:
EZT: VARCHAR2 (
ERRE: VARCHAR(
(igen ott extra szokoz van a number elott)
EZT:  NUMBER
ERRE:  INT
TOROLNI:
NOT DEREFERRABLE
ADMIN USER: USERNAME: admin 
            PASSWORD: admin
            USERNAME: admin2 
            PASSWORD: admin2
            
TEST USER: USERNAME: test
           PASSWORD: test
           USERNAME: test2
           PASSWORD: test2
           USERNAME: test3
           PASSWORD: test3
           USERNAME: test4
           PASSWORD: test4
*/


DROP DATABASE IF EXISTS unizon;

CREATE DATABASE unizon CHARACTER SET UTF8;

USE unizon;

CREATE TABLE ADDRESS
  (
    ADDRESS_ID INT NOT NULL ,
    ZIP        VARCHAR(10) NOT NULL ,
    COUNTRY    VARCHAR(100) NOT NULL ,
    CITY       VARCHAR(100) NOT NULL ,
    STREET     VARCHAR(100) NOT NULL ,
    STR_NUMBER INT NOT NULL ,
    FLOOR      INT ,
    DOOR       INT
  ) ;
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS_PK PRIMARY KEY ( ADDRESS_ID ) ;
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS__UN UNIQUE ( ZIP , COUNTRY , CITY , STREET , STR_NUMBER , FLOOR , DOOR ) ;


CREATE TABLE ADDRESS_TO_USER
  (
    USER_ID    INT NOT NULL ,
    ADDRESS_ID INT NOT NULL
  ) ;
ALTER TABLE ADDRESS_TO_USER ADD CONSTRAINT ADDRESSES_OF_USER_PK PRIMARY KEY ( USER_ID, ADDRESS_ID ) ;


CREATE TABLE ADMINISTRATOR
  ( USER_ID INT NOT NULL
  ) ;
ALTER TABLE ADMINISTRATOR ADD CONSTRAINT ADMINISTRATOR_PK PRIMARY KEY ( USER_ID ) ;


CREATE TABLE CATEGORY
  (
    CATEGORY_ID INT NOT NULL ,
    NAME        VARCHAR(100) NOT NULL
  ) ;
ALTER TABLE CATEGORY ADD CONSTRAINT CATEGORY_PK PRIMARY KEY ( CATEGORY_ID ) ;
ALTER TABLE CATEGORY ADD CONSTRAINT CATEGORY_UNIQUE UNIQUE ( NAME ) ;


CREATE TABLE CAT_TO_PROD
  (
    PRODUCT_ID  INT NOT NULL ,
    CATEGORY_ID INT NOT NULL
  ) ;
ALTER TABLE CAT_TO_PROD ADD CONSTRAINT CAT_TO_PROD_PK PRIMARY KEY ( PRODUCT_ID, CATEGORY_ID ) ;


CREATE TABLE IMAGE
  ( IMAGE_ID INT NOT NULL , IMAGE_URL VARCHAR(1000)
  ) ;
ALTER TABLE IMAGE ADD CONSTRAINT IMAGE_PK PRIMARY KEY ( IMAGE_ID ) ;
ALTER TABLE IMAGE ADD CONSTRAINT IMAGE__UN UNIQUE ( IMAGE_URL ) ;


CREATE TABLE PHONE_NUMBER
  (
    PHONE_NUMBER_ID INT NOT NULL ,
    PHONE_NUMBER    VARCHAR(100) NOT NULL
  ) ;
ALTER TABLE PHONE_NUMBER ADD CONSTRAINT PHONE_NUMBER_PK PRIMARY KEY ( PHONE_NUMBER_ID ) ;
ALTER TABLE PHONE_NUMBER ADD CONSTRAINT PHONE_NUMBER__UNIQUE UNIQUE ( PHONE_NUMBER ) ;


CREATE TABLE PRODUCT
  (
    PRODUCT_ID       INT NOT NULL ,
    TITLE            VARCHAR(100) NOT NULL ,
    PRICE            INT ,
    AMOUNT           INT ,
    DESCRIPTION      VARCHAR(1000) ,
    DEFAULT_IMAGE_ID INT NOT NULL,
    DELETED BOOLEAN NOT NULL
  ) ;
ALTER TABLE PRODUCT ADD CONSTRAINT PRODUCT_PK PRIMARY KEY ( PRODUCT_ID ) ;


CREATE TABLE PRODUCT_TO_IMAGE
  (
    PRODUCT_ID INT NOT NULL ,
    IMAGE_ID   INT NOT NULL
  ) ;
ALTER TABLE PRODUCT_TO_IMAGE ADD CONSTRAINT PRODUCT_TO_IMAGE_PK PRIMARY KEY ( PRODUCT_ID, IMAGE_ID ) ;


CREATE TABLE PROD_TO_ORDER
  (
    ORDER_ID   INT NOT NULL ,
    PRODUCT_ID INT NOT NULL ,
    AMOUNT     INT NOT NULL
  ) ;
ALTER TABLE PROD_TO_ORDER ADD CONSTRAINT PROD_TO_ORDER_PK PRIMARY KEY ( ORDER_ID, PRODUCT_ID ) ;


CREATE TABLE PROD_TO_TAG
  (
    PRODUCT_ID INT NOT NULL ,
    TAG_ID     INT NOT NULL
  ) ;
ALTER TABLE PROD_TO_TAG ADD CONSTRAINT PROD_TO_TAG_PK PRIMARY KEY ( PRODUCT_ID, TAG_ID ) ;


CREATE TABLE TAG
  ( TAG_ID INT NOT NULL , NAME VARCHAR(100) NOT NULL
  ) ;
ALTER TABLE TAG ADD CONSTRAINT TAG_PK PRIMARY KEY ( TAG_ID ) ;
ALTER TABLE TAG ADD CONSTRAINT TAG_UNIQUE UNIQUE ( NAME ) ;


CREATE TABLE UNI_ORDER
  (
    ORDER_ID            INT NOT NULL ,
    USER_ID             INT NOT NULL ,
    PHONE_NUMBER_ID     INT NOT NULL ,
    ORDER_DATE          DATE NOT NULL ,
    SHIPPING_ADDRESS_ID INT NOT NULL ,
    BILLING_ADDRESS_ID  INT NOT NULL
  ) ;
ALTER TABLE UNI_ORDER ADD CONSTRAINT ORDER_PK PRIMARY KEY ( ORDER_ID ) ;


CREATE TABLE UNI_USER
  (
    USER_ID           INT NOT NULL ,
    USERNAME          VARCHAR(100) NOT NULL ,
    PASSWORD          VARCHAR(100) NOT NULL ,
    E_MAIL            VARCHAR(100) NOT NULL ,
    NAME              VARCHAR(100) NOT NULL ,
    REGISTRATION_DATE DATE NOT NULL ,
    STATUS_ID         INT NOT NULL
  ) ;
ALTER TABLE UNI_USER ADD CONSTRAINT UNI_USER_PK PRIMARY KEY ( USER_ID ) ;
ALTER TABLE UNI_USER ADD CONSTRAINT UNI_USER_EMAIL UNIQUE ( E_MAIL ) ;
ALTER TABLE UNI_USER ADD CONSTRAINT UNI_USER_USERNAME UNIQUE ( USERNAME ) ;


CREATE TABLE USER_ACTIVATION
  (
    USER_ID        INT NOT NULL ,
    ACTIVATION_KEY VARCHAR(200) NOT NULL
  ) ;
ALTER TABLE USER_ACTIVATION ADD CONSTRAINT USER_ACTIVATION_PK PRIMARY KEY ( USER_ID ) ;


CREATE TABLE USER_STATUS
  (
    STATUS_ID   INT NOT NULL ,
    STATUS_NAME VARCHAR(100) NOT NULL
  ) ;
ALTER TABLE USER_STATUS ADD CONSTRAINT USER_STATUS_PK PRIMARY KEY ( STATUS_ID ) ;
ALTER TABLE USER_STATUS ADD CONSTRAINT USER_STATUS__UN UNIQUE ( STATUS_NAME ) ;


CREATE TABLE USER_TO_PHONE_NUMBER
  (
    USER_ID         INT NOT NULL ,
    PHONE_NUMBER_ID INT NOT NULL
  ) ;
ALTER TABLE USER_TO_PHONE_NUMBER ADD CONSTRAINT USER_TO_PHONE_NUMBER_PK PRIMARY KEY ( USER_ID, PHONE_NUMBER_ID ) ;


ALTER TABLE ADDRESS_TO_USER ADD CONSTRAINT ADDRESS_TO_USER_ADDRESS_FK FOREIGN KEY ( ADDRESS_ID ) REFERENCES ADDRESS ( ADDRESS_ID ) ;

ALTER TABLE ADDRESS_TO_USER ADD CONSTRAINT ADDRESS_TO_USER_UNI_USER_FK FOREIGN KEY ( USER_ID ) REFERENCES UNI_USER ( USER_ID ) ;

ALTER TABLE ADMINISTRATOR ADD CONSTRAINT ADMINISTRATOR_UNI_USER_FK FOREIGN KEY ( USER_ID ) REFERENCES UNI_USER ( USER_ID ) ;

ALTER TABLE CAT_TO_PROD ADD CONSTRAINT CAT_TO_PROD_CATEGORY_FK FOREIGN KEY ( CATEGORY_ID ) REFERENCES CATEGORY ( CATEGORY_ID ) ;

ALTER TABLE CAT_TO_PROD ADD CONSTRAINT CAT_TO_PROD_PRODUCT_FK FOREIGN KEY ( PRODUCT_ID ) REFERENCES PRODUCT ( PRODUCT_ID ) ;

ALTER TABLE UNI_ORDER ADD CONSTRAINT ORDER_BI_ADDRESS_FK FOREIGN KEY ( BILLING_ADDRESS_ID ) REFERENCES ADDRESS ( ADDRESS_ID ) ;

ALTER TABLE UNI_ORDER ADD CONSTRAINT ORDER_PHONE_NUMBER_FK FOREIGN KEY ( PHONE_NUMBER_ID ) REFERENCES PHONE_NUMBER ( PHONE_NUMBER_ID ) ;

ALTER TABLE UNI_ORDER ADD CONSTRAINT ORDER_SH_ADDRESS_FK FOREIGN KEY ( SHIPPING_ADDRESS_ID ) REFERENCES ADDRESS ( ADDRESS_ID ) ;

ALTER TABLE PRODUCT ADD CONSTRAINT PRODUCT_IMAGE_FK FOREIGN KEY ( DEFAULT_IMAGE_ID ) REFERENCES IMAGE ( IMAGE_ID ) ;

ALTER TABLE PRODUCT_TO_IMAGE ADD CONSTRAINT PRODUCT_TO_IMAGE_IMAGE_FK FOREIGN KEY ( IMAGE_ID ) REFERENCES IMAGE ( IMAGE_ID ) ;

ALTER TABLE PRODUCT_TO_IMAGE ADD CONSTRAINT PRODUCT_TO_IMAGE_PRODUCT_FK FOREIGN KEY ( PRODUCT_ID ) REFERENCES PRODUCT ( PRODUCT_ID ) ;

ALTER TABLE PROD_TO_ORDER ADD CONSTRAINT PROD_TO_ORDER_ORDER_FK FOREIGN KEY ( ORDER_ID ) REFERENCES UNI_ORDER ( ORDER_ID ) ;

ALTER TABLE PROD_TO_ORDER ADD CONSTRAINT PROD_TO_ORDER_PRODUCT_FK FOREIGN KEY ( PRODUCT_ID ) REFERENCES PRODUCT ( PRODUCT_ID ) ;

ALTER TABLE PROD_TO_TAG ADD CONSTRAINT PROD_TO_TAG_PRODUCT_FK FOREIGN KEY ( PRODUCT_ID ) REFERENCES PRODUCT ( PRODUCT_ID ) ;

ALTER TABLE PROD_TO_TAG ADD CONSTRAINT PROD_TO_TAG_TAG_FK FOREIGN KEY ( TAG_ID ) REFERENCES TAG ( TAG_ID ) ;

ALTER TABLE UNI_ORDER ADD CONSTRAINT UNI_ORDER_UNI_USER_FK FOREIGN KEY ( USER_ID ) REFERENCES UNI_USER ( USER_ID ) ;

ALTER TABLE UNI_USER ADD CONSTRAINT UNI_USER_USER_STATUS_FK FOREIGN KEY ( STATUS_ID ) REFERENCES USER_STATUS ( STATUS_ID ) ;

ALTER TABLE USER_ACTIVATION ADD CONSTRAINT USER_ACTIVATION_UNI_USER_FK FOREIGN KEY ( USER_ID ) REFERENCES UNI_USER ( USER_ID ) ;

ALTER TABLE USER_TO_PHONE_NUMBER ADD CONSTRAINT USER_TO_PHONE_NUMBER_FK1 FOREIGN KEY ( PHONE_NUMBER_ID ) REFERENCES PHONE_NUMBER ( PHONE_NUMBER_ID ) ;

ALTER TABLE USER_TO_PHONE_NUMBER ADD CONSTRAINT USER_TO_PHONE_NUMBER_FK2 FOREIGN KEY ( USER_ID ) REFERENCES UNI_USER ( USER_ID ) ;


INSERT INTO USER_STATUS(STATUS_ID, STATUS_NAME) VALUES(-1, 'inactive');
INSERT INTO USER_STATUS(STATUS_ID, STATUS_NAME) VALUES(-2, 'active');
INSERT INTO USER_STATUS(STATUS_ID, STATUS_NAME) VALUES(-3, 'final');


INSERT INTO UNI_USER(USER_ID, USERNAME, PASSWORD, E_MAIL, NAME, REGISTRATION_DATE, STATUS_ID)
    VALUES(-1,'admin', 'cveqGRZfe2VtNuO0yDGhYviUTZWxmiIt3aZmzqm80n8=$NaOrIMoClDCyLPGoVsClrEYLGKw7U5uYFJkrhAhC0R4=','admin@gmail.com','Admin User','2016-04-09', -3);

INSERT INTO UNI_USER(USER_ID, USERNAME, PASSWORD, E_MAIL, NAME, REGISTRATION_DATE, STATUS_ID)
    VALUES(-2,'test','ZbOS5o2SpVBah+t9MMLWpf3a2rxatJVo2hV5hnY4dQ4=$1iz88I5xleACNlQGK9eZdKvSBMo/Us2aKXTAlG8Tmms=','test@gmail.com','Test User', '2016-04-09', -3);

INSERT INTO UNI_USER(USER_ID, USERNAME, PASSWORD, E_MAIL, NAME, REGISTRATION_DATE, STATUS_ID)
    VALUES(-3,'test2','FxkWGMkAwbq3c4kCqLKqSvgnkv7Q6fmlmg0qpNyf+ds=$UAAlW4+ipWxfoE7eVHXD5OA48azauOhEWIiUV/dA0X4=','test2@gmail.com','Test2 User', '2015-03-04', -3);

INSERT INTO UNI_USER(USER_ID, USERNAME, PASSWORD, E_MAIL, NAME, REGISTRATION_DATE, STATUS_ID)
    VALUES(-4,'test3','Nh0+SvHXJoJzrFK71qXs693ftunFkoQ60N/qT5AphOI=$k2xO8Xgy7UbUxKdnAf+8ghdViNeHxFm0jgWpapOgbdE=','test3@gmail.com','Test3 User', '2015-03-04', -3);

INSERT INTO UNI_USER(USER_ID, USERNAME, PASSWORD, E_MAIL, NAME, REGISTRATION_DATE, STATUS_ID)
    VALUES(-5,'test4','xxZiRzrq4yeNuS/O/K/JIwQZb8aL9l6AhoJ4anTlFNU=$Wo+3DXAYNs1euay9WtgMG7iCirIXPfZQBqmGiuygJWY=','test4@gmail.com','Test4 User', '2015-03-04', -3);

INSERT INTO UNI_USER(USER_ID, USERNAME, PASSWORD, E_MAIL, NAME, REGISTRATION_DATE, STATUS_ID)
    VALUES(-6,'admin2','hI+CNsDo2vixy5gMeorczH7Bl2YlxEqWF2022mxkOWo=$jhuFFXytReV39fagWOz030LM+g8ZevWGnctVRWiI294=','admin2@gmail.com','Admin2 User', '2013-03-04', -3);





INSERT INTO ADMINISTRATOR VALUES(-1);
INSERT INTO ADMINISTRATOR VALUES(-6);


INSERT INTO ADDRESS VALUES(-1, 5342, 'Hungary', 'Debrecen', 'Kassai street', 1, 4, 2); 
INSERT INTO ADDRESS VALUES(-2, 3321, 'Hungary', 'Budapest', 'Pesti road', 12, 10, 32); 
INSERT INTO ADDRESS VALUES(-3, 3300, 'Hungary', 'Eger', 'Dobó road', 10, 122, 10);
INSERT INTO ADDRESS VALUES(-4, 2034, 'Hungary', 'Budapest', 'Diószegi street', 12, null, null); 
INSERT INTO ADDRESS VALUES(-5, 4004, 'Hungary', 'Szeged', 'Pesti street', 56, null, null); 
INSERT INTO ADDRESS VALUES(-6, 4027, 'Hungary', 'Sátoraljaújhely', 'Kossuth Lajos street', 13, 2, 4); 

INSERT INTO ADDRESS_TO_USER VALUES(-1, -1);
INSERT INTO ADDRESS_TO_USER VALUES(-2, -2);
INSERT INTO ADDRESS_TO_USER VALUES(-2, -3);
INSERT INTO ADDRESS_TO_USER VALUES(-3, -3);
INSERT INTO ADDRESS_TO_USER VALUES(-4, -4);
INSERT INTO ADDRESS_TO_USER VALUES(-5, -5);
INSERT INTO ADDRESS_TO_USER VALUES(-6, -6);

INSERT INTO PHONE_NUMBER VALUES(-1, '06302133340');
INSERT INTO PHONE_NUMBER VALUES(-2, '06201834201');
INSERT INTO PHONE_NUMBER VALUES(-3, '06706906642');
INSERT INTO PHONE_NUMBER VALUES(-4, '06706119102');
INSERT INTO PHONE_NUMBER VALUES(-5, '06308462444');
INSERT INTO PHONE_NUMBER VALUES(-6, '06127350676');
INSERT INTO PHONE_NUMBER VALUES(-7, '06523136484');
INSERT INTO PHONE_NUMBER VALUES(-8, '06309184653');
INSERT INTO PHONE_NUMBER VALUES(-9, '06704483722');
INSERT INTO PHONE_NUMBER VALUES(-10, '06208374923');
INSERT INTO PHONE_NUMBER VALUES(-11, '06701242174');

INSERT INTO USER_TO_PHONE_NUMBER VALUES(-1, -1);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-1, -11);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-2, -2);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-2, -3);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-3, -4);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-3, -10);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-4, -5);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-5, -6);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-6, -7);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-6, -8);
INSERT INTO USER_TO_PHONE_NUMBER VALUES(-6, -9);



INSERT INTO UNI_ORDER VALUES (-1,-1,-11,'2016-04-15',-1,-1);
INSERT INTO UNI_ORDER VALUES(-2,-1,-1,'2016-04-13',-1,-1);
INSERT INTO UNI_ORDER VALUES(-3,-2,-2,'2016-04-13',-2,-2);
INSERT INTO UNI_ORDER VALUES(-4,-2,-2,'2007-07-02',-3,-3);
INSERT INTO UNI_ORDER VALUES(-5,-2,-3,'2011-01-23',-2,-3);
INSERT INTO UNI_ORDER VALUES(-6,-2,-3,'2014-04-13',-3,-2);
INSERT INTO UNI_ORDER VALUES(-7,-3,-10,'2015-05-09',-3,-3);
INSERT INTO UNI_ORDER VALUES(-8,-3,-4,'2015-05-30',-3,-3);
INSERT INTO UNI_ORDER VALUES(-9,-3,-10,'2015-09-28',-3,-3);
INSERT INTO UNI_ORDER VALUES(-10,-3,-4,'2016-02-29',-3,-3);
INSERT INTO UNI_ORDER VALUES(-11,-4,-5,'2002-02-02',-4,-4);
INSERT INTO UNI_ORDER VALUES(-12,-4,-5,'2002-10-17',-4,-4);
INSERT INTO UNI_ORDER VALUES(-13,-4,-5,'2004-01-03',-4,-4);
INSERT INTO UNI_ORDER VALUES(-14,-4,-5,'2005-08-29',-4,-4);
INSERT INTO UNI_ORDER VALUES(-15,-4,-5,'2010-11-15',-4,-4);
INSERT INTO UNI_ORDER VALUES(-16,-4,-5,'2010-12-17',-4,-4);
INSERT INTO UNI_ORDER VALUES(-17,-4,-5,'2011-06-01',-4,-4);
INSERT INTO UNI_ORDER VALUES(-18,-4,-5,'2011-10-16',-4,-4);
INSERT INTO UNI_ORDER VALUES(-19,-4,-5,'2013-01-20',-4,-4);
INSERT INTO UNI_ORDER VALUES(-20,-4,-5,'2013-05-24',-4,-4);
INSERT INTO UNI_ORDER VALUES(-21,-5,-6,'2004-01-11',-5,-5);
INSERT INTO UNI_ORDER VALUES(-22,-5,-6,'2012-12-23',-5,-5);
INSERT INTO UNI_ORDER VALUES(-23,-5,-6,'2013-03-22',-5,-5);
INSERT INTO UNI_ORDER VALUES(-24,-5,-6,'2013-06-10',-5,-5);
INSERT INTO UNI_ORDER VALUES(-25,-5,-6,'2013-05-06',-5,-5);
INSERT INTO UNI_ORDER VALUES(-26,-5,-6,'2013-03-12',-5,-5);
INSERT INTO UNI_ORDER VALUES(-27,-5,-6,'2014-02-05',-5,-5);
INSERT INTO UNI_ORDER VALUES(-28,-5,-6,'2014-05-01',-5,-5);
INSERT INTO UNI_ORDER VALUES(-29,-5,-6,'2001-11-19',-5,-5);
INSERT INTO UNI_ORDER VALUES(-30,-6,-7,'2015-12-11',-6,-6);
INSERT INTO UNI_ORDER VALUES(-31,-6,-9,'2009-04-11',-6,-6);
INSERT INTO UNI_ORDER VALUES(-32,-6,-8,'2011-12-10',-6,-6);
INSERT INTO UNI_ORDER VALUES(-33,-6,-7,'2014-05-06',-6,-6);
INSERT INTO UNI_ORDER VALUES(-34,-6,-8,'2012-02-04',-6,-6);
INSERT INTO UNI_ORDER VALUES(-35,-6,-7,'2003-11-02',-6,-6);
INSERT INTO UNI_ORDER VALUES(-36,-6,-9,'2006-09-07',-6,-6);
INSERT INTO UNI_ORDER VALUES(-37,-6,-8,'2016-01-23',-6,-6);
INSERT INTO UNI_ORDER VALUES(-38,-6,-7,'2010-03-14',-6,-6);
INSERT INTO UNI_ORDER VALUES(-39,-6,-7,'2009-10-01',-6,-6);
INSERT INTO UNI_ORDER VALUES(-40,-6,-8,'2003-06-02',-6,-6);




INSERT INTO IMAGE VALUES(-1,"images/Consumer electronics/picture_1.jpg");
INSERT INTO IMAGE VALUES(-2,"images/Consumer electronics/picture_2.jpg");
INSERT INTO IMAGE VALUES(-3,"images/Consumer electronics/picture_3.jpg");
INSERT INTO IMAGE VALUES(-4,"images/Consumer electronics/picture_4.jpg");
INSERT INTO IMAGE VALUES(-5,"images/Consumer electronics/picture_5.jpg");
INSERT INTO IMAGE VALUES(-6,"images/Consumer electronics/picture_6.jpg");
INSERT INTO IMAGE VALUES(-7,"images/Consumer electronics/picture_7.jpg");
INSERT INTO IMAGE VALUES(-8,"images/Consumer electronics/picture_8.jpg");
INSERT INTO IMAGE VALUES(-9,"images/Consumer electronics/picture_9.jpg");
INSERT INTO IMAGE VALUES(-10,"images/Consumer electronics/picture_10.jpg");
INSERT INTO IMAGE VALUES(-11,"images/Consumer electronics/picture_11.jpg");
INSERT INTO IMAGE VALUES(-12,"images/Consumer electronics/picture_12.jpg");
INSERT INTO IMAGE VALUES(-13,"images/Consumer electronics/picture_13.jpg");
INSERT INTO IMAGE VALUES(-14,"images/Consumer electronics/picture_14.jpg");
INSERT INTO IMAGE VALUES(-15,"images/Consumer electronics/picture_15.jpg");
INSERT INTO IMAGE VALUES(-16,"images/Consumer electronics/picture_16.jpg");
INSERT INTO IMAGE VALUES(-17,"images/Consumer electronics/picture_17.jpg");
INSERT INTO IMAGE VALUES(-18,"images/Consumer electronics/picture_18.jpg");
INSERT INTO IMAGE VALUES(-19,"images/Consumer electronics/picture_19.jpg");
INSERT INTO IMAGE VALUES(-20,"images/Consumer electronics/picture_20.jpg");
INSERT INTO IMAGE VALUES(-21,"images/Consumer electronics/picture_21.jpg");
INSERT INTO IMAGE VALUES(-22,"images/Consumer electronics/picture_22.jpg");
INSERT INTO IMAGE VALUES(-23,"images/Consumer electronics/picture_23.jpg");
INSERT INTO IMAGE VALUES(-24,"images/Consumer electronics/picture_24.jpg");
INSERT INTO IMAGE VALUES(-25,"images/Consumer electronics/picture_25.jpg");
INSERT INTO IMAGE VALUES(-26,"images/Consumer electronics/picture_26.jpg");
INSERT INTO IMAGE VALUES(-27,"images/Consumer electronics/picture_27.jpg");
INSERT INTO IMAGE VALUES(-28,"images/Consumer electronics/picture_28.jpg");
INSERT INTO IMAGE VALUES(-29,"images/Consumer electronics/picture_29.jpg");
INSERT INTO IMAGE VALUES(-30,"images/Consumer electronics/picture_30.jpg");
INSERT INTO IMAGE VALUES(-31,"images/Consumer electronics/picture_31.jpg");
INSERT INTO IMAGE VALUES(-32,"images/Consumer electronics/picture_32.jpg");
INSERT INTO IMAGE VALUES(-33,"images/Consumer electronics/picture_33.jpg");
INSERT INTO IMAGE VALUES(-34,"images/Consumer electronics/picture_34.jpg");
INSERT INTO IMAGE VALUES(-35,"images/Consumer electronics/picture_35.jpg");
INSERT INTO IMAGE VALUES(-36,"images/Consumer electronics/picture_36.jpg");
INSERT INTO IMAGE VALUES(-37,"images/Consumer electronics/picture_37.jpg");
INSERT INTO IMAGE VALUES(-38,"images/Consumer electronics/picture_38.jpg");
INSERT INTO IMAGE VALUES(-39,"images/Consumer electronics/picture_39.jpg");
INSERT INTO IMAGE VALUES(-40,"images/Consumer electronics/picture_40.jpg");
INSERT INTO IMAGE VALUES(-41,"images/Consumer electronics/picture_41.jpg");
INSERT INTO IMAGE VALUES(-42,"images/Consumer electronics/picture_42.jpg");
INSERT INTO IMAGE VALUES(-43,"images/Consumer electronics/picture_43.jpg");
INSERT INTO IMAGE VALUES(-44,"images/Consumer electronics/picture_44.jpg");
INSERT INTO IMAGE VALUES(-45,"images/Consumer electronics/picture_45.jpg");
INSERT INTO IMAGE VALUES(-47,"images/Consumer electronics/picture_47.jpg");
INSERT INTO IMAGE VALUES(-48,"images/Consumer electronics/picture_48.jpg");
INSERT INTO IMAGE VALUES(-49,"images/Consumer electronics/picture_49.jpg");
INSERT INTO IMAGE VALUES(-50,"images/Consumer electronics/picture_50.jpg");


INSERT INTO PRODUCT VALUES(-1,'Adidas shoes', 29.9,210,'Morbi faucibus eros eu eros blandit ullamcorper. Pellentesque quis magna ut elit vulputate auctor dignissim ut magna. Quisque quis rutrum nulla. Ut porta tempus euismod. Etiam sollicitudin est id leo aliquet, nec euismod arcu posuere. Phasellus lectus eros, feugiat sit amet mattis eget, fermentum quis neque. Aenean luctus ac neque consectetur tempor. Aliquam elit ligula, viverra a cursus vitae, scelerisque in turpis.', -1, false);
INSERT INTO PRODUCT VALUES(-2,'Adidas sweater', 21,210,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean rhoncus nisi mi, vel tincidunt arcu condimentum quis. Integer nec sagittis mauris. Nunc id egestas elit. Vestibulum et mi cursus, scelerisque libero eget, auctor augue. Cras interdum purus sit amet diam placerat ornare. Suspendisse in tristique neque. Interdum et malesuada fames ac ante ipsum primis in faucibus.', -2, false);
INSERT INTO PRODUCT VALUES(-3,'Adidas trousers', 39.9,210,'Duis tempus tincidunt leo ac convallis. In eros augue, ornare a porta eu, egestas nec orci. Nullam luctus, enim at tempor condimentum, est enim suscipit quam, a congue lorem enim vel orci.', -3, false);
INSERT INTO PRODUCT VALUES(-4,'Nike sweater', 129.9,210,'Aenean commodo vitae quam sit amet bibendum. Aenean nec sem tincidunt lacus pharetra ullamcorper eget ut mauris. Curabitur lacinia nisl in aliquet hendrerit. Curabitur faucibus nibh vel tincidunt posuere. Etiam a lobortis erat. Ut accumsan est ultrices, accumsan velit eu, fringilla nulla.', -4, false);
INSERT INTO PRODUCT VALUES(-5,'Nike shoes', 229.9,210,'Suspendisse eu mauris a tellus faucibus semper eget a tellus. Morbi dignissim nunc risus, vitae porta massa commodo nec. Pellentesque at nisl ipsum. Proin vel finibus dui, a eleifend dui. Morbi felis urna, tempus in vestibulum sit amet, porttitor accumsan libero. Curabitur a viverra dolor.', -5, false);
INSERT INTO PRODUCT VALUES(-6,'Nike trousers', 294.9,210,'Curabitur rhoncus tellus odio. Nam varius non arcu ut pretium. Fusce porta molestie nisi, eget accumsan arcu porta ut. Nullam sit amet varius lacus.', -6, false);
INSERT INTO PRODUCT VALUES(-7,'Asics sweater', 12,210,'Aliquam dictum vehicula neque nec imperdiet. Quisque id tincidunt nibh. Cras tincidunt metus venenatis, mattis lorem nec, lacinia metus. Quisque lacus orci, rutrum a massa eget, faucibus laoreet augue.', -7, false);
INSERT INTO PRODUCT VALUES(-8,'Asics shoes', 29,210,'Cras finibus est ut suscipit semper. Vivamus porttitor ex ipsum, sit amet facilisis libero ullamcorper vitae.', -8, false);
INSERT INTO PRODUCT VALUES(-9,'Samsung GALAXY S4 Mini', 599,100,'Aenean non commodo purus, id lobortis velit. Cras ut velit in neque accumsan accumsan. Duis vel diam lobortis, condimentum erat vel, mollis turpis. In maximus turpis mauris, in pretium ipsum iaculis non. Vestibulum rhoncus dui eget erat auctor, porta posuere nisi scelerisque.', -9, false);
INSERT INTO PRODUCT VALUES(-10,'Samsung GALAXY S4', 790,210,'Nunc felis nunc, semper eu feugiat fringilla, facilisis posuere est. Etiam sit amet felis efficitur, ultrices mauris ut, egestas mauris. Nunc in condimentum purus, ac tincidunt dolor. Morbi purus lorem, venenatis sit amet felis ut, ullamcorper elementum sem. Integer orci nisi, consequat a finibus sagittis, faucibus at neque.', -10, false);
INSERT INTO PRODUCT VALUES(-11,'LG G3', 330,100,'Praesent iaculis fringilla nulla a sollicitudin. Mauris ultrices justo in lacus gravida pulvinar. Quisque eget tortor lorem.', -11, false);
INSERT INTO PRODUCT VALUES(-12,'LG 32LF5610', 599,50,'Etiam volutpat pellentesque dui eget posuere. Mauris non ante nec dui vehicula pellentesque. Mauris finibus aliquet dolor, id semper turpis rutrum ac.', -12, false);
INSERT INTO PRODUCT VALUES(-13,'Samsung UE32J5500', 790,60,'Cras at dictum orci. Fusce ac luctus risus, at mattis elit. Etiam et libero id elit hendrerit elementum eu vitae nisl. Proin sit amet tortor vel magna mattis pharetra ut at eros. Aliquam ac nulla at lacus commodo rhoncus a id nibh.', -13, false);
INSERT INTO PRODUCT VALUES(-14,'NIKON D5200 camera', 390,200,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent rutrum pretium consectetur. Maecenas semper vehicula ornare. Nullam vitae ex ultricies, porta mauris a, viverra lectus. Suspendisse rhoncus malesuada quam id fringilla.', -14, false);
INSERT INTO PRODUCT VALUES(-15,'Zanussi ZRT 18100 WA fridge', 1090,300,'Nulla ut sapien mattis, faucibus nulla quis, fermentum tortor. Proin imperdiet justo quam, venenatis gravida dui dapibus sit amet. Mauris ut leo justo. Sed vestibulum velit eget orci pharetra gravida. Curabitur ut nibh pellentesque, consequat lectus sit amet, cursus tortor. Phasellus suscipit metus vel accumsan interdum. Pellentesque neque magna, varius eu faucibus sit amet, pulvinar id sem.', -15, false);
INSERT INTO PRODUCT VALUES(-16,'Electrolux EWT1062TDW washing machine', 890,100,'Sed vitae justo mattis, viverra orci at, molestie nunc. Maecenas pulvinar mauris at vestibulum dignissim. Sed ullamcorper, risus ac rhoncus semper, ex urna aliquam lectus, quis sollicitudin dolor enim ac purus. Nulla facilisi.', -16, false);
INSERT INTO PRODUCT VALUES(-17,'Electrolux EKG54151OW cooker', 790,200,'Aenean non porttitor diam. Duis non mollis felis, et suscipit dui. Suspendisse ac maximus velit, sed congue velit. Praesent porttitor ante in pulvinar pretium. Vestibulum et orci mi. Sed semper ac elit ut dapibus. Maecenas eu semper risus.', -17, false);
INSERT INTO PRODUCT VALUES(-18,'Bosch BGB45331 vacuum cleaner', 290,210,'Suspendisse cursus velit at nibh luctus, nec euismod massa consectetur. Sed viverra malesuada quam, id aliquet justo volutpat vel. Duis finibus ut orci venenatis hendrerit. In et massa varius, consectetur odio ut, dapibus lacus. Nullam viverra purus at fringilla condimentum.', -18, false);
INSERT INTO PRODUCT VALUES(-19,'Electrolux EMS28201OW microwave oven', 590,100,'Nullam quis nunc porttitor, tristique lorem in, ultrices nisl. Vivamus sed sapien suscipit, vestibulum ante non, feugiat mauris. Nunc quis eleifend justo. Nullam non gravida quam. Etiam at aliquet eros, at scelerisque orci. Morbi ac ultrices diam, consequat tincidunt turpis. Nulla sit amet neque sed nisi venenatis tincidunt vel nec sapien.', -19, false);
INSERT INTO PRODUCT VALUES(-20,'Chanel Coco Mademoiselle woman perfume', 90,200,'Vivamus ut ultricies nisi. Sed sit amet nibh efficitur, tristique erat vitae, blandit mi. Donec ut urna vel massa vestibulum efficitur sit amet fringilla tortor. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus sed sollicitudin lectus, sed faucibus magna. Curabitur aliquam risus ante, sollicitudin tempus leo tincidunt nec.', -20, false);
INSERT INTO PRODUCT VALUES(-21,'Calvin Klein Eternity woman perfume', 70,200,'Nulla ante turpis, aliquet vel dignissim ac, tincidunt nec urna. Sed rhoncus venenatis risus a accumsan. Morbi accumsan interdum aliquet. Proin tincidunt urna ut sapien ultrices, quis tincidunt lectus dignissim. Interdum et malesuada fames ac ante ipsum primis in faucibus.', -21, false);
INSERT INTO PRODUCT VALUES(-22,'Giorgio Armani Si woman perfume', 120,100,'Fusce laoreet quam et elit ultrices ullamcorper. Phasellus ornare nibh a felis fringilla feugiat. Quisque turpis risus, semper et congue et, euismod in turpis. Integer tempus nisi tellus, eget porta mi condimentum id. Phasellus ut leo egestas, varius sem sed, convallis ipsum.', -22, false);
INSERT INTO PRODUCT VALUES(-23,'Medisana upper-arm blood pressure monitor', 290, 70,'Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse potenti. Suspendisse quis nulla iaculis, volutpat mi eget, commodo ante.', -23, false);
INSERT INTO PRODUCT VALUES(-24,'Beurer GL blood glucose meter', 390,40,'Suspendisse vel nibh a dui tincidunt consectetur. Maecenas auctor egestas enim, at elementum arcu cursus et. Donec arcu turpis, sollicitudin eget eleifend eu, pulvinar eu massa. Etiam urna massa, mollis quis urna ac, faucibus iaculis eros. Quisque ultricies et eros non lacinia. Proin eu ligula eu metus suscipit bibendum.', -24, false);
INSERT INTO PRODUCT VALUES(-25,'Omron Eco Temp Smart thermometer', 30,210,'Fusce eu felis auctor, lobortis odio quis, blandit dolor. Praesent faucibus semper consectetur. Suspendisse a nisl nec nisi lacinia pretium in eget lorem. Nulla facilisi. Morbi nec risus id quam accumsan tincidunt. Donec in diam eros. Integer hendrerit, nisl ac finibus lobortis, quam ante hendrerit ante, ut varius nibh sapien et metus.', -25, false);
INSERT INTO PRODUCT VALUES(-26,'Robust Turbo Speed Bike exercise bike', 790,20,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas eleifend felis ac vehicula commodo. Nulla accumsan bibendum lacus, quis aliquam felis rhoncus quis. Sed in finibus urna.', -26, false);
INSERT INTO PRODUCT VALUES(-27,'Robust Marathon treadmill', 1290,10,'Integer enim mauris, facilisis non justo bibendum, egestas aliquet tellus. Maecenas quis condimentum ex, sit amet luctus nisi. Vivamus at tellus ac felis euismod ultrices. Proin vehicula tristique lorem non sollicitudin.', -27, false);
INSERT INTO PRODUCT VALUES(-28,'Robust Magnum weight bench', 990,10,'Sed at eros imperdiet, gravida massa sagittis, consequat risus. Integer at odio at lorem venenatis iaculis nec a nibh. Integer eu congue erat. Cras lacus nisi, malesuada non lacus eget, tempus interdum ipsum. Nullam venenatis gravida ante, vitae commodo enim euismod ut. ', -28, false);
INSERT INTO PRODUCT VALUES(-29,'Guess woman bag', 40,100,'Proin volutpat viverra leo quis viverra. Praesent blandit, tellus sed eleifend viverra, tortor magna vehicula massa, non pellentesque sapien mauris in leo. Proin auctor rutrum metus. Quisque molestie pharetra pulvinar. Phasellus lacus tellus, sollicitudin ac sodales et, suscipit in ligula. Etiam ut risus ut lectus vehicula rutrum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas non magna lacus.', -29, false);
INSERT INTO PRODUCT VALUES(-30,'Ralph Lauren dress', 30,100,'Vestibulum vehicula aliquam purus sit amet ultrices. Fusce auctor varius diam sed finibus. Sed efficitur sagittis velit, eget pharetra libero efficitur nec.', -30, false);
INSERT INTO PRODUCT VALUES(-31,'Bracelet', 40,100,'Proin vehicula, nisi vel laoreet mattis, libero ipsum pulvinar eros, eu pretium tellus nulla quis lorem. Suspendisse at ex nibh. Vestibulum semper leo blandit, vestibulum enim vitae, fringilla ligula. Fusce et dictum ex.', -31, false);
INSERT INTO PRODUCT VALUES(-32,'Bracelet', 40,100,'Ut sem odio, tristique sit amet leo at, euismod condimentum metus. Proin nulla purus, malesuada sit amet ipsum nec, eleifend porta orci. Nulla hendrerit, tortor vel pharetra commodo, purus mauris imperdiet nulla, vel fringilla velit purus ut sem. Proin libero felis, laoreet ac lacinia eget, pharetra eu magna. Cras quis placerat lectus.', -32, false);
INSERT INTO PRODUCT VALUES(-33,'Amie Black Faux Suede boots', 30,100,'Nullam condimentum cursus odio id rhoncus. Sed hendrerit elit vulputate neque commodo, efficitur feugiat erat vulputate. Fusce eget nunc nunc.', -33, false);
INSERT INTO PRODUCT VALUES(-34,'Williams Legato Piano',230,100,'Fusce convallis, nisl quis semper consequat, lacus elit commodo dui, fringilla rutrum nisi purus feugiat felis. Curabitur leo sapien, scelerisque at nulla vitae, congue tempor sapien. Vestibulum ac sapien ipsum. Mauris vitae risus lobortis, varius turpis quis, venenatis elit. Nulla rhoncus vulputate eros, a tincidunt sem posuere nec. Curabitur auctor tristique leo, sed tincidunt est maximus eget. Donec non mauris leo.', -34, false);
INSERT INTO PRODUCT VALUES(-35,'Alesis Demo Drums', 280,100,'Nunc mattis leo tortor, ac dapibus lacus pharetra id. Ut convallis sem lorem. Suspendisse feugiat mollis ante non finibus. Morbi malesuada blandit lobortis. Quisque condimentum mollis neque lobortis bibendum. Mauris egestas lacinia laoreet. Etiam dolor metus, bibendum in augue non, aliquam dictum magna. Vivamus ex purus, viverra et eros ac, pharetra gravida lorem.', -35, false);
INSERT INTO PRODUCT VALUES(-36,'Palmer PD21 Guitar', 90,100,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ut euismod purus. Nunc in risus ac nulla faucibus convallis non vel est. Maecenas tristique aliquet mi, et maximus erat consectetur at.', -36, false);
INSERT INTO PRODUCT VALUES(-37,'Jenga', 10,100,'Sed ornare vel enim ac dictum. Cras malesuada malesuada nisi, sit amet ullamcorper leo vulputate sit amet. Donec iaculis pulvinar neque, quis scelerisque nisi molestie sit amet. Vestibulum at diam ut risus iaculis consequat vitae id ante.', -37, false);
INSERT INTO PRODUCT VALUES(-38,'Hasbro Connect4', 7,100,'Etiam at pretium nunc. Donec nec rhoncus elit. Phasellus ut odio dolor. Aenean elementum nibh eu massa sodales pellentesque. Phasellus sed leo scelerisque, maximus erat in, aliquam elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam posuere leo velit, eu placerat diam rutrum id.', -38, false);
INSERT INTO PRODUCT VALUES(-39,'Uno', 10,100,'Praesent diam felis, ornare vitae libero id, viverra iaculis erat. Praesent ultricies libero vel mauris suscipit, ac tristique mi pharetra. Praesent iaculis erat in lacus tempor mollis. Ut fermentum finibus eros vitae tincidunt. Fusce nisl eros, tristique ac aliquam quis, varius eget lacus. Proin sodales mattis rhoncus.', -39, false);
INSERT INTO PRODUCT VALUES(-40,'Rubiks cube', 20,100,'Duis lorem lacus, bibendum id enim et, lacinia pulvinar lacus. Duis sodales nisl ut neque vehicula blandit. Suspendisse sed metus ornare, laoreet dui commodo, convallis odio. Nullam semper eros a blandit suscipit. Nullam nec arcu auctor, hendrerit quam eget, cursus augue. Duis gravida est odio, at tempus felis tempor in. Cras sollicitudin nibh ligula.', -40, false);
INSERT INTO PRODUCT VALUES(-41,'Annie 2014', 15,100,'Curabitur laoreet sed ex a semper. Aenean condimentum risus erat, ac sollicitudin sapien tempus sit amet. Pellentesque aliquam lectus eget nibh venenatis, at accumsan orci consequat. Morbi hendrerit iaculis dui, quis convallis tortor. Pellentesque eget ullamcorper magna, at varius dolor.', -41, false);
INSERT INTO PRODUCT VALUES(-42,'Kate and Leopold', 15,100,'Nunc viverra scelerisque placerat. Etiam et pharetra odio. Pellentesque maximus interdum nibh, a porta neque vehicula eu. Mauris ultricies convallis enim, vitae venenatis ante pharetra quis. Vivamus finibus posuere justo nec aliquam. Phasellus sed pharetra eros, quis feugiat nulla.', -42, false);
INSERT INTO PRODUCT VALUES(-43,'Road to Avonlea', 15,100,'Aliquam viverra metus quis nisi tempus tristique. Curabitur ut eleifend ligula, eu efficitur felis. Aliquam at felis vitae justo lobortis tempor quis efficitur felis. Nam porta lobortis sem, sed suscipit nulla vestibulum a. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nulla lacinia luctus ligula, ut imperdiet lectus vehicula vel.', -43, false);
INSERT INTO PRODUCT VALUES(-44,'Friends', 15,100,'Integer id placerat nunc, ut lacinia magna. Vivamus vitae aliquam mauris, eget maximus turpis. Cras congue, massa eget lobortis dignissim, diam erat venenatis velit, a sollicitudin lacus libero ut quam. Duis nunc diam, iaculis et pulvinar vel, tempus et dui.', -44, false);
INSERT INTO PRODUCT VALUES(-45,'How I met your mother', 15,100,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nisl sapien, aliquam at libero sed, feugiat pellentesque felis. Quisque eget leo aliquam, porttitor lorem nec, faucibus tortor. Curabitur eleifend magna quis dui scelerisque rhoncus. Nunc congue lacus risus, quis tempor nisl sodales vel. Praesent porta ex et leo ullamcorper iaculis. Aenean congue massa et est blandit, ut dapibus justo tempor. Praesent quis arcu pretium, efficitur justo eu, iaculis augue.', -45, false);

INSERT INTO PRODUCT_TO_IMAGE VALUES (-1,-1);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-2,-2);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-3,-3);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-4,-4);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-5,-5);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-6,-6);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-7,-7);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-8,-8);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-9,-9);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-10,-10);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-11,-11);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-12,-12);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-13,-13);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-14,-14);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-15,-15);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-16,-16);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-17,-17);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-18,-18);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-19,-19);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-20,-20);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-21,-21);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-22,-22);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-23,-23);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-24,-24);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-25,-25);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-26,-26);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-27,-27);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-28,-28);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-29,-29);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-30,-30);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-31,-31);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-32,-32);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-33,-33);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-34,-34);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-35,-35);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-36,-36);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-37,-37);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-38,-38);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-39,-39);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-40,-40);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-41,-41);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-42,-42);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-43,-43);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-44,-44);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-45,-45);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-45,-47);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-44,-48);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-44,-49);
INSERT INTO PRODUCT_TO_IMAGE VALUES (-44,-50);



INSERT INTO PROD_TO_ORDER VALUES(-1,-1,5);
INSERT INTO PROD_TO_ORDER VALUES(-1,-6,1);
INSERT INTO PROD_TO_ORDER VALUES(-2,-4,1);
INSERT INTO PROD_TO_ORDER VALUES(-3,-8,2);
INSERT INTO PROD_TO_ORDER VALUES(-4,-24,2);
INSERT INTO PROD_TO_ORDER VALUES(-4,-44,1);
INSERT INTO PROD_TO_ORDER VALUES(-4,-26,1);
INSERT INTO PROD_TO_ORDER VALUES(-5,-3,1);
INSERT INTO PROD_TO_ORDER VALUES(-5,-17,1);
INSERT INTO PROD_TO_ORDER VALUES(-5,-42,1);
INSERT INTO PROD_TO_ORDER VALUES(-6,-13,1);
INSERT INTO PROD_TO_ORDER VALUES(-7,-11,1);
INSERT INTO PROD_TO_ORDER VALUES(-7,-12,1);
INSERT INTO PROD_TO_ORDER VALUES(-8,-15,1);
INSERT INTO PROD_TO_ORDER VALUES(-9,-22,1);
INSERT INTO PROD_TO_ORDER VALUES(-10,-1,1);
INSERT INTO PROD_TO_ORDER VALUES(-11,-33,1);
INSERT INTO PROD_TO_ORDER VALUES(-12,-36,1);
INSERT INTO PROD_TO_ORDER VALUES(-13,-18,1);
INSERT INTO PROD_TO_ORDER VALUES(-14,-6,1);
INSERT INTO PROD_TO_ORDER VALUES(-15,-39,1);
INSERT INTO PROD_TO_ORDER VALUES(-16,-40,1);
INSERT INTO PROD_TO_ORDER VALUES(-17,-10,1);
INSERT INTO PROD_TO_ORDER VALUES(-18,-18,1);
INSERT INTO PROD_TO_ORDER VALUES(-19,-45,1);
INSERT INTO PROD_TO_ORDER VALUES(-20,-20,3);
INSERT INTO PROD_TO_ORDER VALUES(-21,-20,1);
INSERT INTO PROD_TO_ORDER VALUES(-22,-16,1);
INSERT INTO PROD_TO_ORDER VALUES(-23,-38,1);
INSERT INTO PROD_TO_ORDER VALUES(-24,-3,1);
INSERT INTO PROD_TO_ORDER VALUES(-25,-2,1);
INSERT INTO PROD_TO_ORDER VALUES(-26,-9,1);
INSERT INTO PROD_TO_ORDER VALUES(-27,-20,1);
INSERT INTO PROD_TO_ORDER VALUES(-28,-16,2);
INSERT INTO PROD_TO_ORDER VALUES(-29,-43,1);
INSERT INTO PROD_TO_ORDER VALUES(-30,-41,1);
INSERT INTO PROD_TO_ORDER VALUES(-31,-42,1);
INSERT INTO PROD_TO_ORDER VALUES(-32,-29,1);
INSERT INTO PROD_TO_ORDER VALUES(-33,-29,1);
INSERT INTO PROD_TO_ORDER VALUES(-34,-24,1);
INSERT INTO PROD_TO_ORDER VALUES(-35,-23,1);
INSERT INTO PROD_TO_ORDER VALUES(-36,-22,2);
INSERT INTO PROD_TO_ORDER VALUES(-37,-27,1);
INSERT INTO PROD_TO_ORDER VALUES(-38,-28,1);
INSERT INTO PROD_TO_ORDER VALUES(-38,-20,1);
INSERT INTO PROD_TO_ORDER VALUES(-39,-41,1);
INSERT INTO PROD_TO_ORDER VALUES(-39,-43,1);
INSERT INTO PROD_TO_ORDER VALUES(-39,-42,1);
INSERT INTO PROD_TO_ORDER VALUES(-40,-20,1);



INSERT INTO CATEGORY VALUES(-1,'Clothes');
INSERT INTO CATEGORY VALUES(-2,'Mobile phones');
INSERT INTO CATEGORY VALUES(-3,'TV');
INSERT INTO CATEGORY VALUES(-4,'Kitchen appliances');
INSERT INTO CATEGORY VALUES(-5,'Shoes');
INSERT INTO CATEGORY VALUES(-6,'Fregrance');
INSERT INTO CATEGORY VALUES(-7,'Washing machines');
INSERT INTO CATEGORY VALUES(-8,'Vacuum cleaners');
INSERT INTO CATEGORY VALUES(-9,'Health');
INSERT INTO CATEGORY VALUES(-10,'Sport');
INSERT INTO CATEGORY VALUES(-11,'Bags');
INSERT INTO CATEGORY VALUES(-12,'Jewelry');
INSERT INTO CATEGORY VALUES(-13,'Instruments');
INSERT INTO CATEGORY VALUES(-14,'Toys');
INSERT INTO CATEGORY VALUES(-15,'Movies and series');
INSERT INTO CATEGORY VALUES(-16,'Digital cameras');

INSERT INTO CAT_TO_PROD VALUES(-1, -5);
INSERT INTO CAT_TO_PROD VALUES(-2, -1);
INSERT INTO CAT_TO_PROD VALUES(-3, -1);
INSERT INTO CAT_TO_PROD VALUES(-4, -1);
INSERT INTO CAT_TO_PROD VALUES(-5, -5);
INSERT INTO CAT_TO_PROD VALUES(-6, -1);
INSERT INTO CAT_TO_PROD VALUES(-7, -1);
INSERT INTO CAT_TO_PROD VALUES(-8, -5);
INSERT INTO CAT_TO_PROD VALUES(-9, -2);
INSERT INTO CAT_TO_PROD VALUES(-10, -2);
INSERT INTO CAT_TO_PROD VALUES(-11, -2);
INSERT INTO CAT_TO_PROD VALUES(-12, -3);
INSERT INTO CAT_TO_PROD VALUES(-13, -3);
INSERT INTO CAT_TO_PROD VALUES(-14, -16);
INSERT INTO CAT_TO_PROD VALUES(-15, -4);
INSERT INTO CAT_TO_PROD VALUES(-16, -7);
INSERT INTO CAT_TO_PROD VALUES(-17, -4);
INSERT INTO CAT_TO_PROD VALUES(-18, -8);
INSERT INTO CAT_TO_PROD VALUES(-19, -4);
INSERT INTO CAT_TO_PROD VALUES(-20, -6);
INSERT INTO CAT_TO_PROD VALUES(-21, -6);
INSERT INTO CAT_TO_PROD VALUES(-22, -6);
INSERT INTO CAT_TO_PROD VALUES(-23, -9);
INSERT INTO CAT_TO_PROD VALUES(-24, -9);
INSERT INTO CAT_TO_PROD VALUES(-25, -9);
INSERT INTO CAT_TO_PROD VALUES(-26, -10);
INSERT INTO CAT_TO_PROD VALUES(-27, -10);
INSERT INTO CAT_TO_PROD VALUES(-28, -10);
INSERT INTO CAT_TO_PROD VALUES(-29, -11);
INSERT INTO CAT_TO_PROD VALUES(-31, -12);
INSERT INTO CAT_TO_PROD VALUES(-32, -12);
INSERT INTO CAT_TO_PROD VALUES(-33, -5);
INSERT INTO CAT_TO_PROD VALUES(-34, -13);
INSERT INTO CAT_TO_PROD VALUES(-35, -13);
INSERT INTO CAT_TO_PROD VALUES(-36, -13);
INSERT INTO CAT_TO_PROD VALUES(-37, -14);
INSERT INTO CAT_TO_PROD VALUES(-38, -14);
INSERT INTO CAT_TO_PROD VALUES(-39, -14);
INSERT INTO CAT_TO_PROD VALUES(-40, -14);
INSERT INTO CAT_TO_PROD VALUES(-41, -15);
INSERT INTO CAT_TO_PROD VALUES(-42, -15);
INSERT INTO CAT_TO_PROD VALUES(-43, -15);
INSERT INTO CAT_TO_PROD VALUES(-44, -15);
INSERT INTO CAT_TO_PROD VALUES(-45, -15);

INSERT INTO TAG VALUES(-1,'sweater');
INSERT INTO TAG VALUES(-2,'trousers');
INSERT INTO TAG VALUES(-3,'boots');
INSERT INTO TAG VALUES(-4,'sneaker');
INSERT INTO TAG VALUES(-5,'bracelet');
INSERT INTO TAG VALUES(-6,'necklace');
INSERT INTO TAG VALUES(-7,'earring');
INSERT INTO TAG VALUES(-8,'ring');
INSERT INTO TAG VALUES(-9,'movie');
INSERT INTO TAG VALUES(-10,'series');
INSERT INTO TAG VALUES(-11,'comedy');
INSERT INTO TAG VALUES(-12,'romance');
INSERT INTO TAG VALUES(-13,'sitcom');
INSERT INTO TAG VALUES(-14,'drama');
INSERT INTO TAG VALUES(-15,'action');
INSERT INTO TAG VALUES(-16,'sci-fi');
INSERT INTO TAG VALUES(-17,'fantasy');
INSERT INTO TAG VALUES(-18,'costume');
INSERT INTO TAG VALUES(-19,'mobile');
INSERT INTO TAG VALUES(-20,'television');
INSERT INTO TAG VALUES(-21,'electronics');
INSERT INTO TAG VALUES(-22,'fregrance');
INSERT INTO TAG VALUES(-23,'health');
INSERT INTO TAG VALUES(-24,'music');
INSERT INTO TAG VALUES(-25,'games');
INSERT INTO TAG VALUES(-26,'dress');
INSERT INTO TAG VALUES(-27,'bag');






INSERT INTO PROD_TO_TAG VALUES(-2,-1);
INSERT INTO PROD_TO_TAG VALUES(-7,-1);
INSERT INTO PROD_TO_TAG VALUES(-4,-1);
INSERT INTO PROD_TO_TAG VALUES(-3,-2);
INSERT INTO PROD_TO_TAG VALUES(-33,-3);
INSERT INTO PROD_TO_TAG VALUES(-1,-4);
INSERT INTO PROD_TO_TAG VALUES(-5,-4);
INSERT INTO PROD_TO_TAG VALUES(-8,-4);
INSERT INTO PROD_TO_TAG VALUES(-31,-5);
INSERT INTO PROD_TO_TAG VALUES(-32,-5);
INSERT INTO PROD_TO_TAG VALUES(-41,-9);
INSERT INTO PROD_TO_TAG VALUES(-42,-9);
INSERT INTO PROD_TO_TAG VALUES(-43,-10);
INSERT INTO PROD_TO_TAG VALUES(-44,-10);
INSERT INTO PROD_TO_TAG VALUES(-45,-10);
INSERT INTO PROD_TO_TAG VALUES(-41,-11);
INSERT INTO PROD_TO_TAG VALUES(-42,-11);
INSERT INTO PROD_TO_TAG VALUES(-42,-12);
INSERT INTO PROD_TO_TAG VALUES(-44,-13);
INSERT INTO PROD_TO_TAG VALUES(-45,-13);
INSERT INTO PROD_TO_TAG VALUES(-43,-18);
INSERT INTO PROD_TO_TAG VALUES(-6,-2);
INSERT INTO PROD_TO_TAG VALUES(-9,-19);
INSERT INTO PROD_TO_TAG VALUES(-10,-19);
INSERT INTO PROD_TO_TAG VALUES(-11,-19);
INSERT INTO PROD_TO_TAG VALUES(-12,-20);
INSERT INTO PROD_TO_TAG VALUES(-13,-20);
INSERT INTO PROD_TO_TAG VALUES(-9,-21);
INSERT INTO PROD_TO_TAG VALUES(-10,-21);
INSERT INTO PROD_TO_TAG VALUES(-11,-21);
INSERT INTO PROD_TO_TAG VALUES(-12,-21);
INSERT INTO PROD_TO_TAG VALUES(-13,-21);
INSERT INTO PROD_TO_TAG VALUES(-14,-21);
INSERT INTO PROD_TO_TAG VALUES(-15,-21);
INSERT INTO PROD_TO_TAG VALUES(-16,-21);
INSERT INTO PROD_TO_TAG VALUES(-17,-21);
INSERT INTO PROD_TO_TAG VALUES(-18,-21);
INSERT INTO PROD_TO_TAG VALUES(-19,-21);
INSERT INTO PROD_TO_TAG VALUES(-20,-22);
INSERT INTO PROD_TO_TAG VALUES(-21,-22);
INSERT INTO PROD_TO_TAG VALUES(-22,-22);
INSERT INTO PROD_TO_TAG VALUES(-23,-23);
INSERT INTO PROD_TO_TAG VALUES(-24,-23);
INSERT INTO PROD_TO_TAG VALUES(-25,-23);
INSERT INTO PROD_TO_TAG VALUES(-26,-23);
INSERT INTO PROD_TO_TAG VALUES(-27,-23);
INSERT INTO PROD_TO_TAG VALUES(-28,-23);
INSERT INTO PROD_TO_TAG VALUES(-29,-27);
INSERT INTO PROD_TO_TAG VALUES(-30,-26);
INSERT INTO PROD_TO_TAG VALUES(-34,-24);
INSERT INTO PROD_TO_TAG VALUES(-35,-24);
INSERT INTO PROD_TO_TAG VALUES(-36,-24);
INSERT INTO PROD_TO_TAG VALUES(-37,-25);
INSERT INTO PROD_TO_TAG VALUES(-38,-25);
INSERT INTO PROD_TO_TAG VALUES(-39,-25);
INSERT INTO PROD_TO_TAG VALUES(-40,-25);





COMMIT;