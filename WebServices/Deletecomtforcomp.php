<?php
    include('AllFunctions.php');
    
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);
    
    $id = $jsonstring["cid"];
    
    include('Dd_details.php');
        
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);
    
    $query = "DELETE FROM `complaint_comment` WHERE Comment_complainid = '$id'";
    $res = mysql_query($query);
    $msg="";
    if($res>0)
    {
        $msg=1;
    }
    else
    {
        $msg=2;
    }   
    $jsonarray = array("idresult"=>$msg);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>



