DROP TABLE IF EXISTS OAUTH2_REGISTERED_CLIENT;

CREATE TABLE IF NOT EXISTS OAUTH2_REGISTERED_CLIENT (
      ID BIGINT NOT NULL AUTO_INCREMENT,
      ADMIN_CLIENT_ID VARCHAR(255) NOT NULL,
      ADMIN_CLIENT_ID_ISSUED_AT TIMESTAMP(6) NOT NULL,
      AUTHORIZATION_GRANT_TYPES VARCHAR(255) NOT NULL,
      CLIENT_AUTHENTICATION_METHODS VARCHAR(255) NOT NULL,
      CLIENT_NAME VARCHAR(255) NOT NULL,
      CLIENT_SECRET VARCHAR(255),
      CLIENT_SECRET_EXPIRES_AT TIMESTAMP(6),
      JS_CLIENT_ID VARCHAR(255) NOT NULL,
      JS_CLIENT_ID_ISSUED_AT TIMESTAMP(6) NOT NULL,
      NATIVE_CLIENT_ID VARCHAR(255) NOT NULL,
      NATIVE_CLIENT_ID_ISSUED_AT TIMESTAMP(6) NOT NULL,
      POST_LOGOUT_REDIRECT_URIS VARCHAR(255),
      REDIRECT_URIS VARCHAR(255),
      REST_CLIENT_ID VARCHAR(255) NOT NULL,
      REST_CLIENT_ID_ISSUED_AT TIMESTAMP(6) NOT NULL,
      SCOPES VARCHAR(255) NOT NULL,
      PRIMARY KEY (ID)
);

INSERT INTO OAUTH2_REGISTERED_CLIENT (
    ADMIN_CLIENT_ID,
    ADMIN_CLIENT_ID_ISSUED_AT,
    AUTHORIZATION_GRANT_TYPES,
    CLIENT_AUTHENTICATION_METHODS,
    CLIENT_NAME,
    CLIENT_SECRET,
    CLIENT_SECRET_EXPIRES_AT,
    JS_CLIENT_ID,
    JS_CLIENT_ID_ISSUED_AT,
    NATIVE_CLIENT_ID,
    NATIVE_CLIENT_ID_ISSUED_AT,
    POST_LOGOUT_REDIRECT_URIS,
    REDIRECT_URIS,
    REST_CLIENT_ID,
    REST_CLIENT_ID_ISSUED_AT,
    SCOPES
)
VALUES (
    'hSWSmLoSZMbGwKQHFKqCZCO9DWhYb3QwrvL24MNUWeM',
    now(),
    'authorization_code',
    'client_secret_post',
    'test',
    'ZsPHfrialhg_taxssRqIQRvcNNMWqKKE64mcsYSDRCk',  -- client_secret
    null,
    'E8ZSPS7_sUYVv32RyS32RH6htlPATe6sD0Xz7Fl-zFE',
    now(),
    '-D2jnH1B21AIZWxfUuDlLnmHARrp3A6-zLGmRZ_-Di0',
    now(),
    null,
    'http://localhost:8080/login,http://localhost:8080/',
    'SQKUiBl0GDaQOxN9wDV9ZGbcTNNcwDvYTz0sSUxgRPM',  -- rest_client_id
    now(),
    'profile,email'
);