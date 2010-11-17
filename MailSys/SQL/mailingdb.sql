DROP TABLE IF EXISTS mails;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS seq_mails_id;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_mails_id INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;
CREATE SEQUENCE seq_user_id  INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;

CREATE TABLE mails (
  id                BIGINT  NOT NULL DEFAULT (nextval('seq_mails_id')),
  UserId            BIGINT  NOT NULL,
  Subject           TEXT    NOT NULL,
  Content           TEXT    NOT NULL,
  immediate         BOOLEAN NOT NULL,
  creationdate      TIMESTAMP WITH TIME ZONE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id                 BIGINT  NOT NULL DEFAULT (nextval('seq_user_id')),
  pseudo             TEXT    NOT NULL,
  email              TEXT    NOT NULL,
  lastMailSendedDate TIMESTAMP WITH TIME ZONE NULL DEFAULT NULL,
  mailingDelai       INT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE mails ADD CONSTRAINT fk_mailhaveuser FOREIGN KEY (UserId) REFERENCES users(id);
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO users (pseudo, email, mailingDelai	)
                  VALUES ('toto', 'toto@hotmail.com', '0'),
                         ('mami', 'mami@hotmail.com', '1'),
                         ('alain', 'alain@hotmail.com', '2'),
                         ('eric', 'eric@hotmail.com', '2'),
                         ('steph', 'steph@hotmail.com', '1'),
                         ('john', 'john@hotmail.com', '0'),
                         ('tati', 'tati@hotmail.com', '0');

