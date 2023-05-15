<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];

if ( $key == "update" ){

    $ItemID         = $_POST['ItemID'];
    $ItemName       = $_POST['ItemName'];
    $Quantity    = $_POST['Quantity'];
    $Description    = $_POST['Description'];
    $Expiration      = $_POST['Expiration'];
    $picture    = $_POST['picture'];

    $Expiration =  date('Y-m-d', strtotime($Expiration));

    $query = "UPDATE tbl_inventory SET 
    ItemName='$ItemName', 
    Quantity='$Quantity', 
    Description='$Description',
    Expiration='$Expiration' 
    WHERE ItemID='$ItemID' ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

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