<?php
    include("AllFunctions.php");
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $content = $jsonstring["content"];
    $c_address = $jsonstring["add"];
    $category = $jsonstring["category"];
    $c_status = $jsonstring["stats"];
    $c_area = $jsonstring["Area"];
    $comp_id = $jsonstring["compid"];
    $subcat = $jsonstring["Subcat"];

    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

    
    //0.Obtaining Area_id from the given Area
    /* @var $callids2  \My\Name\Space\Path\MyEntity */  
    $ar_id = getareaidfromAreaString($c_area);
    
    //1.Obtaining registration id for given LID
   // $r_id = getRIDfromLID($l_id);
    
    //2.Obtaining Category id from given category String
    $cat_id = getCatIDfromCatString($category);
    
   //obtaining subcatid from given subcat string
    $subcatid = getSubcatIDfromString($subcat);

    
    $query = "UPDATE `complaint_details` SET `complaint_content`= '$content',`complaint_address`='$c_address',`citizen_area_id`= '$ar_id',`complaint_categoryid`='$cat_id',`complaint_subcatid`='$subcatid',`complaint_status`='$c_status' WHERE complaint_id = '$comp_id'"; 
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
   