# Queries Implemented

## Simple Queries

1. Register: ```INSERT INTO users VALUES(NULL, '<name>','<email>','<password>','<join_date>','<user_type>');```
1. Login: ```SELECT * FROM users WHERE email = '<email>' AND password = '<password>'```
1. Update Customer Profile: ```INSERT INTO customers VALUES(NULL, <userId>, <membership_plan_id>, '<address>', <age>, <weight>);```
1. Update Staff Profile: ```INSERT INTO staff VALUES(NULL, <userId>, '<address>',<salary>);```
1. Add Equipment: ```INSERT INTO equipment VALUES(NULL, '<name>','<price>','<date>','<type>');```
1. View All Equipments: ```SELECT * FROM equipment```


## Complex Queries
1. View Customer Profile: ```SELECT * FROM customers INNER JOIN users ON customers.user_id = users.id WHERE customers.user_id = <userID>```
1. View Staff Profile: ```SELECT * FROM staff INNER JOIN users ON staff.user_id = users.id WHERE staff.user_id = <userID>```
1. View All Customers: ```SELECT * FROM users INNER JOIN customers ON users.id = customers.user_id WHERE users.user_type = 'Customer'```
1. View All Staff: ```SELECT * FROM users INNER JOIN staff ON users.id = staff.user_id WHERE users.user_type = 'Staff'```