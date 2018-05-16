<?php
include_once("e_information.php");
$infos=Information::all();
echo json_encode($infos);
?>