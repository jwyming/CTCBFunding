/*=========================================================================================*/
/*						 			DROP CONSTRAINT		 									*/
/*=========================================================================================*/

ALTER TABLE T_ACCOUNT
   DROP CONSTRAINT FK_ACCOUNT_USER
/


ALTER TABLE T_CASHACCOUNT
   DROP CONSTRAINT FK_CASHACCOUNT_ACCOUNT
/

ALTER TABLE T_CASHACCOUNT
   DROP CONSTRAINT FK_CASHACCOUNT_CURRENCY
/

ALTER TABLE T_FUNDACCOUNT
   DROP CONSTRAINT FK_FUNDACCOUNT_FUND
/


ALTER TABLE T_FUNDACCOUNT
   DROP CONSTRAINT FK_FUNDACCOUNT_ACCOUNT
/


ALTER TABLE T_FUNDEQUITY
   DROP CONSTRAINT FK_FUNDEQUITY_FUND
/


ALTER TABLE T_REPORTDATA
   DROP CONSTRAINT FK_REPORTDATA_REPORTINFO
/


ALTER TABLE T_REPORTDATA
   DROP CONSTRAINT FK_REPORT_USER
/


ALTER TABLE T_RESOURCE_LOCATION
   DROP CONSTRAINT FK_RESOURCEHREF_RESOURCE
/


ALTER TABLE T_RESOURCE_LOCATION
   DROP CONSTRAINT FK_RESOURCELOCATION_LOCATION
/


ALTER TABLE T_ROLE_RESOURCE
   DROP CONSTRAINT FK_ROLERESOURCE_RESOURCE
/


ALTER TABLE T_ROLE_RESOURCE
   DROP CONSTRAINT FK_ROLERESOURCE_ROLE
/


ALTER TABLE T_SAVINGPLAN
   DROP CONSTRAINT FK_SAVINGPLAN_ACCOUNT
/


ALTER TABLE T_SAVINGPLAN
   DROP CONSTRAINT FK_SAVINGPLAN_FUND
/


ALTER TABLE T_TRADE
   DROP CONSTRAINT FK_TRADE_ACCOUNT1
/


ALTER TABLE T_TRADE
   DROP CONSTRAINT FK_TRADE_ACCOUNT_2
/


ALTER TABLE T_TRADE
   DROP CONSTRAINT FK_TRADE_USER
/


ALTER TABLE T_TRADETASK
   DROP CONSTRAINT FK_TRADETASK_TRADE
/


ALTER TABLE T_USER_ROLE
   DROP CONSTRAINT FK_USERROLE_ROLE
/


ALTER TABLE T_USER_ROLE
   DROP CONSTRAINT FK_USERROLE_USER
/

ALTER TABLE "T_EXCHANGERATE"
   DROP CONSTRAINT FK_SCURRENCY_CURRENCY
/


ALTER TABLE "T_EXCHANGERATE"
   DROP CONSTRAINT FK_DCURRENCY_CURRENCY
/


ALTER TABLE "T_FUND"
   DROP CONSTRAINT FK_FUND_FUNDAREA
/


ALTER TABLE "T_FUND"
   DROP CONSTRAINT FK_FUND_FUNDTYPE
/


ALTER TABLE "T_FUND"
   DROP CONSTRAINT FK_FUND_FUNDINDUSTRY
/

ALTER TABLE "T_FUND"
   DROP CONSTRAINT FK_FUND_FUNDCOMPANY
/

ALTER TABLE "T_TRADE"
   DROP CONSTRAINT FK_TRADE_CURRENCY
/

ALTER TABLE "T_SAVINGPLAN"
   DROP CONSTRAINT FK_SAVINGPLAN_CURRENCY
/
ALTER TABLE "T_LOG"
   DROP CONSTRAINT FK_LOG_USER
/

/*=========================================================================================*/
/*						 				DROP INDEX		 									*/
/*=========================================================================================*/

DROP INDEX "INDEX_FUNDEQUITY_FUND"
/

/*=========================================================================================*/
/*						 				DROP TABLE		 									*/
/*=========================================================================================*/

DROP TABLE T_ACCOUNT CASCADE CONSTRAINTS
/


DROP TABLE T_CASHACCOUNT CASCADE CONSTRAINTS
/


DROP TABLE T_EXCHANGERATE CASCADE CONSTRAINTS
/


DROP TABLE T_FUND CASCADE CONSTRAINTS
/


DROP TABLE T_FUNDACCOUNT CASCADE CONSTRAINTS
/


DROP TABLE T_FUNDEQUITY CASCADE CONSTRAINTS
/


DROP TABLE T_HOLIDAY CASCADE CONSTRAINTS
/


DROP TABLE T_LOCATION CASCADE CONSTRAINTS
/


DROP TABLE T_LOG CASCADE CONSTRAINTS
/


DROP TABLE T_RANKSET CASCADE CONSTRAINTS
/


DROP TABLE T_REPORTDATA CASCADE CONSTRAINTS
/


DROP TABLE T_REPORTINFO CASCADE CONSTRAINTS
/


DROP TABLE T_RESOURCE CASCADE CONSTRAINTS
/


DROP TABLE T_RESOURCE_LOCATION CASCADE CONSTRAINTS
/


DROP TABLE T_ROLE CASCADE CONSTRAINTS
/


DROP TABLE T_ROLE_RESOURCE CASCADE CONSTRAINTS
/


DROP TABLE T_SAVINGPLAN CASCADE CONSTRAINTS
/


DROP TABLE T_SYSPARAM CASCADE CONSTRAINTS
/


DROP TABLE T_TIMERTASK CASCADE CONSTRAINTS
/


DROP TABLE T_TRADE CASCADE CONSTRAINTS
/


DROP TABLE T_TRADETASK CASCADE CONSTRAINTS
/


DROP TABLE T_TRADETIME CASCADE CONSTRAINTS
/


DROP TABLE T_USER CASCADE CONSTRAINTS
/


DROP TABLE T_USER_ROLE CASCADE CONSTRAINTS
/


DROP TABLE T_CURRENCY CASCADE CONSTRAINTS
/


DROP TABLE T_FUNDAREA CASCADE CONSTRAINTS
/


DROP TABLE T_FUNDINDUSTRY CASCADE CONSTRAINTS
/


DROP TABLE T_FUNDTYPE CASCADE CONSTRAINTS
/

DROP TABLE T_FUNDCOMPANY CASCADE CONSTRAINTS
/

/*=========================================================================================*/
/*						 				DROP SEQUENCE	 									*/
/*=========================================================================================*/

DROP SEQUENCE "SEQ_ACCOUNT"
/


DROP SEQUENCE "SEQ_EXCHANGERATE"
/


DROP SEQUENCE "SEQ_FUND"
/


DROP SEQUENCE "SEQ_FUNDEQUITY"
/


DROP SEQUENCE "SEQ_HOLIDAY"
/


DROP SEQUENCE "SEQ_LOCATION"
/


DROP SEQUENCE "SEQ_LOG"
/


DROP SEQUENCE "SEQ_RANKSET"
/


DROP SEQUENCE "SEQ_REPORTDATA"
/


DROP SEQUENCE "SEQ_REPORTINFO"
/


DROP SEQUENCE "SEQ_RESOURCE"
/


DROP SEQUENCE "SEQ_RESOURCE_LOCATION"
/


DROP SEQUENCE "SEQ_ROLE"
/


DROP SEQUENCE "SEQ_ROLE_RESCOURCE"
/


DROP SEQUENCE "SEQ_SAVINGPLAN"
/


DROP SEQUENCE "SEQ_SYSPARAM"
/


DROP SEQUENCE "SEQ_TIMERTASK"
/


DROP SEQUENCE "SEQ_TRADE"
/


DROP SEQUENCE "SEQ_TRADETASK"
/


DROP SEQUENCE "SEQ_TRADETIMER"
/


DROP SEQUENCE "SEQ_USER"
/


DROP SEQUENCE "SEQ_USER_ROLE"
/



DROP SEQUENCE "SEQ_CURRENCY"
/


DROP SEQUENCE "SEQ_FUNDAREA"
/


DROP SEQUENCE "SEQ_FUNDINDUSTRY"
/


DROP SEQUENCE "SEQ_FUNDTYPE"
/

DROP SEQUENCE "SEQ_FUNDCOMPANY"
/

/*=========================================================================================*/
/*						 				CREATE SEQUENCE	 									*/
/*=========================================================================================*/

CREATE SEQUENCE "SEQ_ACCOUNT"
START WITH 10000
/


CREATE SEQUENCE "SEQ_EXCHANGERATE"
START WITH 10000
/


CREATE SEQUENCE "SEQ_FUND"
START WITH 10000
/


CREATE SEQUENCE "SEQ_FUNDEQUITY"
START WITH 10000
/


CREATE SEQUENCE "SEQ_HOLIDAY"
START WITH 10000
/


CREATE SEQUENCE "SEQ_LOCATION"
START WITH 10000
/


CREATE SEQUENCE "SEQ_LOG"
START WITH 10000
/


CREATE SEQUENCE "SEQ_RANKSET"
START WITH 10000
/


CREATE SEQUENCE "SEQ_REPORTDATA"
START WITH 10000
/


CREATE SEQUENCE "SEQ_REPORTINFO"
START WITH 10000
/


CREATE SEQUENCE "SEQ_RESOURCE"
START WITH 10000
/


CREATE SEQUENCE "SEQ_RESOURCE_LOCATION"
START WITH 10000
/


CREATE SEQUENCE "SEQ_ROLE"
START WITH 10000
/


CREATE SEQUENCE "SEQ_ROLE_RESCOURCE"
START WITH 10000
/


CREATE SEQUENCE "SEQ_SAVINGPLAN"
START WITH 10000
/


CREATE SEQUENCE "SEQ_SYSPARAM"
START WITH 10000
/


CREATE SEQUENCE "SEQ_TIMERTASK"
START WITH 10000
/


CREATE SEQUENCE "SEQ_TRADE"
START WITH 10000
/


CREATE SEQUENCE "SEQ_TRADETASK"
START WITH 10000
/


CREATE SEQUENCE "SEQ_TRADETIMER"
START WITH 10000
/


CREATE SEQUENCE "SEQ_USER"
START WITH 10000
/


CREATE SEQUENCE "SEQ_USER_ROLE"
START WITH 10000
/

CREATE SEQUENCE "SEQ_CURRENCY"
START WITH 10000
/


CREATE SEQUENCE "SEQ_FUNDAREA"
START WITH 10000
/


CREATE SEQUENCE "SEQ_FUNDINDUSTRY"
START WITH 10000
/


CREATE SEQUENCE "SEQ_FUNDTYPE"
START WITH 10000
/


CREATE SEQUENCE "SEQ_FUNDCOMPANY"
START WITH 10000
/

/*=========================================================================================*/
/*						 				CREATE TABLE	 									*/
/*=========================================================================================*/

/*==============================================================*/
/* TABLE: T_ACCOUNT                                             */
/*==============================================================*/


CREATE TABLE T_ACCOUNT  (
   ID                   NUMBER                           NOT NULL,
   "USERID"             NUMBER                           NOT NULL,
   "TYPE"               NUMBER                           NOT NULL,
   "CREATETIME"         DATE                             NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_ACCOUNT PRIMARY KEY (ID)
)
/


/*==============================================================*/
/* TABLE: T_CASHACCOUNT                                         */
/*==============================================================*/


CREATE TABLE T_CASHACCOUNT  (
   "ACCOUNTID"          NUMBER                           NOT NULL,
   "COUNT"              NUMBER                           NOT NULL,
   "CURRENCY"			NUMBER                           NOT NULL,
   CONSTRAINT PK_T_CASHACCOUNT PRIMARY KEY ("ACCOUNTID")
)
/


/*==============================================================*/
/* TABLE: T_EXCHANGERATE                                        */
/*==============================================================*/


CREATE TABLE T_EXCHANGERATE  (
   "ID"                 NUMBER                           NOT NULL,
   "SCURRENCY"          NUMBER                           NOT NULL,
   "DCURRENCY"          NUMBER                           NOT NULL,
   "EXCHANGERATE"       NUMBER                           NOT NULL,
   "UPDATETIME"         DATE                             NOT NULL,
   CONSTRAINT PK_T_EXCHANGERATE PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_FUND                                                */
/*==============================================================*/


CREATE TABLE T_FUND  (
   "ID"                 NUMBER                           NOT NULL,
   "NAME"               VARCHAR(64)                      NOT NULL,
   "CODE"               VARCHAR(20)                      NOT NULL,
   "CURRENCY"           NUMBER                           NOT NULL,
   "TYPE"               NUMBER                           NOT NULL,
   "COMPANY"            NUMBER                           NOT NULL,
   "AREA"               NUMBER                           NOT NULL,
   "INDUSTRY"           NUMBER                           NOT NULL,
   "STATUS"             NUMBER                           NOT NULL,
   "URL"                VARCHAR(256),
   "REMARK"             VARCHAR(2000),
   CONSTRAINT PK_T_FUND PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_2_T_FUND UNIQUE ("CODE")
)
/


/*==============================================================*/
/* TABLE: T_FUNDACCOUNT                                         */
/*==============================================================*/


CREATE TABLE T_FUNDACCOUNT  (
   "ACCOUNTID"          NUMBER                           NOT NULL,
   "COUNT"              NUMBER                           NOT NULL,
   "FUNDID"             NUMBER                           NOT NULL,
   "INCOMINGSET"        NUMBER,
   "INITCOUNT"          NUMBER                           NOT NULL,
   CONSTRAINT PK_T_FUNDACCOUNT PRIMARY KEY ("ACCOUNTID")
)
/


/*==============================================================*/
/* TABLE: T_FUNDEQUITY                                          */
/*==============================================================*/


CREATE TABLE T_FUNDEQUITY  (
   "ID"                 NUMBER                           NOT NULL,
   "FUNDID"             NUMBER                           NOT NULL,
   "TIME"               DATE                             NOT NULL,
   "EQUITY"             NUMBER                           NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_FUNDEQUITY PRIMARY KEY ("ID")
)
/





/*==============================================================*/
/* TABLE: T_HOLIDAY                                             */
/*==============================================================*/


CREATE TABLE T_HOLIDAY  (
   "ID"                 NUMBER                           NOT NULL,
   "DAY"                DATE                             NOT NULL,
   "STATE"              NUMBER,
   CONSTRAINT PK_T_HOLIDAY PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_LOCATION                                            */
/*==============================================================*/


CREATE TABLE T_LOCATION  (
   "ID"                 NUMBER                           NOT NULL,
   "URL"                VARCHAR(256)                     NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_LOCATION PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_LOG                                                 */
/*==============================================================*/


CREATE TABLE T_LOG  (
   "ID"                 NUMBER                           NOT NULL,
   "TYPE"               NUMBER                           NOT NULL,
   "USERID"           	NUMBER                      NOT NULL,
   "TIME"               DATE                             NOT NULL,
   "CONTENT"            VARCHAR(256),
   CONSTRAINT PK_T_LOG PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_RANKSET                                             */
/*==============================================================*/


CREATE TABLE T_RANKSET  (
   "ID"                 NUMBER                           NOT NULL,
   "YEAR"               NUMBER,
   "QUARTER"            NUMBER,
   "TOPIC"              VARCHAR(64)                      NOT NULL,
   "CREATETIME"         DATE                             NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_RANKSET PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_REPORTDATA                                          */
/*==============================================================*/


CREATE TABLE T_REPORTDATA  (
   "ID"                 NUMBER                           NOT NULL,
   "REPORTID"           NUMBER                           NOT NULL,
   "RANK"               NUMBER                           NOT NULL,
   "USERID"             NUMBER                           NOT NULL,
   "STARTEQUITY"        NUMBER                           NOT NULL,
   "ENDEQUITY"          NUMBER                           NOT NULL,
   "INCOMING"           NUMBER                           NOT NULL,
   "INCOMINGRATE"       NUMBER                           NOT NULL,
   CONSTRAINT PK_T_REPORTDATA PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_REPORTINFO                                          */
/*==============================================================*/


CREATE TABLE T_REPORTINFO  (
   "ID"                 NUMBER                           NOT NULL,
   "YEAR"               NUMBER                           NOT NULL,
   "QUARTER"            NUMBER                           NOT NULL,
   "TOPIC"              VARCHAR(64)                      NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_REPORTINFO PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_RESOURCE                                            */
/*==============================================================*/


CREATE TABLE T_RESOURCE  (
   "ID"                 NUMBER                           NOT NULL,
   "NAME"               VARCHAR(64)                      NOT NULL,
   "REMARK"             VARCHAR(256),
   "PARENTID"           NUMBER,
   "TYPE"               NUMBER                           NOT NULL,
   CONSTRAINT PK_T_RESOURCE PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_RESOURCES2 UNIQUE ("NAME")
)
/


/*==============================================================*/
/* TABLE: T_RESOURCE_LOCATION                                   */
/*==============================================================*/


CREATE TABLE T_RESOURCE_LOCATION  (
   "ID"                 NUMBER                           NOT NULL,
   "RESOURCEID"         NUMBER                           NOT NULL,
   "LOCATIONID"     NUMBER                           NOT NULL,
   CONSTRAINT PK_T_RESOURCE_LOCATION PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_2_T_RESOUR UNIQUE ("LOCATIONID", "RESOURCEID")
)
/


/*==============================================================*/
/* TABLE: T_ROLE                                                */
/*==============================================================*/


CREATE TABLE T_ROLE  (
   "ID"                 NUMBER                           NOT NULL,
   "NAME"               VARCHAR(32)                      NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_ROLE PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_2_T_ROLE UNIQUE ("NAME")
)
/


/*==============================================================*/
/* TABLE: T_ROLE_RESOURCE                                       */
/*==============================================================*/


CREATE TABLE T_ROLE_RESOURCE  (
   "ID"                 NUMBER                           NOT NULL,
   "RESOURCEID"          NUMBER                          NOT NULL,
   "ROLEID"             NUMBER                           NOT NULL,
   CONSTRAINT PK_T_ROLE_RESOURCE PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_RESOURCEA2 UNIQUE ("RESOURCEID", "ROLEID")
)
/


/*==============================================================*/
/* TABLE: T_SAVINGPLAN                                          */
/*==============================================================*/


CREATE TABLE T_SAVINGPLAN  (
   "ID"                 NUMBER                           NOT NULL,
   "ACCOUNTID"          NUMBER                           NOT NULL,
   "TRADETYPE"          NUMBER                           NOT NULL,
   "CREATETIME"         DATE,
   "UPDATETIME"         DATE                             NOT NULL,
   "FUNDID"             NUMBER                           NOT NULL,
   "COUNT"              NUMBER                           NOT NULL,
   "CURRENCY"			NUMBER				 			NOT NULL,
   "DAY"                NUMBER                           NOT NULL,
   "INCOMINGSET"        NUMBER                           NOT NULL,
   "RATE"               NUMBER,
   "STATUS"             NUMBER                           NOT NULL,
   CONSTRAINT PK_T_SAVINGPLAN PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_SYSPARAM                                            */
/*==============================================================*/


CREATE TABLE T_SYSPARAM  (
   "ID"                 NUMBER                           NOT NULL,
   "VALUE"              VARCHAR(32)                      NOT NULL,
   "NAME"               VARCHAR(32)                      NOT NULL,
   "REMARK"             VARCHAR(256),
   CONSTRAINT PK_T_SYSPARAM PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_2_T_SYSPAR UNIQUE ("NAME")
)
/


/*==============================================================*/
/* TABLE: T_TIMERTASK                                           */
/*==============================================================*/


CREATE TABLE T_TIMERTASK  (
   "ID"                 NUMBER                           NOT NULL,
   "TASKTYPE"           NUMBER                           NOT NULL,
   "CREATETIME"         DATE                             NOT NULL,
   "TASKCOUNT"          NUMBER                           NOT NULL,
   "STATUS"             NUMBER                           NOT NULL,
   "CONTENT"            VARCHAR(256),
   CONSTRAINT PK_T_TIMERTASK PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_TRADE                                               */
/*==============================================================*/


CREATE TABLE T_TRADE  (
   "ID"                 NUMBER                           NOT NULL,
   "USERID"             NUMBER                           NOT NULL,
   "TYPE"               NUMBER                           NOT NULL,
   "CURRENCY"		NUMBER				 NOT NULL,
   "SACCOUNTID"         NUMBER                           NOT NULL,
   "DACCOUNTID"         NUMBER                           NOT NULL,
   "TRADEMODE"               NUMBER,
   "COUNT"              NUMBER,
   "INCOMINGSET"        NUMBER,
   "CREATETIME"         DATE                             NOT NULL,
   "STATUS"             NUMBER                           NOT NULL,
   "SETTIME"            DATE                             NOT NULL,
   "TRADETIME"          DATE,
   "FEE"                NUMBER,
   CONSTRAINT PK_T_TRADE PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_TRADETASK                                           */
/*==============================================================*/


CREATE TABLE T_TRADETASK  (
   "ID"                 NUMBER                           NOT NULL,
   "TRADEID"            NUMBER                           NOT NULL,
   "CREATETIME"         DATE                             NOT NULL,
   "PLANTIME"           DATE                             NOT NULL,
   "STATUS"             NUMBER                           NOT NULL,
   "COUNT"              NUMBER,
   "LASTTIME"           DATE,
   "REMARK"				VARCHAR(256),
   CONSTRAINT PK_T_TRADETASK PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_TRADETIME                                           */
/*==============================================================*/


CREATE TABLE T_TRADETIME  (
   "ID"                 NUMBER                           NOT NULL,
   "TRADETYPE"          NUMBER                           NOT NULL,
   "FUNDAREA"           NUMBER                           NOT NULL,
   "FUNDTYPE"           NUMBER                           NOT NULL,
   "FUNDID"             NUMBER                           NOT NULL,
   "BEGINTIME"          DATE                             NOT NULL,
   "ENDTIME"            DATE                             NOT NULL,
   CONSTRAINT PK_T_TRADETIME PRIMARY KEY ("ID")
)
/


/*==============================================================*/
/* TABLE: T_USER                                                */
/*==============================================================*/


CREATE TABLE T_USER  (
   "ID"                 NUMBER                           NOT NULL,
   "USERNAME"           VARCHAR(64)                      NOT NULL,
   "PASSWORD"           VARCHAR(64)                      NOT NULL,
   "CREATETIME"         DATE                             NOT NULL,
   "NAME"               VARCHAR(64),
   "SEX"                NUMBER,
   "COMPANY"            VARCHAR(256),
   "ADDRESS"            VARCHAR(256),
   "POST"               CHAR(6),
   "EMAIL"              VARCHAR(64),
   "PHONE"              VARCHAR(32),
   "STATUS"             NUMBER                           NOT NULL,
   "REMARK"             VARCHAR(512),
   "ADDITION"           VARCHAR(32),
   CONSTRAINT PK_T_USER PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_2_T_USER UNIQUE ("USERNAME")
)
/


/*==============================================================*/
/* TABLE: T_USER_ROLE                                           */
/*==============================================================*/


CREATE TABLE T_USER_ROLE  (
   "ID"                 NUMBER                           NOT NULL,
   "USERID"             NUMBER                           NOT NULL,
   "ROLEID"             NUMBER                           NOT NULL,
   CONSTRAINT PK_T_USER_ROLE PRIMARY KEY ("ID"),
   CONSTRAINT AK_KEY_2_T_USER_R UNIQUE ("USERID", "ROLEID")
)
/



/*==============================================================*/
/* TABLE: T_CURRENCY                                              */
/*==============================================================*/


CREATE TABLE T_CURRENCY  (
   ID                   NUMBER                           NOT NULL,
   NAME                 VARCHAR(64)                      NOT NULL,
   TYPE                   NUMBER                           NOT NULL,
   REMARK               VARCHAR(256),
   CONSTRAINT PK_CURRENCY PRIMARY KEY (ID)
)
/


/*==============================================================*/
/* TABLE: T_FUNDAREA                                              */
/*==============================================================*/


CREATE TABLE T_FUNDAREA  (
   ID                   NUMBER                           NOT NULL,
   NAME                 VARCHAR(64)                      NOT NULL,
   REMARK               VARCHAR(256),
   CONSTRAINT PK_FUNDAREA PRIMARY KEY (ID)
)
/


/*==============================================================*/
/* TABLE: T_FUNDINDUSTRY                                          */
/*==============================================================*/


CREATE TABLE T_FUNDINDUSTRY  (
   ID                   NUMBER                           NOT NULL,
   NAME                 VARCHAR(64)                      NOT NULL,
   REMARK               VARCHAR(256),
   CONSTRAINT PK_FUNDINDUSTRY PRIMARY KEY (ID)
)
/


/*==============================================================*/
/* TABLE: T_FUNDTYPE                                              */
/*==============================================================*/


CREATE TABLE T_FUNDTYPE  (
   ID                   NUMBER                           NOT NULL,
   NAME                 VARCHAR(64)                      NOT NULL,
   REMARK               VARCHAR(256),
   CONSTRAINT PK_FUNDTYPE PRIMARY KEY (ID)
)
/


/*==============================================================*/
/* TABLE: T_FUNDCOMPANY                                              */
/*==============================================================*/


CREATE TABLE T_FUNDCOMPANY  (
   ID                   NUMBER                           NOT NULL,
   NAME                 VARCHAR(64)                      NOT NULL,
   REMARK               VARCHAR(256),
   CONSTRAINT PK_FUNDCOMPANY PRIMARY KEY (ID)
)
/

/*=========================================================================================*/
/*						 				CREATE INDEX	 									*/
/*=========================================================================================*/
CREATE UNIQUE INDEX "INDEX_FUNDEQUITY_FUND" ON T_FUNDEQUITY (
   "FUNDID" ASC,
   "TIME" ASC
)
/


/*=========================================================================================*/
/*						 				ADD CONSTRAINT	 									*/
/*=========================================================================================*/

ALTER TABLE T_ACCOUNT
   ADD CONSTRAINT FK_ACCOUNT_USER FOREIGN KEY ("USERID")
      REFERENCES T_USER ("ID")
/


ALTER TABLE T_CASHACCOUNT
   ADD CONSTRAINT FK_CASHACCOUNT_ACCOUNT FOREIGN KEY ("ACCOUNTID")
      REFERENCES T_ACCOUNT (ID)
/

ALTER TABLE T_CASHACCOUNT
   ADD CONSTRAINT FK_CASHACCOUNT_CURRENCY FOREIGN KEY ("CURRENCY")
      REFERENCES T_CURRENCY (ID)
/

ALTER TABLE T_FUNDACCOUNT
   ADD CONSTRAINT FK_FUNDACCOUNT_FUND FOREIGN KEY ("FUNDID")
      REFERENCES T_FUND ("ID")
/


ALTER TABLE T_FUNDACCOUNT
   ADD CONSTRAINT FK_FUNDACCOUNT_ACCOUNT FOREIGN KEY ("ACCOUNTID")
      REFERENCES T_ACCOUNT (ID)
/


ALTER TABLE T_FUNDEQUITY
   ADD CONSTRAINT FK_FUNDEQUITY_FUND FOREIGN KEY ("FUNDID")
      REFERENCES T_FUND ("ID")
/


ALTER TABLE T_REPORTDATA
   ADD CONSTRAINT FK_REPORTDATA_REPORTINFO FOREIGN KEY ("REPORTID")
      REFERENCES T_REPORTINFO ("ID")
/


ALTER TABLE T_REPORTDATA
   ADD CONSTRAINT FK_REPORT_USER FOREIGN KEY ("USERID")
      REFERENCES T_USER ("ID")
/


ALTER TABLE T_RESOURCE_LOCATION
   ADD CONSTRAINT FK_RESOURCEHREF_RESOURCE FOREIGN KEY ("RESOURCEID")
      REFERENCES T_RESOURCE ("ID")
/


ALTER TABLE T_RESOURCE_LOCATION
   ADD CONSTRAINT FK_RESOURCELOCATION_LOCATION FOREIGN KEY ("LOCATIONID")
      REFERENCES T_LOCATION ("ID")
/


ALTER TABLE T_ROLE_RESOURCE
   ADD CONSTRAINT FK_ROLERESOURCE_RESOURCE FOREIGN KEY ("RESOURCEID")
      REFERENCES T_RESOURCE ("ID")
/


ALTER TABLE T_ROLE_RESOURCE
   ADD CONSTRAINT FK_ROLERESOURCE_ROLE FOREIGN KEY ("ROLEID")
      REFERENCES T_ROLE ("ID")
/


ALTER TABLE T_SAVINGPLAN
   ADD CONSTRAINT FK_SAVINGPLAN_ACCOUNT FOREIGN KEY ("ACCOUNTID")
      REFERENCES T_ACCOUNT (ID)
/


ALTER TABLE T_SAVINGPLAN
   ADD CONSTRAINT FK_SAVINGPLAN_FUND FOREIGN KEY ("FUNDID")
      REFERENCES T_FUND ("ID")
/


ALTER TABLE T_TRADE
   ADD CONSTRAINT FK_TRADE_ACCOUNT1 FOREIGN KEY ("SACCOUNTID")
      REFERENCES T_ACCOUNT (ID)
/


ALTER TABLE T_TRADE
   ADD CONSTRAINT FK_TRADE_ACCOUNT_2 FOREIGN KEY ("DACCOUNTID")
      REFERENCES T_ACCOUNT (ID)
/


ALTER TABLE T_TRADE
   ADD CONSTRAINT FK_TRADE_USER FOREIGN KEY ("USERID")
      REFERENCES T_USER ("ID")
/


ALTER TABLE T_TRADETASK
   ADD CONSTRAINT FK_TRADETASK_TRADE FOREIGN KEY ("TRADEID")
      REFERENCES T_TRADE ("ID")
/


ALTER TABLE T_USER_ROLE
   ADD CONSTRAINT FK_USERROLE_ROLE FOREIGN KEY ("ROLEID")
      REFERENCES T_ROLE ("ID")
/


ALTER TABLE T_USER_ROLE
   ADD CONSTRAINT FK_USERROLE_USER FOREIGN KEY ("USERID")
      REFERENCES T_USER ("ID")
/



ALTER TABLE "T_EXCHANGERATE"
   ADD CONSTRAINT FK_SCURRENCY_CURRENCY FOREIGN KEY ("SCURRENCY")
      REFERENCES T_CURRENCY (ID)
/


ALTER TABLE "T_EXCHANGERATE"
   ADD CONSTRAINT FK_DCURRENCY_CURRENCY FOREIGN KEY ("DCURRENCY")
      REFERENCES T_CURRENCY (ID)
/


ALTER TABLE "T_FUND"
   ADD CONSTRAINT FK_FUND_FUNDAREA FOREIGN KEY ("AREA")
      REFERENCES T_FUNDAREA (ID)
/


ALTER TABLE "T_FUND"
   ADD CONSTRAINT FK_FUND_FUNDTYPE FOREIGN KEY ("TYPE")
      REFERENCES T_FUNDTYPE (ID)
/


ALTER TABLE "T_FUND"
   ADD CONSTRAINT FK_FUND_FUNDINDUSTRY FOREIGN KEY ("INDUSTRY")
      REFERENCES T_FUNDINDUSTRY (ID)
/


ALTER TABLE "T_FUND"
   ADD CONSTRAINT FK_FUND_FUNDCOMPANY FOREIGN KEY ("COMPANY")
      REFERENCES T_FUNDCOMPANY (ID)
/


ALTER TABLE "T_TRADE"
   ADD CONSTRAINT FK_TRADE_CURRENCY FOREIGN KEY ("CURRENCY")
      REFERENCES T_CURRENCY (ID)
/


ALTER TABLE "T_SAVINGPLAN"
   ADD CONSTRAINT FK_SAVINGPLAN_CURRENCY FOREIGN KEY ("CURRENCY")
      REFERENCES T_CURRENCY (ID)
/


ALTER TABLE "T_LOG"
   ADD CONSTRAINT FK_LOG_USER FOREIGN KEY ("USERID")
      REFERENCES T_USER (ID)
/
/*=========================================================================================*/
/*						 					COMMIT	 										*/
/*=========================================================================================*/
COMMIT;
