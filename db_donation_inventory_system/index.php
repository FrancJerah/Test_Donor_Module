<?php 

    require_once 'config/db.php';
    require_once 'config/functions.php';
 //  $query = "select * from tbl_donors";
   // $result = mysqli_query($query);

    $result = display_data();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" a href="css/custom.css"/>
    <title>View Records</title>
</head>
<body class="bg-dark">

<div id="subcontent">
<table id="data-list">
                          <tr>
                                <th> Donor ID </th>
                                <th> Name </th>
                                <th> Address </th>
                                <th> Contact Number</th>
                                <th> Email </th>
                                <th> Gender</th>
                                <th> birth </th>
                            </tr>

                            <?php 
                                    
                                    while($row=mysqli_fetch_assoc($result))
                                    {
                                        $DonorID = $row['DonorID'];
                                        $Name = $row['name'];
                                        $Address = $row['Address'];
                                        $ContactNumber = $row['ContactNumber'];
                                        $Email = $row['Email'];
                                        $gender = $row['gender'];
                                        $birth = $row['birth'];
                                       
                            ?>
                                  <tr>
                                        <td><?php echo $DonorID ?></td>
                                        <td><?php echo $Name ?></td>
                                        <td><?php echo $Address ?></td>
                                        <td><?php echo $ContactNumber ?></td>
                                        <td><?php echo $Email ?></td>
                                        <td><?php echo $gender ?></td>
                                        <td><?php echo $birth ?></td>
                                        
                                    </tr>       
                                  
                                   
                            <?php 
                                    }  
                            ?>                                                                    
                                   

                        </table>
                    </div>
                </div>
          
</body>
</html>