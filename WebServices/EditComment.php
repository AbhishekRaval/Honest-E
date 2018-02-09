<?php
    include("AllFunctions.php");
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $content = $jsonstring["content"];
    $commentid = $jsonstring["cmtid"];

    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

    
    $query = "UPDATE `complaint_comment` SET `comment_content`= '$content'  WHERE  Comment_id = '$commentid' ";
    $res = mysql_query($query); 
    $msg = " ";
    $successid;
    if($res>0)
    {
        $msg = $msg."Complaint Added Successfully";
        $successid="1";
    }
    else
    {
        $msg = $msg. "Failed to add complaint";
        $successid="-1";
    }
    
    $jsonarray = array("id"=>$successid,"Message"=>$msg);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
 ?>      
   