CREATE database screenshotDB;

CREATE TABLE screenshotDB.screenshot_request (
	id INTEGER AUTO_INCREMENT PRIMARY KEY, 
	url VARCHAR(100) NOT NULL,
	screenshot_path VARCHAR(100) ,
	create_date DATE NOT NULL
);

