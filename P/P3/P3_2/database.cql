CREATE TABLE users (
    username text,
    name text,
    email text,
    register_date timestamp,
    PRIMARY KEY ((username))
);

CREATE TABLE videos (    
    author text,
    vidName text,
    description text,
    tags map<text, text>,
    upload timestamp,
    PRIMARY KEY ((author))
);

CREATE TABLE comment_by_user (
    username text,
    timeCommented timestamp,
    video text,
    comment text,
    PRIMARY KEY ((username), timeCommented)
)WITH CLUSTERING ORDER BY (timeCommented DESC);

CREATE TABLE comment_by_vid (
    video text,
    timeCommented timestamp,
    username text,
    comment text,
    PRIMARY KEY ((video), timeCommented)
)WITH CLUSTERING ORDER BY (timeCommented DESC);

CREATE TABLE follow (
    user text,
    follows map<text, text>,
    PRIMARY KEY ((user))
);

CREATE TABLE followed (
    vidName text,
    followers map<text, text>,
    PRIMARY KEY ((vidName))
);

CREATE TABLE events_by_vid (
    vidName text,
    eventName text,
    eventTime timestamp,
    eventTimeOnVid timestamp,
    user text,
    PRIMARY KEY ((vidName))
);

CREATE TABLE events_by_user (
    user text,
    eventName text,
    eventTime timestamp,
    eventTimeOnVid timestamp,
    vidName text,
    PRIMARY KEY ((user))
);
