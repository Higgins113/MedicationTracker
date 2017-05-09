<?php
$host = "rds-medication-tracker.ca2v35zvbsgy.us-east-1.rds.amazonaws.com:3306";
$username = "Higgins113";
$password = "flabee113";
$db = "meditracker";

$user = "test";
$pass = "test";
$email = "test";

$conn = new mysqli($host, $username, $password, $db);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "insert into user (username,password,email) values ('test', 'test', 'test')";
if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close($con);
?>