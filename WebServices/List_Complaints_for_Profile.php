<?php
 include("AllFunctions.php");
 include ("Dd_details.php");
 
$jsoninput=  file_get_contents('php://input');
$jsonstring = json_decode($jsoninput,TRUE);
//$otime1  
$otime = new DateTime($jsonstring["time"]);
$user_rid = $jsonstring["userregid"];
include ("Dd_details.php");
mysql_connect($server,$Server_id,$server_pwd);     
mysql_select_db($selectdb);

$query = "SELECT * FROM `complaint_details` WHERE complaint_citizenid = '$user_rid' ORDER BY complaint_timestamp DESC";
$out = mysql_query($query);

 $Array = array();
 
  
   while($row = mysql_fetch_array($out)) 
       {
// Append to the array 
       $compid = $row["complaint_id"];
       $name = getNamefromRID($row["complaint_citizenid"]);
       $category = getCatStringfromCatID($row["complaint_categoryid"]);
       $det = $row["complaint_content"];
       $timen = new DateTime($row["complaint_timestamp"]);
       $ago = agoTime($timen,$otime);      
       $area = getAreaStringfromAreaID($row["citizen_area_id"]);
       $r1id = $row["complaint_citizenid"];
       $subcat = getSubCatStringfromid($row["complaint_subcatid"]);
       $Array[]=array("Area"=>$area,"Name"=>$name,"Cat"=>$category,"Cont"=>$det,"subcat"=>$subcat,"Time"=>$ago,"ID"=>$compid,"Complaint_rid"=>$r1id);  
      }
   
    header("Content-Type: application/json");
    echo json_encode($Array);
    
    ?>