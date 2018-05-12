<?php

include_once("Capsule.php");
include_once("e_receiver.php");	
//use Illuminate\Database\Capsule\Manager as Capsule;
use  Illuminate\Database\Eloquent\Model  as Eloquent;
class Group extends Eloquent
{
	protected $table='e_group';

	public function receivers()
    {
        return $this->hasMany('Receiver');
    }
}
//$number=2;
//$receivers=Group::find($number)->receivers;
//echo json_encode($receivers);
//$user2=User::all();
//echo json_encode($user2);
//$users=User::where('id','>',1)->get();


//$users=User::whereRaw('id>?',[0])->get();
//echo json_encode($users);
//var_dump($user->name);
?>