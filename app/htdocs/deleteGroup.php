<?php
include_once("e_group.php");
$name=$_POST['groupName'];
$affectRow=Group::where('name','=',$name)->delete();
echo "success";
?>