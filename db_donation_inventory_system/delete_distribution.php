<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key                 = $_POST['key'];
$DistributionID      = $_POST['DistributionID'];
$picture             = $_POST['picture'];

if ( $key == "delete" ){

    $query = "DELETE FROM tbl_distribution WHERE DistributionID='$DistributionID' ";

        if ( mysqli_query($conn, $query) ){

            $iparr = split ("/", $picture);
            $picture_split = $iparr[5];

            if ( unlink("pets_picture/".$picture_split) ){

                $result["value"] = "1";
                $result["message"] = "Success!";

                echo json_encode($result);
                mysqli_close($conn);

            } else {
            
                $response["value"] = "0";
                $response["message"] = "Error to delete a image! ".mysqli_error($conn);
                echo json_encode($response);
    
                mysqli_close($conn);
            }

        } 
        else {

            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }

} else {
    $response["value"] = "0";
    $response["message"] = "Error! ".mysqli_error($conn);
    echo json_encode($response);

    mysqli_close($conn);
}

?>