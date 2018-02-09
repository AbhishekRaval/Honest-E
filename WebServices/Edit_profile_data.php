<?php

include("AllFunctions.php");
include ("Dd_details.php");
include ("Dd_details.php");
mysql_connect($server,$Server_id,$server_pwd);     
mysql_select_db($selectdb);
 
$jsoninput=  file_get_contents('php://input');
$jsonstring = json_decode($jsoninput,TRUE);

$rid = $jsonstring["rid"];

$query = "SELECT * FROM `citizen_details_registration` WHERE Citizen_regid = '$rid' ";
$out = mysql_query($query);
 
   while($row = mysql_fetch_array($out)) 
       {
// Append to the array 
       $name = ($row["citizen_name"]);
       $area = getAreaStringfromAreaID($row["citizen_area_id"]);
       $dob = ($row["citizen_dob"]);       
       $phoneno = ($row["citizen_phoneno"]);
       $gender = ($row["citizen_gender"]);
       }
       
 $query2 = "SELECT * FROM `citizen_details_login` WHERE citizen_regid = '$rid'";
 $out2 = mysql_query($query2);
 
    while($row = mysql_fetch_array($out2))
    {
       $email = ($row["citizen_email"]);
       $pass = ($row["citizen_password"]);
    }      
     $jsonarray = array("name"=>$name,"area"=>$area,"dob"=>$dob,"phone"=>$phoneno,"gender"=>$gender,"pass"=>$pass,"email"=>$email);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>