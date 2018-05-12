<?php
include_once("Capsule.php");
include_once("e_receiver.php");	
$receivers=Receiver::all();
echo json_encode($receivers);
?>