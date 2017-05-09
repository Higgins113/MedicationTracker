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

$sql = "SELECT * FROM medication";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo "MedName: " . $row["MedName"]."<br>";
    }
} else {
    echo "0 results";
}

mysqli_close($con);
?>