<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$query = "SELECT * FROM tbl_inventory ORDER BY ItemID DESC ";
$result = mysqli_query($conn, $query);
$response = array();

$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'ItemID'        =>$row['ItemID'], 
        'ItemName'      =>$row['ItemName'], 
        'Quantity'   =>$row['Quantity'],
        'Description'   =>$row['Description'],
        'Expiration'     =>date('d M Y', strtotime($row['Expiration'])),
        'picture'   =>"http://$server_name" . $row['picture']));
}

echo json_encode($response);

mysqli_close($conn);

?>