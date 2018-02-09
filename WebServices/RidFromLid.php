<?php

       $jsoninput = file_get_contents('php://input');
       $jsonstring = json_decode($jsoninput,TRUE);       
        include ("AllFunctions.php");
      
       $lid = $jsonstring["lid"];
       
       $rid = getRIDfromLID($lid);
        
        $jsonarray = array("rid"=>$rid);
        header("Content-Type: application/json");
        $jsonoutput = json_encode($jsonarray);
        echo $jsonoutput;
      
?>