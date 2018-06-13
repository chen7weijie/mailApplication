<?php
include_once("e_group.php");
$groupName=$_POST['groupName'];
$name="分组2";
//$group=Group::find(1);
$group = Group::where('name', '=', $groupName)->first();//根据组名查出分组
//echo($group->id);
$receivers=Group::find($group->id)->receivers;//根据分组id查出该分组下的消息接收者
echo json_encode($receivers);

?>