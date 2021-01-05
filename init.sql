CREATE DATABASE sep
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

\connect sep;

CREATE SCHEMA IF NOT EXISTS eureka_service_schema AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS bitcoin_service_schema AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS paypal_service_schema AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS unicredit_service_schema AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS bank_service_schema AUTHORIZATION postgres;

-- CREATE SCHEMA IF NOT EXISTS raiffeisen_service_schema AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS pcc_service_schema AUTHORIZATION postgres;

-- winpty docker exec -it 92d35b903a41 bash