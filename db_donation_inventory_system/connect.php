<?php 
define('host', 'localhost');
define('user', 'root');
define('pass', '');
// changed to tutorials
define('db', 'db_donation_inventory_system');

$conn = mysqli_connect(host, user, pass, db) or die('Unable to Connect');
?>