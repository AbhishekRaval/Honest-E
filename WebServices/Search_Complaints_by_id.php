<?php
 include("AllFunctions.php");
 include ("Dd_details.php");
 
$jsoninput=  file_get_contents('php://input');
$jsonstring = json_decode($jsoninput,TRUE);
//$otime1  
$otime = new DateTime($jsonstring["time"]);
$id = $jsonstring["searchid"];
$dbid = $jsonstring["dbid"];
include ("Dd_details.php");
mysql_connect($server,$Server_id,$server_pwd);     
mysql_select_db($selectdb);
 $Array = array();
$out;
if ($id == 1)
{
    //1 is for category
    $catid = getCatIDfromCatString($dbid);
    $querycat = "SELECT * FROM `complaint_details` WHERE complaint_categoryid = '$catid'";
    $catout = mysql_query($querycat);
    $out = $catout;
}
else if ($id == 2)
{
    //2 is for area
    $areaid = getareaidfromAreaString($dbid);
    $queryarea= "SELECT * FROM `complaint_details` WHERE citizen_area_id = '$areaid'";
    $areaout = mysql_query($queryarea);
    $out = $areaout;
}    

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