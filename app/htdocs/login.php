<?php

include_once("Capsule.php");
//use Illuminate\Database\Capsule\Manager as Capsule;	
include_once("user.php");
//$users=User::whereRaw('id>?',[0])->get();
$inputname=$_POST['name'];
$inputpassword=$_POST['password'];
//$users = Capsule::table('user')->where('name', '=',$inputname)->where('password','=',$inputpassword)->get();
$users=User::where(['name'=>$inputname,'password'=>$inputpassword])
->first();
//var_dump($inputname);
//echo json_encode($users);
if(!empty($users)){
	echo "成功登陆";
}
else{
	echo "登陆失败";
}

?>