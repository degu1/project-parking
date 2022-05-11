<h1>Project parking</h1>

------------------------------


<h2>Description</h2>

    This app is a private parking lot management system.
    1. As a User you can get a parking slot(with or without electric charge) in a parking lot if there are empty slots available.
    If there is not any slots available you are automaticly put in the queue.
    2. As a Manager you can manage parking lots.(create or remove slot or change name of the lot)
    3. As a Admin you can do everything that a manager can do and add create or remove hole lots, remove peaple from queue.
    
    Default user logins are at the bottom of the readme.

<h2>Install</h2>

<h3>Environment variables for email</h3>
<h4>Edit .env file with these variables.</h4>
<table>
  <tr>
    <th>Variable name</td>
    <th>Value</td>
  </tr>
    <tr>
    <td>EMAIL_USERNAME</td>
    <td>Your <u>Gmail</u> address</td>
  </tr>
    </tr>
    <tr>
    <td>EMAIL_PASSWORD</td>
    <td>Your app password</td>
  </tr>
</table>

1. Download Zip or clone project from: https://github.com/degu1/project-parking
2. (if you have downloaded zip) unpack the zip.file.
3. Set environment variables as listed above.
6. If you don't have Docker, download and install it from here: https://www.docker.com/
4. Open you preferred terminal.
5. In the terminal go to the root folder of the project.
7. Type in docker-compose up.(wait for installment)
8. Run the app in you preferred browser. http://localhost:8080/login


<h2>Default users login</h2>

    Email: milad.nazari@iths.se          Password: password      Role: User
    Email: jonas.fredriksson@iths.se     Password: password      Role: Manager
    Email: dennis.gustafsson@iths.se     Password: password      Roles: User, Manager, Admin




