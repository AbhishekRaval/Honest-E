<?php
    include("AllFunctions.php");
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $regid = $jsonstring["rid"];
    $compid = $jsonstring["cid"];
    
    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

    $query = "INSERT INTO `plusone_complaint`(`plusone_id`, `plusone_status`, `plusone_citizenid`, `plusone_complaintid`) VALUES ('null','1','$regid','$compid')";
    $res = mysql_query($query); 
    $msg = " ";
    $successid;
    $complainid;
    if($res>0)
    {
        $msg = $msg."Liked Successfully";
        $successid="1";     
    }
    else
    {
        $msg = $msg. "Failed to Like";
        $successid="0";
    }
    
    $jsonarray = array("id"=>$successid,"Message"=>$msg);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>        
   