DROP TABLE IF EXISTS mails;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS seq_mails_id;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_mails_id INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;
CREATE SEQUENCE seq_user_id  INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;

CREATE TABLE mails (
  id                BIGINT  NOT NULL DEFAULT (nextval('seq_mails_id')),
  User_Id           BIGINT  NOT NULL,
  Content           TEXT    NOT NULL,
  MainSubject       TEXT    NOT NULL,
  MailType          TEXT    NOT NULL,
  Subject			TEXT	NOT NULL,
  creationdate      TIMESTAMP WITH TIME ZONE NOT NULL,
  useTemplate       BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id                 BIGINT  NOT NULL DEFAULT (nextval('seq_user_id')),
  pseudo             TEXT    NOT NULL,
  email              TEXT    NOT NULL,
  lastMailSendedDate TIMESTAMP WITH TIME ZONE NULL DEFAULT NULL,
  mailingDelai       TEXT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE mails ADD CONSTRAINT fk_mailhaveuser FOREIGN KEY (User_Id) REFERENCES users(id);
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO users (pseudo, email, mailingDelai	)
                  VALUES ('toto', 'toto@hotmail.com', 'DAILY'),
                         ('mami', 'mami@hotmail.com', 'DAILY'),
                         ('alain', 'alain@hotmail.com', 'WEEKLY'),
                         ('eric', 'eric@hotmail.com', 'WEEKLY'),
                         ('steph', 'steph@hotmail.com', 'DAILY'),
                         ('john', 'john@hotmail.com', 'DAILY'),
                         ('tati', 'tati@hotmail.com', 'WEEKLY');

