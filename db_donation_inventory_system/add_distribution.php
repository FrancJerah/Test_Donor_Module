<?php 

require_once 'connect.php';

$key = $_POST['key'];

$Recipient               = $_POST['Recipient'];
$DistributionLocation    = $_POST['DistributionLocation'];
$Quantity                = $_POST['Quantity'];
$Notes                   = $_POST['Notes'];
$Status                  = $_POST['Status'];
$DistributionDate        = $_POST['DistributionDate'];
$picture                 = $_POST['picture'];

if ( $key == "insert" ){

    $DistributionDate = date('Y-m-d', strtotime($DistributionDate));

    $query = "INSERT INTO tbl_distribution (Recipient,DistributionLocation,Quantity,Notes,Status,DistributionDate)
    VALUES ('$Recipient', '$DistributionLocation', '$Quantity', '$Notes', '$Status', '$DistributionDate_newformat') ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $finalPath = "/demo_pets/pet_logo.png"; 
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

                $DistributionID = mysqli_insert_id($conn);
                $path = "pets_picture/$DistributionID.jpeg";
                $finalPath = "/demo_pets/".$path;

                $insert_picture = "UPDATE tbl_distribution SET picture='$finalPath' WHERE DistributionID='$DistributionID' ";
            
                if (mysqli_query($conn, $insert_picture)) {
            
                    if ( file_put_contents( $path, base64_decode($picture) ) ) {
                        
                        $result["value"] = "1";
                        $result["message"] = "Success!";
            
                        echo json_encode($result);
                        mysqli_close($conn);
            
                    } else {
                        
                        $response["value"] = "0";
                        $response["message"] = "Error! ".mysqli_error($conn);
                        echo json_encode($response);

                        mysqli_close($conn);
                    }

                }
            }

        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }
}

?>