<?php
    include('AllFunctions.php');
    
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);
    
   $email = $jsonstring["emaild"];
    
    include('Dd_details.php');        
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);
    
$query3 = "SELECT * FROM `citizen_details_login` WHERE citizen_email = '$email'";
    //checking if E-mail id matches from current entries of DB.
    $mailcheck = mysql_query($query3);
    $no_row = mysql_num_rows($mailcheck);
    $flag = 0;
    //echo $no_row;
    if($no_row>0)
    {
        $deletequery = "DELETE FROM `citizen_details_registration` WHERE Citizen_regid = '$id'";
        $reslt = mysql_query($deletequery);
        $msg = "E-mail already exists";
        $flag = 1;
        //E-mail id already exists so here the program will quit
    }
  
    $jsonarray = array("flag"=>$flag);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>



