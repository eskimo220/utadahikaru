--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.4
-- Dumped by pg_dump version 9.6.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: imgsave; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE imgsave (
    longblob bytea,
    imgid integer,
    username character varying NOT NULL
);


ALTER TABLE imgsave OWNER TO postgres;

--
-- Name: u_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE u_permission (
    id integer NOT NULL,
    url character varying(256) DEFAULT NULL::character varying,
    name character varying(64) DEFAULT NULL::character varying
);


ALTER TABLE u_permission OWNER TO postgres;

--
-- Name: u_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE u_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE u_permission_id_seq OWNER TO postgres;

--
-- Name: u_permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE u_permission_id_seq OWNED BY u_permission.id;


--
-- Name: u_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE u_role (
    id integer NOT NULL,
    name character varying(32) DEFAULT NULL::character varying,
    type character varying(10) DEFAULT NULL::character varying
);


ALTER TABLE u_role OWNER TO postgres;

--
-- Name: u_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE u_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE u_role_id_seq OWNER TO postgres;

--
-- Name: u_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE u_role_id_seq OWNED BY u_role.id;


--
-- Name: u_role_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE u_role_permission (
    rid integer,
    pid integer
);


ALTER TABLE u_role_permission OWNER TO postgres;

--
-- Name: u_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE u_user (
    id integer NOT NULL,
    nickname character varying(20) DEFAULT NULL::character varying,
    email character varying(128) DEFAULT NULL::character varying,
    pswd character varying(32) DEFAULT NULL::character varying,
    create_time timestamp without time zone,
    last_login_time timestamp without time zone,
    status smallint DEFAULT 1,
    tokens character varying(4000) DEFAULT NULL::character varying
);


ALTER TABLE u_user OWNER TO postgres;

--
-- Name: COLUMN u_user.tokens; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN u_user.tokens IS 'character varying(1000000000)';


--
-- Name: u_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE u_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE u_user_id_seq OWNER TO postgres;

--
-- Name: u_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE u_user_id_seq OWNED BY u_user.id;


--
-- Name: u_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE u_user_role (
    uid integer,
    rid integer
);


ALTER TABLE u_user_role OWNER TO postgres;

--
-- Name: u_permission id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY u_permission ALTER COLUMN id SET DEFAULT nextval('u_permission_id_seq'::regclass);


--
-- Name: u_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY u_role ALTER COLUMN id SET DEFAULT nextval('u_role_id_seq'::regclass);


--
-- Name: u_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY u_user ALTER COLUMN id SET DEFAULT nextval('u_user_id_seq'::regclass);


--
-- Data for Name: imgsave; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY imgsave (longblob, imgid, username) FROM stdin;
\.


--
-- Data for Name: u_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY u_permission (id, url, name) FROM stdin;
1	/db\n	perms[1]
\.


--
-- Name: u_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('u_permission_id_seq', 1, false);


--
-- Data for Name: u_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY u_role (id, name, type) FROM stdin;
1	admin	1
2	person	2
\.


--
-- Name: u_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('u_role_id_seq', 1, false);


--
-- Data for Name: u_role_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY u_role_permission (rid, pid) FROM stdin;
1	1
2	1
\.


--
-- Data for Name: u_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY u_user (id, nickname, email, pswd, create_time, last_login_time, status, tokens) FROM stdin;
1	laoliguoa	laoliguo@qq.com	x16YjoF1LNE=	2017-09-19 16:19:14.538	2017-09-27 19:06:44.841	1	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDU4MTA5NTQ1MzMsInBheWxvYWQiOiJ7XCJpZFwiOm51bGwsXCJuaWNrbmFtZVwiOlwibGFvemhpemhvbmdcIixcImVtYWlsXCI6XCJsYW9saWd1b0BxcS5jb21cIixcInBzd2RcIjpcIjEyMzc4OVwiLFwiY3JlYXRlVGltZVwiOm51bGwsXCJsYXN0TG9naW5UaW1lXCI6bnVsbCxcInN0YXR1c1wiOjAsXCJ0b2tlbnNcIjpudWxsfSJ9.-PTpooxG_Paw_ryWILGXxLf2-HMwk8iLYocmgPXgTSk
180	laozhizhong	laozhizhong@qq.com	x16YjoF1LNE=	2017-09-22 14:20:37.239	2017-09-22 14:21:36.792	1	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDYwNjMwMzcxOTIsInBheWxvYWQiOiJ7XCJpZFwiOm51bGwsXCJuaWNrbmFtZVwiOlwibGFvemhpemhvbmdcIixcImVtYWlsXCI6XCJsYW96aGl6aG9uZ0BxcS5jb21cIixcInBzd2RcIjpcIjEyMzQ1NlwiLFwiY3JlYXRlVGltZVwiOm51bGwsXCJsYXN0TG9naW5UaW1lXCI6bnVsbCxcInN0YXR1c1wiOjAsXCJ0b2tlbnNcIjpudWxsfSJ9.6_A6SLTeAzZUdrHTW9ARbOU6NGLdWqAbIWJgnS-pRns
2	liuyuelilua	liuyuelilu@sina.com	x16YjoF1LNE=	2017-09-19 16:25:40.263	2017-10-12 14:18:39.066	1	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDU4MTEzNDAyNjEsInBheWxvYWQiOiJ7XCJpZFwiOm51bGwsXCJuaWNrbmFtZVwiOlwibGFvbGlndW9cIixcImVtYWlsXCI6XCJsaXV5dWVsaWx1QHNpbmEuY29tXCIsXCJwc3dkXCI6XCIxMjM0NTZcIixcImNyZWF0ZVRpbWVcIjpudWxsLFwibGFzdExvZ2luVGltZVwiOm51bGwsXCJzdGF0dXNcIjowLFwidG9rZW5zXCI6bnVsbH0ifQ.QOTgNDUw3AQAaer0enjGsIK3MPAZapK_DuWhtsYTBQc
181	aaaaaa	liuyuelilu@qq.com	x16YjoF1LNE=	2017-09-29 11:39:53.549	\N	1	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDY2NTgxOTM1MjUsInBheWxvYWQiOiJ7XCJpZFwiOm51bGwsXCJuaWNrbmFtZVwiOlwiYWFhYWFhXCIsXCJlbWFpbFwiOlwibGl1eXVlbGlsdUBxcS5jb21cIixcInBzd2RcIjpcIjEyMzQ1NlwiLFwiY3JlYXRlVGltZVwiOm51bGwsXCJsYXN0TG9naW5UaW1lXCI6bnVsbCxcInN0YXR1c1wiOjAsXCJ0b2tlbnNcIjpudWxsLFwibG9uZ2Jsb2JcIjpudWxsfSJ9.oYH8Cf1RX4UYGvWXH28WvYw18nd9_oo3WgtgQj-ExNM
\.


--
-- Name: u_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('u_user_id_seq', 181, true);


--
-- Data for Name: u_user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY u_user_role (uid, rid) FROM stdin;
1	1
180	2
2	2
181	2
\.


--
-- Name: imgsave imgsave_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY imgsave
    ADD CONSTRAINT imgsave_pkey PRIMARY KEY (username);


--
-- Name: u_permission u_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY u_permission
    ADD CONSTRAINT u_permission_pkey PRIMARY KEY (id);


--
-- Name: u_role u_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY u_role
    ADD CONSTRAINT u_role_pkey PRIMARY KEY (id);


--
-- Name: u_user u_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY u_user
    ADD CONSTRAINT u_user_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

