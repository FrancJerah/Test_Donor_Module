<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$query = "SELECT * FROM tbl_donors ORDER BY DonorID DESC ";
$result = mysqli_query($conn, $query);
$response = array();

$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'DonorID'        =>$row['DonorID'], 
        'name'      =>$row['name'], 
        'Address'   =>$row['Address'],
        'Email'   =>$row['Email'],
        'ContactNumber'     =>$row['ContactNumber'],
        'gender'    =>$row['gender'],
        'birth'     =>date('d M Y', strtotime($row['birth'])),
        'picture'   =>"http://$server_name" . $row['picture'],
        'love'      =>$row['love']) 
    );
}

echo json_encode($response);

mysqli_close($conn);

?>