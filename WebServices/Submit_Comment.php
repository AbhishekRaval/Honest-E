<?php
    include("AllFunctions.php");
   
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $content = $jsonstring["content"];
    $l_id = $jsonstring["logid"];
    $comp_id = $jsonstring["compid"];
    $c_time = $jsonstring["time"];

    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

      
    //1.Obtaining registration id for given LID
    $r_id = getRIDfromLID($l_id);
    
    //2.Inserting Data into Database
    //
    //1.{ Putting contents into registration table
    $query = "INSERT INTO `complaint_comment`(`Comment_id`, `Comment_complainid`, `comment_content`, `comment_regid`, `comment_timestamp`)  VALUES ('null', '$comp_id', '$content', '$r_id', '$c_time')";
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
        
   