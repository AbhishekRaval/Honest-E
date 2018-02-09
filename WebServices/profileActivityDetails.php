<?php
 include("AllFunctions.php");
 include ("Dd_details.php");
 
$jsoninput=  file_get_contents('php://input');
$jsonstring = json_decode($jsoninput,TRUE);
$rid = $jsonstring["rid"];
include ("Dd_details.php");
mysql_connect($server,$Server_id,$server_pwd);     
mysql_select_db($selectdb);

$query = "SELECT * FROM `citizen_details_registration` WHERE Citizen_regid = '$rid' ";
$out = mysql_query($query);
 
   while($row = mysql_fetch_array($out)) 
       {
// Append to the array 
       $name = ($row["citizen_name"]);
       $area = getAreaStringfromAreaID($row["citizen_area_id"]);
       $dob = ($row["citizen_dob"]);       
       $age = ageCalculator($dob);
       
       }
     $jsonarray = array("name"=>$name,"age"=>$age,"area"=>$area);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
    
?>