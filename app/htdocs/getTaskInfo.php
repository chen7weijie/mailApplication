<?php
include_once("TaskInfo.php");
$getId=$_POST['taskId'];
$id=(int)$getId;

$task=TaskInfo::where('id','=',$id)->first();
echo json_encode($task);

?>