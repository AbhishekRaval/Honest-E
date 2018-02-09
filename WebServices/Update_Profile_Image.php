<?php
include ("Dd_details.php");
include("AllFunctions.php");
include ("Imagepaths.php");
$con = mysqli_connect($server,$Server_id,$server_pwd,$selectdb);
if($con)
{
	$image = $_POST["image"];
	$name = $_POST["name"];
        $cid = $_POST["rid"];    
        
        $imgn1 = getProfileImageNamefromrid($cid);
        $cpath1 = profileimgpath($imgn1);
        unlink($cpath1);
	$sql = "UPDATE `user_images` SET `user_imgname`='$name' WHERE user_rid = '$cid'";
	$cpath = profileimgpath($name);
	if (mysqli_query($con,$sql)) {
		file_put_contents($cpath,base64_decode($image));
		echo json_encode(array('response'=>'image uploaded successfully'));
	}

	else
	{
	echo json_encode(array('response'=>'image upload failed'));
    	}
}
else
{
echo json_encode(array('response'=>'image upload failed'));	
}
mysqli_close($con);
?>