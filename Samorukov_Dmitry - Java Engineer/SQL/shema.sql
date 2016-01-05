CREATE DATABASE IF NOT EXISTS backend
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE backend;

CREATE TABLE df_customer (
  c_code          VARCHAR(50) NOT NULL,
  c_name          TEXT        NOT NULL,
  c_phone1        TEXT        NOT NULL,
  c_phone2        TEXT        NULL,
  c_address       TEXT        NULL,
  c_creditLimit   DOUBLE      NOT NULL,
  c_currentCredit DOUBLE      NOT NULL,
  PRIMARY KEY (c_code)
)
  ENGINE = InnoDB;

CREATE TABLE df_product (
  c_code        VARCHAR(50) NOT NULL,
  c_description TEXT        NOT NULL,
  c_price       DOUBLE      NOT NULL,
  c_quantity    INT         NOT NULL,
  PRIMARY KEY (c_code)
)
  ENGINE = InnoDB;

CREATE TABLE df_salesOrder (
  c_orderNum   VARCHAR(50) NOT NULL,
  c_customer   VARCHAR(50) NOT NULL,
  c_totalPrice DOUBLE      NOT NULL,
  PRIMARY KEY (c_orderNum)
)
  ENGINE = InnoDB;

ALTER TABLE df_salesOrder ADD CONSTRAINT fk_df_salesOrder_1
FOREIGN KEY (c_customer) REFERENCES df_customer (c_code)
  MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION;

CREATE TABLE df_orderLines (
  c_id       INT         NOT NULL AUTO_INCREMENT,
  c_orderNum VARCHAR(50) NOT NULL,
  c_product  VARCHAR(50) NOT NULL,
  c_quantity INT         NOT NULL,
  PRIMARY KEY (c_id)
)
  ENGINE = InnoDB;

ALTER TABLE df_orderLines ADD CONSTRAINT fk_c_orderNum_1
FOREIGN KEY (c_orderNum) REFERENCES df_salesOrder (c_orderNum)
  MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION;

ALTER TABLE df_orderLines ADD CONSTRAINT fk_c_orderNum_2
FOREIGN KEY (c_product) REFERENCES df_product (c_code)
  MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION;
