<?php

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$query = "SELECT * FROM tbl_distribution ORDER BY DistributionID DESC ";
$result = mysqli_query($conn, $query);
$response = array();

$server_name = $_SERVER['SERVER_ADDR'];

while ($row = mysqli_fetch_assoc($result)) {
    array_push($response, array(
        'DistributionID' => $row['DistributionID'],
        'Recipient' => $row['Recipient'],
        'DistributionLocation' => $row['DistributionLocation'],
        'Quantity' => $row['Quantity'],
        'Notes' => $row['Notes'],
        'Status' => $row['Status'],
        'DistributionDate' => date('d M Y', strtotime($row['DistributionDate'])),
        'picture' => "http://$server_name" . $row['picture']
    ));
}

echo json_encode($response);

mysqli_close($conn);

?>
