<?php
 include("AllFunctions.php");
 include ("Dd_details.php");
 
$jsoninput=  file_get_contents('php://input');
$jsonstring = json_decode($jsoninput,TRUE);
//$otime1  
$otime = new DateTime($jsonstring["time"]);
$com_id = $jsonstring["compID"];
include ("Dd_details.php");
mysql_connect($server,$Server_id,$server_pwd);     
mysql_select_db($selectdb);

$query = "SELECT * FROM `complaint_comment` WHERE Comment_complainid ='$com_id' ORDER BY comment_timestamp ASC";
$out = mysql_query($query);

 $Array = array();
 
  
   while($row = mysql_fetch_array($out)) 
       {
// Append to the array 
       $commentid = $row["Comment_id"];
       $name = getNamefromRID($row["comment_regid"]);
       $commentcnt = $row["comment_content"];
       $timen = new DateTime($row["comment_timestamp"]);
       $ago = agoTime($timen,$otime);
       $ridcomment = $row["comment_regid"];
       $Array[]=array("Name"=>$name,"Content"=>$commentcnt,"Time"=>$ago,"ID"=>$commentid,"comment_regid"=>$ridcomment);   
      }
   
    header("Content-Type: application/json");
    echo json_encode($Array);
    
    ?>