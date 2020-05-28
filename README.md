<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Irina Dyakonova</title>
</head>
<h1>Internet-shop</h1>
<h2>Project purpose</h2>
<p>This is a template for creating an e-store.</p>
<hr>
<p>
It implements typical functions for an online store. It has login and registration forms.<br/>
Available functions for all users:
</p>
<ul>
<li>view menu of the store</li>
<li>view items of the store</li>
<li>registration</li>
<li>log in</li>
<li>log out</li>
</ul>
<p>Available functions for users with a USER role only:</p>
<ul>
<li>add to user's bucket</li>
<li>delete from user's bucket</li>
<li>view all user's orders</li>
<li>view specific order</li>
<li>complete order</li>
<li>view a lists of selected items in user`s bucket</li>
</ul>
<p>Available functions for users with an ADMIN role only:</p>
<ul>
<li>add items to the store</li>
<li>add products to the store from DB</li>
<li>delete items from the store</li>
<li>view a list of all users</li>
<li>delete users from the store</li>
<li>view all orders in the store</li>
<li>view all orders for a specific user</li>
<li>view one specific order of any user</li>
<li>delete any order</li>
</ul>
<hr>
<h2 id="structure">Project Structure</h2>
<ul>
<li>Java 11</li>
<li>Maven 4.0.0</li>
<li>javax.servlet-api 3.1.0</li>
<li>jstl 1.2</li>
<li>log4j 1.2.17</li>
<li>maven-checkstyle-plugin</li>
<li>mysql-connector-java 8.0.20</li>
</ul>
<hr>
<h2 id="dev">For developer</h2>
<p>Open the project in your IDE.</p>
<p>Add it as maven project.</p>
<p>Configure Tomcat:</p>
<ul>
<li>add artifact</li>
<li>add sdk 11.0.6</li>
</ul>
<p>Add sdk 11.0.6 in project structure.</p>
<p>Use file /InternetShop/src/main/resources/init_db.sql to create schema and all the tables required by this app in MySQL database.</p>
<p>At /InternetShop/src/main/java/dev/internetshop/daoDao and /InternetShop/src/main/java/dev/InternetShop/serviceService class use username and password for your DB to create a Connection.</p>
<p>Change a path in /InternetShop/src/main/resources/log4j.properties. It has to reach your logFile.</p>
<p>Run the project.</p>
<p>If you want to try the USER role, you can register / log in</p>
<p>If you do not want to register on the login page, click on the Inject user and admin in DB link and you will have an admin with admin login and password 1, as well as user with password 1 and their roles are similar to logins </p>
<hr>
<h2 id="aut">Authors</h2>
<a href="https://github.com/IrinaDyakonova">Irina Dyakonova</a>
</html>