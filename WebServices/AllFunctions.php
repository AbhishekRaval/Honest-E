<?php
    include ("Dd_details.php");
    mysql_connect($server,$Server_id,$server_pwd);     
    mysql_select_db($selectdb);

//obtaining Area id from given input area string
function getareaidfromAreaString($AreaString) {
    $query0 = "SELECT `area_id` FROM `area_zone_details` WHERE Area = '$AreaString'";
    $res1 = mysql_query($query0);    
    $ar_id = 0;
    while($row = mysql_fetch_array($res1))
        {
            $ar_id=$row['area_id'];
        }
    return $ar_id;
    }
    
    //obtaining Area string from given Area id
    function getAreaStringfromAreaID($AreaID) {
    $query0 = "SELECT `Area` FROM `area_zone_details` WHERE area_id = '$AreaID'";
    $res1 = mysql_query($query0);    
    $AreaString = "";
    if($res1 === FALSE) { 
    die(mysql_error()); // TODO: better error handling
}
    while($row = mysql_fetch_array($res1))
        {
            $AreaString =$row['Area'];
        }
    return $AreaString;
    }
    
    
    //Obtaining registration id for given LID
  function getRIDfromLID($r_id)
    {        
    $query1 = "SELECT  `citizen_regid` FROM `citizen_details_login` WHERE citizen_logid = '$r_id'";
    $res1 = mysql_query($query1);    
    $r_id = 0;
    while($row = mysql_fetch_array($res1))
        {
            $r_id=$row['citizen_regid'];
        }
        return $r_id;    
    }
    
    //Obtaining Category id from given category String
  function getCatIDfromCatString($cat_string)
    {
    $query2 = "SELECT `category_id` FROM `complaint_category` WHERE category_name = '$cat_string'";
    $res2 = mysql_query($query2);    
    $cat_id = 0;
    while($row = mysql_fetch_array($res2))
    {
        $cat_id=$row['category_id'];
    }
    return $cat_id;  
    }
    
    //obtaining SubCategory id from given SubCategory String
    function getSubcatIDfromString($subcat)
    {
        $querysub = "SELECT `subcategory_id` FROM `complaint_subcategory` WHERE subcategory_name = '$subcat'";
        $ressub = mysql_query($querysub);
        $subcatid = 0;
        while ($row = mysql_fetch_array($ressub)) {
            $subcatid = $row['subcategory_id'];
        }
        return $subcatid;
        
    }


    //obtaining category name from given category id
    function getCatStringfromCatID($cat_id)
    {
    $query2 = "SELECT `category_name` FROM `complaint_category` WHERE category_id = '$cat_id'";
    $res2 = mysql_query($query2);    
    $cat_string = 0;
    while($row = mysql_fetch_array($res2))
    {
        $cat_string=$row['category_name'];
    }
    return $cat_string;  
    }
    
    //obtaining Subcategory name from given subcategory id
    function getSubCatStringfromid($sbcatid){
    $query2sub = "SELECT  `subcategory_name` FROM `complaint_subcategory` WHERE subcategory_id = '$sbcatid'";
    $res2sub = mysql_query($query2sub);    
    $subcat_string = 0;
    while($row = mysql_fetch_array($res2sub))
    {
        $subcat_string=$row['subcategory_name'];
    }
    return $subcat_string;        
        
    }
    //obtaining name from given RID
    function getNamefromRID($rid)
    {
     $query3 = "SELECT `citizen_name` FROM `citizen_details_registration` WHERE Citizen_regid = '$rid'";
     $res3 = mysql_query($query3);
     $name = "";
     while($row = mysql_fetch_array($res3))
     {
         $name = $row['citizen_name'];
     }
     return $name;
    }
    
    //obtaining rid from complaint id
    function getRIDfromCompId($ctid)
    {
     $query4 = "SELECT `complaint_citizenid` FROM `complaint_details` WHERE complaint_id = '$ctid'";
     $res4 = mysql_query($query4);
     $rid4 = "";
     while($row = mysql_fetch_array($res4))
     {
         $rid4 = $row['complaint_citizenid'];
     }
     return $rid4;    
    }
    //calculating agotime
    function agoTime($timebefore,$timenow) {
        $delta = $timenow->format('U') - $timebefore->format('U');
        if ($delta < 60) {
          return 'seconds ago';
         } else if ($delta < 120) {
        return 'a minute ago';
         } else if ($delta < (45 * 60)) {
        return floor($delta / 60) . ' minutes ago';
         } else if ($delta < (90 * 60)) {
        return 'an hour ago';
         } else if ($delta < (24 * 60 * 60)) {
        return floor($delta / 3600) . ' hours ago';
         } else if ($delta < (48 * 60 * 60)) {
        return '1 day ago';
         } else if ($delta < (11 * 24 * 60 * 60)) {
        return 'a week ago';
         } else if ($delta < (30 * 24 * 60 * 60)) {
        return floor($delta / 604800) . ' weeks ago';
         } else if ($delta < (45 * 24 * 60 * 60)) {
        return 'a month ago.';
         } else if ($delta < (365 * 24 * 60 * 60)) {
        return floor($delta / 2592000) . ' months ago';
         } else if ($delta < (550 * 24 * 60 * 60)) {
        return 'a year ago';
         } else {
        return floor($delta / 31536000) . ' years ago';
         }
    }
    
    function delete_complaint($id)
    {
       $query = "DELETE FROM `complaint_details` WHERE complaint_id = '$id'";
       $result = mysql_query($query);
       return $result;
    }
    
    function getImagenamefromcid($id)
    {
     $query = "SELECT `cimg_name` FROM `complaint_images` WHERE cimg_compid = '$id'";
    $res = mysql_query($query);
    $imgName = "";
    if($row=mysql_fetch_array($res))
        {
            $imgName = $row['cimg_name'];
        }
        return $imgName;
    }
    
    function getProfileImageNamefromrid($rid)
    {
     $query = "SELECT `user_imgname`FROM `user_images` WHERE user_rid = '$rid'";
     $res = mysql_query($query);
     $imgName = "";
    if($row=mysql_fetch_array($res))
        {
            $imgName = $row['user_imgname'];
        }
        return $imgName;        
    }
    
    function IssetProfileimg($rid)
    {
     $query = "SELECT `user_imgname`FROM `user_images` WHERE user_rid = '$rid'";
     $res = mysql_query($query);
     $no_row = mysql_num_rows($res);
     $Flag=0;
    if($no_row>0)
    {
        $Flag=1;
    }
 else {
    $Flag=0;    
    }
    return $Flag;        
    }       
    
    function ageCalculator($dob){
    if(!empty($dob)){
        $birthdate = new DateTime($dob);
        $today   = new DateTime('today');
        $age = $birthdate->diff($today)->y;
        return $age;
    }else{
        return 0;
    }
}

function IssetLike($rid, $cid)
{
    $query = "SELECT `plusone_id`, `plusone_status`, `plusone_citizenid`, `plusone_complaintid` FROM `plusone_complaint` WHERE `plusone_complaintid` = '$cid' AND `plusone_citizenid` = '$rid' AND `plusone_status`='1' ";
     $res = mysql_query($query);
     $no_row = mysql_num_rows($res);
     $Flag=0;
    if($no_row>0)
    {
        $Flag=1;
    }
 else {
    $Flag=0;    
    }

    return $Flag;
 }    
    
?>