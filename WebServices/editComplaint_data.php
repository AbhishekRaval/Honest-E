<?php
    include("AllFunctions.php");
 $jsoninput = file_get_contents('php://input');
       $jsonstring = json_decode($jsoninput,TRUE);       
      
       $compID = $jsonstring["compid"];
       
       include ("Dd_details.php");
        mysql_connect($server,$Server_id,$server_pwd);     
        mysql_select_db($selectdb);

        $query = "SELECT * FROM `complaint_details` WHERE complaint_id = '$compID'";
        $res = mysql_query($query); 
        $lid = "";
        //$Lid stores the login id of successful login
        
        if($row = mysql_fetch_array($res))
        {
             $content = $row['complaint_content'];
             $add = $row['complaint_address'];
             $are = $row['citizen_area_id'];
             $area = getAreaStringfromAreaID($are);
             $cat = $row['complaint_categoryid'];
             $category = getCatStringfromCatID($cat);
             $subcategory = getSubCatStringfromid($row["complaint_subcatid"]);
        }
        else
        {
         //
        }
        
        $jsonarray = array("description"=>$content,"address"=>$add,"area"=>$area,"category"=>$category,"subcat"=>$subcategory);
        header("Content-Type: application/json");
        $jsonoutput = json_encode($jsonarray);
        echo $jsonoutput;
      
?>