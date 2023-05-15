<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];

if ( $key == "update" ){

    $DonorID         = $_POST['DonorID'];
    $name       = $_POST['name'];
    $Address    = $_POST['Address'];
    $Email    = $_POST['Email'];
    $ContactNumber      = $_POST['ContactNumber'];
    $gender     = $_POST['gender'];
    $birth      = $_POST['birth'];
    $picture    = $_POST['picture'];

    $birth =  date('Y-m-d', strtotime($birth));

    $query = "UPDATE tbl_donors SET 
    name='$name', 
    Address='$Address', 
    Email='$Email',
    ContactNumber='$ContactNumber',
    gender='$gender',
    birth='$birth' 
    WHERE DonorID='$DonorID' ";

        if ( mysqli_query($conn, $query) ){

            if ($picture == null) {

                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {

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