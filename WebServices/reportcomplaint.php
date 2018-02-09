<?php
    include("AllFunctions.php");
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $regid = $jsonstring["rid"];
    $compid = $jsonstring["cid"];
    
    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

    $query = "INSERT INTO `complaint_report`(`report_id`, `Comment_id`, `Registration_id`) VALUES ('null','$compid','$regid')";
    $res = mysql_query($query); 
    $msg = " ";
    $successid=0;
    $complainid;
    if($res>0)
    {
        $msg = $msg."Reported Successfully";
        $successid="1";     
    }
    else
    {
        $msg = $msg. "Failed to Report";
        $successid="0";
    }
    
    $jsonarray = array("id"=>$successid,"Message"=>$msg);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
?>        
   