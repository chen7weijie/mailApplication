<?php

include_once("Capsule.php");	
use Illuminate\Database\Capsule\Manager as Capsule;
$users = Capsule::table('e_user')->where('id', '>', 0)->get();
echo json_encode($users);
?>