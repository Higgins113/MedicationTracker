<?php
$host = "rds-medication-tracker.ca2v35zvbsgy.us-east-1.rds.amazonaws.com:3306";
$username = "Higgins113";
$password = "flabee113";
$db = "meditracker";

$user = $_POST['Username'];
$medName = $_POST['MedName'];
$medType = $_POST['MedType'];
$ingestion = $_POST['Ingestion'];
$startDate = $_POST['StartDate'];

$conn = new mysqli($host, $username, $password, $db);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT medName FROM medication WHERE StartDate = '$startDate'";
//$sql = "SELECT medName FROM medication WHERE StartDate = '4/5/2017'";
$result = $conn->query($sql);


while($row = mysqli_fetch_array($result))
{
   $data = $row[medName];

   if($data){
      echo $data;
   }
}

 //$data = $row[medName];

  // if($data){
 //     echo $data;
 //  }

mysqli_close($con);
?>