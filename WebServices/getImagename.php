<?php
    include('AllFunctions.php');
    
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);
    
    $id = $jsonstring["cid"];
    
    include('Dd_details.php');
        
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);
    
   $imgName = getImagenamefromcid($id);
  
    $jsonarray = array("imgname"=>$imgName);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>



