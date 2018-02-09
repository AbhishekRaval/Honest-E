<?php

       $jsoninput = file_get_contents('php://input');
       $jsonstring = json_decode($jsoninput,TRUE);       
        include ("AllFunctions.php");
      
       $compid = $jsonstring["compid"];
       
       $rid = getRIDfromCompId($compid);
        
        $jsonarray = array("rid"=>$rid);
        header("Content-Type: application/json");
        $jsonoutput = json_encode($jsonarray);
        echo $jsonoutput;
      
?>