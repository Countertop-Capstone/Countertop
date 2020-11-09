USE countertop_db;


# TRUNCATE drops all data from the table but leaves the table intact.
# FOREIGN_KEY_CHECKS will prevent truncating tables with foreign keys.
# FOREIGN_KEY_CHECKS set to 0 will disable the check. Enable the check at the end of truncating by setting to 1.
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE users;
TRUNCATE tags;
TRUNCATE recipes;
TRUNCATE comments;
SET FOREIGN_KEY_CHECKS = 1;

#Resets AUTO_INCREMENT to value
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE tags AUTO_INCREMENT = 1;
ALTER TABLE recipes AUTO_INCREMENT = 1;
ALTER TABLE comments AUTO_INCREMENT = 1;

#Seeds users
INSERT INTO users (dob, email, password, signup_date, url, username)
VALUES
(CURDATE(), 'admin@admin.com', 'admin', CURDATE(), 'www.countertop.com', 'admin'),
('1991/01/01', 'austin@austin.com', 'austin', CURDATE(), 'www.countertop.com', 'austin'),
('1992/02/02', 'charles@charles.com', 'charles', CURDATE(), 'www.countertop.com', 'charles'),
('1993/03/03', 'david@david.com', 'david', CURDATE(), 'www.countertop.com', 'david'),
('1994/04/04', 'tim@tim.com', 'tim', CURDATE(), 'www.countertop.com', 'tim')
;


#Seeds Recipes


#Seeds Tags
INSERT INTO tags (name)
VALUES
('italian'),
('mexican'),
('chinese'),
('american'),
('indian'),
('vietnamese'),
('brazilian'),
('french');


#Seeds Tags
INSERT INTO tags (name)
VALUES
('italian'),
('mexican'),
('chinese'),
('american'),
('indian'),
('vietnamese'),
('brazilian'),
('french');

#Seeds Comments
INSERT INTO comments (comment_body, date, liked, recipe_id, user_id)
VALUES
('This is delicious', CURDATE(), true, 1, 1),
('This is delicious', CURDATE(), false, 2, 3),
('This is delicious', CURDATE(), false, 3, 4),
('This is delicious', CURDATE(), true, 4, 5),
('This is delicious', CURDATE(), false, 1, 1),
('This is delicious', CURDATE(), true, 1, 1),
('This is delicious', CURDATE(), true, 1, 1);

INSERT INTO comments (comment_body, date, liked, parent_comment_id, recipe_id, user_id)
VALUES
('This is delicious', CURDATE(), false, 1, 5, 1),
('This is delicious', CURDATE(), false, 2, 1, 1),
('This is delicious', CURDATE(), false, 6, 1, 1),
('This is delicious', CURDATE(), false, 1, 1, 1),
('This is delicious', CURDATE(), true, 2, 1, 1),
('This is delicious', CURDATE(), true, 4, 1, 1),
('This is delicious', CURDATE(), true, 8, 1, 1),
('This is delicious', CURDATE(), true, 2, 1, 1),
('This is delicious', CURDATE(), false, 1, 1, 1),
('This is delicious', CURDATE(), true, 3, 1, 1);




INSERT INTO users_favorites(user_id)
VALUES (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 8),
       (3, 9);

