<?php
    include('AllFunctions.php');
    
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);
    
   $regid = $jsonstring["rid"];
    $compid = $jsonstring["cid"];
    
    include('Dd_details.php');        
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

   $flag = IssetLike($regid,$compid);
  
    $jsonarray = array("flag"=>$flag);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>



