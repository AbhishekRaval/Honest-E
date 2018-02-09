<?php

function complainimgpath($file)
{
    $upload_path = "./Images/complaintimages/$file.jpg";
    return $upload_path;
}

function profileimgpath($file)
{
    $upload_path = "./Images/profileimages/$file.jpg";
    return $upload_path;
}

?>

