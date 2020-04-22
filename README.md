# About the application

### Reference Documentation
This application consists of the following microservices:

* User Account Service - the command side business logic for User Accounts
* Auth Service - the command sing in and sign up into the system.
* Rol Service - the command side business logic for poles.
* Person Service - the command side business logic for people.

### Guides
The following guides illustrate how to use some features concretely:

* /api/auth/signup and /api/user* should be authenticated.
* /api/auth/signup should have superadmin rol.
* In /credential/src/main/resources/DBScript are Postgres database script:
	** credential_schema.sql has the credential schema.
	** init_data.sql has the initial data for credential schema.