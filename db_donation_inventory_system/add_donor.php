<?php 

require_once 'connect.php';

$key = $_POST['key'];

$name       = $_POST['name'];
$Address    = $_POST['Address'];
$Email    = $_POST['Email'];
$ContactNumber      = $_POST['ContactNumber'];
$gender     = $_POST['gender'];
$birth      = $_POST['birth'];
$picture    = $_POST['picture'];

if ( $key == "insert" ){

    $birth_newformat = date('Y-m-d', strtotime($birth));

    $query = "INSERT INTO tbl_donors (name,Address,Email,ContactNumber,gender,birth)
    VALUES ('$name', '$Address', '$Email', '$ContactNumber', '$gender', '$birth_newformat') ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $finalPath = "/demo_pets/pet_logo.png"; 
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

                $DonorID = mysqli_insert_id($conn);
                $path = "pets_picture/$DonorID.jpeg";
                $finalPath = "/demo_pets/".$path;

                $insert_picture = "UPDATE pets SET picture='$finalPath' WHERE DonorID='$DonorID' ";
            
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