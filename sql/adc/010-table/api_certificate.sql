CREATE TABLE API_CERTIFICATE (
    USER_NO BIGINT NOT NULL,
    API_ACCESS_ID VARCHAR(100) NOT NULL,
    API_SECRET_KEY VARCHAR(100) NOT NULL,
    CONSTRAINT API_CERTIFICATE_PK PRIMARY KEY(USER_NO)
);
