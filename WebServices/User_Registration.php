<?php
    include("AllFunctions.php");
    $jsoninput = file_get_contents('php://input');
    $jsonstring = json_decode($jsoninput,TRUE);       

    $email = $jsonstring["email"];
    $pass = $jsonstring["pwd"];
    $name = $jsonstring["name"];
    $gender = $jsonstring["gender"];
    $dob = $jsonstring["dob"];
    $area = $jsonstring["area"];
    $phone = $jsonstring["phone"];
    //$zone = $jsonstring["zone"];

   include ("Dd_details.php");
   mysql_connect($server,$Server_id,$server_pwd);     
   mysql_select_db($selectdb);

    
    //0.Obtaining Area_id from the given Area
    $ar_id = getareaidfromAreaString($area);
    
    //1.{ Putting contents into registration table
    $query = "INSERT INTO `citizen_details_registration`(`Citizen_regid`, `citizen_name`, `citizen_gender`, `citizen_dob`, `citizen_area_id`, `citizen_phoneno`) VALUES ('null','$name','$gender','$dob','$ar_id','$phone')";
    $norow = mysql_query($query); 
    $msg = "";
    $lid = "-1";
    if($norow>0)
    {
        //if registration is successful
        //$msg = $msg."Success registration";
    }
    else
    {
        //if contents aren't updated in database
        //$msg = $msg. "Try again registration";
    }
    //}.1

    //2.{ Obtaining Registration Id to be added in Login table 
    $query2 = "SELECT `Citizen_regid` FROM `citizen_details_registration` WHERE citizen_name = '$name' AND citizen_dob = '$dob'";
    $num = mysql_query($query2);
    $id =0;
      while($row = mysql_fetch_array($num))
      {
          $id=$row['Citizen_regid'];
      }
    //Registration id is saved in $id ).2
    
    //Updating contents in Login Table from the $id as registration id. 
    //Firstly we'll check if the E-mail id already exists or not.
    // If e-mail Id exists, we'll delete contents from registration DB.
        
    $query3 = "SELECT * FROM `citizen_details_login` WHERE citizen_email = '$email'";
    //checking if E-mail id matches from current entries of DB.
    $mailcheck = mysql_query($query3);
    $no_row = mysql_num_rows($mailcheck);
    //echo $no_row;
    if($no_row>0)
    {
        $deletequery = "DELETE FROM `citizen_details_registration` WHERE Citizen_regid = '$id'";
        $reslt = mysql_query($deletequery);
        $msg = "E-mail already exists";
        //E-mail id already exists so here the program will quit
    }
     else 
    {
     //if E-mail id is unique, we'll update it, into Login Table. 
        $query4 = "INSERT INTO `citizen_details_login`(`citizen_logid`, `citizen_email`, `citizen_password`, `citizen_regid`) VALUES ('null','$email','$pass','$id')";
        $norow1 = mysql_query($query4);        

        if($norow1>0)
        {
           // $msg = $msg." Success login ";
           //Successful Insertion in login table  
            $query = "SELECT * FROM `citizen_details_login` WHERE citizen_email = '$email' AND citizen_password = '$pass'";
            $res11 = mysql_query($query);
            if($row1 = mysql_fetch_array($res11))
            {
                $lid = $row1['citizen_logid'];
            }
            else
            {
                $lid = "-1";
            }
        }
        else
        {   
            //insertion failed
            //$msg = $msg." Try again login ";
            $lid = "-1";
        }
    }
    
    $jsonarray = array("Email"=>$lid,"Message"=>$msg);
    header("Content-Type: application/json");
    $jsonoutput = json_encode($jsonarray);
    echo $jsonoutput;
?>
        
   