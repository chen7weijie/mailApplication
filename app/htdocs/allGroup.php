<?php
include_once("Capsule.php");
include_once("e_group.php");	
$groups=Group::all();
echo json_encode($groups);
?>