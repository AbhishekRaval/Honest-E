<?php
    include('AllFunctions.php');
   $jsoninput = file_get_contents('php://input');
   $jsonstring = json_decode($jsoninput,TRUE);     
   $id1 = $jsonstring["cid"];
   $id = getCatIDfromCatString($id1);
  include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

   $query = "SELECT * FROM `complaint_subcategory` WHERE categoryid = '$id '";
   $out = mysql_query($query);
   $nameArray = array();
   while($row = mysql_fetch_array($out)) 
       { 
       // Append to the array       
       $subcat=$row["subcategory_name"];
       $nameArray1[]=array("Subcat"=>$subcat);   
       }
    header("Content-Type: application/json");
    echo json_encode($nameArray1);
   ?>