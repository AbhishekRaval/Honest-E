<?php
 include("AllFunctions.php");
 include ("Dd_details.php");
 
$jsoninput=  file_get_contents('php://input');
$jsonstring = json_decode($jsoninput,TRUE);
//$otime1  

$cid = $jsonstring["rid"];
include ("Dd_details.php");

mysql_connect($server,$Server_id,$server_pwd);     
mysql_select_db($selectdb);

$query = "SELECT `complaint_id` FROM `complaint_details` WHERE complaint_citizenid = '$cid'";
$out = mysql_query($query);

 $Array = array();
 
  
   while($row = mysql_fetch_array($out)) 
       {
// Append to the array 
        $compID = $row["complaint_id"];
       $Array[]=array("cid"=>$compID);   
      }
   
    header("Content-Type: application/json");
    echo json_encode($Array);
    
    ?>