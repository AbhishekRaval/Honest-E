<?php

 $jsoninput = file_get_contents('php://input');
       $jsonstring = json_decode($jsoninput,TRUE);       
      
       $email = $jsonstring["email"];
       $pass = $jsonstring["pwd"];
       
       include ("Dd_details.php");
        mysql_connect($server,$Server_id,$server_pwd);     
        mysql_select_db($selectdb);

        $query = "SELECT * FROM `citizen_details_login` WHERE citizen_email = '$email' AND citizen_password = '$pass'";
        $res = mysql_query($query); 
        $lid = "";
        //$Lid stores the login id of successful login
        
        if($row = mysql_fetch_array($res))
        {
            $lid = $row['citizen_logid'];
             //lid is stored
        }
        else
        {
            $lid = "-1";
            // -1 means login unsuccessful
        }
        
        $jsonarray = array("Email"=>$lid);
        header("Content-Type: application/json");
        $jsonoutput = json_encode($jsonarray);
        echo $jsonoutput;
      
?>