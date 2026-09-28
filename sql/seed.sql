USE dentalcare;

-- Create an admin account to log in to the admin panel, for example:
-- INSERT INTO staffs (staff_firstname, staff_lastname, staff_phone, staff_email, staff_password)
-- VALUES ('Admin', '', '', 'you@example.com', MD5('your-password'));

-- For Not Assigned dentist purpose (Required and must be at dentist_id = 0)!
-- MySQL won't insert 0 into an AUTO_INCREMENT column, so insert normally,
-- move the row to id 0, then reset the counter so real dentists start at 1.
INSERT INTO dentists (dentist_firstname, dentist_status)
VALUES('Not Assigned', 'Not Available');

UPDATE dentists SET dentist_id = 0 WHERE dentist_firstname = 'Not Assigned';
ALTER TABLE dentists AUTO_INCREMENT = 1;
