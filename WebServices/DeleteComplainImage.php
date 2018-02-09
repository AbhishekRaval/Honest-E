<?php

include ("Dd_details.php");
include("AllFunctions.php");
include ("Imagepaths.php");

    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);     
    $id = $jsonstring["cid"];

    //Getting image name for deleting file
   $imagename = getImagenamefromcid($id);
   $cpath = complainimgpath($imagename);
   unlink($cpath);
    //Deleting image details
    $query2 = "DELETE FROM `complaint_images` WHERE cimg_compid = '$id'";
    $res2 = mysql_query($query2);
    
    
    $msg="";
    if($res2>0)
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