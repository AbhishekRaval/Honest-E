<?php
    include("AllFunctions.php");
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $content = $jsonstring["content"];
    $c_address = $jsonstring["add"];
    $l_id = $jsonstring["lid"];
    $category = $jsonstring["category"];
    $c_status = $jsonstring["stats"];
    $c_time = $jsonstring["time"];
    $c_area = $jsonstring["Area"];
    $subcat = $jsonstring["subcat"];

    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

    
    //0.Obtaining Area_id from the given Area
    /* @var $callids2  \My\Name\Space\Path\MyEntity */  
    $ar_id = getareaidfromAreaString($c_area);
    
    //1.Obtaining registration id for given LID
    $r_id = getRIDfromLID($l_id);
    
    //2.Obtaining Category id from given category String
    $cat_id = getCatIDfromCatString($category);
    
    //obtaining subcatid from given subcat string
    $subcatid = getSubcatIDfromString($subcat);
    
    $query = "INSERT INTO `complaint_details`(`complaint_id`, `complaint_content`, `complaint_address`, `citizen_area_id`, `complaint_citizenid`, `complaint_categoryid`, `complaint_subcatid`, `complaint_status`, `complaint_timestamp`) VALUES ('null','$content','$c_address','$ar_id','$r_id','$cat_id','$subcatid','$c_status','$c_time')"; 
    $res = mysql_query($query); 
    $msg = " ";
    $successid;
    $complainid;
    if($res>0)
    {
        $msg = $msg."Complaint Added Successfully";
        $successid="1";
        $complainid =  mysql_insert_id();
        
        
    }
    else
    {
        $msg = $msg. "Failed to add complaint";
        $successid="-1";
    }
    
    $jsonarray = array("id"=>$successid,"Message"=>$msg,"complainid"=>$complainid);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
 ?>
        
   