<?php 

require_once 'connect.php';

$key = $_POST['key'];

$ItemName       = $_POST['ItemName'];
$Quantity       = $_POST['Quantity'];
$Description    = $_POST['Description'];
$Expiration     = $_POST['Expiration'];
$picture        = $_POST['picture'];

if ( $key == "insert" ){

    $expiration_newformat = date('Y-m-d', strtotime($Expiration));

    $query = "INSERT INTO tbl_inventory (ItemName,Quantity,Description,Expiration)
    VALUES ('$ItemName', '$Quantity', '$Description', '$expiration_newformat') ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $finalPath = "/demo_pets/pet_logo.png"; 
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

                $DonorID = mysqli_insert_id($conn);
                $path = "pets_picture/$ItemID.jpeg";
                $finalPath = "/demo_pets/".$path;

                $insert_picture = "UPDATE tbl_inventory SET picture='$finalPath' WHERE ItemID='$ItemID' ";
            
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