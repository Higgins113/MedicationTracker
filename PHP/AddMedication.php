<?php
$host = "rds-medication-tracker.ca2v35zvbsgy.us-east-1.rds.amazonaws.com:3306";
$username = "Higgins113";
$password = "flabee113";
$db = "meditracker";

$user = $_POST['Username'];
$medName = $_POST['MedName'];
$medType = $_POST['MedType'];
$medQuantity = $_POST['MedQuantity'];
$ingestion = $_POST['Ingestion'];
$startDate = $_POST['StartDate'];
$dosage = $_POST['Dosage'];
$duration = $_POST['Duration'];

$conn = new mysqli($host, $username, $password, $db);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "insert into medication (username,medname,medtype,medquantity,ingestion,startDate,dosage,duration) values ('$user','$medName','$medType','$medQuantity','$ingestion','$startDate','$dosage','$duration')";
if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close($con);
?>