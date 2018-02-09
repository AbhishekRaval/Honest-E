<?php
include ("Dd_details.php");
include("AllFunctions.php");
include ("Imagepaths.php");
$con = mysqli_connect($server,$Server_id,$server_pwd,$selectdb);
if($con)
{
	$image = $_POST["image"];
	$name = $_POST["name"];
        $cid = $_POST["compid"];    

	$sql = "INSERT INTO `complaint_images`(`cimgid`, `cimg_name`, `cimg_compid`) VALUES ('null','$name','$cid')";
	$cpath = complainimgpath($name);
	if (mysqli_query($con,$sql)) {
		file_put_contents($cpath,base64_decode($image));
		echo json_encode(array('response'=>'image uploaded successfully'));
	}

	else
	{
	echo json_encode(array('response'=>'image upload failed'));
        delete_complaint($cid);        
	}
}
else
{
echo json_encode(array('response'=>'image upload failed'));	
}
mysqli_close($con);
?>