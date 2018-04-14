<?php

include_once("Capsule.php");	
//use Illuminate\Database\Capsule\Manager as Capsule;
use  Illuminate\Database\Eloquent\Model  as Eloquent;
class User extends Eloquent
{
	protected $table='user';
}
$user2=User::all();
echo json_encode($user2);
?>
