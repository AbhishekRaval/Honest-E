<?php

   $jsoninput = file_get_contents('php://input');
   $jsonstring = json_decode($jsoninput,TRUE);       
   
  include ("Dd_details.php");
  mysql_connect($server,$Server_id,$server_pwd);     
  mysql_select_db($selectdb);

    //1.{ Putting contents into registration table
   $query = "SELECT * FROM `complaint_category`";
   $out = mysql_query($query);
   $nameArray = array();
   while($row = mysql_fetch_array($out)) 
       { 
       // Append to the array       
       $area=$row["category_name"];
       $nameArray[]=array("cat"=>$area);       
       }
   
    header("Content-Type: application/json");
    echo json_encode($nameArray);
?>