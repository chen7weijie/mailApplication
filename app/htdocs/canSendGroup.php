<?php
include_once("Capsule.php");
include_once("e_group.php");	
$groups=Group::where('status', '=', 1)->get();
echo json_encode($groups);
?>