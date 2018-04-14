<?php

include_once("Capsule.php");	
use Illuminate\Database\Capsule\Manager as Capsule;
$users = Capsule::table('user')->where('id', '=', 1)->get();
echo json_encode($users);
?>