CREATE TABLE `Workplace` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `Building` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255),
  `levels` integer,
  `file_name` varchar(255)
);

CREATE TABLE `Employee` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255),
  `workplace_id` integer,
  FOREIGN KEY (`workplace_id`) REFERENCES `Workplace` (`id`)
);

CREATE TABLE `Room_type` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `Room` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255),
  `building_id` integer,
  `level` integer,
  `emp_space` integer,
  `area_id` integer,
  `room_type_id` integer,


  FOREIGN KEY (`building_id`) REFERENCES `Building` (`id`),
  FOREIGN KEY (`room_type_id`) REFERENCES `Room_type` (`id`)
);

CREATE TABLE `Employee_room` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `emp_id` integer,
  `room_id` integer,
  FOREIGN KEY (`emp_id`) REFERENCES `Employee` (`id`),
  FOREIGN KEY (`room_id`) REFERENCES `Room` (`id`)
);

CREATE TABLE `Role` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255)
);


CREATE TABLE `_User` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `login` varchar(20),
  `name` varchar(255),
  `password` varchar(255),
  `email` varchar(40),
  `created` timestamp,
  `role_id` integer,
    FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)

);

CREATE TABLE `Building_area` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `building_id` integer,
  `coords` varchar(255),
  `overlay` varchar(255),
   FOREIGN KEY (`building_id`) REFERENCES `Building` (`id`)
);

CREATE TABLE `_Level` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `_level` integer,
  `building_id` integer,
  `file_name` varchar(255),
   FOREIGN KEY (`building_id`) REFERENCES `Building` (`id`)

);

CREATE TABLE `Level_area` (
  `id` integer  AUTO_INCREMENT PRIMARY KEY,
  `level_id` integer,
  `coords` varchar(255),
  `overlay` varchar(255),
   FOREIGN KEY (`level_id`) REFERENCES `_Level` (`id`)
);


--
INSERT INTO Building (name, levels, file_name) VALUES ('X1', 3, 'buildX1plan.png');
--
INSERT INTO Workplace (name) VALUES ('Software Developer');
INSERT INTO Workplace (name) VALUES ('IT Support Specialist');
INSERT INTO Workplace (name) VALUES ('Database Administrator');
INSERT INTO Workplace (name) VALUES ('Network Engineer');
INSERT INTO Workplace (name) VALUES ('UX/UI Designer');
INSERT INTO Workplace (name) VALUES ('Cybersecurity Analyst');
INSERT INTO Workplace (name) VALUES ('IT Project Manager');
--
INSERT INTO Employee (name, workplace_id) VALUES ('Jackson Clark', 1);
INSERT INTO Employee (name, workplace_id) VALUES ('Emma White', 2);
INSERT INTO Employee (name, workplace_id) VALUES ('Liam Parker', 3);
INSERT INTO Employee (name, workplace_id) VALUES ('Noah Davis ', 4);
INSERT INTO Employee (name, workplace_id) VALUES ('Ava Mitchell', 5);
INSERT INTO Employee (name, workplace_id) VALUES ('Oliver Thompson', 6);
INSERT INTO Employee (name, workplace_id) VALUES ('Mia Harris', 7);
--
INSERT INTO Room_type (name) VALUES ('Room');
INSERT INTO Room_type (name) VALUES ('Corridor');
INSERT INTO Room_type (name) VALUES ('Warehouse');
--
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('201', 1, 2, 2, 1,1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('202', 1, 2, 2, 2,1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('203', 1, 2, 2, 3,1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('204', 1, 2, 2, 4,1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('205', 1, 2, 2, 5,1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('Corridor', 1, 2, 2, 6, 2);

INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('101', 1, 1, 2, 1, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('102', 1, 1, 2, 2, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('103', 1, 1, 2, 3, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('104', 1, 1, 2, 4, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('105', 1, 1, 2, 5, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('Corridor', 1, 1, 2, 6, 2);

INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('001', 1, 0, 2, 1, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('002', 1, 0, 2, 2, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('003', 1, 0, 2, 3, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('004', 1, 0, 2, 4, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('005', 1, 0, 2, 5, 1);
INSERT INTO Room (name, building_id, level, emp_space, area_id, room_type_id) VALUES ('Corridor', 1, 0, 2, 6, 2);

--
INSERT INTO Employee_room(emp_id, room_id) VALUES (1,1);
INSERT INTO Employee_room(emp_id, room_id) VALUES (2,1);
INSERT INTO Employee_room(emp_id, room_id) VALUES (3,2);
INSERT INTO Employee_room(emp_id, room_id) VALUES (4,2);
INSERT INTO Employee_room(emp_id, room_id) VALUES (5,3);
INSERT INTO Employee_room(emp_id, room_id) VALUES (6,4);
INSERT INTO Employee_room(emp_id, room_id) VALUES (7,5);
--
INSERT INTO Role(name) VALUES ('USER');
--
INSERT INTO _User(login, name, password, email, created, role_id)
VALUES ('user', 'user', '$2a$12$4FuwW1EMD4tJ1udNfvO/M.5lAdGJJoEo2wLjtY8Jnxrnb7c9BTGC6', 'user@mail.com', '2023-11-24 10:07', 1);
--
INSERT INTO Building_area(building_id, coords, overlay) VALUES (1, '5,217,798,320', 0);
INSERT INTO Building_area(building_id, coords, overlay) VALUES (1, '5,117,798,214', 1);
INSERT INTO Building_area(building_id, coords, overlay) VALUES (1, '5,3,797,116', 2);
--
INSERT INTO _Level(_level, building_id, file_name) VALUES (0, 1, 'floor2plan.png');
INSERT INTO _Level(_level, building_id, file_name) VALUES (1, 1, 'floor2plan.png');
INSERT INTO _Level(_level, building_id, file_name) VALUES (2, 1, 'floor2plan.png');
--
INSERT INTO Level_area(level_id, coords, overlay) VALUES ( 1, '181,253,5,5', 1);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 1, '184,5,362,253', 2);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 1, '543,254,366,4', 3);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 1, '548,5,725,253', 4);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 1, '728,5,798,253', 5);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 1, '3,258,798,321', 6);

INSERT INTO Level_area(level_id, coords, overlay) VALUES ( 2, '181,253,5,5', 1);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 2, '184,5,362,253', 2);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 2, '543,254,366,4', 3);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 2, '548,5,725,253', 4);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 2, '728,5,798,253', 5);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 2, '3,258,798,321', 6);

INSERT INTO Level_area(level_id, coords, overlay) VALUES ( 3, '181,253,5,5', 1);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 3, '184,5,362,253', 2);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 3, '543,254,366,4', 3);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 3, '548,5,725,253', 4);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 3, '728,5,798,253', 5);
INSERT INTO Level_area( level_id, coords, overlay) VALUES ( 3, '3,258,798,321', 6);
