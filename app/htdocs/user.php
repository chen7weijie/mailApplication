<?php

include_once("Capsule.php");	
//use Illuminate\Database\Capsule\Manager as Capsule;
use  Illuminate\Database\Eloquent\Model  as Eloquent;
class User extends Eloquent
{
	protected $table='e_user';
}
//$user2=User::all();
//echo json_encode($user2);
//$users=User::where('id','>',1)->get();


//$users=User::whereRaw('id>?',[0])->get();
//echo json_encode($users);
//var_dump($user->name);
?>
