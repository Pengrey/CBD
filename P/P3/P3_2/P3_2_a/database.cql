CREATE COLUMNFAMILY users (
    username text,
    name text,
    email text,
    register_date timestamp,
    PRIMARY KEY ((username))
);

CREATE COLUMNFAMILY videos (    
    author text,
    vidName text,
    description text,
    tags list<text>,
    upload timestamp,
    PRIMARY KEY ((author), upload)
);

CREATE COLUMNFAMILY videos_by_name (    
    author text,
    vidName text,
    description text,
    tags list<text>,
    upload timestamp,
    PRIMARY KEY ((vidName), upload)
);

CREATE COLUMNFAMILY videos_by_tag (    
    tag text,
    author text,
    vidName text,
    description text,
    upload timestamp,
    PRIMARY KEY ((tag), vidName)
);

CREATE COLUMNFAMILY comment_by_user (
    username text,
    timeCommented timestamp,
    video text,
    comment text,
    PRIMARY KEY ((username), timeCommented)
)WITH CLUSTERING ORDER BY (timeCommented DESC);

CREATE COLUMNFAMILY comment_by_vid (
    video text,
    timeCommented timestamp,
    username text,
    comment text,
    PRIMARY KEY ((video), timeCommented)
)WITH CLUSTERING ORDER BY (timeCommented DESC);

CREATE COLUMNFAMILY follow (
    user text,
    follows list<text>,
    PRIMARY KEY ((user))
);

CREATE COLUMNFAMILY followed (
    vidName text,
    followers list<text>,
    PRIMARY KEY ((vidName))
);

CREATE COLUMNFAMILY events_by_vid (
    vidName text,
    eventName text,
    eventTime timestamp,
    eventTimeOnVid int,
    user text,
    PRIMARY KEY ((vidName))
);

CREATE COLUMNFAMILY events_by_user (
    user text,
    eventName text,
    eventTime timestamp,
    eventTimeOnVid int,
    vidName text,
    PRIMARY KEY ((user))
);

CREATE COLUMNFAMILY ratings_by_vid (
    user text,
    vidName text,
    value int,
    PRIMARY KEY ((vidName), user)
);

INSERT INTO users (username, email, name, register_date) VALUES ('user0', 'user0@server.com', 'user 0', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user1', 'user1@server.com', 'user 1', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user2', 'user2@server.com', 'user 2', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user3', 'user3@server.com', 'user 3', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user4', 'user4@server.com', 'user 4', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user5', 'user5@server.com', 'user 5', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user6', 'user6@server.com', 'user 6', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user7', 'user7@server.com', 'user 7', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user8', 'user8@server.com', 'user 8', dateof(now()));
INSERT INTO users (username, email, name, register_date) VALUES ('user9', 'user9@server.com', 'user 9', dateof(now()));

INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author0', 'video0', 'description0', ['Action', 'Animation'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author1', 'video1', 'description1', ['Comedy', 'Crime'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author2', 'video2', 'description2', ['Drama', 'Experimental'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author3', 'video3', 'description3', ['Fantasy', 'Historical'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author4', 'video4', 'description4', ['Horror', 'Romance'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author5', 'video5', 'description5', ['Sci-Fi', 'Thriller'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author6', 'video6', 'description6', ['Western', 'Musical'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author7', 'video7', 'description7', ['War', 'Biopics'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author8', 'video8', 'description8', ['Psychological', 'Spy'], dateof(now()));
INSERT INTO videos (author, vidName, description, tags, upload) VALUES ('author9', 'video9', 'description9', ['Parody', 'Paranormal'], dateof(now()));

INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author0', 'video0', 'description0', ['Action', 'Animation'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author1', 'video1', 'description1', ['Comedy', 'Crime'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author2', 'video2', 'description2', ['Drama', 'Experimental'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author3', 'video3', 'description3', ['Fantasy', 'Historical'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author4', 'video4', 'description4', ['Horror', 'Romance'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author5', 'video5', 'description5', ['Sci-Fi', 'Thriller'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author6', 'video6', 'description6', ['Western', 'Musical'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author7', 'video7', 'description7', ['War', 'Biopics'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author8', 'video8', 'description8', ['Psychological', 'Spy'], dateof(now()));
INSERT INTO videos_by_name (author, vidName, description, tags, upload) VALUES ('author9', 'video9', 'description9', ['Parody', 'Paranormal'], dateof(now()));

INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Action', 'author0', 'video0', 'description0', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Animation', 'author0', 'video0', 'description0', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Comedy', 'author1', 'video1', 'description1', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Crime', 'author1', 'video1', 'description1', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Drama', 'author2', 'video2', 'description2', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Experimental', 'author2', 'video2', 'description2', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Fantasy', 'author3', 'video3', 'description3', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Historical', 'author3', 'video3', 'description3', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Horror', 'author4', 'video4', 'description4', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Romance', 'author4', 'video4', 'description4', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Sci-Fi', 'author5', 'video5', 'description5', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Thriller', 'author5', 'video5', 'description5', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Western', 'author6', 'video6', 'description6', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Musical', 'author6', 'video6', 'description6', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('War', 'author7', 'video7', 'description7', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Biopics', 'author7', 'video7', 'description7', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Psychological', 'author8', 'video8', 'description8', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Spy', 'author8', 'video8', 'description8', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Parody', 'author9', 'video9', 'description9', dateof(now()));
INSERT INTO videos_by_tag (tag, author, vidName, description, upload) VALUES ('Paranormal', 'author9', 'video9', 'description9', dateof(now()));

INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user0', dateof(now()), 'video0', 'comment0');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user1', dateof(now()), 'video1', 'comment1');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user2', dateof(now()), 'video2', 'comment2');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user3', dateof(now()), 'video3', 'comment3');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user4', dateof(now()), 'video4', 'comment4');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user5', dateof(now()), 'video5', 'comment5');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user6', dateof(now()), 'video6', 'comment6');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user7', dateof(now()), 'video7', 'comment7');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user8', dateof(now()), 'video8', 'comment8');
INSERT INTO comment_by_user (username, timeCommented, video, comment) VALUES ('user9', dateof(now()), 'video9', 'comment9');

INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video0', dateof(now()), 'user0', 'comment0');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video1', dateof(now()), 'user1', 'comment1');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video2', dateof(now()), 'user2', 'comment2');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video3', dateof(now()), 'user3', 'comment3');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video4', dateof(now()), 'user4', 'comment4');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video5', dateof(now()), 'user5', 'comment5');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video6', dateof(now()), 'user6', 'comment6');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video7', dateof(now()), 'user7', 'comment7');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video8', dateof(now()), 'user8', 'comment8');
INSERT INTO comment_by_vid (video, timeCommented, username, comment) VALUES ('video9', dateof(now()), 'user9', 'comment9');

INSERT INTO follow (user, follows) VALUES ('user0', ['video0']);
INSERT INTO follow (user, follows) VALUES ('user1', ['video1']);
INSERT INTO follow (user, follows) VALUES ('user2', ['video2']);
INSERT INTO follow (user, follows) VALUES ('user3', ['video3']);
INSERT INTO follow (user, follows) VALUES ('user4', ['video4']);
INSERT INTO follow (user, follows) VALUES ('user5', ['video5']);
INSERT INTO follow (user, follows) VALUES ('user6', ['video6']);
INSERT INTO follow (user, follows) VALUES ('user7', ['video7']);
INSERT INTO follow (user, follows) VALUES ('user8', ['video8']);
INSERT INTO follow (user, follows) VALUES ('user9', ['video9']);

INSERT INTO followed (vidName, followers) VALUES ('video0', ['user0']);
INSERT INTO followed (vidName, followers) VALUES ('video1', ['user1']);
INSERT INTO followed (vidName, followers) VALUES ('video2', ['user2']);
INSERT INTO followed (vidName, followers) VALUES ('video3', ['user3']);
INSERT INTO followed (vidName, followers) VALUES ('video4', ['user4']);
INSERT INTO followed (vidName, followers) VALUES ('video5', ['user5']);
INSERT INTO followed (vidName, followers) VALUES ('video6', ['user6']);
INSERT INTO followed (vidName, followers) VALUES ('video7', ['user7']);
INSERT INTO followed (vidName, followers) VALUES ('video8', ['user8']);
INSERT INTO followed (vidName, followers) VALUES ('video9', ['user9']);

INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video0', 'Pause', dateof(now()), 100, 'user0');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video1', 'Play', dateof(now()), 200, 'user1');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video2', 'Stop', dateof(now()), 300, 'user2');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video3', 'Pause', dateof(now()), 400, 'user3');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video4', 'Play', dateof(now()), 500, 'user4');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video5', 'Stop', dateof(now()), 600, 'user5');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video6', 'Pause', dateof(now()), 700, 'user6');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video7', 'Play', dateof(now()), 800, 'user7');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video8', 'Stop', dateof(now()), 900, 'user8');
INSERT INTO events_by_vid (vidName, eventName, eventTime, eventTimeOnVid, user) VALUES ('video9', 'Pause', dateof(now()), 1000, 'user9');

INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user0', 'Pause', dateof(now()), 100, 'video0');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user1', 'Play', dateof(now()), 200, 'video1');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user2', 'Stop', dateof(now()), 300, 'video2');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user3', 'Pause', dateof(now()), 400, 'video3');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user4', 'Play', dateof(now()), 500, 'video4');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user5', 'Stop', dateof(now()), 600, 'video5');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user6', 'Pause', dateof(now()), 700, 'video6');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user7', 'Play', dateof(now()), 800, 'video7');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user8', 'Stop', dateof(now()), 900, 'video8');
INSERT INTO events_by_user (user, eventName, eventTime, eventTimeOnVid, vidName) VALUES ('user9', 'Pause', dateof(now()), 1000, 'video9');

INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user0', 'video0', 1);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user1', 'video0', 2);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user2', 'video0', 3);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user3', 'video0', 4);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user4', 'video0', 5);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user1', 'video1', 2);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user2', 'video2', 3);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user3', 'video3', 4);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user4', 'video4', 5);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user5', 'video5', 1);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user6', 'video6', 2);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user7', 'video7', 3);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user8', 'video8', 4);
INSERT INTO ratings_by_vid (user, vidName, value) VALUES ('user9', 'video9', 5);
