<?php

   $jsoninput = file_get_contents('php://input');
   $jsonstring = json_decode($jsoninput,TRUE);       
   
    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

    //1.{ Putting contents into registration table
   $query = "SELECT * FROM `area_zone_details`";
   $out = mysql_query($query);
   $nameArray = array();
   $id=0;
   while($row = mysql_fetch_array($out)) {
    // Append to the array0
       $area=$row["Area"];       
       $nameArray[]=array("area"=>$area);     
   }   
    header("Content-Type: application/json");
    echo json_encode($nameArray);
?>