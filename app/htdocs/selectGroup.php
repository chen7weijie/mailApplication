<?php
include_once("e_group.php");
$id=1;
$group = Group::where('id', '=', $id)->first();
echo json_encode($group);
?>