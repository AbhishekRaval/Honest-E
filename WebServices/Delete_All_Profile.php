<?php
    include('AllFunctions.php');
    include ("Imagepaths.php");
    
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);
    
    $id = $jsonstring["lid"];
    
    include('Dd_details.php');
        
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);
    
    //deleting image
    $flag = IssetProfileimg($id);
    if($flag == 1)
    {
    $imagename = getProfileImageNamefromrid($id);
    $cpath1 = profileimgpath($imagename);
    unlink($cpath1);
    }
    //deleting image query from table
    $query3 = "DELETE FROM `user_images` WHERE user_rid = '$id'";
    $res3 = mysql_query($query3);
    
    //deleting plusone
    $query4 = "DELETE FROM `plusone_complaint` WHERE plusone_citizenid = '$id'";
    $res4 = mysql_query($query4);
    
    //deleting registration
    $query = "DELETE FROM `citizen_details_registration` WHERE Citizen_regid = '$id'";
    $res = mysql_query($query);
    
    //deleting login
    $query2 = "DELETE FROM `citizen_details_login` WHERE citizen_regid = '$id'";
    $res2 = mysql_query($query2);
    
    $msg="";
    if($res>0 && $res2>0)
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



